package com.hidian.charging.utils;

import android.widget.Toast;

import com.hidian.charging.App;

public class ToastUtils {

	private static Toast mToast;

	public static void showLong(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(App.getApplication(), text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public static void showShort(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(App.getApplication(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

}
