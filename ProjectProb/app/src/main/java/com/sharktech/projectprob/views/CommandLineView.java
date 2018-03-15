package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customview.ItemDataDetail;

public class CommandLineView extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentActivity activity = getActivity();
        if(activity != null) activity.setTitle(getString(R.string.txt_cmd_line));

        View view = inflater.inflate(R.layout.fragment_command_line_detail, container, false);
        ItemDataDetail cmdNew = view.findViewById(R.id.cmd_new);
        ItemDataDetail cmdAdd = view.findViewById(R.id.cmd_add);
        ItemDataDetail cmdEdt = view.findViewById(R.id.cmd_edit);
        ItemDataDetail cmdDelVar = view.findViewById(R.id.cmd_delete_var);
        ItemDataDetail cmdDelEl = view.findViewById(R.id.cmd_delete_el);
        ItemDataDetail cmdPri = view.findViewById(R.id.cmd_primary);
        ItemDataDetail cmdSec = view.findViewById(R.id.cmd_secondary);
        ItemDataDetail cmdVars = view.findViewById(R.id.cmd_variables);
        ItemDataDetail cmdNewDesc = view.findViewById(R.id.cmd_new_desc);
        ItemDataDetail cmdAddDesc = view.findViewById(R.id.cmd_add_desc);
        ItemDataDetail cmdEdtDesc = view.findViewById(R.id.cmd_edt_desc);
        ItemDataDetail cmdDelDesc = view.findViewById(R.id.cmd_del_desc);
        ItemDataDetail cmdVarDesc = view.findViewById(R.id.cmd_var_desc);
        ItemDataDetail cmdColDesc = view.findViewById(R.id.cmd_col_desc);
        ItemDataDetail cmdRowDesc = view.findViewById(R.id.cmd_row_desc);
        ItemDataDetail cmdValDesc = view.findViewById(R.id.cmd_val_desc);
        ItemDataDetail cmdNumberDesc = view.findViewById(R.id.cmd_number_desc);
        ItemDataDetail cmdTextDesc = view.findViewById(R.id.cmd_text_desc);
        ItemDataDetail cmdValuesDesc = view.findViewById(R.id.cmd_values_desc);
        ItemDataDetail cmdFloatNum = view.findViewById(R.id.cmd_obs_float_num);
        ItemDataDetail cmdTextVal = view.findViewById(R.id.cmd_obs_text_value);
        ItemDataDetail cmdVarType = view.findViewById(R.id.cmd_obs_var_type);
        ItemDataDetail cmdValueType = view.findViewById(R.id.cmd_obs_value_type);
        ItemDataDetail cmdWords = view.findViewById(R.id.cmd_obs_words);

        cmdNew.setTexts(R.string.cmd_add, R.string.cmd_add_detail, R.string.cmd_default);
        cmdAdd.setTexts(R.string.cmd_new, R.string.cmd_new_detail, R.string.cmd_default);
        cmdEdt.setTexts(R.string.cmd_edt, R.string.cmd_edt_detail, R.string.cmd_default);
        cmdDelVar.setTexts(R.string.cmd_del_var, R.string.cmd_del_var_detail, R.string.cmd_default);
        cmdDelEl.setTexts(R.string.cmd_del_el, R.string.cmd_del_el_detail, R.string.cmd_default);
        cmdPri.setTexts(R.string.cmd_primary, R.string.cmd_primary_detail, R.string.cmd_default);
        cmdSec.setTexts(R.string.cmd_secondary, R.string.cmd_secondary_detail, R.string.cmd_default);
        cmdVars.setTexts(R.string.cmd_variables, R.string.cmd_variables_detail, R.string.cmd_default);
        cmdNewDesc.setTitleText("new");
        cmdNewDesc.setEquationText(R.string.cmd_new_desc);
        cmdAddDesc.setTitleText("add");
        cmdAddDesc.setEquationText(R.string.cmd_add_desc);
        cmdEdtDesc.setTitleText("edt");
        cmdEdtDesc.setEquationText(R.string.cmd_edt_desc);
        cmdDelDesc.setTitleText("del");
        cmdDelDesc.setEquationText(R.string.cmd_del_desc);
        cmdVarDesc.setTitleText("var");
        cmdVarDesc.setEquationText(R.string.cmd_var_desc);
        cmdColDesc.setTitleText("col");
        cmdColDesc.setEquationText(R.string.cmd_col_desc);
        cmdRowDesc.setTitleText("row");
        cmdRowDesc.setEquationText(R.string.cmd_row_desc);
        cmdValDesc.setTitleText("val");
        cmdValDesc.setEquationText(R.string.cmd_val_desc);
        cmdNumberDesc.setTitleText("number");
        cmdNumberDesc.setEquationText(R.string.cmd_number_desc);
        cmdTextDesc.setTitleText("text");
        cmdTextDesc.setEquationText(R.string.cmd_text_desc);
        cmdValuesDesc.setTitleText("values");
        cmdValuesDesc.setEquationText(R.string.cmd_values_desc);
        cmdFloatNum.setTitleText("Número decimal");
        cmdFloatNum.setEquationText(R.string.cmd_obs_float_num);
        cmdTextVal.setTitleText("Valor textual");
        cmdTextVal.setEquationText(R.string.cmd_obs_text_value);
        cmdVarType.setTitleText("Tipo da variável");
        cmdVarType.setEquationText(R.string.cmd_obs_var_type);
        cmdValueType.setTitleText("Tipo do valor");
        cmdValueType.setEquationText(R.string.cmd_obs_value_type);
        cmdWords.setTitleText("Palavras reservadas");
        cmdWords.setEquationText(R.string.cmd_obs_words);

        return view;
    }
}
