package com.sharktech.projectprob.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharktech.projectprob.R;

public class ItemDataDetail extends LinearLayout {

    private TextView mTxtTitle, mTxtEquation, mTxtValue;

    public ItemDataDetail(Context context) {
        super(context);
        init(context);
    }

    public ItemDataDetail(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemDataDetail(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int bottom = getResources().getDimensionPixelSize(R.dimen.medium_gap);
        setPadding(0, 0, 0, bottom);

        mTxtTitle = new TextView(context);
        //mTxtTitle.setText(R.string.txt_default);
        mTxtTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTxtTitle.setTextAppearance(context, R.style.TextViewColored);

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.HORIZONTAL);
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTxtEquation = new TextView(context);
        //mTxtEquation.setText(R.string.txt_default);
        mTxtEquation.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTxtEquation.setTextAppearance(context, R.style.TextViewSmallColored);

        mTxtValue = new TextView(context, null, R.style.TextViewSmallColored);
        //mTxtValue.setText(R.string.txt_default);
        mTxtValue.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTxtValue.setTextAppearance(context, R.style.TextViewSmallColored);
        mTxtValue.setGravity(Gravity.END);

        content.addView(mTxtEquation);
        content.addView(mTxtValue);

        addView(mTxtTitle);
        addView(content);
    }

    public void setTexts(int title, int equation, int value){
        setTitleText(title);
        setEquationText(equation);
        setValueText(value);
    }

    public void seTexts(String title, String equation, String value){
        setTitleText(title);
        setEquationText(equation);
        setValueText(value);
    }

    public void setTitleText(int title){
        mTxtTitle.setText(title);
    }

    public void setTitleText(String title){
        mTxtTitle.setText(title);
    }

    public void setEquationText(int equation){
        mTxtEquation.setText(equation);
    }

    public void setEquationText(String equation){
        mTxtEquation.setText(equation);
    }

    public void setValueText(int value){
        mTxtValue.setText(value);
    }

    public void setValueText(String value){
        mTxtValue.setText(value);
    }
}
