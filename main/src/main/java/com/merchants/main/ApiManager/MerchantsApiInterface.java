package com.merchants.main.ApiManager;

import com.merchants.main.Model.LoginBackData;
import com.merchants.main.Model.MenuManagementData;
import com.merchants.main.Model.StoreSettingData;
import com.merchants.main.Model.UploadBackData;
import com.merchants.main.Model.OrderManagementData;
import com.merchants.main.Model.OrderManagementKidData;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by chen on 14-7-28.
 */
public class MerchantsApiInterface {
    public interface ApiManagerMenuUpload {
        @POST("/menus")
        UploadBackData getMenuUploadBackData(@Query("access_token") String access_token
                , @Query("menu_json") String menu_json

        );
    }

    public interface ApiManagerMenuGetFoods {
        @GET("/restaurants/{id}/menu")
        List<MenuManagementData> getMenuFoods(@Path("id") String id);
    }
    public interface ApiManagerLogin {
        @POST("/supervisors/login")
        LoginBackData getLoginBackData(@Query("username") String username, @Query("password") String password);
    }

    public interface ApiManagerOrderGetDatas {
        @GET("/restaurants/{restaurant_id}/orders")
        List<OrderManagementData> getOrderDatas(@Path("restaurant_id") String id, @Query("access_token") String access_token, @Query("id") String page);
    }

    public interface ApiManagerOrderGetDetailDatas {
        @GET("/restaurants/{restaurant_id}/orders/{order_id}")
        List<OrderManagementKidData> getOrderDetailDatas(@Path("restaurant_id") String restaurant_id,@Path("order_id") String order_id, @Query("access_token") String access_token);
    }

    public interface ApiManagerOrderUpload{
        @PUT("/restaurants/{restaurant_id}/orders/{order_id}/check_order")
        UploadBackData orderUpload(@Path("restaurant_id") String restaurant_id,@Path("order_id") String order_id, @Query("access_token") String access_token);
    }

    public interface ApiManagerStoreSetting{
        @GET("/restaurants/{restaurant_id}/setting")
        StoreSettingData getStoreSetting(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token);
    }

    public interface ApiManagerStoreIsOpen{
        @PUT("/restaurants/{restaurant_id}/is_open")
        UploadBackData StoreIsOpen(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token);
    }

    public interface ApiManagerStoreCloseTime{
        @PUT("/restaurants/{restaurant_id}/close_time")
        UploadBackData StoreCloseTime(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token,@Query("close_hour")String close_hour,@Query("close_min")String close_min);
    }

    public interface ApiManagerSetBoard{
        @PUT("/restaurants/{restaurant_id}/board")
        UploadBackData SetBoard(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token,@Query("board")String board);
    }

    public interface ApiManagerStartShipping{
        @PUT("/restaurants/{restaurant_id}/start_shipping_fee")
        UploadBackData StartShipping(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token,@Query("start_shipping_fee")String start_shipping_fee);
    }

    public interface ApiManagerShippingFee{
        @PUT("/restaurants/{restaurant_id}/shipping_fee")
        UploadBackData ShippingFee(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token,@Query("shipping_fee")String shipping_fee);
    }

    public interface ApiManagerShippingTime{
        @PUT("/restaurants/{restaurant_id}/shipping_time")
        UploadBackData ShippingTime(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token,@Query("shipping_time")String shipping_time);
    }

    public interface ApiManagerShippingPhoneNumber{
        @PUT("/restaurants/{restaurant_id}/shipping_phone_number")
        UploadBackData shippingPhoneNumber(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token,@Query("shipping_phone_number")String shipping_phone_number);
    }

    public interface ApiManagerIsSms{
        @PUT("/restaurants/{restaurant_id}/is_sms")
        UploadBackData IsSms(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token);
    }

    public interface ApiManagerIsClient{
        @PUT("/restaurants/{restaurant_id}/is_client")
        UploadBackData IsClient(@Path("restaurant_id") String restaurant_id, @Query("access_token") String access_token);
    }
}
