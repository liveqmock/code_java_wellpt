package com.wellsoft.pt.repository.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mongodb.gridfs.GridFSDBFile;
import com.wellsoft.pt.core.mt.service.TenantService;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dytable.service.FormSignatureDataService;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.repository.entity.FileUpload;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.encode.JsonBinder;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;
import com.wellsoft.pt.utils.web.FileDownloadUtils;

@Controller
@RequestMapping("/repository/file/mongo")
public class MongoFileController extends BaseController {
	public interface PURPOSE_Constance {
		public final String SIGNATURE = "signature_";
	}

	Logger logger = Logger.getLogger(MongoFileController.class);

	@Autowired
	MongoFileService mongoFileService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	FormSignatureDataService formSignatureDataService;

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param fileUpload
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/savefiles", method = RequestMethod.POST)
	public void saveFiles(
			MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response,
			@RequestParam(value = "signUploadFile", defaultValue = "false", required = false) boolean signUploadFile)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		// boolean signUploadFile = Config.SIGN_UPLOAD_FILE;
		List<FileUpload> uploadFiles = new ArrayList<FileUpload>();

		try {
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				MultipartFile multifile = multipartRequest.getFile(fieldName);
				String filename = multifile.getOriginalFilename();
				InputStream multifileIS = multifile.getInputStream();
				String contentType = multifile.getContentType();
				long size = multifile.getSize();
				FileUpload fileUpload = new FileUpload();
				if (signUploadFile) {
					File uploadDir = new File(Config.UPLOAD_DIR);
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					String tempUuid = UUID.randomUUID().toString();
					File tempFile = new File(uploadDir, tempUuid);
					FileOutputStream fos = new FileOutputStream(tempFile);

					IOUtils.copyLarge(multifileIS, fos);
					IOUtils.closeQuietly(fos);

					// 计算MD5消息摘要
					String digestValue = FileController.digestAsMD5(tempFile);
					fileUpload.setDigestAlgorithm("MD5");
					fileUpload.setDigestValue(digestValue);
					fileUpload.setSignatureValue("");
					fileUpload.setCertificate("");
					fileUpload.setSignUploadFile(true);

					FileInputStream fis = new FileInputStream(tempFile);

					MongoFileEntity file = this.mongoFileService.saveFile(
							DynamicUtils.getRandomUUID(), filename, fis,
							fileUpload.getDigestValue(),
							fileUpload.getDigestAlgorithm(),
							fileUpload.getSignatureValue(),
							fileUpload.getCertificate());
					fileUpload.setFileID(file.getId());
					fileUpload.setContentType(file.getContentType());
					IOUtils.closeQuietly(fis);

					tempFile.delete();
				} else {
					MongoFileEntity file = this.mongoFileService
							.saveFile(DynamicUtils.getRandomUUID(), filename,
									multifileIS);
					fileUpload.setFileID(file.getId());
					fileUpload.setContentType(file.getContentType());

				}

				IOUtils.closeQuietly(multifileIS);

				// 保存文件上传记录
				UserDetails userDetails = SpringSecurityUtils.getCurrentUser();
				fileUpload.setUserId(userDetails.getUserId());
				fileUpload.setUserName(userDetails.getUserName());
				fileUpload.setDepartmentId(userDetails.getDepartmentId());
				fileUpload.setDepartmentName(userDetails.getDepartmentName());
				fileUpload.setFilename(filename);
				fileUpload.setContentType(contentType);
				fileUpload.setFileSize(size);

				uploadFiles.add(fileUpload);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setSuccess(false);
			resultMessage.setMsg(new StringBuilder(e.getMessage()));
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			response.getWriter().write(
					JsonBinder.buildNormalBinder().toJson(resultMessage)
							.toString());
			return;
		}
		// res.setContentType("text/html; charset=utf-8");
		// res.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
		resultMessage.setSuccess(true);
		resultMessage.setData(uploadFiles);
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter()
				.write(JsonBinder.buildNormalBinder().toJson(resultMessage)
						.toString());
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param fileUpload
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTextBody", method = RequestMethod.POST)
	public void saveTextBody(
			MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response,
			@RequestParam(value = "signUploadFile", defaultValue = "false", required = false) boolean signUploadFile)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		// boolean signUploadFile = Config.SIGN_UPLOAD_FILE;
		List<FileUpload> uploadFiles = new ArrayList<FileUpload>();

