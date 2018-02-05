package com.sharktech.projectprob.alert;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.sharktech.projectprob.R;

public class DefaultAlert {

    private AlertDialog.Builder mAlert;
    private DialogInterface.OnClickListener mPositiveListener, mNegativeListener;
    private String mTitle, mMessage;
    private Context mContext;

    public DefaultAlert(Context context) {
        this.mTitle = "Alerta";
        this.mContext = context;
        this.mPositiveListener = this.mNegativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        };
        this.mAlert = new AlertDialog.Builder(context)
                .setPositiveButton("", mPositiveListener)
                .setNegativeButton("", mNegativeListener);
    }

    public DefaultAlert setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public DefaultAlert setTitle(int resource) {
        return setTitle(mContext.getString(resource));
    }

    public DefaultAlert setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    public DefaultAlert setMessage(int resource) {
        return setMessage(mContext.getString(resource));
    }

    public void setPositiveListener(DialogInterface.OnClickListener listener) {
        setPositiveListener(R.string.btn_ok, listener);
    }
    public void setPositiveListener(String text, DialogInterface.OnClickListener listener) {
        mAlert.setPositiveButton(text, listener);
    }

    public void setPositiveListener(int resource, DialogInterface.OnClickListener listener) {
        setPositiveListener(mContext.getString(resource), listener);
    }

    public void setNegativeListener(String text, DialogInterface.OnClickListener listener) {
        mAlert.setNegativeButton(text, listener);
    }

    public void setNegativeListener(DialogInterface.OnClickListener listener) {
        setNegativeListener(R.string.btn_cancel, listener);
    }

    public void setNegativeListener(int resource, DialogInterface.OnClickListener listener) {
        setNegativeListener(mContext.getString(resource), listener);
    }

    public void setListeners(final DefaultClickListener listener){

        mPositiveListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.positive(dialog, which);
            }
        };

        mNegativeListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.negative(dialog, which);
            }
        };
        setPositiveListener(mContext.getString(R.string.btn_ok), mPositiveListener);
        setNegativeListener(mContext.getString(R.string.btn_cancel), mNegativeListener);
    }

    public void show() {
        mAlert.setIcon(android.R.drawable.ic_dialog_alert);
        mAlert.setTitle(mTitle);
        mAlert.setMessage(mMessage);
        mAlert.show();
    }

    public interface DefaultClickListener {

        void positive(DialogInterface dialog, int which);

        void negative(DialogInterface dialog, int which);
    }
}
