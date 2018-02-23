package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.InferenceController;

public class InferenceView extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentActivity activity = getActivity();
        if (activity != null) activity.setTitle(getString(R.string.txt_inference));

        InferenceController controller = new InferenceController(this);

        View view = inflater.inflate(R.layout.fragment_inference, container, false);
        TabHost tabHost = view.findViewById(R.id.tab_inference);
        BottomNavigationView bottomView = view.findViewById(R.id.btn_inference);

        controller.setTabSpec(tabHost);
        tabHost.setOnTabChangedListener(controller.getListeners());
        bottomView.setOnNavigationItemSelectedListener(controller.getListeners());

        return view;
    }
}
