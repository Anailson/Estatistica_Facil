package com.sharktech.projectprob.persistence;

import com.sharktech.projectprob.customtable.TableCell.ICell;
import com.sharktech.projectprob.customtable.TableColumn.IVariable;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableObject;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;


public class VariablePersistence {

    private ArrayList<IVariable> mVariables, mVarsAuxi;

    private static VariablePersistence mPersistence;

    private VariablePersistence() {
        this.mVariables = new ArrayList<>();
        this.mVarsAuxi = new ArrayList<>();
        init();
    }

    private void init(){

        VariableNumber flts = new VariableNumber("Float");
        flts.add(new Float[]{1.3f, 2.2f, 1.3f, 4.4f, 2.3f, 4.3f, 7.4f, 7.2f, 2.2f, 2.5f});
        flts.add(new Float[]{1.2f, 1.2f, 7.3f, 4.9f, 1.3f, 7.3f, 8.4f, 7.0f, 4.2f, 2.7f});
        flts.add(new Float[]{9.2f, 2.6f, 5.3f, 4.2f, 8.3f, 7.5f, 3.4f, 7.6f, 2.2f, 2.0f});
        flts.add(new Float[]{9.7f, 4.6f, 5.8f, 9.2f, 2.3f, 7.9f, 2.4f, 7.3f, 5.2f, 2.5f});
        flts.add(new Float[]{6.7f, 0.6f, 2.8f, 1.2f, 2.7f, 4.9f, 9.4f, 7.8f, 1.2f, 2.0f});

        VariableString strs = new VariableString("String");
        strs.add(new String[]{"Um", "Dois", "Três", "Dois", "Cinco", "Um", "Sete"});

        VariableNumber example = new VariableNumber("example_1");
        example.add(new Integer[]{48, 48, 49, 50, 50, 50, 50, 52, 53, 53});
        example.add(new Integer[]{54, 55, 56, 58, 58, 60, 65, 67, 70, 70});
        example.add(new Integer[]{70, 71, 72, 74, 75, 75, 76, 77, 77, 77});
        example.add(new Integer[]{78, 79, 80, 80, 81, 82, 83, 83, 85, 89});

        mVariables.add(flts);
        mVariables.add(strs);
        mVariables.add(example);
    }

    public static VariablePersistence getInstance(){
        if(mPersistence == null){
            mPersistence = new VariablePersistence();
        }
        return mPersistence;
    }

    public ArrayList<IVariable> getVariables(IVariable variables) {
        add(variables);
        return getVariables();
    }

    public ArrayList<IVariable> getVariables(ArrayList<IVariable> variables) {
        add(variables);
        return getVariables();
    }

    public int size(){
        return getVariables().size();
    }

    public IVariable getVariable(int index){
        return getVariables().get(index);
    }

    public ArrayList<IVariable> getVariables() {

        if(mVarsAuxi.size() > 0){
            mVariables.addAll(mVarsAuxi);
            mVarsAuxi.clear();
        }

        return mVariables;
    }

    private IVariable getVariable(final String title, Object[] values){

        final ArrayList<ICell> cells = new ArrayList<>();
        for(final Object o : values){
            cells.add(new VariableObject.ValueObject(o));
        }

        VariableObject var = new VariableObject(title);
        var.add(cells);
        return var;
    }

    public void add(ArrayList<IVariable> vars) {
        mVarsAuxi.addAll(vars);
    }

    public void add(IVariable variable){
        mVarsAuxi.add(variable);
    }

    public void remove(int index){
        mVariables.remove(index);
    }
}
