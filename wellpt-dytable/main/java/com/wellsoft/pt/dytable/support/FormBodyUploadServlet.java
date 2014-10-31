package com.wellsoft.pt.dytable.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.repository.service.MongoFileService;

public class FormBodyUploadServlet extends HttpServlet {

	public FormBodyUploadServlet() {
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RandomAccessFile raf = null;
		File rawFile = null;
		try {
			rawFile = new File(workingpath + "raw_" + UUID.randomUUID());
			if (!rawFile.getParentFile().exists()) {
				rawFile.getParentFile().mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(rawFile);
			IOUtils.copy(request.getInputStream(), fos);
			fos.flush();
			fos.close();
			raf = new RandomAccessFile(rawFile, "r");
			String filename = getFileName(raf);
			printOut(raf, filename);
			response.getWriter().println(filename);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				raf.close();
			}
			if (rawFile != null && rawFile.exists()) {
				rawFile.delete();
			}
		}
	}

	private String getFileName(RandomAccessFile is) throws UnsupportedEncodingException {
		byte[] buf = new byte[KB];
		try {
			is.read(buf, 0, KB);
		} catch (IOException e) {
			return null;
		}
		String token = "\"";
		String temp = new String(buf, "UTF-8");
		int index1 = temp.indexOf("filename");
		temp = temp.substring(index1);
		index1 = temp.indexOf(token);
		temp = temp.substring(index1 + 1);
		index1 = temp.indexOf(token);
		temp = java.net.URLDecoder.decode(temp.substring(0, index1), "UTF-8");
		return org.apache.commons.lang.StringUtils.replace(temp, "?", "");
	}

	private boolean isStreamFlagStart(byte[] bytes) {
		return new String(bytes).equals("stream\r\n\r\n");
	}

	private void printOut(RandomAccessFile raf, String filename) throws IOException {
		FileOutputStream os = null;
		long postSize = raf.length();
		try {
			File f1 = new File(workingpath + filename);
			os = new FileOutputStream(f1);
			int i = PrxfixLen;
			int readSize;
			do {
				byte buf1[] = new byte[10];
				raf.seek(i);
				readSize = raf.read(buf1, 0, 10);
				if (readSize == -1)
					continue;
				if (isStreamFlagStart(buf1))
					break;
				i++;
				buf1 = null;
			} while (readSize != -1);

			//找到标志位后开始写入文件
			i += 10;
			int filesize = Long.valueOf(postSize).intValue() - i - SuffixLen;
			byte buf[] = new byte[10240];
			int iTimes = filesize / 10240;
			int iMode = filesize % 10240;
			raf.seek(i);
			for (int j = 0; j < iTimes; j++) {
				raf.read(buf);
				os.write(buf);
			}
			buf = null;
			buf = new byte[iMode];
			raf.read(buf);
			os.write(buf);
			buf = null;

			//FileService fileService = ApplicationContextHolder.getBean(FileService.class);
			MongoFileService fileService = ApplicationContextHolder.getBean(MongoFileService.class);

			String[] filenames = filename.split("\\.");
			String fileID = filenames[0];
			/* List<Folder> folders = fileService.getFoldersOfFile(fileID);//获取引用该文件的文件夹
			if(folders != null && folders.size() > 0){
				//fileService.popFileFromFolder(folders.get(0).getDataUuid(), fileID);
				 try {
					 fileService.copyFile(fileID);//这里做备份，如果用户在保存
					fileService.destroyFile(fileID);
				} catch (IntrospectionException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) { 
				} 
			}*/

			fileService.saveFile(fileID, filename, new FileInputStream(f1));

			/*String nodeName = "Body_" + filenames[0];
			FileEntity fe = new FileEntity();
			InputStream fileIs = new FileInputStream(f1);
			fe.setFile(fileIs);
			fe.setFilename(filename);*/

			/*boolean isExist = fileService.isExistFile(nodeName, filename);
			if (isExist == true) {
				fileService.deleteFile(nodeName, filename);
				fileService.uploadTempFile(fe, nodeName);
			} else {
				fileService.uploadTempFile(fe, nodeName);
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null)
				os.close();
		}
	}

	private static final long serialVersionUID = 0xcd0cf1d3b3914918L;
	private String workingpath = Config.APP_DATA_DIR + "dytable/MainBody";
	private static final int KB = 1024;
	private static final int SuffixLen = 48;
	private static final int PrxfixLen = 44;

}