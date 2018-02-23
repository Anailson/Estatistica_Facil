package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.widget.TabHost;

public class InferenceController {

    private Fragment mFragment;

    public InferenceController(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public void addTabSpec(TabHost tabHost, String tag, int id){
        TabHost.TabSpec spec = tabHost.newTabSpec(tag);
        spec.setIndicator(tag);
        spec.setContent(id);
        tabHost.addTab(spec);
    }
}
