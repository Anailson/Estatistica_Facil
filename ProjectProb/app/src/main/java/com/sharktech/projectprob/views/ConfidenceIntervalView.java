package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.sharktech.projectprob.R;

public class ConfidenceIntervalView extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confidence_interval, container, false);
        Switch swtVarSample = view.findViewById(R.id.swt_var_sample);
        swtVarSample.setOnCheckedChangeListener((buttonView, isChecked) -> Toast.makeText(getContext(), "Check: " + isChecked, Toast.LENGTH_SHORT).show());

        return view;
    }
}
