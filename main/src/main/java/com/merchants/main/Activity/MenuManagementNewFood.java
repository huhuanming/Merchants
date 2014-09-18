package com.merchants.main.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.merchants.main.Adapter.MenuManagementNewFoodAdapter;
import com.merchants.main.Model.MenuManagementGroupDatabase;
import com.merchants.main.Model.MenuManagementKidsDatabase;
import com.merchants.main.R;
import com.merchants.main.View.Dialog.AlertDialog;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenu;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenuCreator;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenuItem;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenuListView;

import java.util.List;

/**
 * Created by chen on 14-9-10.
 */
public class MenuManagementNewFood extends Activity{

    private List<MenuManagementKidsDatabase> kids_list ;
    private MenuManagementGroupDatabase menuManagementGroupDatabase;
    private MenuManagementNewFoodAdapter adapter;
    private View headview;
    private long id = 0;
    SwipeMenuListView listview;
    private int position;

//    @InjectView(R.id.menu_management_newfood_listView)CustomListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_management_new_food);

        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        id = getIntent().getExtras().getLong("id");
        initList();

        listview = (SwipeMenuListView)findViewById(R.id.menu_management_newfood_listView);
        headview = LayoutInflater.from(this).inflate(R.layout.menu_management_lastitem, null);
        TextView menu_management_lastitem_text = (TextView)headview.findViewById(R.id.menu_management_lastitem_text);
        menu_management_lastitem_text.setText(R.string.new_food);
        RelativeLayout menu_management_lastitem_layout = (RelativeLayout)headview.findViewById(R.id.menu_management_lastitem_layout);
        menu_management_lastitem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog(MenuManagementNewFood.this).builder();
                alertDialog.setMsgOne("新建菜品").setHintOne("输入菜品名称")
                        .setMsgTwo("单价").setHintTwo("单价(元)")
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String MsgOne = alertDialog.getMsgOne();
                                String MsgProice = alertDialog.getMsgTwo();
                                if(!MsgOne.equals("") && !MsgProice.equals(""))
                                {
                                    MenuManagementKidsDatabase data = new MenuManagementKidsDatabase();
                                    data.food_name = MsgOne;
                                    data.shop_price = MsgProice;
                                    data.menuManagementGroupDatabase = menuManagementGroupDatabase;
                                    saveData(data);
                                    kids_list.add(data);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }).show();
            }
        });

        listview.addFooterView(headview,null,false);
        adapter = new MenuManagementNewFoodAdapter(this,kids_list);
        listview.setAdapter(adapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listview.setMenuCreator(creator);

        // step 2. listener item click event
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                deleteData(kids_list.get(position).getId());

                kids_list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void initList(){
            List<MenuManagementKidsDatabase> kid = new Select().from(MenuManagementKidsDatabase.class).where("MenuManagementGroupDatabase = ?",id).execute();
            menuManagementGroupDatabase = new Select().from(MenuManagementGroupDatabase.class).where("Id = ?",id).executeSingle();
            kids_list = kid;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId())
        {
            //监听返回键
            case android.R.id.home:

                MenuManagementNewFood.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteData(long num){
        MenuManagementKidsDatabase database = MenuManagementKidsDatabase.load(MenuManagementKidsDatabase.class,num);
        database.delete();
    }

    private void saveData(MenuManagementKidsDatabase data){
        data.save();
    }
}
