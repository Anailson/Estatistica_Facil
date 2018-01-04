package com.sharktech.projectprob.persistence;

import android.content.Context;

import com.sharktech.projectprob.customtable.Variable;

import java.util.ArrayList;


public class VariablePersistence {

    private ArrayList<Variable> mVariables, mVarsAuxi;

    private static VariablePersistence mPersistence;

    private VariablePersistence() {
        this.mVariables = new ArrayList<>();
        this.mVarsAuxi = new ArrayList<>();
    }

    public static VariablePersistence getInstance(){
        if(mPersistence == null){
            mPersistence = new VariablePersistence();
        }
        return mPersistence;
    }

    public ArrayList<Variable> getVariables(Context context, ArrayList<Variable> addVars) {
        ArrayList<Variable> vars = getVariables(context);
        vars.addAll(addVars);
        return vars;
    }

    public ArrayList<Variable> getVariables(Context context) {

        mVariables.add(getVariable(context, "Integer", new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20}));
        mVariables.add(getVariable(context, "String",  new String[]{"Um", "Dois", "Três"}));
        mVariables.add(getVariable(context, "Character", new Character[]{'U', 'D', 'T', 'Q'}));
        mVariables.add(getVariable(context, "Float", new Float[]{1.3f, 2.2f, 3.3f, 4.4f}));
        if(mVarsAuxi.size() > 0){
            mVariables.addAll(mVarsAuxi);
        }
        return mVariables;
    }

    private <E> Variable getVariable(Context context, String title, E[] values){
        Variable<E> variable = new Variable<>(context, title);
        for(E value : values){
            variable.add(value);
        }
        return variable;
    }

    public void addVars(ArrayList<Variable> vars) {
        mVariables.addAll(vars);
    }
}
