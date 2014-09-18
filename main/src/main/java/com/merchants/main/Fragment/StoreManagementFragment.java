package com.merchants.main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.merchants.main.Activity.MerchantsLogin;
import com.merchants.main.R;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.View.Dialog.TimePickerDialog.RadialPickerLayout;
import com.merchants.main.View.Dialog.TimePickerDialog.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by chen on 14-8-30.
 */
public class StoreManagementFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

    private View parentView;
    private RelativeLayout store_management_business_time;
    private RelativeLayout store_login_exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.store_management, container, false);
        getActivity().invalidateOptionsMenu();

        store_management_business_time = (RelativeLayout)parentView.findViewById(R.id.store_management_business_time);
        store_login_exit = (RelativeLayout)parentView.findViewById(R.id.store_login_exit);
        final Calendar calendar = Calendar.getInstance();
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

        store_management_business_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                timePickerDialog.setVibrate(isVibrate());
//                timePickerDialog.setCloseOnSingleTapMinute(isCloseOnSingleTapMinute());
                timePickerDialog.show(getFragmentManager(),"");
            }
        });
        store_login_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.deleteTokenKey(getActivity());
                Intent intent = new Intent();
                intent.setClass(getActivity(),MerchantsLogin.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return parentView;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }


//    @Override
//    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
//
//    }
}
