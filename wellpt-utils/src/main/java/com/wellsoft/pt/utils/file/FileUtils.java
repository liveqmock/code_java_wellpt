package com.wellsoft.pt.utils.file;

import java.io.*;
import java.util.*;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream.UnicodeExtraFieldPolicy;
import org.apache.commons.io.*;

/**
 * 
* @ClassName: FileOperate
* @Description: 文件操作相关函数
* @author lilin
 */
public class FileUtils {

	/**
	 * 
	* @Title: copyFileToFolder
	* @Description: copy文件
	* @param @param fileName
	* @param @param folderName
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void copyFileToFolder(String fileName, String folderName) throws Exception {
		try {
			File file = new File(fileName);
			if (file.isFile()) {
				if (!folderName.endsWith("\\"))
					folderName += "\\";
				FileInputStream fis = new FileInputStream(file);
				byte[] fileContent = new byte[fis.available()];
				fis.read(fileContent);
				String path = folderName + file.getName();
				FileOutputStream fos = new FileOutputStream(path);
				fos.write(fileContent);
				fis.close();
				fos.close();
			} else {
				throw new Exception(fileName + " is not a file or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	* @Title: cutFileToFolder
	* @Description: 剪切文件到目录
	* @param @param fileName
	* @param @param folderName
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void cutFileToFolder(String fileName, String folderName) throws Exception {
		try {
			File file = new File(fileName);
			if (file.isFile()) {
				copyFileToFolder(fileName, folderName);
				file.delete();
			} else {
				throw new Exception(fileName + " is not a file or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	* @Title: copyFolderFilesToOtherFolder
	* @Description: 目录copy
	* @param @param oldFolderName
	* @param @param folderName
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void copyFolderFilesToOtherFolder(String oldFolderName, String folderName) throws Exception {
		try {
			String strMsg = "";
			File file = new File(oldFolderName);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						try {
							copyFileToFolder(files[i].getPath(), folderName);
						} catch (Exception ee) {
							strMsg += "copy file<<" + files[i].getPath() + ">> error,message:" + ee.getMessage()
									+ "\r\n";
						}
					}
				}
				if (!strMsg.equals("")) {
					throw new Exception(strMsg);
				}
			} else {
				throw new Exception(oldFolderName + " is not a directory or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	* @Title: cutFolderFilesToOtherFolder
	* @Description: 目录剪切
	* @param @param oldFolderName
	* @param @param folderName
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void cutFolderFilesToOtherFolder(String oldFolderName, String folderName) throws Exception {
		try {
			String strMsg = "";
			File file = new File(oldFolderName);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						try {
							cutFileToFolder(files[i].getPath(), folderName);
						} catch (Exception ee) {
							strMsg += "cut file<<" + files[i].getPath() + ">> error,message:" + ee.getMessage()
									+ "\r\n";
						}
					}
				}
				if (!strMsg.equals("")) {
					throw new Exception(strMsg);
				}
			} else {
				throw new Exception(oldFolderName + " is not a directory or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	* @Title: deleteAllFilesInFolder
	* @Description: 删除目录下所有文件
	* @param @param folderName
	* @param @throws Exception    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void deleteAllFilesInFolder(String folderName) throws Exception {
		try {
			String strMsg = "";
			File file = new File(folderName);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			} else {
				throw new Exception(folderName + " is not a directory or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 
	* @Title: writeFile
	* @Description: 向文件中写内容
	* @param @param fileFullName
	* @param @param content
	* @param @param overwrite
	* @param @throws IOException    设定文件
	* @return void    返回类型
	* @throws
	 */
	public static void writeFile(String fileFullName, String content, boolean overwrite) throws IOException {

		if (fileFullName != null) {
			String path = fileFullName.substring(0, fileFullName.lastIndexOf("/"));
			if (!(new File(path).isDirectory())) {
				new File(path).mkdirs();
			}
		}
		File file = new File(fileFullName);
		FileWriter writer = new FileWriter(file);
		if (overwrite) {
			writer.write(content);
		} else {
			writer.write(content);
		}
		writer.flush();
		writer.close();
	}

	/**
	 * 
	* @Title: writeFileUTF
	* @Description: uft-8格式内容
	* @param @param fileFullName
	* @param @param content
	* @param @param overwrite
	* @param @throws IOException    设定文件
	* @return void    返回类型
	* @throws
	 */

	public static void writeFileUTF(String fileFullName, String content, boolean overwrite) throws IOException {

		if (fileFullName != null) {
			String path = fileFullName.substring(0, fileFullName.lastIndexOf("/"));
			if (!(new File(path).isDirectory())) {
				new File(path).mkdirs();
			}
		}
		OutputStream os = new FileOutputStream(fileFullName);
		OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
		if (overwrite) {
			writer.write(content);
		} else {
			writer.write(content);
		}
		writer.flush();
		writer.close();
	}

