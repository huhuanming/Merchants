package com.merchants.main.Utils;

import com.merchants.main.Model.MenuManagementGroupDatabase;
import com.merchants.main.Model.MenuManagementKidsDatabase;

import java.util.List;

/**
 * Created by chen on 14-9-11.
 */
public class JsonUtils {
    public static String getFoodJson(List<MenuManagementGroupDatabase> grouplist, List<List<MenuManagementKidsDatabase>> kidslist)
    {
        int groupnum = grouplist.size();

        String jsonString = "{";
        for(int i = 0; i < groupnum; i++)
        {
            int kidnum = kidslist.get(i).size();
            jsonString = jsonString + "\""+grouplist.get(i).type_name+"\":{";
            for(int j = 0; j < kidnum; j++)
            {
                jsonString = jsonString+ "\""+kidslist.get(i).get(j).food_name+"\":{";
                jsonString = jsonString+ "\"price\":\""+kidslist.get(i).get(j).shop_price+"\"}";
                if(j != kidnum-1)
                {
                    jsonString = jsonString+",";
                }
            }
            jsonString = jsonString+"}";
            if(i != groupnum-1)
            {
                jsonString = jsonString+",";
            }
        }
        jsonString = jsonString+"}";
        return jsonString;
    }
}
