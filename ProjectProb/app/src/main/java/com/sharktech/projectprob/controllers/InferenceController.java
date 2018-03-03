package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.TabHost;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.views.IntervalAverageView;
import com.sharktech.projectprob.views.IntervalProportionView;
import com.sharktech.projectprob.views.IntervalVarianceView;

public class InferenceController {

    private int mCurrentLayout, mCurrentMenu;
    private Fragment mFragment;
    private Listener mListener;

    public InferenceController(Fragment fragment) {
        mFragment = fragment;
        mListener = new Listener();
        mCurrentLayout = R.id.tab_interval;
        mCurrentMenu = R.id.menu_average;
    }

    public Listener getListeners() {
        return mListener;
    }

    public void setTabSpec(TabHost tabHost) {
        tabHost.setup();
        addTabSpec(tabHost, mFragment.getString(R.string.txt_tab_interval), R.id.tab_interval);
        addTabSpec(tabHost, mFragment.getString(R.string.txt_tab_hypothesis), R.id.tab_hypothesis);

        setLayoutContent(mCurrentLayout, mCurrentMenu);
    }

    private void addTabSpec(TabHost tabHost, String tag, int id) {
        tabHost.addTab(tabHost.newTabSpec(tag)
                .setIndicator(tag)
                .setContent(id)
        );
    }

    private void setLayoutContent(int layoutId, int menuId) {

        mCurrentLayout = layoutId;
        mCurrentMenu = menuId;

        Fragment fragment = null;

        if (menuId == R.id.menu_average) {
            fragment = new IntervalAverageView();
        } else if (menuId == R.id.menu_proportion) {
            fragment = new IntervalProportionView();
        } else if (menuId == R.id.menu_variance) {
            fragment = new IntervalVarianceView();
        }

        FragmentManager manager = mFragment.getFragmentManager();
        if (manager != null)
            manager.beginTransaction().replace(layoutId, fragment).commit();
    }

    private class Listener implements TabHost.OnTabChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

        //TabHost.OnTabChangeListener
        @Override
        public void onTabChanged(String tag) {

            int layout = tag.equals(mFragment.getString(R.string.txt_tab_interval))
                    ? R.id.tab_interval
                    : R.id.tab_hypothesis;
            Activity activity = mFragment.getActivity();
            int menu = activity == null ? mCurrentMenu
                    : ((BottomNavigationView) activity.findViewById(R.id.btn_inference)).getSelectedItemId();

            setLayoutContent(layout, menu);
        }

        //BottomNavigationView.OnNavigationItemSelectedListener
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            setLayoutContent(mCurrentLayout, item.getItemId());
            return true;
        }
    }
}
