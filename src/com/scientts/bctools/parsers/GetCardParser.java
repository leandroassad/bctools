package com.scientts.bctools.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCardParser extends AbstractCardExpressionsParser {

	public static final Pattern getcardInputPattern = Pattern.compile("(\\d{2})(\\d{2})(\\d{12})(\\d{6})(\\d{6})(\\d{10})(\\d{2})(.*)");
	public static final Pattern getcardOutputPattern = Pattern.compile("(\\d{2})(\\d)(\\d{2})(\\d{2})(\\d{2})(\\d{2})([\\w\\s\\=\\?]{76})(\\d{2})([\\w\\s\\=\\?]{37})(\\d{3})([\\w\\s]{104})(\\d{2})([\\w\\s]{19})(\\d{2})([\\w\\s]{16})(\\d{3})([\\w\\s\\/]{26})(\\d{6})(\\d{2})([\\w\\s]{19})(\\d{8})(\\d{3})(\\d{3})(.*)");
	
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
				builder.append("Entrada: ").append(expressionString).append('\n');
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
		
		builder.append(">>>> GetCard Input <<<<\n");
		if (expressionString.length() < 342) {
			builder.append("Entrada Com Tamanho Inv�lido");	
		}
		else {
			Matcher m = getcardInputPattern.matcher(expressionString);
			if (m.matches()) {
				builder.append("Em Implementa��o");
			}
			else {
				builder.append("Entrada No Formato Inv�lido");
			}
		}

		return builder.toString();
	}


}
