package com.itxiaox.android.xutils.ui;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 打开或关闭软键盘
 * 
 * @author zhy
 * 
 */
public class KeyBoardUtils{
	private  static InputMethodManager inputMethodManager;
	/**
	 * 打卡软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/**
	 * 关闭软键盘
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		if(inputMethodManager==null){
			inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		}
		if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (activity.getCurrentFocus() != null)
				inputMethodManager.hideSoftInputFromWindow(activity
								.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