	public static void writeFile(String fileFullName, File file) throws Exception {
		if (file != null) {
			if (fileFullName != null) {
				String path = fileFullName.substring(0, fileFullName.lastIndexOf("/"));
				if (!(new File(path).isDirectory())) {
					new File(path).mkdirs();
				}
			}

			FileOutputStream outputStream = new FileOutputStream(fileFullName);

			FileInputStream fileIn = new FileInputStream(file);

			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileIn.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}

			fileIn.close();
			outputStream.close();
		}
	}

	public static void writeFile(String fileName, String path, File file) throws Exception {
		if (file != null) {
			if (fileName != null) {
				//				String path = fileFullName.substring(0, fileFullName.lastIndexOf("/"));
				if (!(new File(path).isDirectory())) {
					new File(path).mkdirs();
				}
			}
			File copyFile = new File(path, fileName);
			FileOutputStream outputStream = new FileOutputStream(copyFile);

			FileInputStream fileIn = new FileInputStream(file);

			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileIn.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}

			fileIn.close();
			outputStream.close();
		}
	}

	public static void writeFile(String fileName, String path, InputStream fileIn) throws Exception {
		if (fileIn != null) {
			if (fileName != null) {
				//				String path = fileFullName.substring(0, fileFullName.lastIndexOf("/"));
				if (!(new File(path).isDirectory())) {
					new File(path).mkdirs();
				}
			}
			File copyFile = new File(path, fileName);
			FileOutputStream outputStream = new FileOutputStream(copyFile);

			byte[] buffer = new byte[1024];
			int len;
			while ((len = fileIn.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}

			fileIn.close();
			outputStream.close();
		}
	}

	public static ArrayList splitTxtToArray(String fileFullName) throws Exception {
		ArrayList txt = new ArrayList();
		File file = new File(fileFullName);
		BufferedReader in = new BufferedReader(new FileReader(file));
		String strLine = "";
		while ((strLine = in.readLine()) != null) {
			txt.add(strLine);
		}

		in.close();
		return txt;
	}

	/**
	 * 
	* @Title: getFolderAllFileName
	* @Description: 获取目录下所有文件名
	* @param @param folderName
	* @param @return
	* @param @throws Exception    设定文件
	* @return ArrayList    返回类型
	* @throws
	 */
	public static ArrayList getFolderAllFileName(String folderName) throws Exception {
		ArrayList fileNamelist = new ArrayList();
		try {
			String strMsg = "";
			File file = new File(folderName);
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					fileNamelist.add(files[i].getName());
				}
			} else {
				throw new Exception(folderName + " is not a directory or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return fileNamelist;
	}

	public static final String getFileContentAsStringUTF(String fileFullName) throws Exception {
		File file = new File(fileFullName);
		StringBuffer sb = new StringBuffer();
		InputStreamReader input = new InputStreamReader(new FileInputStream(file), "utf-8");
		BufferedReader in = new BufferedReader(input);
		String strLine = "";
		while ((strLine = in.readLine()) != null) {
			sb.append(strLine);
			sb.append("\n");
		}

		input.close();
		in.close();
		return sb.toString();
	}

	public static final String getFileContentAsString(String fileFullName) throws Exception {
		File file = new File(fileFullName);
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(file));
		String strLine = "";
		while ((strLine = in.readLine()) != null) {
			sb.append(strLine);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}

	public static File[] getAllFilesInFolder(String folderName) {
		return getAllFilesInFolderByExtension(folderName, "");
	}

	public static File[] getAllFilesInFolderByExtension(String folderName, String extension) {
		File dir = new File(folderName);
		if (dir.isDirectory()) {
			Collection rtn = new ArrayList();
			File[] files = dir.listFiles();

			if (extension != null && extension.trim().length() > 0) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().indexOf("." + extension) != -1) {
						rtn.add(files[i]);
					}
				}
				return (File[]) rtn.toArray(new File[rtn.size()]);
			} else {
				return files;
			}
		}

		return new File[0];
	}

	public static void convertFileEncoding(String path, String fromEncoding, String toEncoding) throws Exception {
		convertFileEncoding(new File(path), fromEncoding, toEncoding);
	}

	public static void convertFileEncoding(File file, String fromEncoding, String toEncoding) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				convertFileEncoding(files[i], fromEncoding, toEncoding);
			}
		} else if (file.isFile() && file.getName().trim().toLowerCase().endsWith(".java")) {
			try {
				String command = "native2ascii -encoding " + fromEncoding + " " + file.getAbsolutePath()
						+ " c:/temp.java";
				Process process = Runtime.getRuntime().exec(command);
				process.waitFor();
				command = "native2ascii -reverse -encoding " + toEncoding + " c:/temp.java " + file.getAbsolutePath();
				process = Runtime.getRuntime().exec(command);
				process.waitFor();
				System.out.println(file.getAbsolutePath() + " Execute Successed");
			} catch (Exception e) {
				System.out.println(file.getAbsolutePath() + " Execute Failed");
				throw e;
			} finally {
				new File("c:/temp.java").delete();
			}
		}
	}

	public static void copyFileToFolderAndRenameFile(String fileName, String newFileName, String newFolder)
			throws Exception {
		try {
			File file = new File(fileName);
			if (file.isFile()) {
				if (!newFolder.endsWith("\\"))
					newFolder += "\\";
				FileInputStream fis = new FileInputStream(file);
				byte[] fileContent = new byte[fis.available()];
				fis.read(fileContent);
				String path = newFolder + newFileName;
				FileOutputStream fos = new FileOutputStream(path);
				fos.write(fileContent);
				fis.close();
				fos.close();
			} else {
				throw new Exception(fileName + " is not a file or it is not exist!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Returns the name of the file whithout the extension.  
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileName(String file) {

		int idx = file.lastIndexOf(".");
		String ret = idx >= 0 ? file.substring(0, idx) : file;

		return ret;
	}

	/**
	 * Returns the filename extension
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(String file) {

		int idx = file.lastIndexOf(".");
		String ret = idx >= 0 ? file.substring(idx + 1) : "";

		return ret;
	}

	/**
	 * @param path
	 * @return
	 */
	public static String getParent(String path) {

		int lastSlash = path.lastIndexOf('/');
		String ret = (lastSlash > 0) ? path.substring(0, lastSlash) : "";

		return ret;
	}

	/**
	 * @param path
	 * @return
	 */
	public static String getName(String path) {

		String ret = path.substring(path.lastIndexOf('/') + 1);

		return ret;
	}

	/**
	 * Eliminate dangerous chars in name
	 * 
	 * @param name
	 * @return
	 */
	public static String escape(String name) {

		String ret = name.replace('/', ' ');
		ret = ret.replace(':', ' ');
		ret = ret.replace('[', ' ');
		ret = ret.replace(']', ' ');
		ret = ret.replace('*', ' ');
		ret = ret.replace('\'', ' ');
		ret = ret.replace('"', ' ');
		ret = ret.replace('|', ' ');
		ret = ret.trim();

		return ret;
	}

	/**
	 * Creates a temporal and unique directory
	 * 
	 * @throws IOException If something fails.
	 */
	public static File createTempDir() throws IOException {
		File tmpFile = File.createTempFile("okm", null);

		if (!tmpFile.delete())
			throw new IOException();
		if (!tmpFile.mkdir())
			throw new IOException();

		return tmpFile;
	}

	/**
	 * Create temp file with extension from mime
	 */
	public static File createTempFileFromMime(String mimeType) throws IOException {
		//			MimeType mt = MimeTypeDAO.findByName(mimeType);
		//			String ext = mt.getExtensions().iterator().next();
		//			return File.createTempFile("okm", "." + ext);
		return null;
	}

	/**
	 * Wrapper for FileUtils.deleteQuietly
	 */
	public static boolean deleteQuietly(File file) {
		return org.apache.commons.io.FileUtils.deleteQuietly(file);
	}

	/**
	 * Recursively create ZIP archive from directory 
	 */
	public static void createZip(File path, OutputStream os) throws IOException {

		if (path.exists() && path.canRead()) {
			ZipArchiveOutputStream zos = new ZipArchiveOutputStream(os);
			zos.setComment("Generated by OpenKM");
			zos.setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy.ALWAYS);
			zos.setUseLanguageEncodingFlag(true);
			zos.setFallbackToUTF8(true);
			zos.setEncoding("UTF-8");

			createZipHelper(path, zos, path.getName());

			zos.flush();
			zos.finish();
			zos.close();
		} else {
			throw new IOException("Can't access " + path);
		}

	}

	/**
	 * Recursively create ZIP archive from directory helper utility 
	 */
	private static void createZipHelper(File fs, ZipArchiveOutputStream zos, String zePath) throws IOException {

		File[] files = fs.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				createZipHelper(files[i], zos, zePath + "/" + files[i].getName());
			} else {
				ZipArchiveEntry zae = new ZipArchiveEntry(files[i], zePath + "/" + files[i].getName());
				zos.putArchiveEntry(zae);
				FileInputStream fis = new FileInputStream(files[i]);
				IOUtils.copy(fis, zos);
				fis.close();
				zos.closeArchiveEntry();
			}
		}

	}

	/**
	 * Count files and directories from a selected directory.
	 */
	public static int countFiles(File dir) {
		File[] found = dir.listFiles();
		int ret = 0;

		if (found != null) {
			for (int i = 0; i < found.length; i++) {
				if (found[i].isDirectory()) {
					ret += countFiles(found[i]);
				}

				ret++;
			}
		}

		return ret;
	}

	public static void main(String[] args) {
		try {
			// FileOperate.cutFolderFilesToOtherFolder("D:\\test", "d:\\yhp");
			convertFileEncoding("C:/Java/workspace/OBPMBeta/src", "GBK", "UTF-8");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}