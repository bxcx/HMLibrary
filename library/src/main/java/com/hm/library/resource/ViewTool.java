package com.hm.library.resource;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewTool {

	public static void setListViewHeight(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	public static void setGridViewHeight(GridView gridView, int verticalSpacing) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int columnsNum = gridView.getNumColumns();

		int totalHeight = 0;
		int tempLen = listAdapter.getCount() / columnsNum;
		if (listAdapter.getCount() % columnsNum > 0) {
			tempLen++;
		}

		for (int i = 0, len = tempLen; i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + (verticalSpacing * (tempLen - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		gridView.setLayoutParams(params);
	}

	/**
	 * @根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * @得到手机的dpi
	 */
	public static float getdpi(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * @得到手机的dpi
	 */
	public static int getWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;

		return screenWidth;
	}

	public static int getHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.heightPixels;

		return screenWidth;
	}

	/**
	 * @根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 隐藏键盘
	 */
	public static void hideSoftInputFromWindow(View editeText) {
		try {
			InputMethodManager imm = (InputMethodManager) editeText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editeText.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void hideSoftInputFromWindow(Activity context) {
		try {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
			if (isOpen)
				imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}

	}

	public static void showSoftInput(final EditText editText) {
		try {
			editText.setFocusable(true);
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
			InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(editText, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void showSoftInput(final EditText editText, long delay) {
		try {
			editText.setFocusable(true);
			editText.setFocusableInTouchMode(true);
			editText.requestFocus();
			// InputMethodManager inputManager = (InputMethodManager) editText
			// .getContext()
			// .getSystemService(Context.INPUT_METHOD_SERVICE);
			//
			// inputManager.showSoftInput(editText, 0);

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(
							Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInput(editText, 0);
				}
			}, delay);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 中间加横线
	 * 
	 * @param text
	 */
	public static void onTextMiddleLine(TextView text) {
		try {
			text.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 底部加横线
	 * 
	 * @param text
	 */
	public static void onTextBottomLine(TextView text) {
		try {
			text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param info
	 *            titile设置标题
	 * @param context
	 */
	public static void setTitileInfo(Activity context, String info, int id) {
		TextView setTitileInfo = (TextView) context.findViewById(id);
		setTitileInfo.setText(info);
	}

	/**
	 * 给titile设置标题和显示返回键
	 * 
	 * @param context
	 * @param info
	 *            标题
	 * @param listener
	 *            返回键的监听事件 可以传null
	 */
	public static void setTitileInfo(final Activity context, String info, OnClickListener listener, int title, int left) {
		TextView setTitileInfo = (TextView) context.findViewById(title);
		setTitileInfo.setText(info);

		View iv_left = context.findViewById(left);
		iv_left.setVisibility(View.VISIBLE);
		if (listener != null) {
			iv_left.setOnClickListener(listener);
		} else {
			iv_left.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					context.finish();
				}
			});
		}
	}

	private static long lastClickTime;

	/**
	 * 是否重复点击
	 * 
	 * @return true 重复点击 不响应
	 */
	public static boolean isFastDoubleClick() {

		long time = System.currentTimeMillis();

		long timeD = time - lastClickTime;

		if (0 < timeD && timeD < 800) {

			return true;
		}

		lastClickTime = time;
		return false;
	}

	public interface OnDoubleClickListener {
		public void OnSingleClick(View v);

		public void OnDoubleClick(View v);
	}

	/**
	 * 注册一个双击事件
	 */
	public static void setOnDoubleClickListener(View view, final OnDoubleClickListener listener) {
		if (listener == null)
			return;
		view.setOnClickListener(new OnClickListener() {
			private static final int DOUBLE_CLICK_TIME = 350; // 双击间隔时间350毫秒
			private boolean waitDouble = true;

			private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					listener.OnSingleClick((View) msg.obj);
				}

			};

			// 等待双击
			public void onClick(final View v) {
				if (waitDouble) {
					waitDouble = false; // 与执行双击事件
					new Thread() {

						public void run() {
							try {
								Thread.sleep(DOUBLE_CLICK_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // 等待双击时间，否则执行单击事件
							if (!waitDouble) {
								// 如果过了等待事件还是预执行双击状态，则视为单击
								waitDouble = true;
								Message msg = handler.obtainMessage();
								msg.obj = v;
								handler.sendMessage(msg);
							}
						}

					}.start();
				} else {
					waitDouble = true;
					listener.OnDoubleClick(v); // 执行双击
				}
			}
		});
	}
}
