package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.Variable;
import com.sharktech.projectprob.models.VariableNumber;

import java.util.ArrayList;

public class DataTableController {

    private Fragment mFragment;
    private Variable.IVariable mVariable;

    public DataTableController(Fragment mFragment, Variable.IVariable variable) {
        this.mFragment = mFragment;
        this.mVariable = variable;
    }

    public ViewGroup buildTable() {
        if(mVariable != null) {
            Variable.Analyses analyses = new Variable<>(mFragment.getContext(), mVariable).calculate();
        }
        CustomTable table = new CustomTable(mFragment.getContext());

        VariableNumber col1 = new VariableNumber("Primeira Coluna INT");
        col1.add(new Integer[]{23, 1243, 4545, 5676, 6756});

        VariableNumber col2 = new VariableNumber("Segunda Coluna FLOAT");
        col2.add(new Float[]{23.2f, 1243.53f, 454.435f, 5676.676575f, 6756.3f});

        VariableNumber col3 = new VariableNumber("Terceira Coluna DOUBLE");
        col3.add(new Double[]{454.3, 767665.43, 565465.554, 456456.});

        ArrayList<Variable.IVariable> variables = new ArrayList<>();
        variables.add(col1);
        variables.add(col2);
        variables.add(col3);

        return table.build(variables);
    }
}
