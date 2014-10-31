package com.wellsoft.pt.utils.file;

import java.io.*;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;

/**   
* 类描述：   
* 创建人：林超  
* 创建时间：2011-7-7 下午04:57:27   
* 修改人：  
* 修改时间：2011-7-7 下午04:57:27   
* 修改备注：   
* @version  V1.0    
*/
public class DESUtils {
	/**
	 * 
	 * 
	 * @param keyFile
	 *           
	 */
	public void savePriveKey(String keyFile) {
		FileOutputStream fos = null;
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			SecureRandom sr = new SecureRandom();
			keyGen.init(sr);
			SecretKey key = keyGen.generateKey();
			byte[] rawKeyData = key.getEncoded();
			fos = new FileOutputStream(keyFile);
			fos.write(rawKeyData);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 文件加密
	 * 调用 文件名、文件存放路径、加密Key文件
	 * 
	 */
	public void encryptionFile(String fileName, String folderName, String keyFile) {
		SecureRandom sr = new SecureRandom();
		SecretKey key = getPrivetKey(keyFile);
		try {
			File file = new File(fileName);
			if (file.isFile()) {
				if (!folderName.endsWith("\\"))
					folderName += "\\";
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, key, sr);
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[fis.available()];
				fis.read(data);
				byte[] encryptedData = cipher.doFinal(data);
				String path = folderName + file.getName();
				FileOutputStream fos = new FileOutputStream(path);
				// fos = new FileOutputStream(new File(outFile));
				fos.write(encryptedData);
				fis.close();
				fos.close();
			} else {
				throw new Exception(fileName + " is not a file or it is not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件解密
	 * 
	 * 调用文件名、文件存放路径、Key文件
	 */
	public void decryptionFile(String inFile, String outFile, String keyFile) {
		SecretKey key = getPrivetKey(keyFile);
		SecureRandom sr = new SecureRandom();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			fis = new FileInputStream(new File(inFile));
			byte[] encryptedData = new byte[fis.available()];
			fis.read(encryptedData);
			byte[] decryptedData = cipher.doFinal(encryptedData);
			fos = new FileOutputStream(new File(outFile));
			fos.write(decryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 产出Key文件
	 * 
	 */
	private SecretKey getPrivetKey(String keyFile) {
		FileInputStream fis = null;
		SecretKey key = null;
		try {
			fis = new FileInputStream(new File(keyFile));
			byte[] rawKeyData = new byte[fis.available()];
			fis.read(rawKeyData);
			DESKeySpec dks = new DESKeySpec(rawKeyData);
			key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return key;
	}

	public static void main(String[] args) {
		DESUtils desUtil = new DESUtils();
		desUtil.savePriveKey("d:\\luceneData\\file\\keyen.txt");
		System.out.println("密钥已生成");
		desUtil.encryptionFile("d:\\luceneData\\test1.txt", "d:\\luceneData\\file\\", "d:\\luceneData\\file\\keyen.txt");
		System.out.println("文件已加密");
		desUtil.decryptionFile("d:\\luceneData\\file\\test1.txt", "d:\\luceneData\\file\\test1.txt",
				"d:\\luceneData\\file\\keyen.txt");
		System.out.println("文件已解码");

		// desUtil.savePriveKey("d:\\luceneData\\file\\key.doc");
		// System.out.println("密钥生成");
		// desUtil.encryptionFile("d:\\luceneData\\test.doc","d:\\luceneData\\file\\test.doc",
		// "d:\\luceneData\\file\\key.doc");
		// System.out.println("文件已加密");
		// desUtil.decryptionFile("d:\\luceneData\\file\\test.doc","d:\\luceneData\\test.doc",
		// "d:\\luceneData\\file\\key.doc");
		// System.out.println("文件已解密�");
	}
}
