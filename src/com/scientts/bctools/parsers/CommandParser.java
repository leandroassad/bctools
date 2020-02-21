package com.scientts.bctools.parsers;

public interface CommandParser {
	public static final int INPUT_EXPRESSION = 0;
	public static final int OUTPUT_EXPRESSION = 1;
	
	public static final String HTML_START = "<html><body>"; 
	public static final String HTML_END = "</body></html>";
	public static final String TABLE_START = "<table>";
	public static final String TABLE_END = "</table>";
	public static final String TR_START = "<tr>";
	public static final String TR_END = "</tr>";
	public static final String TH_START = "<th>";
	public static final String TH_END = "</th>";
	public static final String TD_START = "<td>";
	public static final String TD_END = "</td>";
	
	public String parse(String expressionString);
	public String getCommandName();
}
