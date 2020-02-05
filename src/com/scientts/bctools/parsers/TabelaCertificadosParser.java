package com.scientts.bctools.parsers;

public class TabelaCertificadosParser extends AbstractCommandParser {

	public TabelaCertificadosParser(String commandName) {
		super(commandName);
	}

	@Override
	public String parse(String input) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> Tabela de Certificados <<<<\n");
		builder.append("Não Implementado Ainda\n");
		
		return builder.toString();

	}

}
