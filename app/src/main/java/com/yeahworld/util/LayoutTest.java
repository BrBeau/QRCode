package com.yeahworld.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class LayoutTest {

	private static AlertDialog alertDialog;
	private static FrameLayout frameLayout;
	private static ArrayList<View> arryView = new ArrayList<>();

	public static View pushView(Context context, int layout){

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);

		//设置view的宽高
		int width = (int) Math.min(displayMetrics.widthPixels * 0.85, 540 * displayMetrics.density);
		int height = (int) Math.min(displayMetrics.heightPixels * 0.85, 340 * displayMetrics.density);

		//设置如果是竖屏时候的宽高
		if (context.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
			width = (int) (displayMetrics.widthPixels * 0.95);
			height = (int)Math.min(displayMetrics.heightPixels * 0.85, 340 * displayMetrics.density);
		}

		if (alertDialog == null){

			frameLayout = new FrameLayout(context);
			AlertDialog.Builder dialogBuilder = new YeahAlertDialog.Builder(context);
			alertDialog = dialogBuilder.create();
			alertDialog.show();
			alertDialog.setContentView(frameLayout);

			//实例化宽高
			WindowManager.LayoutParams lp =new WindowManager.LayoutParams();
			lp.copyFrom(alertDialog.getWindow().getAttributes());
			lp.width = width;
			lp.height =WindowManager.LayoutParams.WRAP_CONTENT;

			alertDialog.getWindow().setAttributes(lp);
			alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

			ViewGroup.LayoutParams lp2 = frameLayout.getLayoutParams();
			lp2.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp2.height =height;

			frameLayout.setLayoutParams(lp2);
			frameLayout.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
					if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
						popView(context);
						return true;
					}
					return false;
				}
			});
		}

		View contentView = LayoutInflater.from(context).inflate(layout, null);
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);

		arryView.add(contentView);
		frameLayout.removeAllViews();
		frameLayout.addView(contentView);
		return contentView;
	}

	public static void popView(Context context)
	{
		if (alertDialog == null || arryView.size() == 0){
			return;
		}

		View removeView = arryView.remove(arryView.size() - 1);
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(removeView.getWindowToken(), 0);

		if (arryView.size() > 0){
			View contentView = arryView.get(arryView.size() - 1);

			frameLayout.removeAllViews();
			frameLayout.addView(contentView);
		}else {
			alertDialog.dismiss();
		}
	}

	/**
	 * 清空布局的缓存
	 */
	public static void dismissDialog(){

		try {

			if (alertDialog !=null && alertDialog.isShowing()){
				alertDialog.dismiss();
			}

		}catch (Exception e){
			e.printStackTrace();
		}finally {
			arryView.clear();
			frameLayout = null;
			alertDialog = null;
		}
	}

	/**
	 * 获取布局，避免使用R.layout来获取
	 * @param context 上下文
	 * @param paramString 想要加载布局的名称
	 * @return int类型的layout
	 */
	public static int getResLayout(Context context, String paramString){
		return context.getResources().getIdentifier(paramString, "layout", context.getPackageName());
	}

	/**
	 * 获取相应布局的id或其他id
	 * @param context 上下文
	 * @param paramString id的名称
	 * @return int类型的Id
	 */
	public static int getResId(Context context, String paramString){
		return context.getResources().getIdentifier(paramString, "id", context.getPackageName());
	}

	/**
	 * 获取相应的字符串
	 * @param context 上下文
	 * @param paramString 字符串的name名称
	 * @return int类型的string
	 */
	public static int getResString(Context context, String paramString){
		return context.getResources().getIdentifier(paramString, "string", context.getPackageName());
	}
}
