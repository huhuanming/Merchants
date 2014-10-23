package com.merchants.main.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.merchants.main.ApiManager.MainApiManager;
import com.merchants.main.ApiManager.MerchantsApiManager;
import com.merchants.main.Model.UploadBackData;
import com.merchants.main.R;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.Utils.ToastUtils;
import com.merchants.main.Utils.TokenUtils.AccessToken;
import com.merchants.main.View.Button.BootstrapEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-9-20.
 */
public class StoreManagementNumber extends Activity {

    private int title;
    private int id;
    private String price;

    @InjectView(R.id.text)BootstrapEditText editText;
    @InjectView(R.id.progressbar)SmoothProgressBar progressBar;
    @OnClick(R.id.button)void button(){
        progressBar.setVisibility(View.VISIBLE);
            switch (id){
                case 1:
                    uploadStartShipping();
                    break;
                case 2:
                    uploadShippingFee();
                    break;
                case 3:
                    uploadShippingTime();
                    break;
                case 4:
                    uploadShippingPhoneNumber();
                    break;
            }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_management_number);
        ButterKnife.inject(this);
        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getInt("title");
        id = bundle.getInt("id");
        price = bundle.getString("price");

        getActionBar().setTitle(title);
        editText.setText(price);
    }

    private void uploadStartShipping()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(StoreManagementNumber.this),ShareUtils.getKey(StoreManagementNumber.this));
        startShipping(ShareUtils.getId(StoreManagementNumber.this), accessToken.accessToken(), editText.getText().toString().trim(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "成功");
                Bundle bundle = new Bundle();
                bundle.putString("data", editText.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(1, intent);
                StoreManagementNumber.this.finish();

            }

            @Override
            public void onFailth(int code) {
                progressBar.setVisibility(View.GONE);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "发生错误");
            }

            @Override
            public void onNetworkError() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "网络错误");
            }
        });
    }

    private void startShipping(String id,String access_token,String start_shipping_fee, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.StartShipping(id, access_token, start_shipping_fee).observeOn(AndroidSchedulers.mainThread())
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

    private void uploadShippingFee()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(StoreManagementNumber.this),ShareUtils.getKey(StoreManagementNumber.this));
        shippingFee(ShareUtils.getId(StoreManagementNumber.this), accessToken.accessToken(), editText.getText().toString().trim(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "成功");
                Bundle bundle = new Bundle();
                bundle.putString("data", editText.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(2, intent);
                StoreManagementNumber.this.finish();

            }

            @Override
            public void onFailth(int code) {
                progressBar.setVisibility(View.GONE);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "发生错误");
            }

            @Override
            public void onNetworkError() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "网络错误");
            }
        });
    }

    private void shippingFee(String id,String access_token,String shipping_fee, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.ShippingFee(id, access_token, shipping_fee).observeOn(AndroidSchedulers.mainThread())
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

    private void uploadShippingTime()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(StoreManagementNumber.this),ShareUtils.getKey(StoreManagementNumber.this));
        shippingTime(ShareUtils.getId(StoreManagementNumber.this), accessToken.accessToken(), editText.getText().toString().trim(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "成功");
                Bundle bundle = new Bundle();
                bundle.putString("data", editText.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(3, intent);
                StoreManagementNumber.this.finish();

            }

            @Override
            public void onFailth(int code) {
                progressBar.setVisibility(View.GONE);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "发生错误");
            }

            @Override
            public void onNetworkError() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "网络错误");
            }
        });
    }

    private void shippingTime(String id,String access_token,String shipping_time, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.ShippingTime(id, access_token, shipping_time).observeOn(AndroidSchedulers.mainThread())
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

    private void uploadShippingPhoneNumber()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(StoreManagementNumber.this),ShareUtils.getKey(StoreManagementNumber.this));
        shippingPhoneNumber(ShareUtils.getId(StoreManagementNumber.this), accessToken.accessToken(), editText.getText().toString().trim(), new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "成功");
                Bundle bundle = new Bundle();
                bundle.putString("data", editText.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(4, intent);
                StoreManagementNumber.this.finish();

            }

            @Override
            public void onFailth(int code) {
                progressBar.setVisibility(View.GONE);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "发生错误");
            }

            @Override
            public void onNetworkError() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementNumber.this, "网络错误");
            }
        });
    }

    private void shippingPhoneNumber(String id,String access_token,String shipping_phone_number, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.ShippingPhoneNumber(id, access_token, shipping_phone_number).observeOn(AndroidSchedulers.mainThread())
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId())
        {
            //监听返回键
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(100, intent);
                StoreManagementNumber.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setError(int code){
        switch (code) {
            case 401:
                ToastUtils.setToast(StoreManagementNumber.this, "电话号码或者密码有误");
                break;
            case 404:
                ToastUtils.setToast(StoreManagementNumber.this, "信息已经过期，请重新登录!");
                break;
            case 501:
                ToastUtils.setToast(StoreManagementNumber.this, "上传出错，请再重新上传一次!");
                break;
            case 502:
                ToastUtils.setToast(StoreManagementNumber.this, "上传出错，请再重新上传一次!");
                break;
            default:

                ToastUtils.setToast(StoreManagementNumber.this, "网络错误，请再重新上传一次!");
                break;
        }
    }
}
