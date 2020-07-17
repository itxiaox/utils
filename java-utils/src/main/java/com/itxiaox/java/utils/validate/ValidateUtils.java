package com.itxiaox.java.utils.validate;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ClassName: ValidateUtils
 * @Description: 常用数据格式校验
 * @author 肖骁
 * @date 2014-12-4 下午6:00:44
 * 
 */
public class ValidateUtils {
	private final static String TAG = "ValidateUtils";

	/**
	 * 是否是数字
	 * 
	 * @param str 输入文本
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 是否为空
	 * @Description: 非空判断
	 * @author 肖骁
	 * @date 2014-12-4 下午5:54:02
	 * @param  content 输入内容
	 * @throws
	 */
	public static boolean isEmpty(String content) {
		return null == content || "".equals(content);
	}

	/**
	 * 手机号验证
	 * @param str 输入内容
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param phone 传入的电话号码
	 * @return 验证通过返回true
	 */
	public static boolean isTelephone(String phone) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (phone.length() > 9) {
			m = p1.matcher(phone);
			b = m.matches();
		} else {
			m = p2.matcher(phone);
			b = m.matches();
		}
		return b;
	}


	/**
	 * 检查是否是邮箱
	 * 
	 * @param paramString 输入邮箱
	 * @return
	 */
	public static boolean isEmail(String paramString) {

		String regex = "[a-zA-Z0-9_.]{1,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
		return paramString.matches(regex);
	}

	/**
	 * 是否是昵称
	 */
	public static boolean isNinc(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		try {
			p = Pattern.compile("^\\d*([\u4e00-\u9fa5]|[a-zA-Z])+\\d*$"); //
			m = p.matcher(str);
			b = m.matches();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return b;
	}

	/**
	 * 密码校验
	 */
	public static boolean isPassword(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		try {
			p = Pattern.compile(""); //
			m = p.matcher(str);
			b = m.matches();
		} catch (Exception e) {
			// TODO: handle exception
		}
		b = true;
		return b;
	}

	/**
	 * 是否是真实姓名
	 */
	public static boolean isRealName(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		try {
			p = Pattern.compile(""); //
			m = p.matcher(str);
			b = m.matches();
		} catch (Exception e) {
			// TODO: handle exception
		}
		b = true;
		return b;
	}

	/**
	 * 校验是否是身份证号
	 * @param content 身份证号
	 */
	public static  boolean isIDCard(String content){
		CheckIdCard  cc = new CheckIdCard(content);
		return cc.validate();
	}

	/**
	 * 校验是否是Ip 地址， IPV4
	 * @param ipAddress ip 地址
	 */
	public static boolean isIpv4(String ipAddress) {
		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
}
