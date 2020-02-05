package com.scientts.bctools.parsers;

public interface CommandParser {
	public static final int INPUT_EXPRESSION = 0;
	public static final int OUTPUT_EXPRESSION = 1;
	
	public String parse(String expressionString);
	public String getCommandName();
}
