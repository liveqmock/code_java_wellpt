package com.wellsoft.pt.repository.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.core.mt.service.TenantService;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dytable.service.FormSignatureDataService;
import com.wellsoft.pt.dytable.support.DytableConfig;
import com.wellsoft.pt.dytable.support.FormDataSignature;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.mt.entity.Tenant;
import com.wellsoft.pt.repository.entity.FileUpload;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.repository.support.JcrConstants;
import com.wellsoft.pt.repository.support.JcrRepositoryHelper;
import com.wellsoft.pt.utils.encode.Md5PasswordEncoderUtils;

/**
 * Description: 如何描述该类
 *  
 * @author Administrator
 * @date 2014-4-28
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-4-28.1	Hunt		2014-4-28		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/syn/")
public class SynController extends BaseController {
	Logger logger = Logger.getLogger(SynController.class);

	/**表单数据库中有几个Nodename */
	private int FORMDATA_NODENAME_TOTALCOUNT = 0;
	/** 在JCR中找不到的NodeName有几个 */
	private int FORMDATA_NODENAME_FAILCOUNT = 0;
	/** 在JCR中找到的NodeName有几个 */
	private int FORMDATA_NODENAME_SUCESSFULCOUNT = 0;

	/** 在JCR中共找到几个文件 */
	private int FORMDATA_FILE_TOTALCOUNT = 0;
	/** 在JCR中几个文件保存失败 */
	private int FORMDATA_FILE_FAILCOUNT = 0;
	/** 在JCR中几个文件保存成功 */
	private int FORMDATA_FILE_SUCESSFULCOUNT = 0;

	/** 表单复本的总文件数 */
	private int FORMDATA_COPYFILE_TOTALCOUNT = 0;
	/** 表单复本的失败数 */
	private int FORMDATA_COPYFILE_FAILCOUNT = 0;
	/** 表单复本的成功数 */
	private int FORMDATA_COPYFILE_SUCESSFULCOUNT = 0;

	/** 签名文件的个数 */
	private int FORMDATA_SIGNFILE_TOTALCOUNT = 0;
	/** 最后签名文件数 */
	private int FORMDATA_SIGNFILE_SUCESSFULCOUNT = 0;

	private Map<String, Object> FORMDATA_FOLDER_MAP = new HashMap<String, Object>();

	/** 表单外的普通模块的NodeName总数 */
	private int NOMAL_NODENAME_TOTALCOUNT = 0;

	/** 在JCR中找不到的NodeName有几个  */
	private int NOMAL_NODENAME_FAILCOUNT = 0;

	/** 在JCR中找到的NodeName有几个 */
	private int NOMAL_NODENAME_SUCESSFULCOUNT = 0;

	/** 在JCR中共找到几个文件 */
	private int NOMAL_FILE_TOTALCOUNT = 0;
	/** 在JCR中几个文件保存失败 */
	private int NOMAL_FILE_FAILCOUNT = 0;
	/** 在JCR中几个文件保存成功 */
	private int NOMAL_FILE_SUCESSFULCOUNT = 0;

	private Map<String, Object> NORMAL_FOLDER_MAP = new HashMap<String, Object>();

	@Autowired
	private MongoFileService mongoFileService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private FormSignatureDataService formSignatureDataService;

	Map<String, List<String>> formTableField = new HashMap<String, List<String>>();

	List<String> formTables = new ArrayList<String>();

	@RequestMapping("/formdata")
	public void synFormdata(HttpServletResponse response) throws Exception {
		formTableField.clear();
		formTables.clear();
		//获取有附件的表单表
		List<Map<String, Object>> fieds = formSignatureDataService
				.query("select t.fieldname  FIELDNAME, o.name name from dytable_field_definition  t  left join dytable_form_definition o "
						+ "on o.uuid =   t.FORM_UUID  where     t.input_data_type in ('3', '4', '5', '6', '22') or t.fieldname = 'signature_' or t.fieldname = 'SIGNATURE_' ");

		for (Map<String, Object> map : fieds) {
			String fieldName = (String) map.get("fieldname");
			String tblName = (String) map.get("name");
			if ("test0321001".equalsIgnoreCase(tblName)) {
				System.out.println(tblName);
			}
			List<String> list = (List<String>) formTableField.get(tblName);
			if (list == null) {
				list = new ArrayList<String>();
				formTableField.put(tblName, list);
			}
			list.add(fieldName);

		}

		//获取所有表单表
		List<Map<String, Object>> tableMaps = formSignatureDataService
				.query("select  name , max(version) from dytable_form_definition  where name is not null  group by name order by name");

		for (Map<String, Object> map : tableMaps) {
			formTables.add((String) map.get("name"));
		}

		//循环获取各表单的数据
		for (String tblName : formTables) {
			List<String> fields = formTableField.get(tblName);
			if (fields == null || fields.size() == 0) {//没有附件的表单

				continue;
			}

			//有附件的表单
			synFormdataReady(fields, tblName);

		}

		System.out.println("表单迁移结束");
		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().println("<br>副本失败个数FORMDATA_COPYFILE_FAILCOUNT = " + FORMDATA_COPYFILE_FAILCOUNT);
			response.getWriter().println(
					"</br><br>副本成功个数FORMDATA_COPYFILE_SUCESSFULCOUNT = " + FORMDATA_COPYFILE_SUCESSFULCOUNT);
			response.getWriter()
					.println("</br><br>副本总个数FORMDATA_COPYFILE_TOTALCOUNT = " + FORMDATA_COPYFILE_TOTALCOUNT);
			response.getWriter().println("</br><br>文件失败个数FORMDATA_FILE_FAILCOUNT = " + FORMDATA_FILE_FAILCOUNT);
			response.getWriter().println(
					"</br><br>文件成功个数FORMDATA_FILE_SUCESSFULCOUNT = " + FORMDATA_FILE_SUCESSFULCOUNT);
			response.getWriter().println("</br><br>文件总个数FORMDATA_FILE_TOTALCOUNT = " + FORMDATA_FILE_TOTALCOUNT);
			response.getWriter().println("</br><br>结点失败个数FORMDATA_NODENAME_FAILCOUNT = " + FORMDATA_NODENAME_FAILCOUNT);
			response.getWriter().println(
					"</br><br>结点成功个数FORMDATA_NODENAME_SUCESSFULCOUNT = " + FORMDATA_NODENAME_SUCESSFULCOUNT);
			response.getWriter().println(
					"</br><br>数据库中的结点总数FORMDATA_NODENAME_TOTALCOUNT = " + FORMDATA_NODENAME_TOTALCOUNT);
			response.getWriter().println(
					"</br><br>签名文件成功个数FORMDATA_SIGNFILE_SUCESSFULCOUNT = " + FORMDATA_SIGNFILE_SUCESSFULCOUNT);
			response.getWriter().println(
					"</br><br>签名文件总个数FORMDATA_SIGNFILE_TOTALCOUNT = " + FORMDATA_SIGNFILE_TOTALCOUNT);
			response.getWriter().println("</br><br>文件夹总数FORMDATA_FOLDER_MAP = " + FORMDATA_FOLDER_MAP.size());
			response.getWriter().println("</br>");
		} catch (Exception e) {

		}

	}

	/**
	 * 开始同步表单数据
	 * 
	 * @param fields
	 * @param tblName
	 * @throws Exception
	 */
	private void synFormdataReady(final List<String> fields, final String tblName) throws Exception {
		String project = "uuid";
		for (String field : fields) {
			project += "," + field;
		}
		//project += ", body_col";//正文

		//获取某个表单表中的数据
		List<Map<String, Object>> dataMaps = formSignatureDataService.query("select " + project + " from " + tblName);

		//迁移逐个条表单数据
		for (Map<String, Object> dataMap : dataMaps) {

			//处理该表中的某条记录

			String uuid = (String) dataMap.get("uuid");
			if (uuid == null || uuid.trim().length() == 0) {
				logger.info("no uuid in tableName[" + tblName + "]");
				continue;
			}

			Map<String, Map<String, Node>> fieldFilesMap = new HashMap<String, Map<String, Node>>();
			Map<String, String> folderIDValueNameMap = new HashMap<String, String>();

			//获取某结点的所有信息
			for (String fieldName : fields) {
				fieldName = fieldName.toLowerCase();
				if (fieldName.equalsIgnoreCase("signature_") //签名
						|| fieldName.equalsIgnoreCase("uuid")
				//|| fieldName.equalsIgnoreCase("body_col") //正文
				) {
					continue;
				}

				String fieldValue = (String) dataMap.get(fieldName);
				if (fieldValue == null || fieldValue.trim().length() == 0) {//为空表示没数据
					continue;
				}

				try {
					FORMDATA_NODENAME_TOTALCOUNT++;//node结点递增
					fieldFilesMap.put(fieldValue, getFormFile(fieldValue));
				} catch (Exception e) {
					FORMDATA_NODENAME_FAILCOUNT++;//找不到该结点的相关信息
					logger.error("tableName[" + tblName + "]fieldName[" + fieldName + "]fieldValue[" + fieldValue
							+ "]\n" + e.getMessage(), e);
					continue;
				}
				FORMDATA_NODENAME_SUCESSFULCOUNT++;//成功找到结点信息
				folderIDValueNameMap.put(fieldValue, fieldName);
			}

			//加载一条记录的签名
			String signatureUUID = (String) dataMap.get("signature_");
			List<FileUpload> signatures = null;
			if (signatureUUID != null && signatureUUID.trim().length() > 0) {//有签名
				try {
					FORMDATA_SIGNFILE_TOTALCOUNT++;//签名总数
					signatures = parseSignature(signatureUUID, uuid);
				} catch (Exception e) {
					logger.error("tableName[" + tblName + "]fieldName[" + signatureUUID + "]fieldValue["
							+ signatureUUID + "]\n" + e.getMessage(), e);
					//有签名，但却找不到签名所以直接返回不做处理 
					continue;
				}
			}

			//解析一条记录下的所有结点信息
			Iterator<Entry<String, Map<String, Node>>> it = fieldFilesMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Map<String, Node>> entry = it.next();
				String nodeName = entry.getKey();
				String copyNodeName = nodeName + "_swf";//副本nodename
				boolean isCopyExist = false;
				try {
					getFormFile(copyNodeName);//获取副本,如果没有异常表明有副本
					FORMDATA_COPYFILE_TOTALCOUNT++;//总复本数
					isCopyExist = true;
				} catch (Exception e) {

				}
				String fieldName = folderIDValueNameMap.get(nodeName);

				Map<String, Node> fileNameNode = entry.getValue();
				Iterator<Entry<String, Node>> fileNameNodeIt = fileNameNode.entrySet().iterator();
				while (fileNameNodeIt.hasNext()) {
					FORMDATA_FILE_TOTALCOUNT++;//文件总数递增
					Entry<String, Node> e = fileNameNodeIt.next();
					String fileName = e.getKey();
					Node contentNode = e.getValue();
					//logger.info("nodename[" + nodeName+  "] filename["+fileName+"]");
					long fileSize = contentNode.getProperty(JcrConstants.SIZE).getValue().getLong();

					Property pro = contentNode.getProperty(JcrConstants.JCR_DATA);
					Binary binary = pro.getBinary();
					InputStream is = binary.getStream();
					File uploadDir = new File(Config.UPLOAD_DIR);
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					String tempUuid = UUID.randomUUID().toString();
					File tempFile = new File(uploadDir, tempUuid);
					FileOutputStream fos = new FileOutputStream(tempFile);
					IOUtils.copyLarge(is, fos);
					IOUtils.closeQuietly(fos);
					IOUtils.closeQuietly(is);
					String fileID = DynamicUtils.getRandomUUID();
					boolean isBody = false;
					if (fieldName.equalsIgnoreCase("body_col")) {//正文
						fileID = DytableConfig.BODY_FILEID_PREFIX + fileID;
						fileName = DytableConfig.BODY_FILEID_PREFIX + fileID + ".doc";
						isBody = true;
					}
					MongoFileEntity file = null;
					if (!isBody && signatures != null && signatures.size() > 0) {//非正文，正文是没有签名的
						String digestValue = FileController.digestAsMD5(tempFile);//获取签名
						String digest_value = null;
						String digest_algorithm = null;
						String signature_value = null;
						String certificate = null;
						boolean findit = false;

						for (FileUpload fileUpload : signatures) {
							String sfileName = fileUpload.getFilename();
							long sfileSize = fileUpload.getFileSize();
							String sdigestValue = fileUpload.getDigestValue();

							if (fileName.equals(sfileName) && sfileSize == fileSize & digestValue.equals(sdigestValue)) {
								findit = true;
								digest_value = fileUpload.getDigestValue();
								digest_algorithm = fileUpload.getDigestAlgorithm();
								signature_value = fileUpload.getSignatureValue();
								certificate = fileUpload.getCertificate();
								break;
							}
						}

						if (findit) {//找到签名
							binary = pro.getBinary();
							is = binary.getStream();
							file = this.mongoFileService.saveFile(fileID, fileName, is, digest_value, digest_algorithm,
									signature_value, certificate);
							IOUtils.closeQuietly(is);
						} else {//没有找到签名
							logger.error("cann't find the signature of tableName[" + tblName + "] uuid[" + uuid
									+ "]nodeName[" + nodeName + "] fileName[" + fileName + "]");

						}

					} else {
						binary = pro.getBinary();
						is = binary.getStream();

						file = this.mongoFileService.saveFile(fileID, fileName, is);

						IOUtils.closeQuietly(is);
						// logger.info("**********************table[" + tblName + "]uuid[" + uuid + "] has no signature************");
					}

					if (file == null) {//本来是有签名的，但签名文件没找到的
						FORMDATA_FILE_FAILCOUNT++;//找不到文件
						continue;
					}

					if (file != null) {
						if (!fieldName.equalsIgnoreCase("body_col")) {
							FORMDATA_FILE_SUCESSFULCOUNT++;
							this.mongoFileService.pushFileToFolder(uuid, file.getId(), fieldName);
							FORMDATA_FOLDER_MAP.put(uuid, null);
						} else {
							this.mongoFileService.pushFileToFolder(uuid, file.getId(), fieldName);//正文
							break;
						}
					}

					if (!isCopyExist) {//没有副本文件
						continue;
					}

					//副本
					if (fileName.toLowerCase().indexOf(".doc") > -1 || fileName.toLowerCase().indexOf(".docx") > -1
							|| fileName.toLowerCase().indexOf(".ppt") > -1
							|| fileName.toLowerCase().indexOf(".pptx") > -1
							|| fileName.toLowerCase().indexOf(".xls") > -1
							|| fileName.toLowerCase().indexOf(".xlsx") > -1
							|| fileName.toLowerCase().indexOf(".txt") > -1) {
						this.mongoFileService.createReplicaOfSWF(file.getId());
						FORMDATA_COPYFILE_SUCESSFULCOUNT++;//复本成功数递增
					}

				}
			}

		}

	}

	private List<FileUpload> parseSignature(String signatureUUID, String uuid) throws Exception {
		// List<Signature> signatures = new ArrayList<SynController.Signature>();

		Session session = JcrRepositoryHelper.getSystemSession();

		String path = null;
		Node contentNode = null;
		try {
			path = "/DY_TABLE_FORM/" + signatureUUID + "/" + signatureUUID + "/" + signatureUUID + "/"
					+ JcrConstants.CONTENT;
			contentNode = session.getNode(path);

		} catch (Exception e) {
			throw e;
		}

		if (contentNode == null) {
			throw new PathNotFoundException(" cann't find the node of path[" + path + "] ");
		}

		if (contentNode.isNodeType(JcrConstants.CONTENT_TYPE)) {
			Property pro = contentNode.getProperty(JcrConstants.AUTHOR);
			// String operator =  pro.getValue().getString();

			pro = contentNode.getProperty(JcrConstants.JCR_MIMETYPE);

			pro = contentNode.getProperty(JcrConstants.JCR_DATA);
			Binary binary = pro.getBinary();
			InputStream is = binary.getStream();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024 * 20];
				int size = 0;
				while ((size = is.read(buffer)) != -1) {
					baos.write(buffer, 0, size);
				}
				String json = baos.toString("utf-8");

				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("files", FileUpload.class);
				//classMap.put("folder_id", Id.class);

				//LogicFileInfo logicFileInfo =(LogicFileInfo) JSONObject.toBean(JSONObject.fromObject(json),LogicFileInfo.class , classMap);

				FormDataSignature formDataSignature = (FormDataSignature) //JsonUtils.toBean(json, FormDataSignature.class);
				JSONObject.toBean(JSONObject.fromObject(json), FormDataSignature.class, classMap);
				///JsonUtils.j

				//formDataSignature.get
				//System.out.println(formDataSignature);
				String formData = formDataSignature.getFormData();

				String digest_value = formDataSignature.getDigestValue();
				String digest_algorithm = formDataSignature.getDigestAlgorithm();
				String signature_value = formDataSignature.getSignatureValue();
				String certificate = formDataSignature.getCertificate();

				InputStream formDataInputstream = new StringInputStream(formData, "utf-8");
				MongoFileEntity fileEntitye = mongoFileService
						.saveFile(signatureUUID, formDataInputstream, digest_value, digest_algorithm == null ? "MD5"
								: digest_algorithm, signature_value, certificate);
				mongoFileService.pushFileToFolder(uuid, fileEntitye.getId(),
						MongoFileController.PURPOSE_Constance.SIGNATURE);
				formDataInputstream.close();
				FORMDATA_SIGNFILE_SUCESSFULCOUNT++;//签名文件的成功个数
				FORMDATA_FOLDER_MAP.put(uuid, null);
				//otherSave(fileEntitye);

				baos.flush();
				baos.close();
				is.close();
				return formDataSignature.getFiles();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/*private void otherSave(MongoFileEntity fileEntitye) {
		InputStream is = fileEntitye.getInputstream();
		int size = 0;
		byte[] buffer = new byte[1024* 10];
		 
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while((size = is.read(buffer)) != -1){
				baos.write(buffer, 0, size);
			}
			
			
			FileOutputStream file = new FileOutputStream(new File("c:\\" + fileEntitye.getId()));
			file.write(baos.toByteArray());
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
	}*/

	private Map<String, Node> getFormFile(String fieldValue) throws RepositoryException {
		Map<String, Node> fileMap = new HashMap<String, Node>();

		Session session = JcrRepositoryHelper.getSystemSession();
		//JcrRepositoryHelper.getSystemSession();
		//Node root = null;
		//root = session.getRootNode(); 
		String path = null;

		Node folderNode = null;
		path = "/DY_TABLE_FORM/" + fieldValue + "/" + fieldValue;
		try {
			folderNode = session.getNode(path);
			if (folderNode == null) {
				throw new PathNotFoundException(" cann't find the node of path[" + path + "] ");
			}
		} catch (PathNotFoundException e) {
			throw e;
		} catch (RepositoryException e) {
			throw e;
		}

		Iterator<Node> nodes = JcrUtils.getChildNodes(folderNode).iterator();
		if (!nodes.hasNext()) {
			throw new PathNotFoundException("there is no document node under the path[" + path + "]");
		}

		while (nodes.hasNext()) {
			Node docNode = nodes.next();
			String fileName = docNode.getProperty(JcrConstants.NAME).getValue().getString();
			String cpath = path + "/" + fileName + "/" + JcrConstants.CONTENT;

			Node contentNode = null;

			contentNode = session.getNode(cpath);

			if (contentNode == null) {
				throw new PathNotFoundException(" cann't find the content node of path[" + cpath + "] ");
			}

			if (contentNode.isNodeType(JcrConstants.CONTENT_TYPE)) {
				fileMap.put(fileName, contentNode);
			} else {
				throw new PathNotFoundException("path[" + cpath + "] is not a contenttype node");
			}

		}

		return fileMap;
	}

	class Signature {
		public String digest_value = null;
		public String digest_algorithm = null;
		public String signature_value = null;
		public String certificate = null;
		public int fileSize = 0;
		public String fileName = "";

	}

	@RequestMapping("/syn")
	public void synData(HttpServletResponse response, @RequestParam("moduleName") String moduleName) throws Exception {
		logger.info("同步模块:" + moduleName);
		synNomalFile(moduleName);//同步普通文件

		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		System.out.println("===============================================");
		//synFormSignatureDataFile( );//同步表单签名数据

		//System.out.println(moduleName + " 迁移结束");
		response.setContentType("text/html; charset=utf-8");
		try {

			logger.info("<br>文件失败个数NOMAL_FILE_FAILCOUNT = " + NOMAL_FILE_FAILCOUNT);
			logger.info("</br><br>文件成功个数NOMAL_FILE_SUCESSFULCOUNT = " + NOMAL_FILE_SUCESSFULCOUNT);
			logger.info("</br><br>文件总个数NOMAL_FILE_TOTALCOUNT = " + NOMAL_FILE_TOTALCOUNT);
			logger.info("</br><br>结点失败个数NOMAL_NODENAME_FAILCOUNT = " + NOMAL_NODENAME_FAILCOUNT);
			logger.info("</br><br>结点成功个数NOMAL_NODENAME_SUCESSFULCOUNT = " + NOMAL_NODENAME_SUCESSFULCOUNT);
			logger.info("</br><br>数据库中的结点总数NOMAL_NODENAME_TOTALCOUNT = " + NOMAL_NODENAME_TOTALCOUNT);
			logger.info("</br><br>文件夹总数NORMAL_FOLDER_MAP = " + NORMAL_FOLDER_MAP.size());
			logger.info("</br>");
			logger.info("同步完毕");
		} catch (Exception e) {

		}

	}

	private void synFormSignatureDataFile() throws Exception {

		List<Tenant> tenants = tenantService.getActiveTenants();
		for (Tenant tenant : tenants) {
			this.mongoFileService.setCurrentTenantId(tenant.getId());
			try {
				Session session = JcrRepositoryHelper.getSystemSession(tenant.getId());
				Node root = null;
				root = session.getRootNode();
				System.out.println(root.getPath());
				showEverything1(root);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/*
	 private void synFormSignatureDataFile( ) throws Exception {
		 
		  List<Map<String, Object>> lists = formSignatureDataService.query(" select * from ALL_TAB_COLUMNS t where (t.TABLE_NAME like 'USERFORM_%') and  t.column_name = 'SIGNATURE_' order by t.OWNER , t.TABLE_NAME", new HashMap<String, Object>(), 0, 10000);
		
		for(Map<String, Object> map: lists){
			String owner = (String) map.get("oWNER");
			String tableName = (String) map.get("tableName");
			
			//tableName = owner + "." + tableName;
			
		
			
			System.out.println(owner + "." + tableName);
			

			  List<Map<String, Object>> fieds = formSignatureDataService.query("select t.fieldname from " +
					  owner + ".dytable_field_definition  t  left join " + owner +".dytable_form_definition o " +
					  		"on o.uuid =   t.FORM_UUID  where o.name = '" + tableName +  "' and   t.input_data_type in ('3', '4', '5', '6')", new HashMap<String, Object>(), 0, 10000);
			
		
						
			
			 List<Map<String, Object>> signatureUUIDs = formSignatureDataService.query(" select SIGNATURE_  from " +   owner + "." + tableName, new HashMap<String, Object>(), 0, 10000);
			for(Map<String, Object> signatureUUIDMap: signatureUUIDs){
				String signatureUUID = (String) signatureUUIDMap.get("signature");
				if(signatureUUID == null)
				continue;
				List<Tenant> tenants = tenantService.getActiveTenants();
				for(Tenant tenant: tenants){
					mongoFileService.setCurrentTenantId(tenant.getId());
				  
					try{
						Session session = JcrRepositoryHelper.getSystemSession(tenant.getId()); 
								//JcrRepositoryHelper.getSystemSession();
						//Node root = null;
						//root = session.getRootNode(); 
						String path = null;
						 Node contentNode = null;
						try{
							 path = "/DY_TABLE_FORM/" + signatureUUID + "/" + signatureUUID + "/" + signatureUUID + "/" + JcrConstants.CONTENT;
							 contentNode = session.getNode(path);
						}catch (Exception e) {
							 
						}
						  
						if(contentNode == null){
							continue;
						}
						if(contentNode.isNodeType(JcrConstants.CONTENT_TYPE)){
							 Property pro = contentNode.getProperty(JcrConstants.AUTHOR);
							 String operator =  pro.getValue().getString();
							 
							 pro = contentNode.getProperty(JcrConstants.JCR_MIMETYPE);
							//System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
							String contentType =  pro.getValue().getString();
							System.out.println(signatureUUID + "---->" + contentType+ "----------->" + "operator");
							
							 pro = contentNode.getProperty(JcrConstants.JCR_DATA); 
							Binary binary  = pro.getBinary();   
							InputStream is = binary.getStream();
							 try {
								 MongoFileEntity fileEntitye = filedao.saveFile(fileName, contentType, is, "", "", "", "", EnumFilePurpose.NOMAL);
								folderDao.createFolder(folderId, null);
								folderDao.pushFileToFolder(folderId, fileEntitye.getId()); 
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								byte[] buffer = new byte[1024];
								int size = 0;
								while(( size = is.read(buffer)) != -1){
									baos.write(buffer,0, size);
								}
								String json =baos.toString("utf-8");
								
								if(tableName.indexOf("SSXX_ZTDJ") != -1 || tableName.indexOf("ssxx_ztdj") != -1 ){
									System.out.println("stop");
								}
								
								Map<String, Class> classMap = new HashMap<String, Class>();
							      classMap.put("files", FileUpload.class);
							     //classMap.put("folder_id", Id.class);
								
								//LogicFileInfo logicFileInfo =(LogicFileInfo) JSONObject.toBean(JSONObject.fromObject(json),LogicFileInfo.class , classMap);
							
								FormDataSignature formDataSignature = (FormDataSignature) //JsonUtils.toBean(json, FormDataSignature.class);
								JSONObject.toBean(JSONObject.fromObject(json),FormDataSignature.class , classMap);
										///JsonUtils.j

										//formDataSignature.get
								//System.out.println(formDataSignature);
								String formData = formDataSignature.getFormData();
								InputStream formDataInputstream = new StringInputStream(formData); 
								String digest_value = formDataSignature.getDigestValue();
								String  digest_algorithm= formDataSignature.getDigestAlgorithm();
								String signature_value= formDataSignature.getSignatureValue();
								String certificate= formDataSignature.getCertificate();
								
								
								com.wellsoft.pt.repository.entity.mongo.file.FileEntity fileEntitye 
									= filedao.saveFile(signatureUUID, contentType, formDataInputstream, 
											digest_value, digest_algorithm, signature_value, certificate, EnumFilePurpose.FORM_DATA);
								formDataInputstream.close();
								
								folderDao.createFolder(signatureUUID, null);
								folderDao.pushFileToFolder(signatureUUID, fileEntitye.getId()); 
								
								
								for(FileUpload fileupload :formDataSignature.getFiles()){
									//System.out.println(fileupload.getNodeName());
									if(fileupload.isSignUploadFile()){//表示有签名 
										String nodeName = fileupload.getNodeName();
										 digest_value = fileupload.getDigestValue();
										  digest_algorithm= fileupload.getDigestAlgorithm();
										 signature_value= fileupload.getSignatureValue();
										 certificate= fileupload.getCertificate();
										 String fileName = fileupload.getFilename();
										 
										 List<MongoFileEntity> files = folderDao.getFile(nodeName);
										 boolean getfile = false;
										 for(com.wellsoft.pt.repository.entity.mongo.file.FileEntity file :  files){
											 if(file.getFileName().equals(fileName)){
												 String fileId = file.getId();
												 filedao.updateSignature(fileId, digest_value, digest_algorithm, signature_value, certificate);
												 getfile = true;
												 break;
											 }
										 }
										 if(!getfile){
											 throw new Exception("cann't find the filename[" + fileName + "] in folder[" + nodeName + "]" + "tenantId[" + tenant.getId() + "] tablename[" + owner + "."+tableName+"] signature_[" + signatureUUID + "]" );
										 }
										 //System.out.println(nodeName);
									}
								}
								 baos.close();
								is.close();
							 }   catch (IOException e) {
								e.printStackTrace();
							}
							 
								 pro = entry.getProperty(JcrConstants.JCR_LASTMODIFIED);
									System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
							
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					
					
				
				}
				
				List<FileEntity> files = fileService.getFiles("DY_TABLE_FORM", signatureUUID);
				if(files != null)
				for(FileEntity file: files){
					System.out.println(signatureUUID + "-------->" + file.getFilename());
				}
				
				
			}
			
			
			
		 
		}
		 
	} */

	private void showEverything1(Node root) throws Exception {
		int depth = root.getDepth();
		int depthIndex = depth;
		String shoujin = "";
		for (; depthIndex > -1; depthIndex--) {
			shoujin += " ";
			//System.out.print(shoujin);
		}
		//System.out.println(depth + ":");
		Iterator<Node> nodes = JcrUtils.getChildNodes(root).iterator();
		while (nodes.hasNext()) {
			Node entry = nodes.next();
			String path = entry.getPath();
			if (path.indexOf("DY_TABLE_FORM") == -1) {
				continue;
			}
			System.out.println(shoujin + depth + "*" + path + "=====>" + entry.getPrimaryNodeType().getName());

			if (entry.isNodeType(JcrConstants.FILE)) {
				Property pro = entry.getProperty(JcrConstants.NAME);//文件名字 属性
				String fileName = pro.getValue().getString();
				if (!fileName.endsWith("_signature")) {
					System.out.println(shoujin + depth + "*" + path + "=====> not signature");

					continue;
				}
				System.out.println(shoujin + "------------>" + pro.getName() + "===" + pro.getValue().getString());

				String folderId = "";
				//String folderPath = entry.getPath();//父目录路径
				String regExp = "^/([^/]+)(.*)/([^/]+)$";
				Pattern pattern = Pattern.compile(regExp);
				Matcher matcher = pattern.matcher(path);
				if (matcher.matches()) {
					/*for(int i = 0; i < matcher.groupCount(); i++){
					  System.out.println(matcher.group(i + 1));
					}*/
					String moduleName = matcher.group(1);
					// System.out.println(moduleName);
					String nodeName2 = matcher.group(2);
					// System.out.println(nodeName2);
					String nodeName11 = nodeName2.substring(0, nodeName2.length() / 2);
					String nodeName12 = nodeName2.substring(nodeName2.length() / 2);
					if (nodeName11.equals(nodeName12)) {
						folderId = nodeName11.substring(1);
					} else {
						throw new Exception("cann't find folderID");
					}

				}

				//String folderId = folderPath.substring(folderPath.lastIndexOf("/") + 1);

				Node contentNode = entry.getNode(JcrConstants.CONTENT);
				if (contentNode.isNodeType(JcrConstants.CONTENT_TYPE)) {
					pro = contentNode.getProperty(JcrConstants.AUTHOR);
					// String operator =  pro.getValue().getString();

					pro = contentNode.getProperty(JcrConstants.JCR_MIMETYPE);

					String contentType = pro.getValue().getString();
					System.out.println(shoujin + "------------>" + pro.getName() + "===" + contentType);
					pro = contentNode.getProperty(JcrConstants.JCR_DATA);
					Binary binary = pro.getBinary();
					InputStream is = binary.getStream();

					/*com.wellsoft.pt.repository.entity.mongo.file.FileEntity fileEntitye = filedao.saveFile(fileName, contentType, is, "", "", "", "", EnumFilePurpose.NOMAL);
					folderDao.createFolder(folderId, null);
					folderDao.pushFileToFolder(folderId, fileEntitye.getId()); */
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024 * 20];
					int size = 0;
					while ((size = is.read(buffer)) != -1) {
						baos.write(buffer, 0, size);
					}
					String json = baos.toString("utf-8");
					System.out.println(json);

					Map<String, Class> classMap = new HashMap<String, Class>();
					classMap.put("files", FileUpload.class);
					//classMap.put("folder_id", Id.class);

					//LogicFileInfo logicFileInfo =(LogicFileInfo) JSONObject.toBean(JSONObject.fromObject(json),LogicFileInfo.class , classMap);

					FormDataSignature formDataSignature = (FormDataSignature) //JsonUtils.toBean(json, FormDataSignature.class);
					JSONObject.toBean(JSONObject.fromObject(json), FormDataSignature.class, classMap);
					///JsonUtils.j

					//formDataSignature.get
					//System.out.println(formDataSignature);
					String formData = formDataSignature.getFormData();
					InputStream formDataInputstream = new StringInputStream(formData);
					String digest_value = formDataSignature.getDigestValue();
					String digest_algorithm = formDataSignature.getDigestAlgorithm();
					if (digest_algorithm == null) {
						digest_algorithm = "MD5";
					}
					String signature_value = formDataSignature.getSignatureValue();
					String certificate = formDataSignature.getCertificate();

					com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity fileEntitye = mongoFileService
							.saveFile(folderId, formDataInputstream, digest_value, digest_algorithm, signature_value,
									certificate);
					formDataInputstream.close();

					//folderDao.createFolder(folderId, null);
					mongoFileService.pushFileToFolder(folderId, fileEntitye.getId(), "signature_");

					for (FileUpload fileupload : formDataSignature.getFiles()) {
						//System.out.println(fileupload.getNodeName());
						if (fileupload.isSignUploadFile()) {//表示有签名 
							String nodeName = fileupload.getNodeName();
							digest_value = fileupload.getDigestValue();
							digest_algorithm = fileupload.getDigestAlgorithm();
							signature_value = fileupload.getSignatureValue();
							certificate = fileupload.getCertificate();
							fileName = fileupload.getFilename();

							List<com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity> files = null;

							files = mongoFileService.getFilesFromFolder(nodeName, null);
							if (files == null) {
								continue;
							}

							boolean getfile = false;
							for (com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity file : files) {
								if (file.getFileName().equals(fileName)) {
									String fileId = file.getId();
									mongoFileService.updateSignature(fileId, digest_value, digest_algorithm,
											signature_value, certificate);
									getfile = true;
									break;
								}
							}
							if (!getfile) {
								throw new Exception("cann't find the filename[" + fileName + "] in folder[" + nodeName
										+ "]");
							}
							//System.out.println(nodeName);
						}
					}
					baos.flush();
					baos.close();
					is.close();

				}

			}

			showEverything1(entry);

		}
	}

	private void synNomalFile(String moduleName) throws RepositoryException {

		List<Tenant> tenants = tenantService.getActiveTenants();
		for (Tenant tenant : tenants) {
			mongoFileService.setCurrentTenantId(tenant.getId());
			try {
				Session session = JcrRepositoryHelper.getSystemSession(tenant.getId());
				Node root = null;
				root = session.getRootNode();
				System.out.println(root.getPath());
				showEverything2(root, moduleName);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	String ModuleName_of_EXCHANGE_UPLOAD_FILE = "EXCHANGE_UPLOAD_FILE";
	String ModuleName_of_ORG = "ORG";
	String moduleName_of_cd_print_template = "cd_print_template";

	private void showEverything2(Node root, String syModule) throws Exception {
		int depth = root.getDepth();
		int depthIndex = depth;
		String shoujin = "";
		for (; depthIndex > -1; depthIndex--) {
			shoujin += " ";
			//System.out.print(shoujin);
		}
		//System.out.println(depth + ":");
		Iterator<Node> nodes = JcrUtils.getChildNodes(root).iterator();
		while (nodes.hasNext()) {
			Node entry = nodes.next();
			String path = entry.getPath();
			if (path.indexOf(syModule) == -1) {//表单在SynController已经处理
				System.out.println("不处理模块:" + path);
				continue;
			}
			System.out.println(shoujin + depth + "*" + path + "=====>" + entry.getPrimaryNodeType().getName());

			if (entry.isNodeType(JcrConstants.FILE)) {
				NOMAL_FILE_TOTALCOUNT++;//文件总数递增
				Property pro = entry.getProperty(JcrConstants.NAME);//文件名字 属性
				String fileName = pro.getValue().getString();
				if (fileName.endsWith("_signature")) {//目前没有签名
					continue;
				}
				System.out.println(shoujin + "------------>" + pro.getName() + "===" + pro.getValue().getString());
				System.out.println("/***************************************");
				long time1 = System.currentTimeMillis();
				System.out.println("*开始时间:" + time1);
				String folderId = "";
				String folderIdBeforeMd5 = "";
				//String folderPath = entry.getPath();//父目录路径
				String regExp = "^/([^/]+)(.*)/([^/]+)$";
				Pattern pattern = Pattern.compile(regExp);
				Matcher matcher = pattern.matcher(path);
				if (matcher.matches()) {
					/*for(int i = 0; i < matcher.groupCount(); i++){
					  System.out.println(matcher.group(i + 1));
					}*/
					String moduleName = matcher.group(1);
					System.out.println(moduleName);
					String nodeName2 = matcher.group(2);
					System.out.println(nodeName2);
					String nodeName11 = nodeName2.substring(0, nodeName2.length() / 2);
					String nodeName12 = nodeName2.substring(nodeName2.length() / 2);
					if (nodeName11.equals(nodeName12)) {
						folderId = nodeName11.substring(1);
					} else {
						throw new Exception("cann't find folderID");
					}
					if (ModuleName_of_EXCHANGE_UPLOAD_FILE.equals(syModule)) {//如果是数据交换模块
						folderIdBeforeMd5 = folderId;
						folderId = Md5PasswordEncoderUtils.encodePassword(folderId, null);
					}

				}

				//String folderId = folderPath.substring(folderPath.lastIndexOf("/") + 1);
				if (folderIdBeforeMd5.indexOf("streamingData") != -1) {//数据交换附件及其签名
					logger.error("==============>数据交换有附件 ................");
				}

				Node contentNode = entry.getNode(JcrConstants.CONTENT);

				if (contentNode.isNodeType(JcrConstants.CONTENT_TYPE)) {
					pro = contentNode.getProperty(JcrConstants.AUTHOR);
					//String operator =  pro.getValue().getString();

					pro = contentNode.getProperty(JcrConstants.JCR_MIMETYPE);
					//System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
					//String contentType =  pro.getValue().getString();

					pro = contentNode.getProperty(JcrConstants.JCR_DATA);
					long time3 = System.currentTimeMillis();
					System.out.println("***开始mongo操作");
					Binary binary = pro.getBinary();
					InputStream is = binary.getStream();
					try {
						long time5 = System.currentTimeMillis();
						MongoFileEntity fileEntitye = null;
						if (syModule.equals(ModuleName_of_ORG)) {//用户图片，文件ID和文件夹ID一样,一个文件夹只对应一个文件
							String fileID = folderId;
							fileEntitye = mongoFileService.saveFile(fileID, fileName, is, "", "", "", "");
						} else if (syModule.equals(ModuleName_of_EXCHANGE_UPLOAD_FILE)) {//数据交换fileID为nodename组合fileName再用md5加密
							String fileId = Md5PasswordEncoderUtils.encodePassword(folderIdBeforeMd5 + fileName, null);
							fileEntitye = mongoFileService.saveFile(fileId, fileName, is, "", "", "", "");
						} else {
							fileEntitye = mongoFileService.saveFile(fileName, is, "", "", "", "");
						}
						long time6 = System.currentTimeMillis();
						System.out.println("*保存到mongo库耗时:" + (time6 - time5) / 1000.0 + "s");

						NOMAL_FILE_SUCESSFULCOUNT++;//文件成功个数递增

						mongoFileService.pushFileToFolder(folderId, fileEntitye.getId(), "");
						long time7 = System.currentTimeMillis();

						System.out.println("*保存文件关系至oracle库耗时:" + (time7 - time6) / 1000.0 + "s");
						NORMAL_FOLDER_MAP.put(folderId, null);

						is.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
					long time4 = System.currentTimeMillis();
					System.out.println("*mongo文件操作共耗时:" + (time4 - time3) / 1000.0 + "s");
				} else {
					System.out.println("*not conenttype");
				}

				long time2 = System.currentTimeMillis();
				System.out.println("*结束时间:" + time2);

				System.out.println("*总耗时:" + (time2 - time1) / 1000.0 + "s");

				System.out.println("***************************************/");
				//System.out.println(shoujin + "------------>size" + contentNode.getProperty(JcrConstants.SIZE).getValue().getString());
			}
			/*else if(entry.isNodeType(JcrConstants.CONTENT_TYPE)){
				Property pro = entry.getProperty(JcrConstants.SIZE);
				System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
				 pro = entry.getProperty(JcrConstants.AUTHOR);
				System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
				 pro = entry.getProperty(JcrConstants.JCR_MIMETYPE);
				System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
				 pro = entry.getProperty(JcrConstants.JCR_DATA);
					//System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
					
					 pro = entry.getProperty(JcrConstants.JCR_LASTMODIFIED);
						System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
			} */

			/*PropertyIterator it = entry.getProperties();
			while(it.hasNext()){
				Property pro = it.nextProperty();
				String name = pro.getName();
				String value = null;
				//Value valueObj = pro.getValue();
				try{if(name.equals("jcr:uuid"))
					value= pro.getValue().getString();
				}catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println(shoujin + "------------>" + name + "===" + value 
						);
			}
			
			*/

			showEverything2(entry, syModule);

			/*else if(entry.isNodeType(JcrConstants.CONTENT_TYPE)){
				Property pro = entry.getProperty(JcrConstants.SIZE);
				System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
				 pro = entry.getProperty(JcrConstants.AUTHOR);
				System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
				 pro = entry.getProperty(JcrConstants.JCR_MIMETYPE);
				System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
				 pro = entry.getProperty(JcrConstants.JCR_DATA);
					System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
					
					 pro = entry.getProperty(JcrConstants.JCR_LASTMODIFIED);
						System.out.println(shoujin + "------------>" + pro.getName()  + "===" + pro.getValue().getString());
				
			}*/

		}
	}

	public int getFORMDATA_NODENAME_TOTALCOUNT() {
		return FORMDATA_NODENAME_TOTALCOUNT;
	}

	public int getFORMDATA_NODENAME_FAILCOUNT() {
		return FORMDATA_NODENAME_FAILCOUNT;
	}

	public int getFORMDATA_NODENAME_SUCESSFULCOUNT() {
		return FORMDATA_NODENAME_SUCESSFULCOUNT;
	}

	public int getFORMDATA_FILE_TOTALCOUNT() {
		return FORMDATA_FILE_TOTALCOUNT;
	}

	public int getFORMDATA_FILE_FAILCOUNT() {
		return FORMDATA_FILE_FAILCOUNT;
	}

	public int getFORMDATA_FILE_SUCESSFULCOUNT() {
		return FORMDATA_FILE_SUCESSFULCOUNT;
	}

	public int getFORMDATA_COPYFILE_TOTALCOUNT() {
		return FORMDATA_COPYFILE_TOTALCOUNT;
	}

	public int getFORMDATA_COPYFILE_FAILCOUNT() {
		return FORMDATA_COPYFILE_FAILCOUNT;
	}

	public int getFORMDATA_COPYFILE_SUCESSFULCOUNT() {
		return FORMDATA_COPYFILE_SUCESSFULCOUNT;
	}

	public int getFORMDATA_SIGNFILE_TOTALCOUNT() {
		return FORMDATA_SIGNFILE_TOTALCOUNT;
	}

	public int getFORMDATA_SIGNFILE_SUCESSFULCOUNT() {
		return FORMDATA_SIGNFILE_SUCESSFULCOUNT;
	}

	public Map<String, Object> getFORMDATA_FOLDER_MAP() {
		return FORMDATA_FOLDER_MAP;
	}

	public FormSignatureDataService getFormSignatureDataService() {
		return formSignatureDataService;
	}

}
