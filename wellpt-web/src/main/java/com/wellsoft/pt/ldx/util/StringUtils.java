package com.wellsoft.pt.ldx.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    private static final Logger LOG = Logger.getLogger(StringUtils.class);

    public static String nullToString(Object str) {
        if (str == null) {
          return "";
        }
        return str.toString().trim();
      }
    
    public static String getRptPortion(String rptno) {
        return isEmpty(rptno) ? "" : left(rptno, 6);
    }

    public static String getValueFromArray(Object[] values, int index) {
        if (index < values.length) {
            return values[index] == null || isEmpty(values[index].toString()) ? "0" : values[index].toString();
        } else {
            return "0";
        }
    }

    public static String replaceAmpersandWithEmpty(String str) {
        return replace(str, "&", "");
    }

    public static String encodeUtf8(String str) {
        try {
            return URLEncoder.encode(str, "utf8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("error encode str : " + str, e);
            return str;
        }
    }

    public static String toCamelCase(String value, boolean startWithLowerCase) {
        String[] strings = StringUtils.split(value.toLowerCase(), "_");
        for (int i = startWithLowerCase ? 1 : 0; i < strings.length; i++) {
            strings[i] = StringUtils.capitalize(strings[i]);
        }
        return StringUtils.join(strings);
    }

    public static String removeLastComma(String str) {
        return isNotEmpty(str) && str.endsWith(",") ? str.substring(0, str.length() - 1) : str;
    }

    public static List<String> splitStrutsCollectionString(final String str) {
        if (!StringUtils.isBlank(str)) {
            return Arrays.asList(str.replaceAll(" ", "").split(","));
        }
        return new ArrayList<String>();
    }

    public static String replaceBlank(String str) { 
           String dest = "";  
           if (str!=null) {  
    	        Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
    	        Matcher m = p.matcher(str);  
    	        dest = m.replaceAll("");  
    	        dest = dest.replaceAll("\'", "");
    	        dest = dest.replaceAll("\"", "");
    	      }  
               return dest;  
    	  }  

    /**
	 * 对字符串进行32位MD5加密
	 * @param str - 需要加密的字符串
	 * @return 将字符串进行MD5加密后的值
	 */
	public static String md5(String str){
		MessageDigest messageDigest = null;     
	     
        try {     
            messageDigest = MessageDigest.getInstance("MD5");     
     
            messageDigest.reset();     
     
            messageDigest.update(str.getBytes("UTF-8"));     
        } catch (NoSuchAlgorithmException e) {     
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }     
     
        byte[] byteArray = messageDigest.digest();     
     
        StringBuffer md5StrBuff = new StringBuffer();     
     
        for (int i = 0; i < byteArray.length; i++) {                 
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)     
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));     
            else     
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));     
        }     
     
        return md5StrBuff.toString();   
	}
	
	/**
	 * 生成一个18位随机的文件名. 生成规则如下: 前面14位由日期时间生成,生成形式为"yyyyMMddHHmmss", 
	 * 后4位由伪随机数(0-9999,不足4位则补0)构成
	 * @return
	 */
	public static String randFileName(){
		String result = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		result += sdf.format(date);
		Double rand =  Math.random()*10000;
		if(rand < 10)
			result += "000" + rand.toString().substring(0, 1);
		else if(rand < 100)
			result += "00" + rand.toString().substring(0, 2);
		else if(rand < 1000)
			result += "0" + rand.toString().substring(0, 3);
		else
			result += rand.toString().substring(0, 4);
		
		return result;
	}
	
	/**
	 * 去掉字符串左侧的零
	 * 
	 * @param str
	 * @return
	 */
	public static String removeLeftZero(String str) {
		while (StringUtils.isNotBlank(str) && str.startsWith("0")) {
			str = str.replaceFirst("0", "");
		}
		return str;
	}
	
	/**
	 * 字符串左侧添0
	 * @param str
	 * @param length 拼接最后总长度
	 * @return
	 */
	public static String addLeftZero(String str, int length) {
		while (str.length() < length) {
			str = "0".concat(str);
		}
		return str;
	}
	
	/**
	 * 转化为货币格式
	 * @param from
	 * @return
	 */
	public static String getCurrencyStr(String from) {
		if(StringUtils.isBlank(from)){
			return "";
		}
		DecimalFormat df = new DecimalFormat("###,###,###,###.00");
		return df.format(Double.valueOf(from));
	}
	
	/**
	 * 截取字符串
	 * @param from
	 * @param len
	 * @return
	 */
	public static String getLimitLength(String from,int len){
		if(isBlank(from)||len<1)
			return "";
		return from.length()>len?from.substring(0,len):from;
	}
	
	public static String[] chineseDigits = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

	 /**
     * 把金额转换为汉字表示的数量，小数点后四舍五入保留两位
     * @param amount
     * @return
     */
    public static String amountToChinese(double amount) {

        if(amount > 99999999999999.99 || amount < -99999999999999.99)
            throw new IllegalArgumentException("参数值超出允许范围 (-99999999999999.99 ～ 99999999999999.99)！");

        boolean negative = false;
        if(amount < 0) {
            negative = true;
            amount = amount * (-1);
        }

        long temp = Math.round(amount * 100);
        int numFen = (int)(temp % 10); // 分
        temp = temp / 10;
        int numJiao = (int)(temp % 10); //角
        temp = temp / 10;
        //temp 目前是金额的整数部分

        int[] parts = new int[20]; // 其中的元素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分
        int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）
        for(int i=0; ; i++) {
            if(temp ==0)
                break;
            int part = (int)(temp % 10000);
            parts[i] = part;
            numParts ++;
            temp = temp / 10000;
        }

        boolean beforeWanIsZero = true; // 标志“万”下面一级是不是 0

        String chineseStr = "";
        for(int i=0; i<numParts; i++) {

            String partChinese = partTranslate(parts[i]);
            if(i % 2 == 0) {
                if("".equals(partChinese))
                    beforeWanIsZero = true;
                else
                    beforeWanIsZero = false;
            }

            if(i != 0) {
                if(i % 2 == 0)
                    chineseStr = "亿" + chineseStr;
                else {
                    if("".equals(partChinese) && !beforeWanIsZero)   // 如果“万”对应的 part 为 0，而“万”下面一级不为 0，则不加“万”，而加“零”
                        chineseStr = "零" + chineseStr;
                    else {
                        if(parts[i-1] < 1000 && parts[i-1] > 0) // 如果"万"的部分不为 0, 而"万"前面的部分小于 1000 大于 0， 则万后面应该跟“零”
                            chineseStr = "零" + chineseStr;
                        chineseStr = "万" + chineseStr;
                    }
                }
            }
            chineseStr = partChinese + chineseStr;
        }

        if("".equals(chineseStr))  // 整数部分为 0, 则表达为"零元"
            chineseStr = chineseDigits[0];
        else if(negative) // 整数部分不为 0, 并且原金额为负数
            chineseStr = "负" + chineseStr;

        chineseStr = chineseStr + "元";

        if(numFen == 0 && numJiao == 0) {
            chineseStr = chineseStr + "整";
        }
        else if(numFen == 0) { // 0 分，角数不为 0
            chineseStr = chineseStr + chineseDigits[numJiao] + "角";
        }
        else { // “分”数不为 0
            if(numJiao == 0)
                chineseStr = chineseStr + "零" + chineseDigits[numFen] + "分";
            else
                chineseStr = chineseStr + chineseDigits[numJiao] + "角" + chineseDigits[numFen] + "分";
        }

        return chineseStr;

    }


    /**
     * 把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 ""
     * @param amountPart
     * @return
     */
    private static String partTranslate(int amountPart) {

        if(amountPart < 0 || amountPart > 10000) {
            throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");
        }


        String[] units = new String[] {"", "拾", "佰", "仟"};

        int temp = amountPart;

        String amountStr = new Integer(amountPart).toString();
        int amountStrLength = amountStr.length();
        boolean lastIsZero = true; //在从低位往高位循环时，记录上一位数字是不是 0
        String chineseStr = "";

        for(int i=0; i<amountStrLength; i++) {
            if(temp == 0)  // 高位已无数据
                break;
            int digit = temp % 10;
            if(digit == 0) { // 取到的数字为 0
                if(!lastIsZero)  //前一个数字不是 0，则在当前汉字串前加“零”字;
                    chineseStr = "零" + chineseStr;
                lastIsZero = true;
            }
            else { // 取到的数字不是 0
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }
    
	public static String delInvalidZero(String str) {
		if ("0".equals(str.substring(0, 1))) {
			return delInvalidZero(str.substring(1, str.length()));
		} else if (str.contains(",")) {
			return delInvalidZero(str.replaceAll(",", ""));
		} else {
			return str;
		}
	}

	public static String removeStrAfterComma(Object obj){
		String result = nullToString(obj);
		if(result.indexOf(".")<0)
			return result;
		return result.split("\\.")[0];
	}
}
