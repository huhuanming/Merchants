package com.merchants.main.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merchants.main.R;

/**
 * Created by chen on 14-8-30.
 */
public class StoreManagementFragment extends Fragment{

    private View parentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.store_management, container, false);
        return parentView;
    }

}
