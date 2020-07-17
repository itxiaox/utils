package com.itxiaox.java.utils.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	/**
	 * 1、将byte[]转换成InputStream
	 * 
	 * @param b
	 * @return
	 */
	public static InputStream byteToInputStream(byte[] b) {
		return new ByteArrayInputStream(b);
	}



	/**
	 * InputStream 转字符串
	 * @param inputStream
	 * @return
	 */
	public static String inputSteamToString(InputStream inputStream){
		ByteArrayOutputStream baos = null;
		String result = null;
		try{
			 baos = new ByteArrayOutputStream();
			int len = -1;
			byte[] buf = new byte[128];
			while ((len = inputStream.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = baos.toString();
		}catch(Exception e){
		    e.printStackTrace();
		}finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 字符串转byte[] 数组
	 * @param content
	 * @return
	 */
	public static byte[] stringToBytes(String content){
		if (content==null||"".equals(content))return null;
		return content.getBytes();
	}

	/**
	 * 将InputStream转换成byte[]
	 * 方法二， 此方法效率不高
	 * @param is
	 * @return
	 */
	@Deprecated
	public static byte[] inputStreamToBytes2(InputStream is) {
		String str = "";
		byte[] readByte = new byte[1024];
		int readCount = -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}





}
