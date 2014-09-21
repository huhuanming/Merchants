package com.merchants.main.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.merchants.main.Activity.MenuManagementNewFood;
import com.merchants.main.Activity.MerchantsMenu;
import com.merchants.main.Adapter.MenuMangementAdapter;
import com.merchants.main.ApiManager.MainApiManager;
import com.merchants.main.ApiManager.MerchantsApiManager;
import com.merchants.main.Model.MenuManagementData;
import com.merchants.main.Model.MenuManagementGroupDatabase;
import com.merchants.main.Model.MenuManagementKidsDatabase;
import com.merchants.main.Model.UploadBackData;
import com.merchants.main.R;
import com.merchants.main.Utils.JsonUtils;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.Utils.ToastUtils;
import com.merchants.main.Utils.TokenUtils.AccessToken;
import com.merchants.main.View.Dialog.AlertDialog;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenu;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenuCreator;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenuItem;
import com.merchants.main.View.Listview.SwipeMenuListView.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-8-30.
 */
public class MenuManagementFragment extends Fragment implements MerchantsMenu.MenuItemClick,SwipeRefreshLayout.OnRefreshListener {
    private RotateAnimation rAnimation; //设置旋转
    private View parentView;
    private List<MenuManagementGroupDatabase> group_list = new ArrayList<MenuManagementGroupDatabase>();;
    private List<List<MenuManagementKidsDatabase>> kids_list = new ArrayList<List<MenuManagementKidsDatabase>>();
    private MenuMangementAdapter adapter;
    private SwipeMenuListView listview;
    private View headview;
    private SwipeRefreshLayout swipeRefreshLayout;
//    private SmoothProgressBar progressbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.menu_management, container, false);
        getActivity().invalidateOptionsMenu();
        listview = (SwipeMenuListView)parentView.findViewById(R.id.menu_management_listView);
//        progressbar = (SmoothProgressBar)parentView.findViewById(R.id.login_progressbar);
        swipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_bright);
        initList();
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.menu_management_lastitem, null);
        TextView menu_management_lastitem_text = (TextView)headview.findViewById(R.id.menu_management_lastitem_text);
        menu_management_lastitem_text.setText(R.string.new_classification);
        final RelativeLayout menu_management_lastitem_layout = (RelativeLayout)headview.findViewById(R.id.menu_management_lastitem_layout);

        menu_management_lastitem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog(getActivity()).builder();
                alertDialog.setMsgOne("新建分类").setHintOne("输入分类名称")
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String MsgOne = alertDialog.getMsgOne();
                                if(!MsgOne.equals(""))
                                {
                                    MenuManagementGroupDatabase data = new MenuManagementGroupDatabase();
                                    data.type_name = MsgOne;
                                    data.save();
                                    group_list.add(data);

                                    ArrayList<MenuManagementKidsDatabase> list = new ArrayList<MenuManagementKidsDatabase>();
                                    kids_list.add(list);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }).show();
            }
        });

        //从数据库取出，并赋值list

        listview.addFooterView(headview,null,false);
        adapter = new MenuMangementAdapter(getActivity(),group_list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MenuManagementNewFood.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id",group_list.get(position).getId());
                intent.putExtras(bundle);
                startActivityForResult(intent, 201);

            }
        });

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
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
                new Delete().from(MenuManagementKidsDatabase.class).where("MenuManagementGroupDatabase = ?",group_list.get(position).getId()).execute();

                MenuManagementGroupDatabase database = MenuManagementGroupDatabase.load(MenuManagementGroupDatabase.class,group_list.get(position).getId());
                database.delete();
                group_list.remove(position);
                kids_list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        init();
        return parentView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    private void initList(){
        //从数据库里面取出来
        List<MenuManagementGroupDatabase> group =new Select().from(MenuManagementGroupDatabase.class).execute();
        List<List<MenuManagementKidsDatabase>> kids = new ArrayList<List<MenuManagementKidsDatabase>>();
        int num = group.size();
        for(int i = 0; i < num; i++)
        {
            List<MenuManagementKidsDatabase> kid = new Select().from(MenuManagementKidsDatabase.class).where("MenuManagementGroupDatabase=?",group.get(i).getId()).execute();
            kids.add(kid);
        }
        group_list = group;
        kids_list = kids;

    }

    private void getDataFromDatabase(){
//        group_list.clear();
        kids_list.clear();
        //从数据库里面取出来
//        List<MenuManagementGroupDatabase> group =new Select().from(MenuManagementGroupDatabase.class).execute();
        List<List<MenuManagementKidsDatabase>> kids = new ArrayList<List<MenuManagementKidsDatabase>>();
        int num = group_list.size();
        for(int i = 0; i < num; i++)
        {
            List<MenuManagementKidsDatabase> kid = new Select().from(MenuManagementKidsDatabase.class).where("MenuManagementGroupDatabase=?",group_list.get(i).getId()).execute();
            kids.add(kid);
        }
//        group_list = group;
        kids_list = kids;


    }


    private void init(){
//        progressbar.setVisibility(View.VISIBLE);
        getFoods(ShareUtils.getId(getActivity()),new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
//                progressbar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                ArrayList<MenuManagementData> menuManagementDatas = (ArrayList<MenuManagementData>)object;

                group_list.clear();

                kids_list.clear();

                //删除数据库里面的内容,注意删除顺序
                new Delete().from(MenuManagementKidsDatabase.class).execute();
                new Delete().from(MenuManagementGroupDatabase.class).execute();


                List<MenuManagementGroupDatabase> menuManagementGroupDatabases = new ArrayList<MenuManagementGroupDatabase>();
                List<MenuManagementKidsDatabase> menuManagementKidsDatabases = new ArrayList<MenuManagementKidsDatabase>();
                int num = menuManagementDatas.size();
                for(int i = 0; i < num; i++)
                {
                    MenuManagementGroupDatabase menuManagementGroupDatabase = new MenuManagementGroupDatabase();
                    menuManagementGroupDatabase.type_name = menuManagementDatas.get(i).type_name;
                    menuManagementGroupDatabases.add(menuManagementGroupDatabase);
                    group_list.add(menuManagementGroupDatabase);

                    ArrayList<MenuManagementKidsDatabase> menuManagementKidsDatas = new ArrayList<MenuManagementKidsDatabase>();
                    int food_num = menuManagementDatas.get(i).foods.size();
                    for(int j = 0; j < food_num; j++)
                    {

                        MenuManagementKidsDatabase menuManagementKidsDatabase = new MenuManagementKidsDatabase();
                        menuManagementKidsDatabase.food_name = menuManagementDatas.get(i).foods.get(j).food_name;
                        menuManagementKidsDatabase.shop_price = menuManagementDatas.get(i).foods.get(j).shop_price;
                        menuManagementKidsDatabase.menuManagementGroupDatabase = menuManagementGroupDatabase;
                        menuManagementKidsDatabases.add(menuManagementKidsDatabase);
                        menuManagementKidsDatas.add(menuManagementKidsDatabase);
                    }
                    kids_list.add(menuManagementKidsDatas);
                }

                //进行数据储存
                ActiveAndroid.beginTransaction();
                try {
                    int len = menuManagementGroupDatabases.size();
                    for (int i = 0; i < len; i++) {
                        menuManagementGroupDatabases.get(i).save();
                    }
                    len = menuManagementKidsDatabases.size();
                    for (int i = 0; i < len; i++) {
                        menuManagementKidsDatabases.get(i).save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
//                progressbar.setVisibility(View.GONE);
                ToastUtils.setToast(getActivity(),"发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
//                progressbar.setVisibility(View.GONE);
                ToastUtils.setToast(getActivity(),"网络错误");
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
//                progressbar.setVisibility(View.GONE);
                switch (code)
                {
                    case 401:
                        ToastUtils.setToast(getActivity(), "电话号码或者密码有误");
                        break;
                    case 404:
                        ToastUtils.setToast(getActivity(),"信息已经过期，请重新登录!");
                        break;
                    case 501:
                        ToastUtils.setToast(getActivity(),"上传出错，请再重新上传一次!");
                        break;
                    case 502:
                        ToastUtils.setToast(getActivity(),"上传出错，请再重新上传一次!");
                        break;
                    default:

                        ToastUtils.setToast(getActivity(),"网络错误，请再重新上传一次!");
                        break;
                }
            }
        });
    }

    private void Upload(String token,String jsonString, final MainApiManager.FialedInterface fialedInterface)
    {

        MerchantsApiManager.getMenuUploadBackData(token, jsonString).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UploadBackData>() {
                    @Override
                    public void call(UploadBackData uploadBackData) {
                        fialedInterface.onSuccess(uploadBackData);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable.getClass().getName().toString().indexOf("RetrofitError") != -1) {
                            retrofit.RetrofitError e = (retrofit.RetrofitError) throwable;
                            if(e.isNetworkError())
                            {
//                                if (e.getCause() instanceof SocketTimeoutException) {
//                                    fialedInterface.onFailth(e.getResponse().getStatus());
//                                } else {
//                                    fialedInterface.onNetworkError();
//                                }
                                fialedInterface.onNetworkError();
                            }
                            else {
                                fialedInterface.onFailth(e.getResponse().getStatus());
                            }
                        }
                        else{
                            fialedInterface.onOtherFaith();
                        }
                    }
                });
    }

    private void getFoods(String id, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.getMenuFoods(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MenuManagementData>>() {
                    @Override
                    public void call(List<MenuManagementData> menuUploadBackDatas) {
                        fialedInterface.onSuccess(menuUploadBackDatas);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        if(throwable.getClass().getName().toString().indexOf("RetrofitError") != -1) {
                            retrofit.RetrofitError e = (retrofit.RetrofitError) throwable;
                            if(e.isNetworkError())
                            {
//                                if (e.getCause() instanceof SocketTimeoutException) {
//                                    fialedInterface.onFailth(e.getResponse().getStatus());
//                                } else {
//                                    fialedInterface.onNetworkError();
//                                }
                                fialedInterface.onNetworkError();

                            }
                            else {
                                fialedInterface.onFailth(e.getResponse().getStatus());
                            }
                        }
                        else{
                            fialedInterface.onOtherFaith();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        MerchantsMenu.menuItemClick = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        MerchantsMenu.menuItemClick = null;
    }

    @Override
    public void saveMenu() {
        swipeRefreshLayout.setRefreshing(true);
//        progressbar.setVisibility(View.VISIBLE);
        getDataFromDatabase();
        ToastUtils.setToast(getActivity(), JsonUtils.getFoodJson(group_list, kids_list));
        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        Upload(accessToken.accessToken(), JsonUtils.getFoodJson(group_list,kids_list),new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);
//                progressbar.setVisibility(View.GONE);
                ToastUtils.setToast(getActivity(), "保存成功!");
            }
            @Override
            public void onOtherFaith() {
//                progressbar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(),"发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
//                progressbar.setVisibility(View.GONE);
                ToastUtils.setToast(getActivity(),"网络错误");
            }
            @Override
            public void onFailth(int code) {
//                progressbar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                switch (code)
                {
                    case 401:
                        ToastUtils.setToast(getActivity(), "电话号码或者密码有误");
                        break;
                    case 404:
                        ToastUtils.setToast(getActivity(),"信息已经过期，请重新登录!");
                        break;

                    case 501:
                        ToastUtils.setToast(getActivity(),"上传出错，请再重新上传一次!");
                        break;
                    case 502:
                        ToastUtils.setToast(getActivity(),"上传出错，请再重新上传一次!");
                        break;
                    default:
                        ToastUtils.setToast(getActivity(),"网络错误，请再重新上传一次!");
                        break;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        init();
    }
}
