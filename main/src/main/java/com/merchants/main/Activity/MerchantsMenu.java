package com.merchants.main.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.merchants.main.R;


public class MerchantsMenu extends FragmentActivity{

    final String[] menuEntries = {"店铺管理","数据报表","订单管理","菜单管理","关于我们"};
    final String[] fragments = {
            "com.merchants.main.Activity.StoreManagementFragment",
            "com.merchants.main.Activity.DataReportFragment",
            "com.merchants.main.Activity.OrderManagementFragment",
            "com.merchants.main.Activity.MenuManagementFragment",
            "com.merchants.main.Activity.AboutUsFragment"
    };

    private ActionBarDrawerToggle drawerToggle;
    private View headview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchants_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuEntries);

        final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        final ListView navList = (ListView) findViewById(R.id.drawer);
        headview = LayoutInflater.from(this).inflate(R.layout.menu_listview_head, null);
        navList.addHeaderView(headview,null,false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawer.setDrawerListener(drawerToggle);

        navList.setAdapter(adapter);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
                drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
                    @Override
                    public void onDrawerClosed(View drawerView){
                        super.onDrawerClosed(drawerView);
                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.main, Fragment.instantiate(MerchantsMenu.this, fragments[pos-1]));
                        getActionBar().setTitle(menuEntries[pos-1]);
                        tx.commit();
                    }
                });
                drawer.closeDrawer(navList);
            }
        });
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main,Fragment.instantiate(MerchantsMenu.this, fragments[0]));
        tx.commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
