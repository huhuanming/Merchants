package com.merchants.main.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.merchants.main.ApiManager.MainApiManager;
import com.merchants.main.ApiManager.MerchantsApiManager;
import com.merchants.main.Model.LoginBackData;
import com.merchants.main.R;
import com.merchants.main.Utils.ShareUtils;
import com.merchants.main.Utils.ToastUtils;
import com.merchants.main.View.Button.BootstrapButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-8-15.
 */
public class MerchantsLogin extends Activity {

    private Handler handler;
    private SharedPreferences.Editor editor;
    private SharedPreferences mshared;

    @InjectView(R.id.login_phonenumber)EditText userphonenumber;
    @InjectView(R.id.login_password)EditText userpassword;
    @InjectView(R.id.login_button)BootstrapButton button;
    @InjectView(R.id.login_progressbar)SmoothProgressBar progressBar;

    @OnClick(R.id.login_button)void loginbutton(){
        String username = userphonenumber.getText().toString().trim();
        String password = userpassword.getText().toString().trim();
        if(username.equals(""))
        {
            ToastUtils.setToast(MerchantsLogin.this, "请输入手机号!");
        }
        else if(password.equals(""))
        {
            ToastUtils.setToast(MerchantsLogin.this,"请输入密码!");
        }
//        else if(!ValidationUtils.isMobileNO(username))
//        {
//            ToastUtils.setToast(PromotionerLogin.this,"请输入正确的电话号码!");
//        }
        else {
            Login(username,password,new MainApiManager.FialedInterface() {
                @Override
                public void onSuccess(Object object) {
                    LoginBackData loginBackData = (LoginBackData)object;
                    mshared = getSharedPreferences("usermessage", 0);
                    editor = mshared.edit();
                    editor.putString("token", loginBackData.access_token.token);
                    editor.putString("key", loginBackData.access_token.key);
                    editor.commit();
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent();
                    intent.setClass(MerchantsLogin.this,MerchantsMenu.class);
                    startActivity(intent);
                    MerchantsLogin.this.finish();
                }

                @Override
                public void onFailth(int code) {
                    progressBar.setVisibility(View.GONE);
                    switch (code)
                    {
                        case 401:
                            ToastUtils.setToast(MerchantsLogin.this,"电话号码或者密码有误");
                            ShareUtils.deleteTokenKey(MerchantsLogin.this);
                            break;
                        case 404:
                            ToastUtils.setToast(MerchantsLogin.this,"信息已经过期，请重新登录!");
                            ShareUtils.deleteTokenKey(MerchantsLogin.this);
                            break;
                        case 501:
                            ToastUtils.setToast(MerchantsLogin.this,"上传出错，请再重新上传一次!");
                            break;
                        default:
                            ToastUtils.setToast(MerchantsLogin.this,"网络错误，请再重新上传一次!");
                            break;
                    }
                }
            });
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_merchants);
        ButterKnife.inject(this);//为监听所用
//        //显示actionbar上面的返回键
//        ActionBar actionBar = this.getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        if(!ShareUtils.getToken(MerchantsLogin.this).equals(""))
        {
            Intent intent = new Intent();
            intent.setClass(MerchantsLogin.this,MerchantsMenu.class);
            startActivity(intent);
            MerchantsLogin.this.finish();
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
                MerchantsLogin.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Login(String username, String password, final MainApiManager.FialedInterface fialedInterface)
    {
        MerchantsApiManager.getLoginBackData(username, password).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoginBackData>() {
                    @Override
                    public void call(LoginBackData loginBackData) {
                        fialedInterface.onSuccess(loginBackData);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                        retrofit.RetrofitError e = (retrofit.RetrofitError)throwable;
                        fialedInterface.onFailth(e.getResponse().getStatus());
                    }
                });
    }
}
