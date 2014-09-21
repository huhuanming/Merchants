package com.merchants.main.ApiManager;

import com.merchants.main.Model.LoginBackData;
import com.merchants.main.Model.MenuManagementData;
import com.merchants.main.Model.StoreSettingData;
import com.merchants.main.Model.UploadBackData;
import com.merchants.main.Model.OrderManagementData;
import com.merchants.main.Model.OrderManagementKidData;

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
    public static Observable<UploadBackData> getMenuUploadBackData(final String access_token, final String menu_json) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
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

    private static final MerchantsApiInterface.ApiManagerOrderGetDetailDatas GET_OrderDetailapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerOrderGetDetailDatas.class);
    public static Observable<List<OrderManagementKidData>> getOrderDetailDatas(final String restaurant_id, final String order_id, final String access_token) {
        return Observable.create(new Observable.OnSubscribeFunc<List<OrderManagementKidData>>() {
            @Override
            public Subscription onSubscribe(Observer<? super List<OrderManagementKidData>> observer) {
                try {
                    observer.onNext(GET_OrderDetailapiManager.getOrderDetailDatas(restaurant_id, order_id, access_token));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerOrderUpload PUT_OrderDetailapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerOrderUpload.class);
    public static Observable<UploadBackData> putOrderDetailDatas(final String restaurant_id, final String order_id, final String access_token) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_OrderDetailapiManager.orderUpload(restaurant_id, order_id, access_token));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerStoreSetting GET_SettingapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerStoreSetting.class);
    public static Observable<StoreSettingData> getStoreSetting(final String restaurant_id, final String access_token) {
        return Observable.create(new Observable.OnSubscribeFunc<StoreSettingData>() {
            @Override
            public Subscription onSubscribe(Observer<? super StoreSettingData> observer) {
                try {
                    observer.onNext(GET_SettingapiManager.getStoreSetting(restaurant_id, access_token));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerStoreIsOpen PUT_IsOpenapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerStoreIsOpen.class);
    public static Observable<UploadBackData> StoreIsOpen(final String restaurant_id, final String access_token) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_IsOpenapiManager.StoreIsOpen(restaurant_id, access_token));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerStoreCloseTime PUT_CloseTimeapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerStoreCloseTime.class);
    public static Observable<UploadBackData> StoreCloseTime(final String restaurant_id, final String access_token,final String close_hour,final String close_min) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_CloseTimeapiManager.StoreCloseTime(restaurant_id, access_token,close_hour,close_min));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerSetBoard PUT_BoardapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerSetBoard.class);
    public static Observable<UploadBackData> SetBoard(final String restaurant_id, final String access_token,final String board) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_BoardapiManager.SetBoard(restaurant_id, access_token,board));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerStartShipping PUT_StartShippingapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerStartShipping.class);
    public static Observable<UploadBackData> StartShipping(final String restaurant_id, final String access_token,final String start_shipping_fee) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_StartShippingapiManager.StartShipping(restaurant_id, access_token,start_shipping_fee));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerShippingFee PUT_ShippingFeeapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerShippingFee.class);
    public static Observable<UploadBackData> ShippingFee(final String restaurant_id, final String access_token,final String shipping_fee) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_ShippingFeeapiManager.ShippingFee(restaurant_id, access_token,shipping_fee));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerShippingTime PUT_ShippingTimeapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerShippingTime.class);
    public static Observable<UploadBackData> ShippingTime(final String restaurant_id, final String access_token,final String shipping_time) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_ShippingTimeapiManager.ShippingTime(restaurant_id, access_token,shipping_time));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerShippingPhoneNumber PUT_ShippingPhoneNumberapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerShippingPhoneNumber.class);
    public static Observable<UploadBackData> ShippingPhoneNumber(final String restaurant_id, final String access_token,final String shipping_phone_number) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_ShippingPhoneNumberapiManager.shippingPhoneNumber(restaurant_id, access_token,shipping_phone_number));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerIsSms PUT_IsSmsapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerIsSms.class);
    public static Observable<UploadBackData> IsSms(final String restaurant_id, final String access_token) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_IsSmsapiManager.IsSms(restaurant_id, access_token));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

    private static final MerchantsApiInterface.ApiManagerIsClient PUT_IsClientapiManager = restAdapter.create(MerchantsApiInterface.ApiManagerIsClient.class);
    public static Observable<UploadBackData> IsClient(final String restaurant_id, final String access_token) {
        return Observable.create(new Observable.OnSubscribeFunc<UploadBackData>() {
            @Override
            public Subscription onSubscribe(Observer<? super UploadBackData> observer) {
                try {
                    observer.onNext(PUT_IsClientapiManager.IsClient(restaurant_id, access_token));
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }

                return Subscriptions.empty();
            }
        }).subscribeOn(Schedulers.threadPoolForIO());
    }

}
