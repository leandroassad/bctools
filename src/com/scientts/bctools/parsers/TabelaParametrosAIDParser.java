package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabelaParametrosAIDParser extends AbstractCommandParser {
	public static final Pattern paramTablePattern = Pattern.compile("(\\d{3})(\\d)(\\d{2})(\\d{2})(\\d{2})([\\w\\s]{32})(\\d{2})([\\w\\s]{16})(\\d{2})(\\w{4})(\\w{4})(\\w{4})(\\d{3})(\\d{3})(\\d)([\\w\\s]{15})(\\d{4})([\\w\\s]{8})(\\w{6})(\\w{10})(\\d{2})(\\w{10})(\\w{10})(\\w{10})(\\w{8})(\\w)(\\w)(\\d)(\\w{8})(\\w{8})(\\w{8})(\\w{4})(\\d)(\\w{40})(\\w{40})(\\w{8})(.*)");
	public static final Pattern tacClessPattern = Pattern.compile("(\\w{10})(\\w{10})(\\w{10})");

	protected Map<String, String> redeMap = new HashMap<String, String>() {{
		put("01", "Rede Amex");
		put("02", "Redecard");
		put("03", "Cielo");
	}};
	
	protected Map<String, String> terminalTypeMap = new HashMap<String, String>() {{
		put("21", "online");
		put("22", "offline com capacidade online");
		put("23", "somente offline");
		put("24", "online, não atendido");
		put("25", "offline com capacidade online, não atendido");
		put("26", "somente offline, não atendido");
	}};
		
	protected Map<String, String> clessMap = new HashMap<String, String>() {{
		put("0", "Não suporta");
		put("1", "Suporta VISA MSD");
		put("2", "Suporta VISA qVSDC");
		put("3", "Suporta MasterCard PayPass Mag Stripe");
		put("4", "Suporta MasterCard PayPass M/Chip");
		put("5", "Suporta Amex Expresspay Magstripe Mode");
		put("6", "Suporta Amex Expresspay EMV Mode");
	}};
	
	public TabelaParametrosAIDParser(String commandName) {
		super(commandName);
	}

	@Override
	public String parse(String expressionString) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(">>>> Tabela de Parametros x AID <<<<\n");
		if (expressionString.length() < 284) {
			builder.append("Entrada Com Tamanho Inválido");	
		}
		else {
			Matcher m = paramTablePattern.matcher(expressionString);
			if (m.matches()) {
				builder.append("Entrada: ").append(expressionString).append("\n\n");
				builder.append("Tamanho do registro: [").append(m.group(1)).append("]\n");
				builder.append("Identificação de Tabela de Parâmetros (Deve ser 1): [").append(m.group(2)).append("]\n");
				builder.append("Identificador da Rede Adquirente: [").append(m.group(3)).append("] - ").append(redeMap.get(m.group(3))).append("\n");
				builder.append("Índice do Registro: [").append(m.group(4)).append("]\n");
				builder.append("Tamanho do AID, em bytes (05 a 16): [").append(m.group(5)).append("]\n");
				builder.append("AID Application Identifier - Tag 9F06: [").append(m.group(6)).append("]\n");
				builder.append("Tipo da Aplicação: [").append(m.group(7)).append("] - ").append(m.group(7).equals("01")?"CREDITO" : "DEBITO").append("\n");
				builder.append("Etiqueta da Aplicação: [").append(m.group(8)).append("]\n");
				builder.append("Padrão da Aplicação: [").append(m.group(9)).append("] - 03 = EMV\n");
				builder.append("Application Version Number (Terminal) - opção #1 - Tag 9F09: [").append(m.group(10)).append("]\n");
				builder.append("Application Version Number (Terminal) - opção #2 - Tag 9F09: [").append(m.group(11)).append("]\n");
				builder.append("Application Version Number (Terminal) - opção #3 - Tag 9F09: [").append(m.group(12)).append("]\n");
				builder.append("Terminal Country Code - Tag 9F1A: [").append(m.group(13)).append("]\n");
				builder.append("Transaction Currency Code - Tag 5F2A: [").append(m.group(14)).append("]\n");
				builder.append("Transaction Currency Exponent - Tag 5F36: [").append(m.group(15)).append("]\n");
				builder.append("Merchant Identifier - Tag 9F16: [").append(m.group(16)).append("]\n");
				builder.append("Merchant Category Code - Tag 9F15: [").append(m.group(17)).append("]\n");
				builder.append("Terminal Identification - Tag 9F1C: [").append(m.group(18)).append("]\n");
				builder.append("Terminal Capabilities - Tag 9F33: [").append(m.group(19)).append("]\n");
				builder.append("Additional Terminal Capabilities - Tag 9F40: [").append(m.group(20)).append("]\n");
				builder.append("Terminal Type - Tag 9F35: [").append(m.group(21)).append("] - ").append(terminalTypeMap.get(m.group(21))).append("\n");
				builder.append("Terminal Action Code – Default: [").append(m.group(22)).append("]\n");
				builder.append("Terminal Action Code – Denial: [").append(m.group(23)).append("]\n");
				builder.append("Terminal Action Code – Online: [").append(m.group(24)).append("]\n");
				builder.append("Terminal Floor Limit - Tag 9F1B: [").append(m.group(25)).append("]\n");
				builder.append("Transaction Category Code- Tag 9F53: [").append(m.group(26)).append("]\n");
				builder.append("Indica a ação para cartão com chip sem contato se o valor da transação estiver zerado: [").append(m.group(27)).append("] - ").append(m.group(27).equals("0")?"Não Suporta":"Suporta, porém somente Online").append("\n");
				builder.append("Capacidade de tratamento do terminal para o referido AID, caso este seja localizado em um cartão com chip sem contato: [").append(m.group(28)).append("] - ").append(clessMap.get(m.group(28))).append("\n");
				builder.append("Terminal/Reader Contactless Transaction Limit: [").append(m.group(29)).append("]\n");
				builder.append("Terminal/Reader Contactless Floor Limit: [").append(m.group(30)).append("]\n");
				builder.append("Terminal/Reader CVM Required Limit: [").append(m.group(31)).append("]\n");
				builder.append("PayPass Mag Stripe Application Version Number (Terminal) - Tag 9F6D: [").append(m.group(32)).append("]\n");
				builder.append("Indica a forma de seleção da aplicação do cartão sem contato: [").append(m.group(33)).append("] - ").append(m.group(33).equals("0")?"A aplicação é selecionada automaticamente pela prioridade":"A aplicação é selecionada automaticamente pela prioridade").append("\n");
				builder.append("Default Transaction Certificate Data Object List (TDOL): [").append(m.group(34)).append("]\n");
				builder.append("Default Dynamic Data Authentication Data Object List (DDOL): [").append(m.group(35)).append("]\n");
				builder.append("Authorization Response Codes para transações offline: [").append(m.group(36)).append("]\n");
				if (expressionString.length() > 284) {
					Matcher tacM = tacClessPattern.matcher(m.group(37));
					if (tacM.matches()) {
						builder.append("Terminal Action Code – Default (para cartões sem contato): [").append(tacM.group(1)).append("]\n");
						builder.append("Terminal Action Code – Denial (para cartões sem contato): [").append(tacM.group(2)).append("]\n");
						builder.append("Terminal Action Code – Online (para cartões sem contato): [").append(tacM.group(3)).append("]\n");
					}
				}
			}
			else {
				builder.append("Entrada No Formato Inválido");
			}
		}

		return builder.toString();
	}

}
