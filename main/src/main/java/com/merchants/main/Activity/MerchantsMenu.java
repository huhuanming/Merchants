package com.merchants.main.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.merchants.main.Adapter.MerchantsMainAdapter;
import com.merchants.main.R;

import java.util.ArrayList;


public class MerchantsMenu extends FragmentActivity{

    private MenuItem item;
//    final String[] menuEntries = {"店铺管理","数据报表","订单管理","菜单管理","关于我们"};
    private ArrayList<String> menuEntries = new ArrayList<String>();
    final String[] fragments = {
            "com.merchants.main.Fragment.StoreManagementFragment",
            "com.merchants.main.Fragment.DataReportFragment",
            "com.merchants.main.Fragment.OrderManagementFragment",
            "com.merchants.main.Fragment.MenuManagementFragment",
            "com.merchants.main.Fragment.AboutUsFragment"
    };
    public static abstract interface MenuItemClick{
        public abstract void saveMenu();
    }

    private ActionBarDrawerToggle drawerToggle;
    private View headview;
    private int fragmentPositon = 1;
    private MerchantsMainAdapter adapter;
    public static MenuItemClick  menuItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchants_main);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuEntries);

        menuEntries.add("店铺管理");
        menuEntries.add("数据报表");
        menuEntries.add("订单管理");
        menuEntries.add("菜单管理");
        menuEntries.add("关于我们");
        final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        final ListView navList = (ListView) findViewById(R.id.drawer);
        adapter = new MerchantsMainAdapter(getActionBar().getThemedContext(),menuEntries,fragmentPositon);


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
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
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
                        fragmentPositon = pos;
                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        Fragment fragment = Fragment.instantiate(MerchantsMenu.this, fragments[pos - 1]);
                        tx.replace(R.id.main, fragment);
                        getActionBar().setTitle(menuEntries.get(pos-1));

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
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
        this.item = item;
        switch (item.getItemId()){
            case R.id.menu_save:{
                menuItemClick.saveMenu();
            }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menumanagement_right, menu);
        MenuItem saveMenuItem = menu.getItem(0);
        saveMenuItem.setVisible(false);
        switch (fragmentPositon){
            case 1:{
                saveMenuItem.setVisible(false);
                adapter.notifyDataSetChanged();
            }
            break;
            case 2:{
                saveMenuItem.setVisible(false);
                adapter.notifyDataSetChanged();
            }
            break;
            case 3:{
                saveMenuItem.setVisible(false);
                adapter.notifyDataSetChanged();
            }
            break;
            case 4:{
                saveMenuItem.setVisible(true);
                adapter.notifyDataSetChanged();
            }
            break;
            case 5:{
                saveMenuItem.setVisible(false);
                adapter.notifyDataSetChanged();
            }
            break;
        }
        return true;
    }
}
