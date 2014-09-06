package com.merchants.main.ApiManager;

import com.merchants.main.Model.LoginBackData;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.concurrency.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by chen on 14-7-19.
 */
public class MerchantsApiManager extends MainApiManager{

//    private static final MerchantsApiInterface.ApiManagerNoticeService apiManager = restAdapter.create(MerchantsApiInterface.ApiManagerNoticeService.class);
//    public static Observable<PMessageBackData> getPMessageBackData(final String access_token, final String restaurant_name,
//                                                                   final String supervisor_name,final String back_account,
//                                                                   final String phone_number,final String linsece,
//                                                                   final String id_card_front,final String id_card_reverse,
//                                                                   final String address,final String radius,
//                                                                   final String longitude,final String latitude,
//                                                                   final String coordinate_x1,final String coordinate_x2,
//                                                                   final String coordinate_y1,final String coordinate_y2) {
//        return Observable.create(new Observable.OnSubscribeFunc<PMessageBackData>() {
//            @Override
//            public Subscription onSubscribe(Observer<? super PMessageBackData> observer) {
//                try {
//                    observer.onNext(apiManager.getPMessageBackData(access_token,restaurant_name,supervisor_name,
//                            back_account,phone_number,linsece,id_card_front,id_card_reverse,
//                            address,radius,longitude,latitude,coordinate_x1,coordinate_x2,coordinate_y1,coordinate_y2));
//                    observer.onCompleted();
//                } catch (Exception e) {
//                    observer.onError(e);
//                }
//
//                return Subscriptions.empty();
//            }
//        }).subscribeOn(Schedulers.threadPoolForIO());
//    }

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
}
