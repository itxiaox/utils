package com.itxiaox.android.xutils.log;


import com.itxiaox.android.xutils.file.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 将logcat输出到文件中
 * Created by xiao on 2017/9/18.
 */

class LogCatUtils {
    private static final String TAG = "LogCatUtils";
    public static String getLogcatText() throws IOException {
        Process process = Runtime.getRuntime().exec("logcat -d");
        BufferedReader bufferedReader = new BufferedReader
                (new InputStreamReader(process.getInputStream()));
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        return stringBuffer.toString();
    }


    private static InputStream getLogCat() throws IOException {
        Process process = Runtime.getRuntime().exec("logcat -d" +
                "sdfsafd" +
                "");
        return process.getInputStream();
    }

    public static void writeLogCatToFile(String filePath) {
        File file = new File(filePath);
        FileOutputStream outputStream = null;
        InputStream is = null;
        try {
            File f = new File(file.getParent());
            if (!f.exists()) {
                boolean result = f.mkdirs();
            }
            if (!file.exists())
                file.createNewFile();
            outputStream = new FileOutputStream(filePath);
            is = getLogCat();
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将logCat 日志信息输出到文件中，便于后面跟踪调试
     * @param fileName
     * @param msg
     * @param loglevel
     */
    public static void writeLogCatToFile(String fileName, String msg, String loglevel) {
        if (FileUtils.checkAndCreateFile(fileName)){
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());
//            "日志：时间"+date +"\t"+"级别："+loglevel+"\tmsg="+msg;
            String content = String.format("日志：时间：%s 级别：%s  msg:%s",date,loglevel,msg);
            FileUtils.writeFile(fileName,content,true);
        }
    }
}