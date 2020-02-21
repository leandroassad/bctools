package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabelaCertificadosParser extends AbstractCommandParser {

	public static final Pattern certTablePattern = Pattern.compile("(\\d{3})(\\d)(\\d{2})(\\d{2})([0-9a-fA-F]{10})([0-9a-fA-F]{2})([0-9a-fA-F]{2})(\\d{1})([0-9a-fA-F]{6})(\\d{3})([0-9a-fA-F]{496})(\\d)([0-9a-fA-F]{40})(\\d{42})");
	
	protected Map<String, String> redeMap = new HashMap<String, String>() {{
		put("01", "Rede Amex");
		put("02", "Redecard");
		put("03", "Cielo");
	}};
	
	public TabelaCertificadosParser(String commandName) {
		super(commandName);
	}

	@Override
	public void parseExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		if (expressionString.length() < 611) {
			appendString("<h2>Entrada Com Tamanho Inválido</h2>");	
		}
		else {
			Matcher m = certTablePattern.matcher(expressionString);
			if (m.matches()) {
				appendString("Entrada: ").append(expressionString).append("\n\n");
				appendString("Tamanho do registro: [").append(m.group(1)).append("]\n");
				appendString("Identificação da Tabela de Chaves Públicas (fixo 2): [").append(m.group(2)).append("]\n");
				appendString("Identificador da rede adquirente: [").append(m.group(1)).append("] - ").append(redeMap.get(m.group(3))).append("\n");
				appendString("Índice do registro na tabela: [").append(m.group(4)).append("]\n");
				appendString("RID - Registered Application Provider Identifier: [").append(m.group(5)).append("]\n");
				appendString("Certification Authority Public Key Index: [").append(m.group(6)).append("]\n");
				appendString("Reservado para uso futuro - preenchido com zeros (00): [").append(m.group(7)).append("]\n");
				appendString("Tamanho em bytes do Certification Authority Public Key Exponent (1 ou 3): [").append(m.group(8)).append("]\n");
				appendString("Certification Authority Public Key Exponent (alinhado à esquerda): [").append(m.group(9)).append("]\n");
				appendString("Tamanho em bytes do Certification Authority Public Key Modulus (até 248): [").append(m.group(10)).append("]\n");
				appendString("Certification Authority Public Key Modulus (alinhado à esquerda): [").append(m.group(11)).append("]\n");
				appendString("Status do Check Sum (Hash SHA-1): [").append(m.group(12)).append("] - ").append(m.group(12).charAt(0) == '0' ? "Não utilizado" : "Presente").append("\n");
				appendString("Certification Authority Public Key Check Sum (Hash SHA-1): [").append(m.group(13)).append("]\n");
				appendString("Reservado para uso futuro - preencher com zeros: [").append(m.group(14)).append("]\n");
			}
			else {
				appendString("<h2>Entrada No Formato Inválido</h2>");
			}
		}		
	}

}
