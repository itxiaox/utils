package com.itxiaox.android.xutils.file;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.style.TabStopSpan;

import com.itxiaox.java.utils.data.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 *   文件工具类
 *  调用文件操作，注意要配置sdcard读写权限：
 *
 *   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *   <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 *
 */
public class FileUtils {

    /**
     * 获取的 缓存目录，用于文件保存
     *
     * @return
     */
    /**
     * 获取缓存目录，用于文件保存
     *
     * @param context 上下文对象
     * @return
     */
    public static String getCacheDirPath(Context context) {
        File f;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            f = new File(Environment.getExternalStorageDirectory()
                    + "");
            if (!f.exists() && !f.isDirectory()) {
                f.mkdirs();
            }
        } else {
            f = context.getCacheDir();
        }
        return f.getAbsolutePath();
    }

    /**
     * 获取私有（内存存储下的文件目录），可以用来保存不能公开的给其他应用的一些敏感数据如用户信息
     * /data/data/应用包名/files/
     * 这是手机内存存储，没有root无法查看，可以随着app数据清除，卸载一起清理
     * @param context
     * @return
     */
    public String getPrivateFileDir(Context context){
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取内置在存储器下的缓存目录，可以用来保存一些缓存文件如图片，当内存存储空间不足时候系统将自动清除
     * 这是手机内存存储，没有root无法查看，可以随着app数据清除，卸载一起清理
     * /data/data/应用包名/cache/
     * @return
     */
    public String getPrivateCacheDir(Context context){
        return  context.getCacheDir().getAbsolutePath();
    }

    /**
     * 判断文件夹是否存在，不存在则创建文件
     *
     * @param fileName
     * @return
     */
    public static boolean fileIsExist(String fileName) throws IOException {
        boolean flag = false;
        File f = new File(fileName);
        if (!f.exists()) {
            flag = false;
            f.createNewFile();
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断文件夹是否存在，不存在则创建文件夹及相关父级目录
     *
     * @param folderPath
     * @return
     * @throws IOException
     */
    public static boolean folderIsExist(String folderPath) throws IOException {
        boolean flag = false;
        File f = new File(folderPath);
        if (!f.exists() && !f.isDirectory()) {
            flag = false;
            f.mkdirs();//创建目录及所有父目录
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 提供常用的文件保存目录，默认保存，
     *
     * @param context 上下文对象
     * @param name    保存文件的父级目录
     * @return
     */
    public static String getSavePath(Context context, String name) {
        String cacheDir = getCacheDirPath(context);
        File f = new File(cacheDir + File.separator + name + File.separator);
        if (!f.exists() && !f.isDirectory()) {
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }

    /**
     * 从assets文件夹中获取图片
     *
     * @param ct
     * @param fileName
     * @return
     */
    public static Bitmap getImageFromAssetsFile(Context ct, String fileName) throws IOException{
        Bitmap image = null;
        AssetManager am = ct.getAssets();
        InputStream is = null;
        try {
             is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            throw  e;
        }finally {
            if (is != null) {
                is.close();
            }
        }
        return image;

    }

    /**
     * 序列化对象到SP文件中
     *
     * @param context 上下文对象
     * @param object 要序列号化保存的实体类
     * @param beanName ,实体类的名字
     */
    public static void serializeObject(Context context, Object object,
                                       String beanName) throws FileNotFoundException,IOException{
        // ObjectOutputStream
        // 对象输出流，将Person对象存储到E盘的Person.txt文件中，完成对Person对象的序列化操作
        String filePath = getCacheDirPath(context) + "/sp_data/";
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        File f = new File(filePath + beanName + ".txt");

        ObjectOutputStream oo = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            oo = new ObjectOutputStream(new FileOutputStream(f));
            oo.writeObject(object);
        } catch (FileNotFoundException e) {
            throw  e;
        } catch (IOException e) {
            throw  e;
        } finally {
            if (oo != null) {
                try {
                    oo.close();
                } catch (IOException e) {
                    throw  e;
                }
            }
        }
    }

    /**
     * 对象序列化
     *
     * @param context
     * @param beanName
     * @return
     */
    public static Object deserializeObject(Context context, String beanName)
            throws StreamCorruptedException,FileNotFoundException,IOException,ClassNotFoundException{
        String filePath = getCacheDirPath(context) + "/sp_data/";
        File file = new File(filePath);
        if (!file.exists() && file.isDirectory()) {
            file.mkdirs();
        }
        File f = new File(filePath + beanName + ".txt");
        ObjectInputStream ois = null;
        Object object = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            ois = new ObjectInputStream(new FileInputStream(f));
            object = ois.readObject();
        } catch (StreamCorruptedException e) {
            throw  e;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

        return object;
    }

    /**
     * 保存bitmap为文件
     *
     * @param context
     * @param bm
     * @param picName 文件名，需要后缀名
     * @return
     */
    @SuppressWarnings("unused")
    public static String saveBitmap(Context context, Bitmap bm, String picName) throws IOException {
        String filePath = getCacheDirPath(context) + "/image/";
        File rootfilePath = new File(filePath);
        if (!rootfilePath.exists() && !rootfilePath.isDirectory()) {//判断文件夹是否存在，不存在则创建
            rootfilePath.mkdirs();//主要区分mkdir
        }
        FileOutputStream out = null;
        try {
            File f = new File(filePath, picName);
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (IOException e) {
            throw e;
        } finally {
            out.flush();
            out.close();
        }
        return filePath + picName;
    }


    /**
     * 删除当前目录及其子目录
     * @param filePath
     */
    public static void deleteDir(String filePath) {
        File dir = new File(filePath);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir(filePath);
        }
        dir.delete();
    }

    /**
     * 文件是否存在
     * @param filePath 文件路径
     * @return
     */
    public static boolean fileIsExists(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return false;
    }
    /**
     * 删除文件
     *
     * @param filePath
     */
    public static boolean deleteFile(String filePath) {
        boolean delete = false;
            File file = new File(filePath);
            if (file.exists()) {
                delete =  file.delete();
            }
            return delete;
    }

    /**
     * 根据文件路径，获取文件名
     *
     * @param dir
     * @return
     */
    public static String getFileName(String dir) {
        int lastIndexOf = dir.lastIndexOf("/");
        return dir.substring(lastIndexOf);
    }

    /**
     * 打开文件
     *通过 Intent意图打开
     * @param filePath
     */
    public static void openFile(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        // 获取文件file的MIME类型
        String type = getMIMEType(file);
        // 设置intent的data和Type属性。
        intent.setDataAndType(/* uri */Uri.fromFile(file), type);
        // 跳转
        context.startActivity(intent);
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex).toLowerCase(Locale.getDefault());
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"},
            {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"}, {"", "*/*"}
    };

    /**
     *创建文件，创建之前检查文件目录，如果目录不存在，则新建目录，如果文件已存在，则先删除文件
     * @param filePath
     * @return
     */
    public static boolean checkAndCreateFile(String filePath) {
        boolean createFlag = false;
        File file = new File(filePath);
        File dir = new File(file.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            createFlag = file.createNewFile();
        } catch (IOException e) {
            createFlag = false;
            e.printStackTrace();
        }
        return createFlag;
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath()))
        {
            filePath = getSDCardPath();
        } else
        {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    /**
     * 将字符串追加到文件已有内容后面
     *
     * @param filePath 文件完整地址：D:/test.txt
     * @param append     是否追加内容，true 追加，false 覆盖
     * @param content      需要写入的
     */
    public static void writeFile(String filePath, String content,boolean append) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(filePath, append);
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


    /**
     * 读取文件
     * @param filePath 文件路径
     * @return 返回读取的内容
     */
    public static String readFile(String filePath){
        String text = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            byte[] bytes = new byte[1024];
            fis.read(bytes);//返回读取文件大小
            text = new String(bytes, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }

    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     */
    public void textToFile(final String strFilename, final String strBuffer) {
        try {
            // 创建文件对象
            File fileText = new File(strFilename);
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(fileText);

            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fileName 文件路径
     * @param isDeleteOldFile 是否删除旧文件
     * @return 是否创建成功
     */
    public boolean checkAndCreateFile(String fileName,boolean isDeleteOldFile) {
        boolean createNewFile = false;
        File file = new File(fileName);
        File dir = new File(file.getParent());
        //文件假路径不曾在，创建文件文件夹路径
        if (!dir.exists()) {
            boolean mkDirs = dir.mkdirs();
            if (!mkDirs) {
                throw new IllegalStateException("mkdirs fail");
            }
        }
        if (isDeleteOldFile && file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                createNewFile = file.createNewFile();
                if (!createNewFile) {
                    throw new IllegalStateException("createNewFile fail");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else { //文件已存在
            createNewFile = true;
        }
        return createNewFile;
    }


    /**
     * 保存字符串到 文件中
     * test ok 2019/6/26
     * @param filePath 保存路径
     * @param content 保存内容
     * @return true成功，false失败
     */
    public static boolean save(String filePath,String content){
        boolean save = false;
        if(TextUtils.isEmpty(content)){
            return false;
        }
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()){
            save = dir.mkdirs();
        }
        if (file.exists()){
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            save =  file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return save;
    }
}
