package com.merchants.main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.merchants.main.Activity.OrderManagementDetails;
import com.merchants.main.Adapter.OrderManagementAdapter;
import com.merchants.main.ApiManager.MainApiManager;
import com.merchants.main.ApiManager.MerchantsApiManager;
import com.merchants.main.Model.OrderManagementData;
import com.merchants.main.R;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.Utils.ToastUtils;
import com.merchants.main.Utils.TokenUtils.AccessToken;

import java.util.ArrayList;
import java.util.List;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-8-30.
 */
public class OrderManagementFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View parentView;
    private ListView listview;
    private List<OrderManagementData> list = new ArrayList<OrderManagementData>();
    private OrderManagementAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isrefresh = false;
    private String order_id;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.order_management, container, false);
        getActivity().invalidateOptionsMenu();
        listview = (ListView)parentView.findViewById(R.id.order_management_listView);
        swipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_bright);
        adapter = new OrderManagementAdapter(getActivity(),list);
        listview.setAdapter(adapter);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //判断是否滚动到底部
                if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
                    order_id = list.get(list.size()-1).order_id+"";
                    getData();
                }
            }

            @Override
            public void onScroll(AbsListView absListView,  int firstVisibleItem, int ItemCount, int totalItemCount) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), OrderManagementDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("order_id",list.get(position).order_id);
                bundle.putString("shipping_user",list.get(position).shipping_user);
                bundle.putString("phone_number",list.get(position).phone_number);
                bundle.putString("shipping_address",list.get(position).shipping_address);
                bundle.putString("updated_at",list.get(position).updated_at);
                bundle.putString("shipping_at",list.get(position).shipping_at);
                bundle.putString("order_remark",list.get(position).order_remark);
                bundle.putString("is_ticket",list.get(position).is_ticket);
                bundle.putString("is_receipt",list.get(position).is_receipt);
                bundle.putString("is_now",list.get(position).is_now);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        order_id = null;
        getData();
        return parentView;
    }



    private void getData()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        getOrderData(ShareUtils.getId(getActivity()),accessToken.accessToken(),order_id,new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                if(isrefresh)
                {

                    isrefresh = false;
                    list.clear();
                }
                swipeRefreshLayout.setRefreshing(false);
                List<OrderManagementData> data_list = (List<OrderManagementData>)object;
                list.addAll(data_list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailth(int code) {
                isrefresh = false;
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

            @Override
            public void onOtherFaith() {
                isrefresh = false;
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(),"发生错误");
            }

            @Override
            public void onNetworkError() {
                isrefresh = false;
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(),"网络错误");
            }
        });
    }

    private void getOrderData(String id,String access_token,String page, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.getOrderDatas(id,access_token,page).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<OrderManagementData>>() {
                    @Override
                    public void call(List<OrderManagementData> orderManagementDatas) {
                        fialedInterface.onSuccess(orderManagementDatas);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        if(throwable != null && throwable.getClass().getName().toString().indexOf("RetrofitError") != -1) {
                            retrofit.RetrofitError e = (retrofit.RetrofitError) throwable;
                            if(e.isNetworkError())
                            {
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
    public void onRefresh() {
        isrefresh = true;
        order_id = null;
        getData();
    }
}
