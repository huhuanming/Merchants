package com.merchants.main.ApiManager;

import com.merchants.main.Model.LoginBackData;
import com.merchants.main.Model.MenuManagementData;
import com.merchants.main.Model.MenuUploadBackData;
import com.merchants.main.Model.OrderManagementData;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by chen on 14-7-28.
 */
public class MerchantsApiInterface {
    public interface ApiManagerMenuUpload {
        @POST("/menus")
        MenuUploadBackData getMenuUploadBackData(@Query("access_token") String access_token
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
        @GET("/restaurants/{id}/orders")
        List<OrderManagementData> getOrderDatas(@Path("id") String id, @Query("access_token") String access_token, @Query("page") String page);
    }
}
