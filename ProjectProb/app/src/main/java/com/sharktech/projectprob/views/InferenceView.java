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
import com.sharktech.projectprob.controllers.InferenceController;

public class InferenceView extends Fragment {

    private InferenceController mController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentActivity activity = getActivity();
        if(activity != null) activity.setTitle(getString(R.string.txt_inference));

        View view = inflater.inflate(R.layout.fragment_inference, container, false);
        mController = new InferenceController(this);
        return view;
    }
}
