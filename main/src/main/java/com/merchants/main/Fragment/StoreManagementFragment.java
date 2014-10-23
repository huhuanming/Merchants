package com.merchants.main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.merchants.main.Activity.MerchantsLogin;
import com.merchants.main.Activity.StoreManagementNumber;
import com.merchants.main.Activity.StoreManagementText;
import com.merchants.main.ApiManager.MainApiManager;
import com.merchants.main.ApiManager.MerchantsApiManager;
import com.merchants.main.Model.StoreSettingData;
import com.merchants.main.Model.UploadBackData;
import com.merchants.main.R;
import com.merchants.main.Utils.Constant;
import com.merchants.main.Utils.DateUtils;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.Utils.ToastUtils;
import com.merchants.main.Utils.TokenUtils.AccessToken;
import com.merchants.main.View.Dialog.TimePickerDialog.RadialPickerLayout;
import com.merchants.main.View.Dialog.TimePickerDialog.TimePickerDialog;
import com.merchants.main.View.SwitchButton.UISwitchButton;

import java.util.Calendar;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-8-30.
 */
public class StoreManagementFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,SwipeRefreshLayout.OnRefreshListener{

    private View parentView;
    private RelativeLayout store_management_business_time;
    private RelativeLayout store_login_exit;
    private TextView business_time_text;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler;
    private StoreSettingData storeSettingData;

