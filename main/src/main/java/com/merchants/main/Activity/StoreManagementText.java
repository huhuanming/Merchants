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
public class StoreManagementText extends Activity{
    @InjectView(R.id.text)BootstrapEditText editText;
    @InjectView(R.id.progressbar)SmoothProgressBar progressBar;
    @OnClick(R.id.button)void button(){
        progressBar.setVisibility(View.VISIBLE);
        uploadBoard();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_management_text);
        ButterKnife.inject(this);
        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        editText.setText(getIntent().getExtras().get("data").toString());
    }

    private void uploadBoard()
    {

        AccessToken accessToken = new AccessToken(ShareUtils.getToken(StoreManagementText.this),ShareUtils.getKey(StoreManagementText.this));
        SetBoard(ShareUtils.getId(StoreManagementText.this),accessToken.accessToken(),editText.getText().toString().trim(),new MainApiManager.FialedInterface() {
            @Override
            public void onSuccess(Object object) {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementText.this, "成功");
                Bundle bundle = new Bundle();
                bundle.putString("data",editText.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(0,intent);
                StoreManagementText.this.finish();
            }

            @Override
            public void onFailth(int code) {
                progressBar.setVisibility(View.GONE);
                setError(code);
            }

            @Override
            public void onOtherFaith() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementText.this,"发生错误");
            }

            @Override
            public void onNetworkError() {
                progressBar.setVisibility(View.GONE);
                ToastUtils.setToast(StoreManagementText.this,"网络错误");
            }
        });
    }

    private void SetBoard(String id,String access_token,String board, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.SetBoard(id, access_token, board).observeOn(AndroidSchedulers.mainThread())
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
                ToastUtils.setToast(StoreManagementText.this, "电话号码或者密码有误");
                break;
            case 404:
                ToastUtils.setToast(StoreManagementText.this, "信息已经过期，请重新登录!");
                break;
            case 501:
                ToastUtils.setToast(StoreManagementText.this, "上传出错，请再重新上传一次!");
                break;
            case 502:
                ToastUtils.setToast(StoreManagementText.this, "上传出错，请再重新上传一次!");
                break;
            default:

                ToastUtils.setToast(StoreManagementText.this, "网络错误，请再重新上传一次!");
                break;
        }
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
                StoreManagementText.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
