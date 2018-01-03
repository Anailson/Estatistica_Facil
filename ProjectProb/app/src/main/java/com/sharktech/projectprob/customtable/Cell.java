package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.sharktech.projectprob.R;

public class Cell<E> extends android.support.v7.widget.AppCompatEditText{

    private Value<E> value;
    private Position position;
    private Listeners listeners;

    public Cell(Context context){
        this(context, null);
    }

    public Cell(Context context, Value<E> value){
        super(context);
        this.value = value;
        this.position = new Position(-1, -1);
        this.listeners = new Listeners(getContext(), position);

        init();
    }

    public Float asFloat(){
        if(value.isNumber()){
            return Float.valueOf(value.toString());
        }
        return 0f;
    }

    public int getCount(){
        return value.getCount();
    }

    private void init(){

        setText(value.formattedText());
        setSingleLine(true);
        setTextColor(getResources().getColor(R.color.color_default));
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setPadding(20, 10, 20, 5);
        setTextSize(16);
        setFocusableInTouchMode(false);
        setImeOptions(EditorInfo.IME_ACTION_DONE);
        setOnLongClickListener(listeners);
        setOnFocusChangeListener(listeners);
        setOnEditorActionListener(listeners);
    }

    private void setGravity() {

        if(position.col >=  0 && position.row >= 0){
            setGravity(Gravity.START);
        } else if(position.col >= 0 && position.row < 0) {
            setGravity(Gravity.CENTER);
        } else if(position.col < 0 && position.row < 0){
            setGravity(Gravity.END);
        }
    }

    protected boolean isEmpty(){
        return  value.isEmpty();
    }

    protected boolean isNumber(){
        return  value.isNumber();
    }

    protected void setHeading() {

        position.row = -1;
        setBackgroundColor(getContext().getResources().getColor(R.color.color_primary_light));
        setTypeface(getTypeface(), Typeface.BOLD);
        setTextColor(Color.WHITE);
        setText(value.simpleText());
    }

    protected void setPosition(int col, int row) {

        if(value != null) {
            position.setPosition(col, row);
        }
        if(col < 0){
            setHeading();
        }
        setGravity();
    }

    protected void setCol(int col) {
        setPosition(col, position.row);
    }

    @Override
    public String toString() {
        return value.formattedText();
    }

    public Value<E> getValue() {
        return value;
    }

    public void setValue(Value<E> value){
        this.value = value;
    }

    private class Position {
        private int col, row;

        private Position(int col, int row) {
            setPosition(col, row);
        }

        private void setPosition(int col, int row) {
            this.col = col;
            this.row = row;
        }

        private boolean isClickable() {
            return row >= 0 && col >= 0;
        }

        @Override
        public String toString() {
            return "Pos: (" + col + "x" + row + ");";
        }
    }

    private class Listeners implements View.OnLongClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {

        private Context context;
        private Position position;

        private Listeners(Context context, Position position) {
            this.context = context;
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {
            if (position.isClickable()) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show();
                v.setFocusableInTouchMode(true);
            }
            return true;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (position.isClickable()) {
                Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show();
                if (!hasFocus) v.setFocusableInTouchMode(false);
            }
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if(actionId == EditorInfo.IME_ACTION_DONE){

                v.setFocusableInTouchMode(false);
                v.setFocusable(false);
            }
            return false;
        }
    }
}

