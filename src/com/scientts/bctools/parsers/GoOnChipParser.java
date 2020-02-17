package com.scientts.bctools.parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.payneteasy.tlv.BerTag;
import com.payneteasy.tlv.BerTlv;
import com.payneteasy.tlv.BerTlvParser;
import com.payneteasy.tlv.BerTlvs;
import com.payneteasy.tlv.HexUtil;
import com.scientts.bctools.util.EMVTag;
import com.scientts.bctools.util.EMVTagUtil;

public class GoOnChipParser extends AbstractCardExpressionsParser {

	public static final Pattern goonchipInputPattern = Pattern.compile("(\\d{12})(\\d{12})(\\d)(\\d)(\\d)(\\d)(\\d{2})([0-9A-Fa-f]{32})(\\d)([0-9A-Fa-f]{8})(\\d{2})([0-9A-Fa-f]{8})(\\d{2})(\\d{3})(.*)");
	public static final Pattern goonchipOutputPattern = Pattern.compile("(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)([0-9A-Fa-f]{16})([0-9A-Fa-f]{20})(\\d{3})(.*)");
	
	protected Map<String, String> modoCriptMap = new HashMap<String, String>() {{
		put("0", "Master Key / Working DES (8 bytes)");
		put("1", "Master Key / Working 3DES (16 bytes)");
		put("2", "DUKPT DES");
		put("3", "DUKPT 3DES");
	}};

	protected Map<String, String> decisaoMap = new HashMap<String, String>() {{
		put("0", "Transação aprovada offline");
		put("1", "Transação negada");
		put("2", "Transação requer autorização online");
	}};
	
	public GoOnChipParser(String commandName, int expression) {
		super(commandName, expression);		
	}

	public String parseInputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> GonOnChip Input <<<<\n");
		
		Matcher m = goonchipInputPattern.matcher(expressionString);
		if (m.matches()) {
			builder.append("Entrada: ").append(expressionString).append("\n\n");
			builder.append("Amount, authorized: [").append(m.group(1)).append("]\n");
			builder.append("Parcela do valor da transação referente a saque ou troco: [").append(m.group(2)).append("]\n");
			builder.append("Resultado da consulta à Lista Negra (só para EMV com contato): [").append(m.group(3)).append("] - ").append(m.group(3).charAt(0) == '0' ? "PAN não consta na Lista Negra" : "PAN consta na Lista Negra").append("\n");
			builder.append("Obrigatoriedade de conexão (só para EMV com contato): [").append(m.group(4)).append("] - ").append(m.group(4).charAt(0) == '0' ? "Transação pode ser efetuada offline" : "Transação somente pode ser efetuada online").append("\n");
			builder.append("Requerimento de PIN nas tabelas do Servidor TEF: [").append(m.group(5)).append("] - ").append(m.group(5).charAt(0) == '0' ? "Não é exigida a verificação de um PIN" : "É exigida a verificação de um PIN").append("\n");
			builder.append("Modo de criptografia: [").append(m.group(6)).append("] - ").append(modoCriptMap.get(m.group(6))).append("\n");
			builder.append("Índice da Master Key ou do registro de tratamento DUKPT: [").append(m.group(7)).append("]\n");
			builder.append("Working Key (criptografada pela Master Key): [").append(m.group(8)).append("]\n");
			builder.append("Terminal Risk Management: [").append(m.group(9)).append("] - ").append(m.group(9).charAt(0) == '0' ? "Não faz o gerenciamento de risco" : "Faz o gerenciamento de risco usando os parâmetros a seguir").append("\n");
			builder.append("Terminal Floor Limit (em centavos): [").append(m.group(10)).append("]\n");
			builder.append("Target Percentage to be used for Biased Random Selection: [").append(m.group(11)).append("]\n");
			builder.append("Threshold Value for Biased Random Selection (em centavos): [").append(m.group(12)).append("]\n");
			builder.append("Maximum Target Percentage to be used for Biased Random Selection: [").append(m.group(13)).append("]\n");
			int nDadosExtrasLen = Integer.parseInt(m.group(14));
			if (nDadosExtrasLen > 0) {
				builder.append("Tamanho dos dados específicos do adquirente : [").append(m.group(14)).append("]\n");
				builder.append("dados específicos do adquirente : [").append(m.group(15)).append("]\n");
			}	
		}
		else {
			builder.append("Entrada No Formato Inválido");
		}
		
		return builder.toString();
	}
	
	public String parseOutputExpression(String expressionString) {
		StringBuilder builder = new StringBuilder();		

		builder.append(">>>> GonOnChip Output <<<<\n");
		
		Matcher m = goonchipOutputPattern.matcher(expressionString);
		if (m.matches()) {
			builder.append("Entrada: ").append(expressionString).append("\n\n");
			builder.append("Decisão tomada: [").append(m.group(1)).append("] - ").append(decisaoMap.get(m.group(1))).append("\n");
			builder.append("Assinatura em papel deve ser obtida: [").append(m.group(2)).append("] - ").append(m.group(2).charAt(0) == '0' ? "Não" : "Sim").append("\n");
			builder.append("PIN foi verificadooffine: [").append(m.group(3)).append("] - ").append(m.group(3).charAt(0) == '0' ? "Não" : "Sim").append("\n");
			builder.append("Número de apresentações inválidas de PIN offline: [").append(m.group(4)).append("]\n");
			builder.append("PIN offline foi bloqueado na última apresentação inválida: [").append(m.group(5)).append("] - ").append(m.group(5).charAt(0) == '0' ? "Não" : "Sim").append("\n");
			builder.append("PIN capturado para verificação online: [").append(m.group(6)).append("] - ").append(m.group(6).charAt(0) == '0' ? "Não" : "Sim").append("\n");
			builder.append("PIN criptografado: [").append(m.group(7)).append("]\n");
			builder.append("Key Serial Number: [").append(m.group(8)).append("]\n");
			builder.append("Tamanho em Bytes do Bit 55: [").append(m.group(9)).append("]\n");
			int nBytes = Integer.parseInt(m.group(9));
			if (nBytes > 0) {
				nBytes *= 2;
				builder.append("Bit 55: [").append(m.group(10).substring(0, nBytes)).append("]\n");
				
				builder.append("Tags separadas\n");
				byte[] bytes = HexUtil.parseHex(m.group(10).substring(0, nBytes));

				BerTlvParser parser = new BerTlvParser();
				BerTlvs tlvs = parser.parse(bytes, 0, bytes.length);
				List<BerTlv> tlvList = tlvs.getList();
				EMVTagUtil tagUtil = EMVTagUtil.getInstance();
				for (BerTlv berTlv : tlvList) {
					BerTag berTag = berTlv.getTag();
					String tagStr = HexUtil.toHexString(berTag.bytes);
					EMVTag tag = tagUtil.getEMVTag(tagStr);
					String tagName = tag != null? tag.name : "Sem Descrição";
					builder.append("Tag ").append(tagStr).append(": ").append(tagName).append("\n");
					builder.append("  Valor: ").append(berTlv.getHexValue()).append("\n");
				}
			}
		}
		else {
			builder.append("Entrada No Formato Inválido");
		}
		
		return builder.toString();
	}
}
