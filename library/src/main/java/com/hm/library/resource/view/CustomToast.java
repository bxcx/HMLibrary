package com.hm.library.resource.view;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class CustomToast {

	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	public static Toast makeText(Context mContext, String text, int duration) {

		mHandler.removeCallbacks(r);
		if (mToast != null)
			mToast.setText(text);
		else
			mToast = Toast.makeText(mContext, text, duration);
		mHandler.postDelayed(r, 1000);

		return mToast;
	}

	public static Toast makeText(Context mContext, int resId, int duration) {
		return makeText(mContext, mContext.getResources().getString(resId), duration);
	}

}