package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.Map;

public class CheckEventParser extends AbstractCardExpressionsParser {

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
		
		builder.append(">>>> CheckEvent Input <<<<\n");
		if (expressionString.length() < 4) {
			builder.append("Entrada Inválida");			
		}
		else {		
			builder.append("Entrada: " + expressionString)
			.append("\n\n");
			char c = expressionString.charAt(0);
			if (c == '0') builder.append("0 = Ignora Teclas\n");
			else if (c == '1') builder.append("1 = Verifica Pressionamento de Teclas\n");
			else builder.append(c).append(" - Valor Inválido");
			
			c = expressionString.charAt(1);
			if (c == '0') builder.append("0 = Ignora Cartão Magnético\n");
			else if (c == '1') builder.append("1 = Verifica Passagem de Cartão Magnético\n");
			else builder.append(c).append(" - Valor Inválido");
	
			c = expressionString.charAt(2);
			if (c == '0') builder.append("0 = Ignora Cartão Com Chip\n");
			else if (c == '1') builder.append("1 = Verifica Inserção de Cartão Com Chip\n");
			else if (c == '2') builder.append("1 = Verifica Remoção de Cartão Com Chip\n");
			else builder.append(c).append(" - Valor Inválido");
			
			c = expressionString.charAt(3);
			if (c == '0') builder.append("0 = Não Ativa Antena\n");
			else if (c == '1') builder.append("1 = Ativa Antena e Verifica Passagem de Cartão Sem Contato\n");
			else builder.append(c).append(" - Valor Inválido");
		}
		
		return builder.toString();
	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(">>>> CheckEvent Output <<<<\n");
		builder.append("Entrada: " + expressionString)
		.append("\n\n");
		char c = expressionString.charAt(0);
		switch (c) {
		case '0':
			builder.append("Evento de Tecla Pressionada: ");
			String key = expressionString.substring(1, 3);
			builder.append(keymap.get(key));
			break;
		case '1':
			builder.append("Evento de Cartão Magnético\n");
			int track1Len = Integer.parseInt(expressionString.substring(1, 3));
			int track2Len = Integer.parseInt(expressionString.substring(79, 81));
			int track3Len = Integer.parseInt(expressionString.substring(118, 121));
			String track1 = expressionString.substring(3, 76);
			String track2 = expressionString.substring(81, 118);
			String track3 = expressionString.substring(121, 224);
			
			builder.append("Tamanho da Trilha 1: ").append(track1Len).append("\n");
			builder.append("Trilha 1: [").append(track1).append("]\n");
			builder.append("Tamanho da Trilha 2: ").append(track2Len).append("\n");
			builder.append("Trilha 2: [").append(track2).append("]\n");
			builder.append("Tamanho da Trilha 3: ").append(track3Len).append("\n");
			builder.append("Trilha 3: [").append(track3).append("]\n");
			break;
		case '2':
			builder.append("Evento de Cartão Com Chip\n");
			c = expressionString.charAt(1);
			builder.append(c == '0' ? "Cartão Com Chip Ausente" : "Cartao Com Chip Presente");
			break;
		case '3':
			builder.append("Evento de Cartão Sem Contato\n");
			c = expressionString.charAt(1);
			builder.append(c == '0' ? "Cartão Com Sem Contato Não Foi Detectado em 2 Minutos" : "Cartao Com Chip Sem Contato Foi Detectado");
			break;
		default:
		}

		return builder.toString();
	}



}
