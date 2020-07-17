package com.itxiaox.java.utils.data;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类
 * @author xiaoxiao
 *
 */
public class StringUtil {


	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	private static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13)
				+ s.substring(14, 18)
				+ s.substring(19, 23)
				+ s.substring(24);
	}

	/**
	 * 获得指定数目的UUID
	 *
	 * 生成3位随机数
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	/**
	 * 三位数编码自增(ex: param 001,return 002;param 002,return 003)
	 * 
	 * @param CODE
	 * @return string
	 */
	public static String codeIncrement(String CODE) {
		int i = Integer.valueOf(CODE);
		i = i + 1;
		CODE = String.valueOf(i);
		if (i < 10) {
			CODE = "00" + CODE;
		} else if (i >= 10 && i < 100) {
			CODE = "0" + CODE;
		}
		return CODE;
	}

	/**
	 * 数字格式化为n位字符串
	 * 
	 * @param orderId
	 * @param n
	 * @return string
	 */
	public static String int2String(int orderId, int n) {
		String str = "";
		for (int i = 0; i < n; i++) {
			str += "0";
		}
		str = str + orderId;
		str = str.substring(str.length() - n);
		return str;
	}

	/**
	 * double 型转成保留两位小数的字符串型
	 * 
	 * @param dou
	 * @return
	 */
	public static String double2string(double dou) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(dou);
	}

	/**
	 * 两个Double数相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 两个Double数相除，并保留scale位小数
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return Double
	 */
	public static Double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 判定两个字符串的值是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2) {
		boolean b = false;
		if (!str1.equals("") && !str1.equals("")) {
			str1.equals(str2);
			b = true;
		}
		return b;
	}


	/**
	 * 字符串是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	/**
	 * 判断集合是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list){
		return list==null||list.size()==0;
	}

	/**
	 * 获取方法名
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getFunName(String tableName) {
		if (tableName == null)
			return null;
		tableName = tableName.toLowerCase();
		String[] strs = tableName.split("_");
		if (strs.length > 0) {
			tableName = "";
			for (int i = 0; i < strs.length; i++) {
				tableName = tableName + strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1, strs[i].length());
			}
		}
		return tableName;
	}

	/**
	 * 把Null值格式化为指定数字
	 * 
	 * @param bf
	 * @param af
	 * @return int
	 */
	public static int formatNullToInteger(Integer bf, Integer af) {
		if (bf == null) {
			bf = af;
		}
		return bf;
	}

	/**
	 * 把Null值格式化为指定数字
	 * 
	 * @param bf
	 * @param af
	 * @return int
	 */
	public static Double formatNullToDouble(Double bf, Double af) {
		if (bf == null) {
			bf = af;
		}
		return bf;
	}

	/**
	 * 空文字转化为指定文字
	 * 
	 * @param bf
	 * @param af
	 * @return String
	 */
	public static String formatNullToString(String bf, String af) {
		if (bf == null || bf == "") {
			bf = af;
		}
		return bf;
	}

	/**
	 * 把Null值格式化为空字符
	 * 
	 * @return Object
	 */
	public static Object formatNull(Object obj) {
		if (obj == null) {
			obj = "";
		}
		return obj;
	}



	/**
	 * 把数组根据特定分隔符拼成字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String arrayToString(String[] str) {
		return arrayToString(str, ",", "", "");
	}
	/**
	 * 把数组根据特定分隔符拼成字符串
	 *
	 * @param str
	 * @return
	 */
	private static String arrayToString(String[] str, String delimiter, String prefix, String suffix) {
		String temp = "";
		if (str != null) {
			if (delimiter == null) {
				delimiter = "";
			}
			if (prefix == null) {
				prefix = "";
			}
			if (suffix == null) {
				suffix = "";
			}
			for (int i = 0; i < str.length; i++) {
				temp += prefix + str[i] + suffix + delimiter;
			}
			if (temp != "") {
				temp = temp.substring(0, temp.length() - delimiter.length());
			}
		}
		return temp;
	}

	/**
	 * 解析字符串
	 * 
	 * @param str
	 *            要解析的字符串
	 * @param divisionChar
	 *            解析所需要的字符
	 * @return String[]
	 */
	public static String[] stringSplit(String str, String divisionChar) {
		String[] strs = null;
		try {
			strs = str.split(divisionChar);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return strs;
	}


	/***
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 去除特殊字符。
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	@SuppressWarnings("Annotator")
	public static String stringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 得到网页中图片的地址
	 * @param htmlStr
	 * @return
     */
    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();
        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
            }
        }
        return pics;
    }

	/**
	 * 将double 类型转换为2位小数的字符串，一般用户金额的显示，电商项目中常用
	 * @param d
	 * @return
	 */
	public static String getDouble(double d){
		// 2. 通过BigDecimal的setScale()实现四舍五入与小数点位数确定, 将转换为一个BigDecimal对象.
		BigDecimal bd = new BigDecimal(d);
		BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		// System.out.println("通过BigDecimal.setScale获得: " + bd2);
		return bd2.toString();
	}

	/**
	 * 生成随机数字符串
	 * Test:Junit test ok
	 * @param num 生成随机数的位数
	 *
	 * @return
	 */
	public static String createRandomStr(int num){
		if(num<=0){
			throw new IllegalArgumentException("num must be > 0");
		}
		String str = "";
		for (int i = 0; i < num; i++) {
			str = str + (char) (Math.random() * 26 + 'a');
		}
		return str;
	}

	public static String format(String format,Object... strings){
		return String.format(Locale.getDefault(),format,strings);
	}

}
