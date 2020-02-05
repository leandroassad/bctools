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
	
}
