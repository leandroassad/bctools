package com.scientts.bctools.parsers;

public abstract class AbstractCardExpressionsParser extends AbstractCommandParser {

	int expression;
	public AbstractCardExpressionsParser(String commandName, int expression) {
		super(commandName);
		this.expression = expression;
	}
	
	@Override
	public void parseExpression(String expressionString) {
		
		switch (expression) {
		case INPUT_EXPRESSION:
			appendString(parseInputExpression(expressionString));
			break;
		case OUTPUT_EXPRESSION:
			appendString(parseOutputExpression(expressionString));
			break;
		default:
			appendString("ERRO INTERNO!");
		}
	}
	
	public abstract String parseInputExpression(String expressionString);
	public abstract String parseOutputExpression(String expressionString);
	
}
