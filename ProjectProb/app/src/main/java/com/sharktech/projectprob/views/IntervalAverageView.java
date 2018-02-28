package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.adapters.SpinAdapter;
import com.sharktech.projectprob.controllers.IntervalAverageController;
import com.sharktech.projectprob.customview.ItemConfidenceInterval;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;

public class IntervalAverageView extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_interval_average, container, false);
        IntervalAverageController controller = new IntervalAverageController(this);

        ((ItemConfidenceInterval) view.findViewById(R.id.ci_sample_avg)).setTitle(R.string.txt_sample_avg);
        ((ItemConfidenceInterval) view.findViewById(R.id.ci_sample_size)).setTitle(R.string.txt_sample_size);
        ((ItemConfidenceInterval) view.findViewById(R.id.ci_population_size)).setTitle(R.string.txt_population_size);
        ((ItemConfidenceInterval) view.findViewById(R.id.ci_population_deviation)).setTitle(R.string.txt_population_deviation);
        ((ItemConfidenceInterval) view.findViewById(R.id.ci_confidence)).setTitle(R.string.txt_confidence_level);
        Switch swtVarSample = view.findViewById(R.id.swt_var_sample);
        Spinner spnVarSample = view.findViewById(R.id.spn_var_sample);
        Button btnCalculate = view.findViewById(R.id.btn_calculate_ic_avg);

        SpinAdapter adapter = new SpinAdapter(getContext());
        ArrayList<String> titles = VariablePersistence.getInstance().getTitles();
        titles.add(0, getString(R.string.txt_default));

        spnVarSample.setAdapter(adapter.getAdapter(titles));
        swtVarSample.setOnCheckedChangeListener(controller.getListeners());
        spnVarSample.setOnItemSelectedListener(controller.getListeners());
        btnCalculate.setOnClickListener(controller.getListeners());

        return view;
    }
}
