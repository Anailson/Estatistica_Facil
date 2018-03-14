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

public class FormulaView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentActivity activity = getActivity();
        if(activity != null) activity.setTitle(getString(R.string.txt_formula));
        View view = inflater.inflate(R.layout.fragment_formula, container, false);

        ItemDataDetail fmlAvgArt = view.findViewById(R.id.fml_avg_arithmetic);
        ItemDataDetail fmlAvgArtPnd = view.findViewById(R.id.fml_avg_arithmetic_pnd);
        ItemDataDetail fmlAvgGeo = view.findViewById(R.id.fml_avg_geometric);
        ItemDataDetail fmlAvgGeoPnd = view.findViewById(R.id.fml_avg_geometric_pnd);
        ItemDataDetail fmlAvgWeigh = view.findViewById(R.id.fml_avg_weighted);
        ItemDataDetail fmlAvgWeighPnd = view.findViewById(R.id.fml_avg_weighted_pnd);
        ItemDataDetail fmlAvgQuad = view.findViewById(R.id.fml_avg_quadratic);
        ItemDataDetail fmlAvgQuadPnd= view.findViewById(R.id.fml_avg_quadratic_pnd);
        ItemDataDetail fmlKurtosis = view.findViewById(R.id.fml_kurtosis);
        ItemDataDetail fmlAsymmetry = view.findViewById(R.id.fml_asymmetry);
        ItemDataDetail fmlDeviation = view.findViewById(R.id.fml_deviation);
        ItemDataDetail fmlFstQuart = view.findViewById(R.id.fml_fst_quart);
        ItemDataDetail fmlSndQuart = view.findViewById(R.id.fml_snd_quart);
        ItemDataDetail fmlTrdQuart = view.findViewById(R.id.fml_trd_quart);
        ItemDataDetail fmlFstTenth = view.findViewById(R.id.fml_fst_tenth);
        ItemDataDetail fmlNthTenth = view.findViewById(R.id.fml_nth_tenth);
        ItemDataDetail fmlIntervalAvg = view.findViewById(R.id.fml_confidence_interval_avg);
        ItemDataDetail fmlIntervalProp = view.findViewById(R.id.fml_confidence_interval_prop);
        ItemDataDetail fmlIntervalVar = view.findViewById(R.id.fml_confidence_interval_var);

        fmlAvgArt.setTexts(R.string.fml_arithmetic_avg_txt, R.string.sym_avg_arithmetic, R.string.fml_default);
        fmlAvgArtPnd.setTexts(R.string.fml_arithmetic_avg_pnd_txt, R.string.sym_avg_arithmetic_pound, R.string.fml_default);
        fmlAvgGeo.setTexts(R.string.fml_geometric_avg_txt, R.string.sym_avg_geometric, R.string.fml_default);
        fmlAvgGeoPnd.setTexts(R.string.fml_geometric_avg_pnd_txt, R.string.sym_avg_geometric_pound, R.string.fml_default);
        fmlAvgWeigh.setTexts(R.string.fml_weighted_avg_txt, R.string.sym_avg_weighted, R.string.fml_default);
        fmlAvgWeighPnd.setTexts(R.string.fml_weighted_avg_pnd_txt, R.string.sym_avg_weighted_pound, R.string.fml_default);
        fmlAvgQuad.setTexts(R.string.fml_quadratic_avg_txt, R.string.sym_avg_quadratic, R.string.fml_default);
        fmlAvgQuadPnd.setTexts(R.string.fml_quadratic_avg_pnd_txt, R.string.sym_avg_quadratic_pound, R.string.fml_default);
        fmlKurtosis.setTexts(R.string.txt_kurtosis, R.string.sym_kurtosis, R.string.fml_default);
        fmlAsymmetry.setTexts(R.string.txt_asymmetry, R.string.sym_asymmetry_quart, R.string.fml_default);
        fmlDeviation.setTexts(R.string.fml_deviation_txt, R.string.sym_standard_deviation, R.string.fml_default);
        fmlFstQuart.setTexts(R.string.fml_fst_quart_txt, R.string.sym_fst_quart, R.string.fml_default);
        fmlSndQuart.setTexts(R.string.fml_snd_quart_txt, R.string.sym_snd_quart, R.string.fml_default);
        fmlTrdQuart.setTexts(R.string.fml_trd_quart_txt, R.string.sym_trd_quart, R.string.fml_default);
        fmlFstTenth.setTexts(R.string.fml_fst_tenth_txt, R.string.sym_fst_tenth, R.string.fml_default);
        fmlNthTenth.setTexts(R.string.fml_nth_tenth_txt, R.string.sym_nth_tenth, R.string.fml_default);
        fmlIntervalAvg.setTexts(R.string.fml_confidence_interval_avg_txt, R.string.sym_interval_avg, R.string.fml_default);
        fmlIntervalProp.setTexts(R.string.fml_confidence_interval_prop_txt, R.string.sym_interval_prop, R.string.fml_default);
        fmlIntervalVar.setTexts(R.string.fml_confidence_interval_var_txt, R.string.sym_interval_var, R.string.fml_default);


        return view;
    }
}
