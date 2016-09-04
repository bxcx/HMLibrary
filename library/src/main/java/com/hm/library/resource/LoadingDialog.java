package com.hm.library.resource;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.hm.library.R;
import com.victor.loading.book.BookLoading;


public class LoadingDialog extends AlertDialog {

    private TextView tips_loading_msg;
    private BookLoading bookloading;


    private CharSequence message = "加载中";

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.v_loadingdialog);
        bookloading = (BookLoading) findViewById(R.id.bookloading);
        tips_loading_msg = (TextView) findViewById(R.id.tv_loading_msg);
        tips_loading_msg.setText(this.message);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setMessage(CharSequence message) {
        this.message = message;
    }

    public void setMessage(int resId) {
        setMessage(getContext().getResources().getString(resId));
    }


    @Override
    public void show() {
        super.show();
        bookloading.stop();
        bookloading.start();
    }

    @Override
    public void cancel() {
        super.cancel();
        bookloading.stop();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        bookloading.stop();
    }
}
