package com.wellsoft.pt.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

public class PinyinUtil {
	/**  
	 * 将汉字转换为全拼  
	 *   
	 * @param src  
	 * @return String  
	 */
	public static String getPinYin(String src) {
		String t4 = "";
		if (StringUtils.isBlank(src)) {
			return t4;
		}
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式   
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符   
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中   
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后   
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后   
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return t4;
	}

	/**  
	 * 提取每个汉字的首字母  
	 *   
	 * @param str  
	 * @return String  
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		if (StringUtils.isBlank(str)) {
			return convert;
		}
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母   
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
}