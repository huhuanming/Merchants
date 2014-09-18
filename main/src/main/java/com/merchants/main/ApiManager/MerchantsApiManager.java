package com.merchants.main.ApiManager;

import com.merchants.main.Model.LoginBackData;
import com.merchants.main.Model.MenuManagementData;
import com.merchants.main.Model.MenuUploadBackData;
import com.merchants.main.Model.OrderManagementData;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by chen on 14-7-19.
 */
public class MerchantsApiManager extends MainApiManager{

    private static final MerchantsApiInterface.ApiManagerMenuUpload apiManager = restAdapter.create(MerchantsApiInterface.ApiManagerMenuUpload.class);
    public static Observable<MenuUploadBackData> getMenuUploadBackData(final String access_token, final String menu_json) {
        return Observable.create(new Observable.OnSubscribeFunc<MenuUploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super MenuUploadBackData> observer) {
                try {
                    observer.onNext(apiManager.getMenuUploadBackData(access_token, menu_json));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerLogin LoginapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerLogin.class);
    public static Observable<LoginBackData> getLoginBackData(final String username, final String passwork) {
        return Observable.create(new Observable.OnSubscribeFunc<LoginBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super LoginBackData> observer) {
                try {
                    observer.onNext(LoginapiManager.getLoginBackData(username,passwork));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerMenuGetFoods GET_FoodsapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerMenuGetFoods.class);
    public static Observable<List<MenuManagementData>> getMenuFoods(final String id) {
        return Observable.create(new Observable.OnSubscribeFunc<List<MenuManagementData>>() {
            @Override
            public Subscription onSubscribe(Observer<? super List<MenuManagementData>> observer) {
                try {
                    observer.onNext(GET_FoodsapiManager.getMenuFoods(id));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerOrderGetDatas GET_OrderapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerOrderGetDatas.class);
    public static Observable<List<OrderManagementData>> getOrderDatas(final String id, final String access_token, final String page) {
        return Observable.create(new Observable.OnSubscribeFunc<List<OrderManagementData>>() {
            @Override
            public Subscription onSubscribe(Observer<? super List<OrderManagementData>> observer) {
                try {
                    observer.onNext(GET_OrderapiManager.getOrderDatas(id,access_token,page));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }
}
