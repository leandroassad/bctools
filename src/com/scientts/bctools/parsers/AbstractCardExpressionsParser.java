package com.scientts.bctools.parsers;

public abstract class AbstractCardExpressionsParser extends AbstractCommandParser {

	int expression;
	public AbstractCardExpressionsParser(String commandName, int expression) {
		super(commandName);
		this.expression = expression;
	}
	
	@Override
	public String parse(String expressionString) {
		StringBuilder result = new StringBuilder();
		
		switch (expression) {
		case INPUT_EXPRESSION:
			result.append(parseInputExpression(expressionString));
			break;
		case OUTPUT_EXPRESSION:
			result.append(parseOutputExpression(expressionString));
			break;
		default:
			result.append("ERRO INTERNO!");
		}
		return result.toString();
	}
	
	public abstract String parseInputExpression(String expressionString);
	public abstract String parseOutputExpression(String expressionString);
	
}
