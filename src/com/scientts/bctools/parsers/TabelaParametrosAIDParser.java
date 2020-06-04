package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabelaParametrosAIDParser extends AbstractCommandParser {
	public static final Pattern paramTablePattern = Pattern.compile("(\\d{3})(\\d)(\\d{2})(\\d{2})(\\d{2})([\\w\\s]{32})(\\d{2})([\\w\\s]{16})(\\d{2})(\\w{4})(\\w{4})(\\w{4})(\\d{3})(\\d{3})(\\d)([\\w\\s]{15})(\\d{4})([\\w\\s]{8})(\\w{6})(\\w{10})(\\d{2})(\\w{10})(\\w{10})(\\w{10})(\\w{8})([\\w\\s])([\\w\\s])([\\w\\s])([\\w\\s]{8})([\\w\\s]{8})([\\w\\s]{8})([\\w\\s]{4})([\\w\\s])([\\w\\s]{40})([\\w\\s]{40})([\\w\\s]{8})(.*)");
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
	public void parseExpression(String expressionString) {
		if (expressionString.length() < 284) {
			appendString("<h2>Entrada Com Tamanho Inválido</h2><br/>");	
		}
		else {
			Matcher m = paramTablePattern.matcher(expressionString);
			if (m.matches()) {
				appendStartTable();
				appendString("<tr><th>Descrição</th><th>Valor</th></tr>");
				appendString("<tr><td>Tamanho do registro</td>").append("<td>").append(m.group(1)).append("</td></tr>");
				appendString("<tr><td>Identificação de Tabela de Parâmetros (Deve ser 1)</td>").append("<td>").append(m.group(2)).append("</td></tr>");
				appendString("<tr><td>Identificador da Rede Adquirente</td>").append("<td>").append(m.group(3)).append(" - ").append(redeMap.get(m.group(3))).append("</td></tr>");
				appendString("<tr><td>Índice do Registro</td>").append("<td>").append(m.group(4)).append("</td></tr>");
				appendString("<tr><td>Tamanho do AID, em bytes (05 a 16)</td>").append("<td>").append(m.group(5)).append("</td></tr>");
				appendString("<tr><td>AID Application Identifier - Tag 9F06</td>").append("<td>").append(m.group(6)).append("</td></tr>");
				appendString("<tr><td>Tipo da Aplicação</td>").append("<td>").append(m.group(7)).append(" - ").append(m.group(7).equals("01")?"CREDITO" : "DEBITO").append("</td></tr>");
				appendString("<tr><td>Etiqueta da Aplicação</td>").append("<td>").append(m.group(8)).append("</td></tr>");
				appendString("<tr><td>Padrão da Aplicação</td>").append("<td>").append(m.group(9)).append(" - 03 = EMV</td></tr>");
				appendString("<tr><td>Application Version Number (Terminal) - opção #1 - Tag 9F09</td>").append("<td>").append(m.group(10)).append("</td></tr>");
				appendString("<tr><td>Application Version Number (Terminal) - opção #2 - Tag 9F09</td>").append("<td>").append(m.group(11)).append("</td></tr>");
				appendString("<tr><td>Application Version Number (Terminal) - opção #3 - Tag 9F09</td>").append("<td>").append(m.group(12)).append("</td></tr>");
				appendString("<tr><td>Terminal Country Code - Tag 9F1A</td>").append("<td>").append(m.group(13)).append("</td></tr>");
				appendString("<tr><td>Transaction Currency Code - Tag 5F2A</td>").append("<td>").append(m.group(14)).append("</td></tr>");
				appendString("<tr><td>Transaction Currency Exponent - Tag 5F36</td>").append("<td>").append(m.group(15)).append("</td></tr>");
				appendString("<tr><td>Merchant Identifier - Tag 9F16</td>").append("<td>").append(m.group(16)).append("</td></tr>");
				appendString("<tr><td>Merchant Category Code - Tag 9F15</td>").append("<td>").append(m.group(17)).append("</td></tr>");
				appendString("<tr><td>Terminal Identification - Tag 9F1C</td>").append("<td>").append(m.group(18)).append("</td></tr>");
				appendString("<tr><td>Terminal Capabilities - Tag 9F33</td>").append("<td>").append(m.group(19)).append("</td></tr>");
				appendString("<tr><td>Additional Terminal Capabilities - Tag 9F40</td>").append("<td>").append(m.group(20)).append("</td></tr>");
				appendString("<tr><td>Terminal Type - Tag 9F35</td>").append("<td>").append(m.group(21)).append(" - ").append(terminalTypeMap.get(m.group(21))).append("</td></tr>");
				appendString("<tr><td>Terminal Action Code – Default</td>").append("<td>").append(m.group(22)).append("</td></tr>");
				appendString("<tr><td>Terminal Action Code – Denial</td>").append("<td>").append(m.group(23)).append("</td></tr>");
				appendString("<tr><td>Terminal Action Code – Online</td>").append("<td>").append(m.group(24)).append("</td></tr>");
				appendString("<tr><td>Terminal Floor Limit - Tag 9F1B</td>").append("<td>").append(m.group(25)).append("</td></tr>");
				appendString("<tr><td>Transaction Category Code- Tag 9F53</td>").append("<td>").append(m.group(26)).append("</td></tr>");
				appendString("<tr><td>Indica a ação para cartão com chip sem contato se o valor da transação estiver zerado</td>").append("<td>").append(m.group(27)).append(" - ").append(m.group(27).equals("0")?"Não Suporta":"Suporta, porém somente Online").append("</td></tr>");
				appendString("<tr><td>Capacidade de tratamento do terminal para o referido AID, caso este seja localizado em um cartão com chip sem contato</td>").append("<td>").append(m.group(28)).append(" - ").append(clessMap.get(m.group(28))).append("</td></tr>");
				appendString("<tr><td>Terminal/Reader Contactless Transaction Limit</td>").append("<td>").append(m.group(29)).append("</td></tr>");
				appendString("<tr><td>Terminal/Reader Contactless Floor Limit</td>").append("<td>").append(m.group(30)).append("</td></tr>");
				appendString("<tr><td>Terminal/Reader CVM Required Limit</td>").append("<td>").append(m.group(31)).append("</td></tr>");
				appendString("<tr><td>PayPass Mag Stripe Application Version Number (Terminal) - Tag 9F6D</td>").append("<td>").append(m.group(32)).append("</td></tr>");
				appendString("<tr><td>Indica a forma de seleção da aplicação do cartão sem contato</td>").append("<td>").append(m.group(33)).append(" - ").append(m.group(33).equals("0")?"A aplicação é selecionada automaticamente pela prioridade":"A aplicação é selecionada automaticamente pela prioridade").append("</td></tr>");
				appendString("<tr><td>Default Transaction Certificate Data Object List (TDOL)</td>").append("<td>").append(m.group(34)).append("</td></tr>");
				appendString("<tr><td>Default Dynamic Data Authentication Data Object List (DDOL)</td>").append("<td>").append(m.group(35)).append("</td></tr>");
				appendString("<tr><td>Authorization Response Codes para transações offline</td>").append("<td>").append(m.group(36)).append("</td></tr>");
				if (expressionString.length() > 284) {
					Matcher tacM = tacClessPattern.matcher(m.group(37));
					if (tacM.matches()) {
						appendString("Terminal Action Code – Default (para cartões sem contato)</td>").append("<td>").append(tacM.group(1)).append("</td></tr>");
						appendString("Terminal Action Code – Denial (para cartões sem contato)</td>").append("<td>").append(tacM.group(2)).append("</td></tr>");
						appendString("Terminal Action Code – Online (para cartões sem contato)</td>").append("<td>").append(tacM.group(3)).append("</td></tr>");
					}
				}
				appendEndTable();
			}
			else {
				appendString("<h2>Entrada No Formato Inválido<h2><br/>");
			}
		}
	}

}
