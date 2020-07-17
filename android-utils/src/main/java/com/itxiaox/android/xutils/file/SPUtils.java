package com.itxiaox.android.xutils.file;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SPUtils 主要是保存程序的key-value的简单信息，
 * value的类型为简单数据类型 int/long/float/boolean/string
 * 注意：不支持double类型的数据存储
 * 以及多个的value,set<String></>
 *
 * 对SharedPreference的使用做了建议的封装，对外公布出put，get，remove，clear等等方法；
 注意一点，里面所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit

 首先说下为什么，因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；

 所以我们使用apply进行替代，apply异步的进行写入；

 但是apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配；

 SharedPreferencesCompat也可以给大家创建兼容类提供了一定的参考~~

 * @ClassName: SPUtils
 * @Description: TODO
 * @author xiaoxiao
 * @date modify by 2015-9-21 下午5:08:20
 *
 */

/**
 *
 * SharedPreferencesCompat.apply失效，test by xiaox 2020/1/10
 *
 *
 */
public class SPUtils{
    private static SharedPreferences sp;
    private static Context mContext;
    /**
     * 保存在手机里面的文件名
     * data/data/<package>/shared_prefs目录下
     */
    public static  String FILE_NAME = "share_data";//默认值

    public static  void init(Context context,String fileName){
        mContext = context.getApplicationContext();
        if(!TextUtils.isEmpty(fileName)){
            FILE_NAME = fileName;
        }
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 同步保存数据的方法，
     *我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param object
     */
    public static void putSync(String key, Object object) {
        Editor editor = edit(sp,key,object);
        editor.commit();
    }

    /**
     * 异步存储</br>
     * 要求：</br>
     * API >= 9
     * @param key
     * @param object
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public static void putAsync(String key, Object object) {
        Editor editor = edit(sp,key,object);
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
    /**
     * 保存数据，调用此方法前提是已经有Context，即需要先调用
     * <pre>
     *     SPUtils.init(context,filename)
     * <pre/>
     * 此方法可以统一的在Application onCreate中统一初始化， 这样在其它的地方就不需要使用Context，也不会造成引用中到处都是context对象
     *
     * @param key   需要获取的key
     * @param defaultObject  默认值，当sp文件中没有的时候的默认值
     */
    public static Object get(String key, Object defaultObject) {
        if(mContext==null){
            throw new NullPointerException("mContext is NUll, please invoke init(context,filename) first");
        }
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
//        else if (defaultObject instanceof Double) {
//            return Double.longBitsToDouble(sp.getLong(key, (Long) defaultObject));
//        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void removeSync(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
//        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 同步清空某个key值已经对应的值
     * @param key 想要移除的key
     */
    public static void clearSync(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 异步清空某个key对应的值
     * @param key
     */
    public static void clearAsync(String key){
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 异步清除所有数据
     */
    public static void clearAllAsync() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 同步清除所有数据
     */
    public static void clearAllSync() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @return
     */
    public static Map<String, ?> getAll() {
        return sp.getAll();
    }

    private static Editor edit(SharedPreferences sp,String key, Object object){
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
//        }
//        else if (object instanceof Double) {
//            //java.lang.Double cannot be cast to java.lang.Float
//            editor.putLong(key, Double.doubleToRawLongBits((Double) object));
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            throw new IllegalStateException("Unsupported data types");
        }
        return editor;
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}
