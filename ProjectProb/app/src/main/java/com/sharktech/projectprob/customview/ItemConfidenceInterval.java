package com.sharktech.projectprob.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sharktech.projectprob.R;


public class ItemConfidenceInterval extends LinearLayout {

    private CheckBox mCheckBox;
    private EditText mEditText;

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

        mCheckBox = new CheckBox(context);
        mCheckBox.setLayoutParams(getParams(5));
        mCheckBox.setText(context.getText(R.string.txt_default));
        mCheckBox.setChecked(false);
        mCheckBox.setTextAppearance(context, R.style.TextViewColored);
        mCheckBox.setOnCheckedChangeListener(getCheckListener());

        mEditText = new EditText(context);
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mEditText.setLayoutParams(getParams(2));
        mEditText.setEnabled(false);
        mEditText.setTextAppearance(context, R.style.TextViewColored);
        mEditText.setGravity(Gravity.END);

        addView(mCheckBox);
        addView(mEditText);
    }

    public boolean isChecked(){
        return mCheckBox.isChecked();
    }

    public ItemConfidenceInterval setChecked(boolean checked){
        mCheckBox.setChecked(checked);
        return this;
    }

    public ItemConfidenceInterval setTitle(int text){
        mCheckBox.setText(text);
        return this;
    }

    public ItemConfidenceInterval setValue(String text){
        mEditText.setText(text);
        return this;
    }

    public ItemConfidenceInterval setValue(int text){
        mEditText.setText(text);
        return this;
    }

    public String getValue(){
        return mEditText.getText().toString();
    }

    private LinearLayout.LayoutParams getParams(int weight){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = weight;
        return params;
    }

    private CompoundButton.OnCheckedChangeListener getCheckListener(){
        return (buttonView, isChecked) -> {
            mEditText.setEnabled(isChecked);
            mEditText.setText(isChecked ? mEditText.getText() : "");
        };
    }
}
