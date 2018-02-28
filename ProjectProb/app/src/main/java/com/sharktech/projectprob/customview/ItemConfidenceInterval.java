package com.sharktech.projectprob.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharktech.projectprob.R;


public class ItemConfidenceInterval extends LinearLayout {

    private TextView mTitle;
    private EditText mValue;

    public ItemConfidenceInterval(Context context) {
        super(context);
        init(context);
    }

    public ItemConfidenceInterval(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemConfidenceInterval(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTitle = new TextView(context);
        mTitle.setLayoutParams(getParams(5));
        mTitle.setText(context.getText(R.string.txt_default));
        mTitle.setTextAppearance(context, R.style.TextViewColored);

        mValue = new EditText(context);
        mValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mValue.setLayoutParams(getParams(2));
        mValue.setTextAppearance(context, R.style.TextViewColored);
        mValue.setGravity(Gravity.END);

        addView(mTitle);
        addView(mValue);
    }

    public boolean isChecked(){
        return !mTitle.getText().toString().equals("");
    }

    public ItemConfidenceInterval setTitle(int text){
        mTitle.setText(text);
        return this;
    }

    public ItemConfidenceInterval setValue(String text){
        mValue.setText(text);
        return this;
    }

    public ItemConfidenceInterval setValue(int text){
        mValue.setText(text);
        return this;
    }

    public String getValue(){
        return mValue.getText().toString();
    }

    private LinearLayout.LayoutParams getParams(int weight){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = weight;
        return params;
    }
}
