package com.itxiaox.java.utils.secret;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version v1.0
 * @Title:加密工具类
 * @Description: 各种常用的加密工具类，包含MD5
 * @author: xiao
 * @date: 2019/4/28 18:03
 */
public class SecretUtils {


    /**
     * 使用MD5对原文进行加密
     *
     * @param value 需要被加密的原文
     * @return MD5加密后数据
     */
    public static String md5(String value) throws NoSuchAlgorithmException {
        StringBuilder sb = null;
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] result = digest.digest(value.getBytes());
        sb = new StringBuilder();
        for (byte b : result) {
            String hexString = Integer.toHexString(b & 0xFF);
            if (hexString.length() == 1) {
                sb.append("0" + hexString);// 0~F
            } else {
                sb.append(hexString);
            }
        }
        return sb.toString();
    }

    /**
     * 获取文件的MD5值
     *
     * @param file 目标文件
     * @return 文件的md5值
     * @throws FileNotFoundException
     */
    public static String getFileMD5(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


    private static final Comparator cwjComparator = new Comparator() {
        private final Collator collator = Collator.getInstance();

        public final int compare(Object a, Object b) {
            // CharSequence a = ((Item) a).sName;
            // CharSequence b = ((Item) b).sID;
            return collator.compare(a, b);
        }
    };


    /**
     * sha1 加密
     *
     * @param info 要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String encryptToSHA(String info) {
        byte[] digest = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digest = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byte2hex(digest);
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }


}
