package com.itxiaox.java.utils.secret;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 常用请求参数签名工具类</br>
 * 签名算法：</br>
 * step1：先对请求参数的key升序排序 </br>
 * step2：把参数的key+value拼接接起来，将拼接的字符串加上+secretkey(放最后)，最后将拼接的字符进行MD5加密。</br>
 * 这样客户端服务器采用同样的加密方式，双端进行校验比对，如果两边签名一致则是有效请求，否则就是无效请求。
 */
public class SignUtils {

    /**
     * 得到签名
     * @param params 参数集合不含secretkey
     * @param secret 验证接口的secretkey
     * @return
     */
    public static String getSign(Map<String, Object> params, String secret) {
        String sign = "";
        StringBuilder sb = new StringBuilder();
        //step1：先对请求参数排序
        Set<String> keyset = params.keySet();
        TreeSet<String> sortSet = new TreeSet<String>(keyset);
        Iterator<String> it = sortSet.iterator();
        //step2：把参数的key value链接起来 secretkey放在最后面，得到要加密的字符串
        while (it.hasNext()) {
            String key = it.next();
            Object value = params.get(key);
            sb.append(key).append(value);
        }
        sb.append(secret);
        byte[] md5Digest;
        try {
            md5Digest = encryptMD5(sb.toString());
            sign = byte2hex(md5Digest);
        } catch (IOException e) {
            System.out.print("生成签名错误:" + e.getMessage());
        }
        return sign;
    }

    private static byte[] encryptMD5(String data)
            throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e.toString());
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

}
