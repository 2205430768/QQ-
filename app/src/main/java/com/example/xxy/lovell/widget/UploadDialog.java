package com.example.xxy.lovell.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.xxy.lovell.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by xxy on 2017/2/5.
 */

public class UploadDialog extends Dialog {
    private TextView dialogLoadingText;
    private CircularProgressBar progressBar;

    public UploadDialog(Context context) {
        super(context, R.style.progress_style);
        setCancelable(true);
        setContentView(R.layout.layout_upload);
        dialogLoadingText = (TextView) findViewById(R.id.dialog_loading_text);
        progressBar = (CircularProgressBar) findViewById(R.id.video_progress);
    }
    public UploadDialog(Context context, int resid) {
        this(context);
        dialogLoadingText.setText(resid);
    }

    public UploadDialog(Context context, String message) {
        this(context);
        if (!TextUtils.isEmpty(message)) {
            dialogLoadingText.setText(message);
        }
    }

    public final void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            dialogLoadingText.setText(message);
        }
    }

    public void setPercentsProgress(int percentsProgress){
        progressBar.setProgress(percentsProgress);
    }

    public final void dismiss() {
        super.dismiss();
    }

    public final void show() {
        super.show();
    }

    public void canceable(boolean value) {
        setCancelable(value);
    }




}
