package com.merchants.main.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merchants.main.R;
import com.merchants.main.View.Progress.ProgressWheel;

/**
 * Created by chen on 14-8-30.
 */
public class DataReportFragment extends Fragment {
    private View parentView;
    private ProgressWheel progressWheel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.data_report, container, false);
        getActivity().invalidateOptionsMenu();

        progressWheel = (ProgressWheel)parentView.findViewById(R.id.data_report_progress);
        progressWheel.setProgress(270);

        return parentView;
    }
}
