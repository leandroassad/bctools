package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCardParser extends AbstractCardExpressionsParser {

	public static final Pattern getcardInputPattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{12})(\\d{6})(\\d{6})(\\d{10})(\\d{2})(.*)");
	public static final Pattern getcardOutputPattern = Pattern.compile("(\\d{2})(\\d)(\\d{2})(\\d{2})(\\d{2})(\\d{2})(.{76})(\\d{2})(.{37})(\\d{3})(.{104})(\\d{2})([\\w\\s]{19})(\\d{2})(.{16})(\\d{3})(.{26})(\\d{6})(\\d{2})(.{19})(\\d{8})(\\d{3})(\\d{3})(.*)");
	
	protected Map<String, String> tipoCartaoLidoMap = new HashMap<String, String>() {{
		put("00", "Magn�tico");
		put("01", "Moedeiro VISA Cash sobre TIBC v1");
		put("02", "Moedeiro VISA Cash sobre TIBC v3");
		put("03", "EMV com contato");
		put("04", "Easy-Entry sobre TIBC v1");
		put("05", "Chip sem contato simulando tarja");
		put("06", "EMV sem contato");
	}};
	
	public GetCardParser(String commandName, int expression) {
		super(commandName, expression);
	}

	public String parseInputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(">>>> GetCard Input <<<<\n");
		if (expressionString.length() < 41) {
			builder.append("Entrada Com Tamanho Inv�lido");			
		}
		else {
			Matcher m = getcardInputPattern.matcher(expressionString);
			if (m.matches()) {
				builder.append("Entrada: ").append(expressionString).append("\n\n");
				builder.append("Identificador da Rede Adquirente: [").append(m.group(1)).append("] - 00 Abrange todas as redes\n");
				builder.append("Tipo da Aplica��o: [").append(m.group(2)).append("] - Para qualquer aplica��o, usar 99. Para uma lista de espec�fica de aplica��es, usar 00\n");
				builder.append("Valor inicial da transa��o em centavos: [").append(m.group(3)).append("]\n");
				builder.append("Data da transa��o (�AAMMDD�): [").append(m.group(4)).append("]\n");
				builder.append("Hora da transa��o (�HHMMSS�): [").append(m.group(5)).append("]\n");
				builder.append("Time-stamp das tabelas de par�metros, formado por dia, m�s, ano e um n�mero seq�encial (DDMMAAAASS): [").append(m.group(6)).append("]\n");
				builder.append("Quantidade de entradas da lista de Parametros x AID, se tipo de aplica��o desejada for �00�: [").append(m.group(7)).append("]\n");
				
				int nParam = Integer.parseInt(m.group(7));
				if (nParam > 0) {
					String paramList = m.group(8).substring(0, m.group(8).length()-1);
					builder.append("Identificadore de rede + �ndice para Tabela de Par�metros x AID: [").append(paramList).append("]\n");					
				}
				
				builder.append("Habilita interface de cart�o sem contato?: [").append(m.group(8).charAt(m.group(8).length()-1)).append("]\n");
			}
			else {
				builder.append("Entrada No Formato Inv�lido");
			}
		}


		return builder.toString();
	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(">>>> GetCard Output <<<<\n");
		if (expressionString.length() < 342) {
			builder.append("Entrada Com Tamanho Inv�lido");	
		}
		else {
			Matcher m = getcardOutputPattern.matcher(expressionString);
			if (m.matches()) {
				builder.append("Entrada: ").append(expressionString).append("\n\n");
				builder.append("Tipo do Cartao Lido: [").append(m.group(1)).append("] - ").append(tipoCartaoLidoMap.get(m.group(1))).append("\n");
				builder.append("Status da �ltima leitura com cart�o com chip: [").append(m.group(2)).append("]\n");
				builder.append("Tipo de aplica��o selecionada, conforme Tabela de Par�metros x AID (posi��o 043-044): [").append(m.group(3)).append("]\n");
				builder.append("Identificador da rede adquirente, conforme Tabela de Par�metros x AID (posi��o 005-006): [").append(m.group(4)).append("]\n");
				builder.append("�ndice do registro da Tabela de Par�metros x AID (posi��o 007-008): [").append(m.group(5)).append("]\n");
				builder.append("Tamanho da trilha 1: [").append(m.group(6)).append("]\n");
				builder.append("trilha 1: [").append(m.group(7)).append("]\n");
				builder.append("Tamanho da trilha 2: [").append(m.group(8)).append("]\n");
				builder.append("trilha 2: [").append(m.group(9)).append("]\n");
				builder.append("Tamanho da trilha 3: [").append(m.group(10)).append("]\n");
				builder.append("trilha 3: [").append(m.group(11)).append("]\n");
				builder.append("Tamanho do PAN: [").append(m.group(12)).append("]\n");
				builder.append("PAN: [").append(m.group(13)).append("]\n");
				builder.append("Application PAN Sequence Number: [").append(m.group(14)).append("]\n");
				builder.append("Application Label: [").append(m.group(15)).append("]\n");
				builder.append("Service Code: [").append(m.group(16)).append("]\n");
				builder.append("Cardholder Name: [").append(m.group(17)).append("]\n");
				builder.append("Application Expiration Date (YYMMDD): [").append(m.group(18)).append("]\n");
				builder.append("Tamanho do n�mero externo do cart�o: [").append(m.group(19)).append("]\n");
				builder.append("N�mero externo do cart�o: [").append(m.group(20)).append("]\n");
				builder.append("Saldo, para o caso de moedeiro: [").append(m.group(21)).append("]\n");
				builder.append("Issuer Country Code: [").append(m.group(22)).append("]\n");
				int nDadosExtrasLen = Integer.parseInt(m.group(23));
				if (nDadosExtrasLen > 0) {
					builder.append("Tamanho dos dados espec�ficos do adquirente : [").append(m.group(23)).append("]\n");
					builder.append("dados espec�ficos do adquirente : [").append(m.group(24)).append("]\n");
				}				
			}
			else {
				builder.append("Entrada No Formato Inv�lido");
			}
		}

		return builder.toString();
	}
}
