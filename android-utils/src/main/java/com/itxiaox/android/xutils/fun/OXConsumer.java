package com.itxiaox.android.xutils.fun;

/**
 * 一个参数的回调接口，
 * 代替java.util.function.BiConsumer;此接口只能在Android 7.1（sdk 24）版本中使用
 * test ok
 * @param <T1>
 */
public interface OXConsumer<T1> {
    void accept(T1 t1);
}
