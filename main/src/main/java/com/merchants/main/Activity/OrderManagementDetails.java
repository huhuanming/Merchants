package com.merchants.main.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.merchants.main.Adapter.OrderManagementDetailAdapter;
import com.merchants.main.ApiManager.MainApiManager;
import com.merchants.main.ApiManager.MerchantsApiManager;
import com.merchants.main.Model.OrderManagementKidData;
import com.merchants.main.Model.UploadBackData;
import com.merchants.main.R;
import com.merchants.main.Utils.Constant;
import com.merchants.main.Utils.DateUtils;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.Utils.ToastUtils;
import com.merchants.main.Utils.TokenUtils.AccessToken;
import com.merchants.main.View.Listview.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-9-16.
 */
public class OrderManagementDetails extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    private String order_id;
    private String shipping_user;
    private String phone_number;
    private String shipping_address;
    private String updated_at;
    private String shipping_at;
    private String order_remark;
    public String is_ticket;
    public String is_receipt;
    public String is_now;

    private Handler handler;
    private OrderManagementDetailAdapter adapter;
    private List<OrderManagementKidData> dataList = new ArrayList<OrderManagementKidData>();

    @InjectView(R.id.swipe_container)SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.order_management_item_details_name)TextView order_management_item_details_name;
    @InjectView(R.id.order_management_item_details_number)TextView order_management_item_details_number;
    @InjectView(R.id.order_management_item_details_address)TextView order_management_item_details_address;
    @InjectView(R.id.order_management_item_details_gettime)TextView order_management_item_details_gettime;
    @InjectView(R.id.order_management_item_details_grid)MyGridView order_management_item_details_grid;
    @InjectView(R.id.order_note_detail)TextView order_note_detail;
    @InjectView(R.id.order_management_item_details_timetosend)TextView order_management_item_details_timetosend;
    @InjectView(R.id.order_management_item_details_provide_the_invoice)TextView order_management_item_details_provide_the_invoice;
    @InjectView(R.id.order_management_item_details_commercial_invoice )TextView order_management_item_details_commercial_invoice;

    @InjectView(R.id.order_management_details_name)TextView order_management_details_name;
    @InjectView(R.id.order_management_details_time)TextView order_management_details_time;
    @InjectView(R.id.order_management_details_money)TextView order_management_details_money;
    @InjectView(R.id.order_management_details_totalmoney)TextView order_management_details_totalmoney;

    @OnClick(R.id.order_management_item_details_finish)void order_management_item_details_finish(){
        swipeRefreshLayout.setRefreshing(true);
        putDetailData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_management_details);
        ButterKnife.inject(this);

        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_bright);
        initData();
        getDetailData();


        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub

                switch(msg.what)
                {
                    //获取成功
                    case Constant.MSG_SUCCESS:
                       initMoney();
                       adapter.notifyDataSetChanged();
                       break;
                }
                super.handleMessage(msg);
            }

        };
    }

    private void initData(){
        Bundle bundle = getIntent().getExtras();
        order_id = bundle.getString("order_id");
        shipping_user = bundle.getString("shipping_user");
        phone_number = bundle.getString("phone_number");
        shipping_address = bundle.getString("shipping_address");
        updated_at = bundle.getString("updated_at");
        shipping_at = bundle.getString("shipping_at");
        order_remark = bundle.getString("order_remark");
        is_ticket = bundle.getString("is_ticket");
        is_receipt = bundle.getString("is_receipt");
        is_now = bundle.getString("is_now");

        if(is_now.equals("0"))
        {
            String string = "及时送";
            SpannableString sp = new SpannableString(string);
            sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            order_management_item_details_timetosend.setText(sp);
            order_management_item_details_timetosend.setTextColor(getResources().getColor(R.color.delete_text));
            order_management_item_details_gettime.setText("预约"+ DateUtils.getTime(shipping_at)+"送达");
        }
        else
        {
            String string = "预约送";
            SpannableString sp = new SpannableString(string);
            sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            order_management_item_details_gettime.setText(sp);
            order_management_item_details_gettime.setTextColor(getResources().getColor(R.color.delete_text));
        }

        if(is_receipt.equals("0"))
        {
            String string = "需要提供商用小票";
            SpannableString sp = new SpannableString(string);
            sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            order_management_item_details_commercial_invoice.setText(sp);
            order_management_item_details_commercial_invoice.setTextColor(getResources().getColor(R.color.delete_text));

        }

        if(is_ticket.equals("0"))
        {
            String string = "需要提供收银小票";
            SpannableString sp = new SpannableString(string);
            sp.setSpan(new StrikethroughSpan(), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            order_management_item_details_provide_the_invoice.setText(sp);
            order_management_item_details_provide_the_invoice.setTextColor(getResources().getColor(R.color.delete_text));
        }

        order_management_item_details_name.setText("收货人: "+shipping_user);
        order_management_item_details_number.setText(phone_number);
        order_management_item_details_address.setText("收货地址: "+shipping_address);

        order_note_detail.setText(order_remark);

        order_management_details_name.setText(shipping_user+"的订单");
        order_management_details_time.setText(DateUtils.getMonthTime(updated_at));

        adapter = new OrderManagementDetailAdapter(OrderManagementDetails.this,dataList);
        order_management_item_details_grid.setAdapter(adapter);

    }

    private void initMoney(){
        if(dataList.size() > 0)
        {
            double num = Double.parseDouble(dataList.get(0).actual_total_price) - Double.parseDouble(dataList.get(0).total_price);
            order_management_details_money.setText("订单金额: ¥"+dataList.get(0).total_price+"   运费: ¥"+num);
            order_management_details_totalmoney.setText("订单总额: ¥"+dataList.get(0).actual_total_price);
        }
    }

    private void getDetailData(){
        AccessToken accessToken = new AccessToken(ShareUtils.getToken(OrderManagementDetails.this),ShareUtils.getKey(OrderManagementDetails.this));
        getOrderDetailData(ShareUtils.getId(OrderManagementDetails.this),order_id,accessToken.accessToken(),new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                dataList.clear();
                swipeRefreshLayout.setRefreshing(false);
                List<OrderManagementKidData> list = (List<OrderManagementKidData>)object;
                dataList.addAll(list);
                handler.obtainMessage(Constant.MSG_SUCCESS)
                        .sendToTarget();
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                switch (code)
                {
                    case 401:
                        ToastUtils.setToast(OrderManagementDetails.this, "电话号码或者密码有误");
                        break;
                    case 404:
                        ToastUtils.setToast(OrderManagementDetails.this,"信息已经过期，请重新登录!");
                        break;
                    case 501:
                        ToastUtils.setToast(OrderManagementDetails.this,"上传出错，请再重新上传一次!");
                        break;
                    case 502:
                        ToastUtils.setToast(OrderManagementDetails.this,"上传出错，请再重新上传一次!");
                        break;
                    default:
                        ToastUtils.setToast(OrderManagementDetails.this,"网络错误，请再重新上传一次!");
                        break;
                }
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(OrderManagementDetails.this,"发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(OrderManagementDetails.this,"网络错误");
            }
        });
    }
    private void getOrderDetailData(String restaurant_id,String order_id,String access_token ,final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.getOrderDetailDatas(restaurant_id, order_id, access_token).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<OrderManagementKidData>>() {
                    @Override
                    public void call(List<OrderManagementKidData> orderManagementKidDatas) {
                        fialedInterface.onSuccess(orderManagementKidDatas);
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

    private void putOrderDetailData(String restaurant_id,String order_id,String access_token ,final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.putOrderDetailDatas(restaurant_id, order_id, access_token).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UploadBackData>() {
                    @Override
                    public void call(UploadBackData uploadBackData) {

                        fialedInterface.onSuccess(uploadBackData);
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

    private void putDetailData(){
        AccessToken accessToken = new AccessToken(ShareUtils.getToken(OrderManagementDetails.this),ShareUtils.getKey(OrderManagementDetails.this));
        putOrderDetailData(ShareUtils.getId(OrderManagementDetails.this),order_id,accessToken.accessToken(),new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(OrderManagementDetails.this,"成功确认");
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                switch (code)
                {
                    case 401:
                        ToastUtils.setToast(OrderManagementDetails.this, "电话号码或者密码有误");
                        break;
                    case 404:
                        ToastUtils.setToast(OrderManagementDetails.this,"没有找到相应的订单");
                        break;
                    case 501:
                        ToastUtils.setToast(OrderManagementDetails.this,"上传出错，请再重新上传一次!");
                        break;
                    case 502:
                        ToastUtils.setToast(OrderManagementDetails.this,"上传出错，请再重新上传一次!");
                        break;
                    default:
                        ToastUtils.setToast(OrderManagementDetails.this,"网络错误，请再重新上传一次!");
                        break;
                }
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(OrderManagementDetails.this,"发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(OrderManagementDetails.this,"网络错误");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId())
        {
            //监听返回键
            case android.R.id.home:

                OrderManagementDetails.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRefresh() {
        getDetailData();
    }
}
