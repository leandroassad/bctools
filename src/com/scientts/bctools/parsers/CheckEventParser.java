package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckEventParser extends AbstractCardExpressionsParser {

	public static final Pattern checkEventInputPattern = Pattern.compile("(\\d)(\\d)(\\d)(\\d)");
	public static final Pattern checkEventOutputPattern = Pattern.compile("(\\d)(.*)");

	protected Map<String, String> keymap = new HashMap<String, String>() {{
			put("00", "OK");
			put("04", "F1");
			put("05", "F2");
			put("06", "F3");
			put("07", "F4");
			put("08", "BACKSPACE");
			put("13", "CANCEL");
	}};
	
	public CheckEventParser(String commandName, int expression) {
		super(commandName, expression);
	}
	
	public String parseInputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();
		
		if (expressionString.length() != 4) {
			builder.append("<h2>Entrada Inválida</h2>");			
		}
		else {
			Matcher m = checkEventInputPattern.matcher(expressionString);
			if (m.matches()) {
				appendStartTable();
				builder.append("<tr><th>Descrição</th><th>Valor</th></tr>");
				
				char c = m.group(1).charAt(0);
				builder.append(TR_START);
				builder.append("<tr><td>Evento de pressionamento de tecla</td>");
				builder.append("<td>").append(c);
				if (c == '0') builder.append(" - Ignora Teclas");
				else if (c == '1') builder.append(" - Verifica Pressionamento de Teclas");
				else builder.append(" - Valor Inválido");
				builder.append("</td>");
				builder.append(TR_END);
				
				c = m.group(2).charAt(0);
				builder.append(TR_START);
				builder.append("<tr><td>Evento de passagem de cartão magnético</td>");
				builder.append("<td>").append(c);
				if (c == '0') builder.append(" - Ignora Cartão Magnético");
				else if (c == '1') builder.append(" - Verifica Passagem de Cartão Magnético");
				else builder.append(" - Valor Inválido");
				builder.append("</td>");
				builder.append(TR_END);
		
				c = m.group(3).charAt(0);
				builder.append(TR_START);
				builder.append("<tr><td>Evento de inserção/remoção de cartão com chip</td>");				
				builder.append("<td>").append(c);
				if (c == '0') builder.append(" - Ignora Cartão Com Chip");
				else if (c == '1') builder.append(" - Verifica Inserção de Cartão Com Chip");
				else if (c == '2') builder.append(" - Verifica Remoção de Cartão Com Chip");
				else builder.append(" - Valor Inválido");
				builder.append("</td>");
				builder.append(TR_END);
				
				c = m.group(4).charAt(0);
				builder.append(TR_START);
				builder.append("<tr><td>Evento de aproximação de cartão com om chip sem contato</td>");				
				builder.append("<td>").append(c);
				if (c == '0') builder.append(" - Não Ativa Antena<");
				else if (c == '1') builder.append(" - Ativa Antena e Verifica Passagem de Cartão Sem Contato");
				else builder.append("<td>Valor Inválido");
				builder.append("</td>");
				builder.append(TR_END);
				appendEndTable();
			}
		}
		
		return builder.toString();
	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();

		Matcher m = checkEventOutputPattern.matcher(expressionString);
		if (m.matches()) {
			appendStartTable();

			char c = m.group(1).charAt(0);
			switch (c) {
			case '0':
				builder.append(TR_START);
				builder.append("<td colspan='2'>Evento de Tecla Pressionada</td>");
				builder.append(TR_END);
				
				builder.append("<tr><th>Descrição</th><th>Valor</th></tr>");
				builder.append(TR_START);
				builder.append("<td>Tecla</td>");
				String key = m.group(2);
				builder.append(keymap.get(key));
				builder.append(TR_END);
				break;
			case '1':
				builder.append(TR_START);
				builder.append("<td colspan='2'>Evento de Cartão Magnético</td>");
				builder.append(TR_END);
				
				builder.append("<tr><th>Descrição</th><th>Valor</th></tr>");
				String cardData = m.group(2);
				int track1Len = Integer.parseInt(cardData.substring(0, 2));
				int track2Len = Integer.parseInt(cardData.substring(78, 82));
				int track3Len = Integer.parseInt(cardData.substring(117, 120));
				String track1 = cardData.substring(2, 75);
				String track2 = cardData.substring(80, 117);
				String track3 = cardData.substring(120, 223);
				
				builder.append(TR_START).append("<td>Tamanho da Trilha 1</td>").append("<td>").append(track1Len).append("</td>").append(TR_END);
				builder.append(TR_START).append("<td>Trilha 1</td>").append("<td>").append(track1).append("</td>").append(TR_END);
				builder.append(TR_START).append("<td>Tamanho da Trilha 2</td>").append("<td>").append(track2Len).append("</td>").append(TR_END);
				builder.append(TR_START).append("<td>Trilha 2</td>").append(track2).append("<td>").append("</td>").append(TR_END);
				builder.append(TR_START).append("<td>Tamanho da Trilha 3</td>").append("<td>").append(track3Len).append("</td>").append(TR_END);
				builder.append(TR_START).append("<td>Trilha 3</td>").append(track3).append("<td>").append("</td>").append(TR_END);
				break;
			case '2':
				builder.append(TR_START);
				builder.append("<td colspan='2'>Evento de Cartão Com Chip</td>");
				builder.append(TR_END);
				
				c = m.group(2).charAt(0);
				builder.append("<tr><th>Descrição</th><th>Valor</th></tr>");
				builder.append("<td>Evento</td><td>").append(c).append(c == '0' ? " - Cartão Com Chip Ausente" : " - Cartao Com Chip Presente").append("</td>");
				break;
			case '3':
				builder.append(TR_START);
				builder.append("<td colspan='2'>Evento de Cartão Sem Contato</td>");
				builder.append(TR_END);
				
				c = m.group(2).charAt(0);
				builder.append("<tr><th>Descrição</th><th>Valor</th></tr>");
				builder.append(TR_START);
				builder.append("<td>Evento</td><td>").append(c).append(c == '0' ? " - Cartão Com Sem Contato Não Foi Detectado em 2 Minutos" : " - Cartao Com Chip Sem Contato Foi Detectado").append("</td>");
				builder.append(TR_END);
				break;
			default:
			}
			appendEndTable();
		}

		return builder.toString();
	}



}
