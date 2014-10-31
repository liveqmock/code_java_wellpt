package com.wellsoft.pt.repository.support.convert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.wellsoft.pt.repository.support.FileUploadHandler;

/**
 * Description: 文件工具类
 *  
 * @author jackCheng
 * @date 2013-4-16
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-16.1	jackCheng		2013-4-16		Create
 * </pre>
 *
 */
public class FileUtil {

	/**
	 * 获取文件名称[不含后缀名]
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}

	/**
	 * 
	 *文件复制
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @throws FileNotFoundException
	 */
	public static void copyFile(String inputFile, String outputFile) throws FileNotFoundException {
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		InputStreamReader in = null;
		OutputStreamWriter out = null;
		int temp = 0;
		char[] buf = new char[2048];

		try {
			/*while ((temp = fis.read()) != -1) {  
			    fos.write(temp);  
			}*/
			in = new InputStreamReader(fis, "UTF-8");
			out = new OutputStreamWriter(fos, "UTF-8");
			while ((temp = in.read(buf)) != -1) {
				out.write(buf, 0, temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//fis.close();
				//fos.close();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 *  将文档转换成SWF文件，参加中列表文件所在的位置，即为swf所生成的位置，原文件名与swf文件名一致
	 * 
	 * @param files
	 */
	public static void convert2SWF(List<java.io.File> files) {
		if (null == files || files.size() == 0) {
			return;
		}
		List<java.io.File> dosFiles = new ArrayList<java.io.File>();

		for (int i = 0; i < files.size(); i++) {
			dosFiles.add(files.get(i));
			//每20个文档一个处理线程
			if (i % 20 == 0) {

				new Thread(new FileUploadHandler(dosFiles.subList(i, i == 0 ? i + 1 : (i % 20 + 1) * 20))).start();
				//tempFiles.clear();
			}
		}

	}

	/**
	 * 
	 * 将文件流生成的相应的swf文件，fileName是文件流对应的文件名称【带后缀】，这个接口生成的swf文件放在System.properties中配置的TempSaveSwfFilePath.dir中
	 * 
	 * @param fileStream
	 * @param fileName
	 */
	public static void convert2SWF(InputStream fileStream, String fileName) {
		new Thread(new FileUploadHandler(fileStream, fileName));
	}

	public static boolean isImage(String filePath) throws FileNotFoundException{
		try {
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			return bi != null;
		}catch (IOException e) {    
            return false;    
        } 
	}
	
	public static void main(String[] args) {
		//		File tempFile = new File("F:\\配置问题.doc");
		//		List<File> listFile = new ArrayList<File>();
		//		listFile.add(tempFile);
		//		FileUtil.convert2SWF(listFile);
		String a = getFilePrefix("12312313123");
		System.out.println(a);
	}
	
	
}
