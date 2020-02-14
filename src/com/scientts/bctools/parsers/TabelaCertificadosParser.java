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
	public String parse(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> Tabela de Certificados <<<<\n");
		if (expressionString.length() < 611) {
			builder.append("Entrada Com Tamanho Inválido");	
		}
		else {
			Matcher m = certTablePattern.matcher(expressionString);
			if (m.matches()) {
				builder.append("Entrada: ").append(expressionString).append("\n\n");
				builder.append("Tamanho do registro: [").append(m.group(1)).append("]\n");
				builder.append("Identificação da Tabela de Chaves Públicas (fixo 2): [").append(m.group(2)).append("]\n");
				builder.append("Identificador da rede adquirente: [").append(m.group(1)).append("] - ").append(redeMap.get(m.group(3))).append("\n");
				builder.append("Índice do registro na tabela: [").append(m.group(4)).append("]\n");
				builder.append("RID - Registered Application Provider Identifier: [").append(m.group(5)).append("]\n");
				builder.append("Certification Authority Public Key Index: [").append(m.group(6)).append("]\n");
				builder.append("Reservado para uso futuro - preenchido com zeros (00): [").append(m.group(7)).append("]\n");
				builder.append("Tamanho em bytes do Certification Authority Public Key Exponent (1 ou 3): [").append(m.group(8)).append("]\n");
				builder.append("Certification Authority Public Key Exponent (alinhado à esquerda): [").append(m.group(9)).append("]\n");
				builder.append("Tamanho em bytes do Certification Authority Public Key Modulus (até 248): [").append(m.group(10)).append("]\n");
				builder.append("Certification Authority Public Key Modulus (alinhado à esquerda): [").append(m.group(11)).append("]\n");
				builder.append("Status do Check Sum (Hash SHA-1): [").append(m.group(12)).append("] - ").append(m.group(12).charAt(0) == '0' ? "Não utilizado" : "Presente").append("\n");
				builder.append("Certification Authority Public Key Check Sum (Hash SHA-1): [").append(m.group(13)).append("]\n");
				builder.append("Reservado para uso futuro - preencher com zeros: [").append(m.group(14)).append("]\n");
			}
			else {
				builder.append("Entrada No Formato Inválido");
			}
		}
		
		return builder.toString();

	}

}
