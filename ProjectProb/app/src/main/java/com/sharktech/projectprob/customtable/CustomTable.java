package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.TableColumn.IVariable;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;

public class CustomTable {

    private Context mContext;
    private HorizontalScrollView mHorizontalContent;
    private ScrollView mContent;
    private String mNoDataFound;
    private boolean mLineCounter;

    public CustomTable(Context context) {
        this.mContext = context;
        init();
    }

    public void setNoDataFound(int stringResource){
        setNoDataFound(mContext.getString(stringResource));
    }

    public void setNoDataFound(String noDataFound){
        this.mNoDataFound = noDataFound;
    }

    public void setLineCounter(boolean lineCounter) {
        this.mLineCounter = lineCounter;
    }

    private void init() {
        mNoDataFound = "";

        mContent = new ScrollView(mContext);
        mContent.setLayoutParams(getParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContent.setVerticalScrollBarEnabled(true);

        mHorizontalContent = new HorizontalScrollView(mContext);
        mHorizontalContent.setLayoutParams(getParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHorizontalContent.setHorizontalScrollBarEnabled(true);
        mHorizontalContent.addView(mContent);
    }

    public ViewGroup build(ArrayList<IVariable> variables) {
        LinearLayout layout = getHorizontalLayout();
        int nRows = nRows(variables);

        if (hasVariables(variables)) {

            if(mLineCounter){
                layout.addView(addLineCounter(nRows));
            }
            buildColumns(variables, layout, nRows);

        } else {

            TableColumn title = new TableColumn<>(mContext, new VariableString(mNoDataFound));
            layout.addView(title.build());
        }
        mContent.addView(layout);
        return mHorizontalContent;
    }

    public void clear() {

        ViewGroup parentContent = (ViewGroup) mContent.getParent();
        if(parentContent != null) parentContent.removeView(mContent);

        mHorizontalContent.removeView(mContent);
        init();
    }

    private void buildColumns(ArrayList<IVariable> variables, LinearLayout layout, int nRows){

        for(int col = 0; col < variables.size(); col++) {

            int nElements = variables.get(col).nElements();
            TableColumn<IVariable> column = new TableColumn<>(mContext, variables.get(col));

            if(nElements < nRows) {
                fillColumn(nRows, column);
            }
            column.setPosition(col);
            removeParent(column);
            layout.addView(column.build());
        }
    }


    private void removeParent(View child){
        ViewGroup parent = (ViewGroup) child.getParent();
        if(parent != null) parent.removeView(child);
    }

    private void fillColumn(int nRows, TableColumn column){
        for(int i = column.nElements(); i < nRows; i++){
            //column.emptyCell();
        }
    }

    private TableColumn addLineCounter(int nRows){

        VariableNumber column = new VariableNumber("#");
        for(int i = 0; i < nRows; i++){
            column.add(i + 1);
        }

        TableColumn<VariableNumber> variable = new TableColumn<>(mContext, column);
        variable.setBackgroundColor(mContext.getResources().getColor(R.color.color_primary_light));
        return variable.build();
    }

    private LinearLayout getHorizontalLayout(){
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

    private boolean hasVariables(ArrayList<IVariable> variables) {

        if (variables.size() < 0) {
            return false;
        }
        for (IVariable column: variables) {

            if (column.nElements() > 0) {
                return true;
            }
        }
        return false;
    }

    private int nRows(ArrayList<IVariable> variables){

        int nRows = 0;
        for (IVariable var : variables) {
            nRows = nRows > var.nElements() ? nRows : var.nElements();
        }
        return nRows;
    }

    private ViewGroup.LayoutParams getParams(int width, int height) {
        return new ViewGroup.LayoutParams(width, height);
    }
}
