package com.merchants.main.Model;

/**
 * Created by chen on 14-8-16.
 */
public class LoginBackData {
    public String login_count;
    public String last_login_at;
    public Access_token access_token;
    public Restaurant restaurant;
    public static class Access_token{
        public String token;
        public String key;
        public String id;
    }
    public static class Restaurant{
        public String restaurant_id;
        public String restaurant_name;
    }
}
