package com.github.itxiaox.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itxiaox.android.xutils.app.AndroidUtils;
import com.itxiaox.android.xutils.app.AppUtils;
import com.itxiaox.android.xutils.app.Utils;
import com.itxiaox.android.xutils.log.LogUtils;
import com.itxiaox.android.xutils.ui.UIHelper;

public class MainActivity extends AppCompatActivity {

    StringBuffer buffer = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(getApplicationContext());
        TextView textView = findViewById(R.id.tv_info);
        buffer.append("======AppUtils测试：=======\n");
        buffer.append("appName:"+AppUtils.getAppName(this));
        buffer.append("\nversionName:"+AppUtils.getVersionName(this));
        buffer.append("\nversionCode:"+AppUtils.getVersionCode(this));
        buffer.append("\nsign:"+AppUtils.getAppSign(this));
        buffer.append("======AndroidUtils：=======\n");
        buffer.append("AndroidId:"+AndroidUtils.getAndroidId(this));
        buffer.append("是否是模拟器："+AndroidUtils.isEmulator(this));
        textView.setText(buffer.toString());
        boolean isForeground = AppUtils.isForeground(this);
        LogUtils.d("isForeground:"+isForeground);
    }


    @Override
    protected void onPause() {
        super.onPause();
        boolean isForeground = AppUtils.isForeground(this);
        LogUtils.d("isForeground:"+isForeground);
    }

    public void test(View view) {
        UIHelper.toast(this,"测试Toast");
//        UIHelper.showToast(this,"测试Toast");
    }

    public void reBoot(View view) {
        UIHelper.toast(this,"重启App");
        AppUtils.rebootApp2(this);
    }

    public void exitApp(View view) {
        UIHelper.toast("退出应用");
        AppUtils.exitApp(this);
    }
}