package com.itxiaox.android.xutils.file;


/**
 * Android中内部存储，外部存储的概念
 *概念：注意内部存储不是内存。内部存储位于系统中很特殊的一个位置，如果你想将文件存储于内部存储中，
 * 那么文件默认只能被你的应用访问到，且一个应用所创建的所有文件都在和应用包名相同的目录下。
 * 也就是说应用创建于内部存储的文件，与这个应用是关联起来的。当一个应用卸载之后，
 * 内部存储中的这些文件也被删除。从技术上来讲如果你在创建内部存储文件的时候将文件属性设置成可读，
 * 其他app能够访问自己应用的数据，前提是他知道你这个应用的包名，如果一个文件的属性是私有（private），
 * 那么即使知道包名其他应用也无法访问。 内部存储空间十分有限，因而显得可贵，
 * 另外，它也是系统本身和系统应用程序主要的数据存储所在地，一旦内部存储空间耗尽，
 * 手机也就无法使用了。所以对于内部存储空间，我们要尽量避免使用。
 * Shared Preferences和SQLite数据库都是存储在内部存储空间上的。内部存储一般用Context来获取和操作。
 * 访问内部存储的API方法：
 *
 *
 *
 *
 */

public class PathUtils {
    private static final String TAG = "PathUtils";


//    public String tetsFilePath(Context context){
//        //内部存储，一般用Context来获取和操作
//        Log.d(TAG, "Environment.getDataDirectory().getAbsolutePath():"+Environment.getDataDirectory().getAbsolutePath());
//        Log.d(TAG, "getFilesDir()():"+context.getFilesDir().getAbsolutePath());
//        Log.d(TAG, "getCacheDir()():"+context.getCacheDir().getAbsolutePath());
//        Log.d(TAG, "getCacheDir()():"+context.getDir("test", Context.MODE_PRIVATE).getAbsolutePath());
//
//
//        //遍历所有
//        File[] files;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            files = context.getExternalFilesDirs(Environment.MEDIA_MOUNTED);
//            for (File file : files) {
//                Log.e(TAG, file.getAbsolutePath());
//            }
//        }
//
//    }
}
