package com.wellsoft.pt.dytable.support;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.cfg.Configuration;

import com.wellsoft.pt.dytable.bean.ColInfoBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.bean.TableInfoBean;

public class HibernateUtil {

	public static void addMapping(Configuration config, URL hbml) {
		config.addURL(hbml);
	}

	/**
	 * 把hbm.xml的路径加入到cfg.xml的mapping结点
	 * 
	 * @param cfg
	 *            .xml的路径
	 * @param hbm
	 *            .xml的路径
	 */
	public static void updateHbmCfg(URL url, String hbm) {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element element = (Element) doc.getRootElement().selectSingleNode("session-factory");

			Element hbmnode = element.addElement("mapping");
			hbmnode.addAttribute("resource", hbm);
			String filepath = url.getFile();
			if (filepath.charAt(0) == '/')
				filepath = filepath.substring(1);
			FileOutputStream outputstream = new FileOutputStream(filepath);
			XMLWriter writer = new XMLWriter(outputstream);
			writer.write(doc);
			outputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户配置，将配置信息组织成hibernate配置文件形式的XML字符串
	 * 
	 * @param rootTableInfoBean
	 * @return
	 */
	public static String getHbmCfgXml(RootTableInfoBean rootTableInfo) {
		TableInfoBean tableInfo = rootTableInfo.getTableInfo();
		List<ColInfoBean> colInfoList = tableInfo.getFields();

		Document doc = DocumentHelper.createDocument();

		doc.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN",
				"http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd");

		Element root = doc.addElement("hibernate-mapping");

		Element classEl = root.addElement("class").addAttribute("entity-name", tableInfo.getTableName());

		// uuid字段
		Element uuidEl = classEl.addElement("id");
		uuidEl.addAttribute("name", "uuid");
		uuidEl.addAttribute("column", "uuid");
		uuidEl.addAttribute("type", "string");
		uuidEl.addElement("generator").addAttribute("class", "org.hibernate.id.UUIDGenerator");

		// c_time字段
		// Element timeEl = classEl.addElement("property");
		// timeEl.addAttribute("name", "c_time");
		// timeEl.addAttribute("column", "c_time");
		// timeEl.addAttribute("type", "long");

		// creator字段
		Element creatorEl = classEl.addElement("property");
		creatorEl.addAttribute("name", "creator");
		creatorEl.addAttribute("column", "creator");
		creatorEl.addAttribute("type", "string");

		// createTime字段
		Element createTimeEl = classEl.addElement("property");
		createTimeEl.addAttribute("name", "createTime");
		createTimeEl.addAttribute("column", "create_time");
		createTimeEl.addAttribute("type", "java.sql.Timestamp");

		// modifier字段
		Element modifierEl = classEl.addElement("property");
		modifierEl.addAttribute("name", "modifier");
		modifierEl.addAttribute("column", "modifier");
		modifierEl.addAttribute("type", "string");

		// modifyTime字段
		Element modifyTimeEl = classEl.addElement("property");
		modifyTimeEl.addAttribute("name", "modifyTime");
		modifyTimeEl.addAttribute("column", "modify_time");
		modifyTimeEl.addAttribute("type", "java.sql.Timestamp");

		// sortOrder字段
		Element sortOrderEl = classEl.addElement("property");
		sortOrderEl.addAttribute("name", "sortOrder");
		sortOrderEl.addAttribute("column", "sort_order");
		sortOrderEl.addAttribute("type", "int");

		// 表dytable_form_definition的外键
		Element formUuidEl = classEl.addElement("property");
		formUuidEl.addAttribute("name", "form_id");
		formUuidEl.addAttribute("column", "form_id");
		formUuidEl.addAttribute("type", "string");

		// 从表指向主表的外键
		Element subTableEl = classEl.addElement("property");
		subTableEl.addAttribute("name", "parent_id");
		subTableEl.addAttribute("column", "parent_id");
		subTableEl.addAttribute("type", "string");

		// 用户配置字段
		for (ColInfoBean colInfo : colInfoList) {

			Element proEl = classEl.addElement("property");
			proEl.addAttribute("name", colInfo.getColEnName());
			proEl.addAttribute("column", colInfo.getColEnName());
			if ("2".equals(colInfo.getDataType()) || "7".equals(colInfo.getDataType())
					|| "8".equals(colInfo.getDataType()) || "10".equals(colInfo.getDataType())
					|| "11".equals(colInfo.getDataType())) {// 日期暂时也存为字符类型
				proEl.addAttribute("type", "java.sql.Timestamp");
				//				proEl.addAttribute("type", "string");
				//				proEl.addAttribute("length", "50");
			} else if ("13".equals(colInfo.getDataType())) {
				proEl.addAttribute("type", "int");
				//				proEl.addAttribute("length", "8");
			} else if ("14".equals(colInfo.getDataType())) {
				proEl.addAttribute("type", "long");
			} else if ("15".equals(colInfo.getDataType())) {
				proEl.addAttribute("type", "float");
			} else if ("16".equals(colInfo.getDataType())) {
				proEl.addAttribute("type", "clob");
			} else {
				proEl.addAttribute("type", "string");
				if (null != colInfo.getDataLength()) {
					proEl.addAttribute("length", colInfo.getDataLength().toString());
				} else {
					proEl.addAttribute("length", "255");
				}
			}
		}
		return doc.asXML();
	}

	public static String convertClobToString(Clob clob) {
		StringBuffer sb = new StringBuffer();
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (clobStream != null) {
				try {
					clobStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
