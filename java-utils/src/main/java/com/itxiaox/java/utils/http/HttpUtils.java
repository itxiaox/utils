package com.itxiaox.java.utils.http;

import com.itxiaox.java.utils.file.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  网络请求工具类
 */
public class HttpUtils {

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    interface CallBack<T> {
        void onSuccess(T result);
        void onFail(String error);
    }


    /**
     * 异步的Get请求
     * @param url 请求的uil
     * @param callBack 请求回调，返回String类型的数据
     */
    public static void doGetAsync(final String url, final CallBack<String> callBack) {
        new Thread(() -> {
            try {
                InputStream is = doGet(url);
                String result = IOUtils.inputSteamToString(is);
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            } catch (Exception e) {
                if (callBack != null) {
                    callBack.onFail(e.getMessage());
                }
            }

        }).start();
    }
    /**
     * 异步的Get请求，返回InputStream数据格式
     * @param url 请求的uil
     * @param callBack 请求回调 返回InputStream类型数据
     */
    public static void doGetAsync2(final String url, final CallBack<InputStream> callBack) {
        new Thread() {
            public void run() {
                try {
                    InputStream is = doGet(url);
                    if (callBack != null) {
                        callBack.onSuccess(is);
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        callBack.onFail(e.getMessage());
                    }
                }

            }
        }.start();
    }


    /**
     * 异步的Post请求，返回InputStream数据格式
     *
     * @param urlStr 请求url
     * @param params 请求参数
     * @param callBack 请求回调,返回inputStream格式数据
     */
    public static void doPostAsync2(final String urlStr, final String params,
                                  final CallBack<InputStream> callBack) throws Exception {
        new Thread(() -> {
            try {
                InputStream result = doPostSync(urlStr, params);
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            } catch (Exception e) {
                if (callBack != null) {
                    callBack.onFail(e.getMessage());
                }
            }
        }).start();

    }

    /**
     *post异步请求，返回String类型格式
     * @param url 请求url
     * @param params 请求参数(jsonString)
     * @param callBack 返回String类型格式数据
     * @throws Exception
     */
    public static void doPostAsync(final String url, final String params,
                                   final CallBack<String> callBack) throws Exception {
        new Thread(() -> {
            try {
                InputStream inputStream = doPostSync(url, params);
                String result = IOUtils.inputSteamToString(inputStream);
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            } catch (Exception e) {
                if (callBack != null) {
                    callBack.onFail(e.getMessage());
                }
            }
        }).start();

    }

    /**
     * Get请求，获得返回InputStream类型数据
     *  
     *
     * @param urlStr
     * @return
     */
    public static InputStream doGet(String urlStr){
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }
        return is;
    }

    /**
     * 同步Get请求，获取到String类型数据
     * @param urlStr 请求URl
     * @return
     */
    public static String doGet2(String urlStr){
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        String result = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                result = IOUtils.inputSteamToString(is);
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *  
     *
     * @param url 发送请求的 URL
     * @param param 请求参数；请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static InputStream doPostSync(String url, String param){
        InputStream is = null;
        PrintWriter out = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

           //获取输出流
            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            //获取输入流
            is = conn.getInputStream();
//            InputStream  co
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
        // 使用finally块来关闭输出流、输入流
        return is;
    }

    /**
     *将InputStream转为String
     * @param is
     * @return
     */
    public static String getContent(InputStream is){
        if (is==null)return null;
        return IOUtils.inputSteamToString(is);
    }
}