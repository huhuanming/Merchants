package com.merchants.main.Model;

import java.util.List;

/**
 * Created by chen on 14-9-11.
 */
public class MenuManagementData {
    public String type_name;
    public List<Foods> foods;
    public static class Foods{
        public String food_name;
        public String shop_price;
    }
}
