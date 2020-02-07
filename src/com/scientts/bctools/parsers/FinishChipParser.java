package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinishChipParser extends AbstractCardExpressionsParser {

	public static final Pattern fcInputPattern = Pattern.compile("(\\d)(\\d)(\\w{2})(\\d{3})(.*)");
	public static final Pattern fcOutputPattern = Pattern.compile("(\\d{2})(\\d)(\\d{2})(\\d{2})(\\d{2})(\\d{2})(.{76})(\\d{2})(.{37})(\\d{3})(.{104})(\\d{2})([\\w\\s]{19})(\\d{2})(.{16})(\\d{3})(.{26})(\\d{6})(\\d{2})(.{19})(\\d{8})(\\d{3})(\\d{3})(.*)");

	protected Map<Character, String> decisaoMap = new HashMap<Character, String>() {{
		put('0', "Transação aprovada");
		put('1', "Transação negada pelo cartão");
		put('2', "Transação negada pelo host");
	}};

	
	public FinishChipParser(String commandName, int expression) {
		super(commandName, expression);
	}

	public String parseInputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> FinishChip Input <<<<\n");
		Matcher m = fcInputPattern.matcher(expressionString);
		if (m.matches()) {
			builder.append("Entrada: ").append(expressionString).append("\n\n");
			builder.append("Status da comunicação com o Host: [").append(m.group(1)).append("] - ").append(m.group(1).charAt(0)== '0' ? "OK, comunicação bem sucedida" : "Não foi possível comunicar com o host").append("\n");
			builder.append("Tipo de Emissor: [").append(m.group(2)).append("] - ").append(m.group(2).charAt(0)== '0' ? "EMV Full Grade" : "EMV Partial Grade").append("\n");
			builder.append("Authorization Response Code: [").append(m.group(3)).append("]\n");
			
			int authOnlineDataLen = Integer.parseInt(m.group(4));
			if (authOnlineDataLen > 0) {
				authOnlineDataLen *= 2;
				builder.append("Dados recebidos no bit55 da ISO8583: [").append(m.group(5).substring(0, authOnlineDataLen)).append("\n");
			}
			else
				builder.append("Sem dados de autenticação online");			
		}
		else {
			builder.append("Entrada No Formato Inválido");
		}

		
		return builder.toString();

	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> FinishChip Input <<<<\n");
		builder.append("Entrada: ").append(expressionString).append("\n\n");
		builder.append("Decisão Tomada: [").append(expressionString.charAt(0)).append("] - ").append(decisaoMap.get(expressionString.charAt(0))).append("\n");
		int nTagLen = Integer.parseInt(expressionString.substring(1, 4));
		if (nTagLen > 0) {
			nTagLen *= 2;
			builder.append("Tags: [").append(expressionString.substring(4, nTagLen+4)).append("]\n");
		}
		else {
			builder.append("Sem tags de resposta\n");
		}
		
		int index = nTagLen + 4;
		int nIssuerScriptsLen = Integer.parseInt(expressionString.substring(index, index+2));
		if (nIssuerScriptsLen > 0) {
			nIssuerScriptsLen *= 2;
			builder.append("Issuer Script Results: [").append(expressionString.substring(index+2, index+2+nIssuerScriptsLen)).append("]\n");
		}
		else {
			builder.append("Sem Issuer Script Results\n");
		}
	
		
		return builder.toString();
	}
}
