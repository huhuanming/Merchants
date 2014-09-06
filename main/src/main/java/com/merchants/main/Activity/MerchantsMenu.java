package com.merchants.main.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.merchants.main.R;
import com.merchants.main.ResideMenu.ResideMenu;
import com.merchants.main.ResideMenu.ResideMenuItem;


public class MerchantsMenu extends FragmentActivity implements View.OnClickListener{

    public ResideMenu resideMenu;
    private ResideMenuItem store_management;
    private ResideMenuItem data_report;
    private ResideMenuItem order_management;
    private ResideMenuItem menu_management;
    private ResideMenuItem about_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchants_main);
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);

        store_management = new ResideMenuItem(this, R.color.transparent,R.string.store_management );
        data_report = new ResideMenuItem(this, R.color.transparent,R.string.data_report );
        order_management = new ResideMenuItem(this, R.color.transparent,R.string.order_management );
        menu_management = new ResideMenuItem(this, R.color.transparent,R.string.menu_management );
        about_us = new ResideMenuItem(this, R.color.transparent,R.string.about_us );

        resideMenu.addMenuItem(store_management);
        resideMenu.addMenuItem(data_report);
        resideMenu.addMenuItem(order_management);
        resideMenu.addMenuItem(menu_management);
        resideMenu.addMenuItem(about_us);

        store_management.setOnClickListener(this);
        data_report.setOnClickListener(this);
        order_management.setOnClickListener(this);
        menu_management.setOnClickListener(this);
        about_us.setOnClickListener(this);

        changeFragment(new StoreManagementFragment(resideMenu));
    }
    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_framelayout, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.onInterceptTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }



    @Override
    public void onClick(View view) {
        if(view == store_management)
            changeFragment(new StoreManagementFragment(resideMenu));
        else if(view == data_report)
            changeFragment(new DataReportFragment());
        else if(view == order_management)
            changeFragment(new OrderManagementFragment());
        else if(view == menu_management)
            changeFragment(new MenuManagementFragment(resideMenu));
        else if(view == about_us)
            changeFragment(new AboutUsFragment());
    }
}
