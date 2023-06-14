package com.test.moviegallery.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.test.moviegallery.R;

public class LoadingDialog {
    private Context context;
    private Dialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loading);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}