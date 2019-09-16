package com.yeahworld.util;

import android.app.AlertDialog;
import android.content.Context;

public class YeahAlertDialog extends AlertDialog {
	protected YeahAlertDialog(Context context) {
		super(context);
	}

	protected YeahAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	protected YeahAlertDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
	}
}
