package com.scientts.bctools.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class EMVTagUtil {

	protected static final String TAGS_FILE = "conf/tags.json";
	
	protected EMVTagUtil() {
		loadEMVTagFile();
	}
	
	protected static EMVTagUtil emvTag = null;
	public static EMVTagUtil getInstance() {
		if (emvTag == null) {
			emvTag = new EMVTagUtil();
		}
		
		return emvTag;
	}
	
	protected String tagsFile = TAGS_FILE;
	protected Map<String, EMVTag> emvTagMap = new HashMap<String, EMVTag>();
	
	
	public void setTagsFile(String tagsFile) {
		this.tagsFile = tagsFile;
	}
	
	public void loadEMVTagFile() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(tagsFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			
			emvTagMap.clear();
			
			JSONArray jsonArray = new JSONArray(buffer.toString());
			int numberOfTags = jsonArray.length();
			JSONObject entry;
			for (int index = 0; index < numberOfTags; index++) {
				entry = jsonArray.getJSONObject(index);
				EMVTag tag = new EMVTag();
				tag.tag = entry.getString("tag");
				tag.name = entry.getString("name");
				tag.description = entry.getString("description");
				
				emvTagMap.put(entry.getString("tag"), tag);
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e1) {
			}			
		}		
	}
	
	public EMVTag getEMVTag(String tag) {
		return emvTagMap.get(tag);
	}
	
	public static void main(String[] args) {
		EMVTagUtil util = EMVTagUtil.getInstance();
	}
}
