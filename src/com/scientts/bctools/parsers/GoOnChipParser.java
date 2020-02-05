package com.scientts.bctools.parsers;

public class GoOnChipParser extends AbstractCardExpressionsParser {

	public GoOnChipParser(String commandName, int expression) {
		super(commandName, expression);		
	}

	public String parseInputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> GonOnChip Input <<<<\n");
		builder.append("Não Implementado Ainda\n");
		
		return builder.toString();
	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> GonOnChip Output <<<<\n");
		builder.append("Não Implementado Ainda\n");
		
		return builder.toString();
	}
}
