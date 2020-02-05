package com.scientts.bctools.parsers;

public class FinishChipParser extends AbstractCardExpressionsParser {

	public FinishChipParser(String commandName, int expression) {
		super(commandName, expression);
	}

	public String parseInputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> FinishChip Input <<<<\n");
		builder.append("Não Implementado Ainda\n");
		
		return builder.toString();

	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> FinishChip Input <<<<\n");
		builder.append("Não Implementado Ainda\n");
		
		return builder.toString();
	}
}
