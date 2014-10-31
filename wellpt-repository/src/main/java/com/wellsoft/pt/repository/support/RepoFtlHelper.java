package com.wellsoft.pt.repository.support;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.wellsoft.pt.mt.entity.Tenant;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * JCR repository.xml的模版处理类
 * 
 * @author lilin
 * 
 */
public class RepoFtlHelper {
	private Configuration cfg;

	/**
	 * 初始化后加载模版路径
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	// @PostConstruct
	public void init() throws IOException {
		cfg = new Configuration();
		// 设置FreeMarker的模版文件位置,这里统一放置到ftl目录下面
		cfg.setDirectoryForTemplateLoading(new File(
				JcrConstants.FTL_TEMPLATE_PATH));
	}

	/**
	 * 将模版和数据合并返回前台需要的string
	 * 
	 * @param templateName
	 *            模版名称
	 * @param data
	 *            数据
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 * @throws Exception
	 */
	public StringWriter process(String templateName, Object data)
			throws IOException, TemplateException {
		
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("data", data);
		Template t = cfg.getTemplate(templateName + ".ftl");

		StringWriter writer = new StringWriter();
		t.process(data, writer);
		return writer;
	}

	public StringWriter processFile(String templateName) throws IOException,
			TemplateException {
		// Map<String, Object> root = new HashMap<String, Object>();
		// root.put("data", data);
		
		Template t = cfg.getTemplate(templateName + ".ftl");

		StringWriter writer = new StringWriter();
		t.process(null, writer);
		return writer;
	}

	public static Map<String, String> convertTenantData(Tenant tenant) {
		Map<String, String> tenantmap = new HashMap<String, String>();
		tenantmap.put("tenant", tenant.getAccount());
		tenantmap.put("jdbc_driver",
				"com.microsoft.sqlserver.jdbc.SQLServerDriver");
		tenantmap.put(
				"jdbc_url",
				"jdbc:sqlserver://" + tenant.getJdbcServer() + ":"
						+ tenant.getJdbcPort() + ";" + "databaseName="
						+ tenant.getJdbcDatabaseName());
		tenantmap.put("jdbc_username", tenant.getJdbcUsername());
		tenantmap.put("jdbc_password", tenant.getJdbcPassword());
		// tenantmap.put("jdbc_type", tenant.getJdbcType());
		tenantmap.put("jdbc_type", "mssql");
		return tenantmap;
	}
}
