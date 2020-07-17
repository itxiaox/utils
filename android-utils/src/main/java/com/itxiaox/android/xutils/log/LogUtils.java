package com.itxiaox.android.xutils.log;

import com.itxiaox.java.utils.date.DateUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 日志封装类
 */
public class LogUtils {
    private static final String TAG = "LogUtils";

    static {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(2)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void d(String msg) {
//        if (BuildConfig.DEBUG)
        Logger.d(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void json(String prefix, String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void d_format(String format, Object... args) {
        if (BuildConfig.DEBUG)
            Logger.d(String.format(format, args));

    }

    /**
     * 将字符串追加到文件已有内容后面
     *
     * @param filePath 文件完整地址：D:/test.txt
     * @param append   是否追加内容，true 追加，false 覆盖
     * @param args  需要写入的
     */
    public static void writeFile(String filePath,boolean append,
                                 String format,Object... args ) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(filePath, append);
            String content = String.format(format,args);
            //写入
            fos.write(content.getBytes());
            // 写入一个换行
            fos.write("\r\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
