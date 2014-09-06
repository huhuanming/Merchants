package com.merchants.main.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merchants.main.R;
import com.merchants.main.ResideMenu.ResideMenu;

/**
 * Created by chen on 14-8-30.
 */
public class StoreManagementFragment extends Fragment{

    private View parentView;
    private ResideMenu resideMenu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.store_management, container, false);

        parentView.findViewById(R.id.store_management_menu_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu();
            }
        });
        return parentView;
    }

    public StoreManagementFragment(ResideMenu resideMenu)
    {
        this.resideMenu = resideMenu;
    }
    public StoreManagementFragment(){
        super();
    }

}
