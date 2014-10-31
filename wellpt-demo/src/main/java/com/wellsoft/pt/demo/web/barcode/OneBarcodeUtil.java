package com.wellsoft.pt.demo.web.barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

/**
 * Description: 条形码生成示例
 *  
 * @author hunt
 * @date 2014-9-12
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-9-12.1	hunt		2014-9-12		Create
 * </pre>
 *
 */
public class OneBarcodeUtil {

	public static void main(String[] paramArrayOfString) {
		try {
			/*条形码有不同的码制,关于码制请查看http://baike.baidu.com/view/139831.htm?fr=aladdin#2*/

			/*EAN码制*/
			JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(),
					EAN13TextPainter.getInstance());

			String str = "788515004012";//条形码的下方的编码
			BufferedImage localBufferedImage = localJBarcode.createBarcode(str);//生成条形码图片对象
			saveToGIF(localBufferedImage, "EAN13.gif");//将条形码保存到硬盘

			/*39码码制, 对于我们目前的项目而言,这种码制更适合*/
			localJBarcode.setEncoder(Code39Encoder.getInstance());
			localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
			localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
			localJBarcode.setShowCheckDigit(false);
			str = "JBARCODE-39";
			localBufferedImage = localJBarcode.createBarcode(str);
			saveToPNG(localBufferedImage, "Code39.png");

		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	static void saveToJPEG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "jpeg");
	}

	static void saveToPNG(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "png");
	}

	static void saveToGIF(BufferedImage paramBufferedImage, String paramString) {
		saveToFile(paramBufferedImage, paramString, "gif");
	}

	static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2) {
		try {
			File file = new File("d:/images/" + paramString1);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream localFileOutputStream = new FileOutputStream(file);
			ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);
			localFileOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}