    private UISwitchButton shop_business_switchbutton;
    private RelativeLayout shop_notices_layout;
    private RelativeLayout send_price_layout;
    private TextView send_price_text;
    private RelativeLayout send_food_price_layout;
    private TextView send_food_price_text;
    private RelativeLayout send_time_layout;
    private TextView send_time_text;
    private UISwitchButton message_business_switchbuton;
    private TextView store_phone_number;
    private UISwitchButton client_business_switchbutton;
    private RelativeLayout store_phone_number_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.store_management, container, false);
        getActivity().invalidateOptionsMenu();


        swipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(true);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_light,
                android.R.color.white, android.R.color.holo_blue_bright);


        store_management_business_time = (RelativeLayout)parentView.findViewById(R.id.store_management_business_time);
        store_login_exit = (RelativeLayout)parentView.findViewById(R.id.store_login_exit);
        business_time_text = (TextView)parentView.findViewById(R.id.business_time_text);
        shop_business_switchbutton = (UISwitchButton)parentView.findViewById(R.id.shop_business_switchbutton);
        shop_notices_layout = (RelativeLayout)parentView.findViewById(R.id.shop_notices_layout);
        send_price_layout = (RelativeLayout)parentView.findViewById(R.id.send_price_layout);
        send_price_text = (TextView)parentView.findViewById(R.id.send_price_text);
        send_food_price_layout = (RelativeLayout)parentView.findViewById(R.id.send_food_price_layout);
        send_food_price_text = (TextView)parentView.findViewById(R.id.send_food_price_text);
        send_time_layout = (RelativeLayout)parentView.findViewById(R.id.send_time_layout);
        send_time_text = (TextView)parentView.findViewById(R.id.send_time_text);
        message_business_switchbuton = (UISwitchButton)parentView.findViewById(R.id.message_business_switchbuton);
        store_phone_number = (TextView)parentView.findViewById(R.id.store_phone_number);
        client_business_switchbutton = (UISwitchButton)parentView.findViewById(R.id.client_business_switchbutton);
        store_phone_number_layout = (RelativeLayout)parentView.findViewById(R.id.store_phone_number_layout);

        final Calendar calendar = Calendar.getInstance();
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);



        store_management_business_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        getSetting();

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub

                switch(msg.what)
                {
                    //获取成功
                    case Constant.MSG_SUCCESS:
                        storeSettingData = (StoreSettingData)msg.obj;
                        init(storeSettingData);
                        break;
                }
                super.handleMessage(msg);
            }

        };
        return parentView;
    }

    private void init(final StoreSettingData storeSettingData){

        if(DateUtils.getLocalDistDatas(storeSettingData.checked_at,DateUtils.getUTCTimestamp(System.currentTimeMillis()+"")) == 0)
        {
            shop_business_switchbutton.setChecked(true);
            store_management_business_time.setEnabled(true);
        }
        else {
            shop_business_switchbutton.setChecked(false);
            store_management_business_time.setEnabled(false);
        }
        business_time_text.setText(storeSettingData.close_hour+":"+storeSettingData.close_min);
        send_price_text.setText(storeSettingData.start_shipping_fee+"元/起送");
        send_food_price_text.setText(storeSettingData.shipping_fee+"元/送餐费");
        send_time_text.setText(storeSettingData.shipping_time+"分钟/送达");
        store_phone_number.setText(storeSettingData.shipping_phone_number);
        if(storeSettingData.is_sms.equals("0"))
        {
            message_business_switchbuton.setChecked(false);
            store_phone_number_layout.setEnabled(false);
        }
        else {
            message_business_switchbuton.setChecked(true);
            store_phone_number_layout.setEnabled(true);
        }

        if(storeSettingData.is_client.equals("0")){
            client_business_switchbutton.setChecked(false);
        }
        else
        {
            client_business_switchbutton.setChecked(true);
        }

        shop_business_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                swipeRefreshLayout.setRefreshing(true);
                isOpen();

            }
        });

        message_business_switchbuton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                swipeRefreshLayout.setRefreshing(true);
                isSms();
            }
        });

        client_business_switchbutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                swipeRefreshLayout.setRefreshing(true);
                isIsClient();
            }
        });

        shop_notices_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), StoreManagementText.class);
                Bundle bundle = new Bundle();
                bundle.putString("data",storeSettingData.board);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });

        send_price_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), StoreManagementNumber.class);
                Bundle bundle = new Bundle();
                bundle.putString("price",storeSettingData.start_shipping_fee);
                bundle.putInt("title", R.string.send_price);
                bundle.putInt("id", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

        send_food_price_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), StoreManagementNumber.class);
                Bundle bundle = new Bundle();
                bundle.putString("price",storeSettingData.shipping_fee);
                bundle.putInt("title", R.string.send_food_price);
                bundle.putInt("id", 2);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });

        send_time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), StoreManagementNumber.class);
                Bundle bundle = new Bundle();
                bundle.putString("price",storeSettingData.shipping_time);
                bundle.putInt("title", R.string.send_time);
                bundle.putInt("id", 3);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });

        store_phone_number_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), StoreManagementNumber.class);
                Bundle bundle = new Bundle();
                bundle.putString("price",storeSettingData.shipping_phone_number);
                bundle.putInt("title", R.string.phonenumber);
                bundle.putInt("id", 4);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });

    }


    private void getSetting()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        getStoreSetting(ShareUtils.getId(getActivity()), accessToken.accessToken(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);
                // 获取一个Message对象，设置what为1
                Message msg = Message.obtain();
                msg.obj = object;
                msg.what = Constant.MSG_SUCCESS;
                // 发送这个消息到消息队列中
                handler.sendMessage(msg);
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "网络错误");
            }
        });
    }

    private void getStoreSetting(String id,String access_token, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.getStoreSetting(id, access_token).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StoreSettingData>() {
                    @Override
                    public void call(StoreSettingData storeSettingData) {
                        fialedInterface.onSuccess(storeSettingData);
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

    private void isOpen()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        storeIsOpen(ShareUtils.getId(getActivity()), accessToken.accessToken(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);
                if(store_management_business_time.isEnabled())
                {
                    ToastUtils.setToast(getActivity(), "已经关闭营业");
                    store_management_business_time.setEnabled(false);
                }
                else {
                    ToastUtils.setToast(getActivity(), "已经开始营业");
                    store_management_business_time.setEnabled(true);
                }
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "网络错误");
            }
        });
    }

    private void storeIsOpen(String id,String access_token, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.StoreIsOpen(id, access_token).observeOn(AndroidSchedulers.mainThread())
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

    private void closeTime(String close_hour,String close_min)
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        storeCloseTime(ShareUtils.getId(getActivity()), accessToken.accessToken(), close_hour, close_min, new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "网络错误");
            }
        });
    }

    private void storeCloseTime(String id,String access_token,String close_hour,String close_min, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.StoreCloseTime(id, access_token,close_hour,close_min).observeOn(AndroidSchedulers.mainThread())
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

    private void isSms()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        storeIsSms(ShareUtils.getId(getActivity()), accessToken.accessToken(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);
                if (store_phone_number_layout.isEnabled()) {
                    store_phone_number_layout.setEnabled(false);
                } else {
                    store_phone_number_layout.setEnabled(true);
                }
            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "网络错误");
            }
        });
    }

    private void storeIsSms(String id,String access_token, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.IsSms(id, access_token).observeOn(AndroidSchedulers.mainThread())
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

    private void isIsClient()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(getActivity()),ShareUtils.getKey(getActivity()));
        storeIsClient(ShareUtils.getId(getActivity()), accessToken.accessToken(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailth(int code) {
                swipeRefreshLayout.setRefreshing(false);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "发生错误");
            }

            @Override
            public void onNetworkError() {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.setToast(getActivity(), "网络错误");
            }
        });
    }

    private void storeIsClient(String id,String access_token, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.IsClient(id, access_token).observeOn(AndroidSchedulers.mainThread())
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

    private void setError(int code){
        switch (code) {
            case 401:
                ToastUtils.setToast(getActivity(), "电话号码或者密码有误");
                break;
            case 404:
                ToastUtils.setToast(getActivity(), "信息已经过期，请重新登录!");
                break;
            case 501:
                ToastUtils.setToast(getActivity(), "上传出错，请再重新上传一次!");
                break;
            case 502:
                ToastUtils.setToast(getActivity(), "上传出错，请再重新上传一次!");
                break;
            default:

                ToastUtils.setToast(getActivity(), "网络错误，请再重新上传一次!");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case 0:
                storeSettingData.board = data.getExtras().getString("data");
                break;
            case 1:
                storeSettingData.start_shipping_fee = data.getExtras().getString("data");
                send_price_text.setText(storeSettingData.start_shipping_fee+"元/起送");
                break;
            case 2:
                storeSettingData.shipping_fee = data.getExtras().getString("data");
                send_food_price_text.setText(storeSettingData.shipping_fee+"元/送餐费");

                break;
            case 3:
                storeSettingData.shipping_time = data.getExtras().getString("data");
                send_time_text.setText(storeSettingData.shipping_time+"分钟/送达");
                break;
            case 4:
                storeSettingData.shipping_phone_number = data.getExtras().getString("data");
                store_phone_number.setText(storeSettingData.shipping_phone_number);
                break;
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        business_time_text.setText(hourOfDay+":"+minute);
        swipeRefreshLayout.setRefreshing(true);
        closeTime(hourOfDay+"",minute+"");
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
