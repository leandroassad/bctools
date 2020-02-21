package com.scientts.bctools.parsers;

public abstract class AbstractCommandParser implements CommandParser {

	String commandName;
	public AbstractCommandParser(String commandName) {
		this.commandName = commandName;
	}
	
	public String getCommandName() {
		return commandName;
	}
	
	public String toString() {
		return commandName;
	}
	

	protected StringBuffer buffer = new StringBuffer();
	public String parse(String expressionString) {
		buffer.delete(0, buffer.length());
		
		buffer.append(HTML_START);
		buffer.append("<h1>").append(getCommandName()).append("</h1>");
		
		parseExpression(expressionString);
				
		buffer.append(HTML_END);
				
		return buffer.toString();
	}
	
	public StringBuffer appendString(String str) {
		return buffer.append(str);
	}
	
	public StringBuffer appendStartTable() {
		return appendString(TABLE_START);
	}
	
	public StringBuffer appendEndTable() {
		return appendString(TABLE_END);
	}
	
	public void appendTableHeader(String[] header) {
		buffer.append(TR_START);
		
		for (String h : header) {
			buffer.append(TH_START).append(h).append(TH_END);
		}
		buffer.append(TR_END);
	}
	
	public void appendTableRow(String[] content) {
		buffer.append(TR_START);
		
		for (String c : content) {
			buffer.append(TD_START).append(c).append(TD_END);
		}
		buffer.append(TR_END);		
	}

	protected abstract void parseExpression(String expressionString);
	
}
