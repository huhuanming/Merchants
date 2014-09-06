package com.merchants.main.ApiManager;

import com.merchants.main.Model.LoginBackData;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by chen on 14-7-28.
 */
public class MerchantsApiInterface {
//    public interface ApiManagerNoticeService {
//        @POST("/restaurants")
//        PMessageBackData getPMessageBackData(@Query("access_token") String access_token
//                , @Query("restaurant_name") String restaurant_name
//                , @Query("supervisor_name") String supervisor_name
//                , @Query("back_account") String back_account
//                , @Query("phone_number") String phone_number
//                , @Query("linsece") String linsece
//                , @Query("id_card_front") String id_card_front
//                , @Query("id_card_reverse") String id_card_reverse
//                , @Query("address") String address
//                , @Query("radius") String radius
//                , @Query("longitude") String longitude
//                , @Query("latitude") String latitude
//                , @Query("coordinate_x1") String coordinate_x1
//                , @Query("coordinate_x2") String coordinate_x2
//                , @Query("coordinate_y1") String coordinate_y1
//                , @Query("coordinate_y2") String coordinate_y2
//        );
//    }

    public interface ApiManagerLogin {
        @POST("/supervisors/login")
        LoginBackData getLoginBackData(@Query("username") String username, @Query("password") String password);
    }
}
