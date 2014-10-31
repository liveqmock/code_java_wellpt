package com.wellsoft.pt.demo.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sf.json.JSONObject;

public class Test {

	/**
	 * 如何描述该方法
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String path = "c:/json.txt";
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int size = 0;
		byte[] buffer = new byte[1024];
		while ((size = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, size);
		}
		String definitionJson = baos.toString("GBK");
		//String definitionJson = "{}";
		//JsonConfig jsonConfig = new JsonConfig();
		//jsonConfig.setExcludes(new String[] { "definitionJson" });
		System.out.println(definitionJson);
		JSONObject json = new JSONObject();
		JSONObject jsonObj = json.fromObject(definitionJson);
		//System.out.println(jsonObj.getString("version"));
		System.out.println(jsonObj.get("definitionJson"));
		//DyFormDefinition dy = (DyFormDefinition) JSONObject.toBean(jsonObj, DyFormDefinition.class);
	}
}
