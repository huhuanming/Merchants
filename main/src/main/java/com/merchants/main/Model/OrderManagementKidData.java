package com.merchants.main.Model;

/**
 * Created by chen on 14-9-19.
 */
public class OrderManagementKidData {
    public String count;
    public String total_price;
    public String actual_total_price;
    public Food food;
    public static class Food{
        public String food_name;
        public String shop_price;
    }
}