		try {
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				MultipartFile multifile = multipartRequest.getFile(fieldName);
				String filename = multifile.getOriginalFilename();
				String fileID = filename.split("\\.")[0];
				InputStream multifileIS = multifile.getInputStream();
				String contentType = multifile.getContentType();
				long size = multifile.getSize();
				FileUpload fileUpload = new FileUpload();
				if (signUploadFile) {
					File uploadDir = new File(Config.UPLOAD_DIR);
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					String tempUuid = UUID.randomUUID().toString();
					File tempFile = new File(uploadDir, tempUuid);
					FileOutputStream fos = new FileOutputStream(tempFile);

					IOUtils.copyLarge(multifileIS, fos);
					IOUtils.closeQuietly(fos);

					// 计算MD5消息摘要
					String digestValue = FileController.digestAsMD5(tempFile);
					fileUpload.setDigestAlgorithm("MD5");
					fileUpload.setDigestValue(digestValue);
					fileUpload.setSignatureValue("");
					fileUpload.setCertificate("");
					fileUpload.setSignUploadFile(true);

					FileInputStream fis = new FileInputStream(tempFile);

					MongoFileEntity file = this.mongoFileService.saveFile(
							fileID, filename, fis, fileUpload.getDigestValue(),
							fileUpload.getDigestAlgorithm(),
							fileUpload.getSignatureValue(),
							fileUpload.getCertificate());
					fileUpload.setFileID(file.getId());
					IOUtils.closeQuietly(fis);
					tempFile.delete();
				} else {
					MongoFileEntity file = this.mongoFileService
							.saveFile(DynamicUtils.getRandomUUID(), filename,
									multifileIS);
					fileUpload.setFileID(file.getId());

				}

				IOUtils.closeQuietly(multifileIS);

				// 保存文件上传记录
				UserDetails userDetails = SpringSecurityUtils.getCurrentUser();
				fileUpload.setUserId(userDetails.getUserId());
				fileUpload.setUserName(userDetails.getUserName());
				fileUpload.setDepartmentId(userDetails.getDepartmentId());
				fileUpload.setDepartmentName(userDetails.getDepartmentName());
				fileUpload.setFilename(filename);
				fileUpload.setContentType(contentType);
				fileUpload.setFileSize(size);

				uploadFiles.add(fileUpload);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setSuccess(false);
			resultMessage.setMsg(new StringBuilder(e.getMessage()));
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			response.getWriter().write(
					JsonBinder.buildNormalBinder().toJson(resultMessage)
							.toString());
			return;
		}
		// res.setContentType("text/html; charset=utf-8");
		// res.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
		resultMessage.setSuccess(true);
		resultMessage.setData(uploadFiles);
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter()
				.write(JsonBinder.buildNormalBinder().toJson(resultMessage)
						.toString());
	}

	/**
	 * 更新签名
	 * 
	 * @param response
	 * @param folderID
	 * @param jsonData
	 *            字段也fileID的对应关系
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateSignature")
	public void updateSignature(HttpServletResponse response,
			@RequestParam(value = "fileID") String fileID,
			@RequestParam(value = "signatureData") String signatureData)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		SignatureBean signature = JsonBinder.buildNormalBinder().fromJson(
				signatureData, SignatureBean.class);
		try {
			this.mongoFileService.updateSignature(fileID,
					signature.getDigestValue(), signature.getDigestAlgorithm(),
					signature.getSignatureValue(), signature.getCertificate());
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setSuccess(false);
			resultMessage.setMsg(new StringBuilder(e.getMessage()));
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			response.getWriter().write(
					JsonBinder.buildNormalBinder().toJson(resultMessage)
							.toString());
			return;
		}
		// res.setContentType("text/html; charset=utf-8");
		// res.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
		resultMessage.setSuccess(true);

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter()
				.write(JsonBinder.buildNormalBinder().toJson(resultMessage)
						.toString());
	}

	/**
	 * 文件推入文件夹
	 * 
	 * @param response
	 * @param folderID
	 * @param jsonData
	 *            字段也fileID的对应关系
	 * @throws IOException
	 */
	@RequestMapping(value = "/pushFilesToFolder")
	public void pushFilesToFolder(HttpServletResponse response,
			@RequestParam(value = "folderID") String folderID, String jsonData)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		Map<String, String[]> fieldFileIDs = JsonBinder.buildNormalBinder()
				.fromJson(jsonData, Map.class);
		try {
			Iterator<Entry<String, String[]>> it = fieldFileIDs.entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, String[]> entry = it.next();
				String field = entry.getKey();
				String[] fileIDs = entry.getValue();
				List<String> fileIDList = Arrays.asList(fileIDs);
				this.mongoFileService.pushFilesToFolder(folderID, fileIDList,
						field);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setSuccess(false);
			resultMessage.setMsg(new StringBuilder(e.getMessage()));
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			response.getWriter().write(
					JsonBinder.buildNormalBinder().toJson(resultMessage)
							.toString());
			return;
		}
		// res.setContentType("text/html; charset=utf-8");
		// res.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
		resultMessage.setSuccess(true);

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter()
				.write(JsonBinder.buildNormalBinder().toJson(resultMessage)
						.toString());
	}

	/**
	 * 上传文件并推送入文件夹
	 * 
	 * @param request
	 * @param fileUpload
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveFilesAndPushToFolder", method = RequestMethod.POST)
	public void saveFilesAndPushToFolder(
			MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response,
			@RequestParam("folderID") String folderID,
			@RequestParam(value = "signUploadFile", defaultValue = "false", required = false) boolean signUploadFile)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		// boolean signUploadFile = Config.SIGN_UPLOAD_FILE;
		List<FileUpload> uploadFiles = new ArrayList<FileUpload>();
		if ("".equals(folderID) || "undefined".equals(folderID)) {
			folderID = UUID.randomUUID().toString();
		}

		try {
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				MultipartFile multifile = multipartRequest.getFile(fieldName);
				String filename = multifile.getOriginalFilename();
				InputStream multifileIS = multifile.getInputStream();
				String contentType = multifile.getContentType();
				long size = multifile.getSize();
				FileUpload fileUpload = new FileUpload();
				if (signUploadFile) {
					File uploadDir = new File(Config.UPLOAD_DIR);
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					String tempUuid = UUID.randomUUID().toString();
					File tempFile = new File(uploadDir, tempUuid);
					FileOutputStream fos = new FileOutputStream(tempFile);

					IOUtils.copyLarge(multifileIS, fos);
					IOUtils.closeQuietly(fos);

					// 计算MD5消息摘要
					String digestValue = FileController.digestAsMD5(tempFile);
					fileUpload.setDigestAlgorithm("MD5");
					fileUpload.setDigestValue(digestValue);
					fileUpload.setSignUploadFile(true);

					FileInputStream fis = new FileInputStream(tempFile);

					MongoFileEntity file = this.mongoFileService.saveFile(
							DynamicUtils.getRandomUUID(), filename, fis,
							fileUpload.getDigestValue(),
							fileUpload.getDigestAlgorithm(),
							fileUpload.getSignatureValue(),
							fileUpload.getCertificate());
					this.mongoFileService.pushFileToFolder(file.getId(),
							folderID, null);
					fileUpload.setFileID(file.getId());

					IOUtils.closeQuietly(fis);
					tempFile.delete();
				} else {

					MongoFileEntity file = this.mongoFileService
							.saveFile(DynamicUtils.getRandomUUID(), filename,
									multifileIS);

					this.mongoFileService.pushFileToFolder(file.getId(),
							folderID, null);
					fileUpload.setFileID(file.getId());

				}

				IOUtils.closeQuietly(multifileIS);

				// 保存文件上传记录
				UserDetails userDetails = SpringSecurityUtils.getCurrentUser();
				fileUpload.setUserId(userDetails.getUserId());
				fileUpload.setUserName(userDetails.getUserName());
				fileUpload.setDepartmentId(userDetails.getDepartmentId());
				fileUpload.setDepartmentName(userDetails.getDepartmentName());
				fileUpload.setFolderID(folderID);
				fileUpload.setFilename(filename);
				fileUpload.setContentType(contentType);
				fileUpload.setFileSize(size);

				uploadFiles.add(fileUpload);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setSuccess(false);
			resultMessage.setMsg(new StringBuilder(e.getMessage()));
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			response.getWriter().write(
					JsonBinder.buildNormalBinder().toJson(resultMessage)
							.toString());
			return;
		}
		// res.setContentType("text/html; charset=utf-8");
		// res.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
		resultMessage.setSuccess(true);
		resultMessage.setData(uploadFiles);
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter()
				.write(JsonBinder.buildNormalBinder().toJson(resultMessage)
						.toString());
	}

	@RequestMapping("download")
	public void download(@RequestParam("fileID") String fileID,
			HttpServletResponse res, HttpServletRequest request)
			throws IOException {
		/*
		 * if(fileID.startsWith(DytableConfig.BODY_FILEID_PREFIX)){ fileID =
		 * fileID.substring(0, fileID.lastIndexOf(".")); }
		 */
		MongoFileEntity file = mongoFileService.getFile(fileID);
		InputStream is = file.getInputstream();
		String fileName = file.getFileName();
		FileDownloadUtils.download(request, res, is, fileName);
		is.close();
	}

	@RequestMapping("/downloadBody/{fileID}")
	public void downloadBody(@PathVariable("fileID") String fileID,
			HttpServletResponse res, HttpServletRequest request) {
		/*
		 * if(fileID.startsWith(DytableConfig.BODY_FILEID_PREFIX)){ fileID =
		 * fileID.substring(0, fileID.lastIndexOf(".")); }
		 */
		MongoFileEntity file = mongoFileService.getFile(fileID);

		FileDownloadUtils.download(request, res, file.getInputstream(),
				file.getFileName());

	}

	@RequestMapping("downloadSWF")
	public void downloadSWF(@RequestParam("fileID") String fileID,
			HttpServletResponse res, HttpServletRequest request) {
		MongoFileEntity file = null;
		try {
			file = mongoFileService.getReplicaOfSWF(fileID);
			FileDownloadUtils.download(request, res, file.getInputstream(),
					file.getFileName());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@RequestMapping("downloadall")
	public void downLoadAll(@RequestParam("folderID") String folderID,
			@RequestParam("purpose") String purpose)
			throws FileNotFoundException {

		mongoFileService.getFilesFromFolder(folderID, purpose);

	}

	@RequestMapping(value = "downLoadFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> downLoadFiles(
			@RequestParam("folderID") String folderID,
			@RequestParam("purpose") String purpose)
			throws FileNotFoundException {

		List<String> files = new ArrayList<String>();

		List<MongoFileEntity> mongoFiles = mongoFileService.getFilesFromFolder(
				folderID, purpose);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (mongoFiles != null) {
			for (MongoFileEntity mfe : mongoFiles) {
				String edittime = sdf.format(mfe.getUploadDate());
				files.add(mfe.getFileName() + ";" + mfe.getId() + ";"
						+ edittime);
			}
		}

		return files;
	}

	@RequestMapping("popFileFromFolder")
	@ResponseBody
	public ResultMessage delete(@RequestParam("fileID") String fileID,
			@RequestParam("folderID") String folderID) {
		ResultMessage resultMessage = new ResultMessage();
		try {
			this.mongoFileService.popFileFromFolder(folderID, fileID);
		} catch (Exception ex) {
			resultMessage.setSuccess(false);
		}
		return resultMessage;
	}

	@RequestMapping("deleteFile")
	@ResponseBody
	public ResultMessage delete(@RequestParam("fileID") String fileID) {
		ResultMessage resultMessage = new ResultMessage();
		try {
			this.mongoFileService.destroyFile(fileID);
		} catch (Exception ex) {
			resultMessage.setSuccess(false);
		}
		return resultMessage;
	}

	/**
	 * 保存文件和push到文件夹在同一个事务
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("testInOneTra")
	@ResponseBody
	public ResultMessage testInOneTra() throws IOException {
		ResultMessage resultMessage = new ResultMessage();

		this.mongoFileService.testInOneTra();

		return resultMessage;
	}

	@RequestMapping("updateFile")
	@ResponseBody
	public ResultMessage updateFile4OneFileId() throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		String fileId = this.mongoFileService.createFolderID();
		String foderId = this.mongoFileService.createFolderID();
		File file1 = new File("c:/3.txt");
		MongoFileEntity fileEntitye = this.mongoFileService.saveFile(fileId,
				"test.txt", new FileInputStream(file1));
		System.out.println(fileEntitye.getFileID().equals(fileId));
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");
		File file2 = new File("c:/4.txt");
		fileEntitye = this.mongoFileService.saveFile(fileId, "test2.txt",
				new FileInputStream(file2));
		System.out.println(fileEntitye.getFileID().equals(fileId));
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");
		return resultMessage;
	}

	@RequestMapping("file2Folders")
	@ResponseBody
	public ResultMessage file2Folders() throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		String fileId = this.mongoFileService.createFolderID();
		File file1 = new File("c:/3.txt");
		MongoFileEntity fileEntitye = this.mongoFileService.saveFile(fileId,
				"test.txt", new FileInputStream(file1));
		String foderId = this.mongoFileService.createFolderID();
		String foderId2 = this.mongoFileService.createFolderID();
		this.mongoFileService.pushFileToFolder(foderId, fileId, "test");
		this.mongoFileService.pushFileToFolder(foderId2, fileId, "test");
		return resultMessage;
	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public void file(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "path", required = true) String path)
			throws IOException, ServletException {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(request.getRealPath("/") + "/"
					+ URLDecoder.decode(path, "utf-8"));
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// 以流的形式下载文件。
			/*
			 * InputStream fis = new BufferedInputStream(new
			 * FileInputStream(file)); byte[] buffer = new
			 * byte[fis.available()]; fis.read(buffer); fis.close(); //
			 * 清空response response.reset(); // 设置response的Header
			 * response.addHeader("Content-Disposition", "attachment;filename="
			 * + filename); response.addHeader("Content-Length", "" +
			 * file.length()); OutputStream toClient = new
			 * BufferedOutputStream(response.getOutputStream());
			 * response.setContentType("application/octet-stream");
			 * toClient.write(buffer); toClient.flush(); toClient.close();
			 */
			FileDownloadUtils.download(request, response, new FileInputStream(
					file), filename);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public void deleteFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) String fileID) {
		mongoFileService.destroyFile(fileID);
	}

	/**
	 * 删除没有用的mongo原文件
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("destroyUselessPysicalFile")
	@ResponseBody
	public ResultMessage destroyUselessPysicalFile() throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		String synbeforedays = Config.getValue("mongodb.server.gc.synbeforedays");
		 List<GridFSDBFile> fsdbFiles = this.mongoFileService.findProtoFiles(synbeforedays);
		 if(fsdbFiles == null || fsdbFiles.size() == 0){
			 resultMessage.setData("no files in mongodb");
			 return resultMessage;
		 }
		 
		long uselesscount = 0l;
		long usefulcount = 0l;
		long uselessSize = 0l;
		long usefulSize = 0l;
		long totalcount = 0;
		List<String> physicalFileIds = new ArrayList<String>();
		Map<String, GridFSDBFile> physicalInfos = new HashMap<String, GridFSDBFile>();
		 for(GridFSDBFile fsdbFile:fsdbFiles){
			String physicalFileId = fsdbFile.getId().toString();
			physicalFileIds.add(physicalFileId);
			physicalInfos.put(physicalFileId, fsdbFile);
			totalcount ++;
			if(physicalFileIds.size() <= 60 && totalcount != fsdbFiles.size()){ 
				continue;
			}
			
			List<LogicFileInfo> logicFileInfos = this.mongoFileService.getFilesByPhysicalFileId(physicalFileIds);
			for(String physicalFileIdx:physicalFileIds){
				fsdbFile = physicalInfos.get(physicalFileIdx);
				boolean isUsed = isUsedOfPhysicalFile(physicalFileIdx, logicFileInfos);
				if(isUsed){
					usefulcount ++;
					usefulSize += fsdbFile.getLength();
					logger.info("used file:" + physicalFileIdx + "(" + fsdbFile.getFilename() + ") fulcount: "  + usefulcount + "space:" + usefulSize+ "  total:" + totalcount); 
				}else{
					//this.mongoFileService.destroyProtoFile(fsdbFile.getId().toString()); 
					uselesscount ++;
					uselessSize += fsdbFile.getLength();
					logger.info("------->find a useless file:" + physicalFileIdx + "(" + fsdbFile.getFilename() + ") lesscount:" + uselesscount + " space:" + uselessSize + "  total:" + totalcount);
				}
			}
			
			
			
			physicalFileIds.clear();
			physicalInfos.clear();
		 }
		 logger.info("no useless file any more ^-^ it's cleaned count:" + uselesscount + "  size:" + uselessSize);
		 resultMessage.setData("yes , it's cleaned count:" + uselesscount + "  size:" + uselessSize);
		return resultMessage;
	}

	private boolean isUsedOfPhysicalFile(String physicalFileId, List<LogicFileInfo> logicFileInfos) {
		if(logicFileInfos == null || logicFileInfos.size() == 0){
			return false;
		}
		for(LogicFileInfo lfile: logicFileInfos){
			if(physicalFileId.equals(lfile.getPhysicalFileId())){
				return true;
			}
		}
		return false;
	 
		
	}
	
	
	/**
	 * 删除没有用的mongo原文件
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("destroyUselessLogicFile")
	@ResponseBody
	public ResultMessage destroyUselessLogicFile() throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		long emptycount = 0l;
		long entitycount = 0l;
		 
		long totalcount = 0;
		 int first = 0;
		while(true){
			List<QueryItem> items = this.mongoFileService.getFilesByPage(first, 100);
			
			for(QueryItem item: items){
				//String fileID = item.getString("uuid");
				String key = QueryItem.getKey("PHYSICAL_FILE_ID");
				String PHYSICAL_FILE_ID = (String) item.get(key);
				MongoFileEntity file = this.mongoFileService.findProtoFile(PHYSICAL_FILE_ID);
				if(file == null){
					emptycount ++;
				}else{
					entitycount ++;
				}
				totalcount ++;
				first ++;
				 
			}
			logger.info("yes , it's cleaned count emptycount=" + emptycount + " totalcount=" + totalcount + " entitycount" + entitycount);
			if(items.size() < 100){
				break;
			}
		}
		
		 resultMessage.setData("yes , it's cleaned count emptycount=" + emptycount + " totalcount=" + totalcount + " entitycount" + entitycount);
		return resultMessage;
	}
	 
	
	

}
