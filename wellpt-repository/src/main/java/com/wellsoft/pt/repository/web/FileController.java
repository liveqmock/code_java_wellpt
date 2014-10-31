package com.wellsoft.pt.repository.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.repository.entity.FileEntity;
import com.wellsoft.pt.repository.entity.FileUpload;
import com.wellsoft.pt.repository.support.JcrConstants;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.encode.JsonBinder;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;
import com.wellsoft.pt.utils.web.FileDownloadUtils;

@Controller
@RequestMapping("/repository/file")
public class FileController extends BaseController {
	@Autowired
	private FileService fileService;

	/**
	 * 多文件上传
	 * 
	 * @param request
	 * @param fileUpload
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public void uploadFiles(MultipartHttpServletRequest multipartRequest, HttpServletResponse response,
			@RequestParam("attach") String uuid, @RequestParam("moduleName") String moduleName,
			@RequestParam(value = "signUploadFile", defaultValue = "false", required = false) boolean signUploadFile,
			@RequestParam(value = "logUploadFile", defaultValue = "false", required = false) boolean logUploadFile)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		// boolean signUploadFile = Config.SIGN_UPLOAD_FILE;
		List<FileUpload> uploadFiles = new ArrayList<FileUpload>();
		String nodeName = uuid;
		if ("".equals(uuid) || "undefined".equals(uuid)) {
			nodeName = UUID.randomUUID().toString();
		}
		try {
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				String fieldName = iterator.next();
				MultipartFile multifile = multipartRequest.getFile(fieldName);
				String filename = multifile.getOriginalFilename();
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
					IOUtils.copyLarge(multifile.getInputStream(), fos);
					IOUtils.closeQuietly(fos);

					// 计算MD5消息摘要
					String digestValue = digestAsMD5(tempFile);
					fileUpload.setDigestAlgorithm("MD5");
					fileUpload.setDigestValue(digestValue);
					fileUpload.setSignUploadFile(true);

					FileInputStream fis = new FileInputStream(tempFile);
					FileEntity fileEntity = new FileEntity();
					fileEntity.setFilename(multifile.getOriginalFilename());
					fileEntity.setFile(fis);
					// 单一文件用文件名作为的节点名称
					if (StringUtils.isBlank(moduleName)) {
					} else {
					}
					IOUtils.closeQuietly(fis);
				} else {
					FileEntity fileEntity = new FileEntity();
					fileEntity.setFilename(multifile.getOriginalFilename());
					fileEntity.setFile(multifile.getInputStream());
					// 单一文件用文件名作为的节点名称
					if (StringUtils.isBlank(moduleName)) {
					} else {
					}
				}

				// 保存文件上传记录
				UserDetails userDetails = SpringSecurityUtils.getCurrentUser();
				fileUpload.setUserId(userDetails.getUserId());
				fileUpload.setUserName(userDetails.getUserName());
				fileUpload.setDepartmentId(userDetails.getDepartmentId());
				fileUpload.setDepartmentName(userDetails.getDepartmentName());
				fileUpload.setModuleName(moduleName);
				fileUpload.setNodeName(nodeName);
				fileUpload.setFilename(filename);
				fileUpload.setContentType(contentType);
				fileUpload.setFileSize(size);

				// 保存上传日志
				if (logUploadFile) {
					fileService.saveFileUpload(fileUpload);
				}

				uploadFiles.add(fileUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setSuccess(false);
			resultMessage.setMsg(new StringBuilder(e.getMessage()));
			response.setContentType("text/html; charset=utf-8");
			response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
			response.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
			return;
		}
		// res.setContentType("text/html; charset=utf-8");
		// res.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
		resultMessage.setSuccess(true);
		resultMessage.setData(uploadFiles);
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter().write(JsonBinder.buildNormalBinder().toJson(resultMessage).toString());
	}

	@RequestMapping("download")
	public void download(@RequestParam("filename") String filename, @RequestParam("modulename") String modulename,
			@RequestParam("nodename") String nodename, HttpServletResponse res, HttpServletRequest request) {
		InputStream is = fileService.downFile(modulename, nodename, filename);
		FileDownloadUtils.download(request, res, is, filename);
	}

	@RequestMapping("downloadtemp")
	public void downloadTemp(@RequestParam("filename") String filename, @RequestParam("attach") String attach,
			HttpServletResponse res, HttpServletRequest request) {
		InputStream is = fileService.downFile(JcrConstants.TEMP_NODE_NAME, attach, filename);
		FileDownloadUtils.download(request, res, is, filename);
	}

	@RequestMapping("downloadall")
	public void downLoadAll(@RequestParam("attach") String attach) {
		String moduleName = "DY_TABLE_FORM";
		fileService.downFiles(moduleName, attach);
	}

	@RequestMapping(value = "downLoadFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> downLoadFiles(@RequestParam("attach") String attach) {
		String moduleName = "DY_TABLE_FORM";
		List<FileEntity> list = fileService.downFiles(moduleName, attach);
		List<String> files = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (FileEntity fileEntity : list) {
			String edittime = sdf.format(fileEntity.getEdittime().getTime());
			files.add(fileEntity.getFilename() + ";" + edittime);
		}
		return files;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResultMessage delete(@RequestParam("filename") String filename, @RequestParam("attach") String attach) {
		ResultMessage resultMessage = new ResultMessage();
		try {
			fileService.deleteFile(JcrConstants.TEMP_NODE_NAME, attach, filename);
		} catch (Exception ex) {
			resultMessage.setSuccess(false);
		}
		return resultMessage;
	}

	@RequestMapping("deleteform")
	@ResponseBody
	public ResultMessage deleteForm(@RequestParam("filename") String filename, @RequestParam("attach") String attach) {
		ResultMessage resultMessage = new ResultMessage();
		try {
			String moduleName = "DY_TABLE_FORM";
			fileService.deleteFile(moduleName, attach, filename);
		} catch (Exception ex) {
			resultMessage.setSuccess(false);
		}
		return resultMessage;
	}

	/**
	 * @param moduleName
	 *            需要存放的模块名
	 * @param nodeName
	 *            节点名，是存放数据库后的节点uuid
	 * @param attach
	 */
	@RequestMapping("testsubmit")
	@ResponseBody
	public ResultMessage testSubmit(@RequestParam("modulename") String modulename,
			@RequestParam("nodename") String nodename, @RequestParam("attach") String attach) {
		ResultMessage resultMessage = new ResultMessage();
		List<FileEntity> files = fileService.getFiles(modulename, nodename);
		resultMessage.setData(files);
		return resultMessage;
	}

	/**
	 * 对文件用MD5算法生成消息摘要
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String digestAsMD5(File file) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		FileInputStream fis = new FileInputStream(file);
		FileChannel ch = fis.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		m.update(byteBuffer);
		byte s[] = m.digest();
		IOUtils.closeQuietly(fis);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length; i++) {
			sb.append(Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6));
		}
		return sb.toString();
	}
}
