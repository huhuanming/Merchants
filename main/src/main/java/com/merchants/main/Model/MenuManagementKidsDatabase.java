package com.merchants.main.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chen on 14-9-12.
 */
@Table(name = "KidsData")
public class MenuManagementKidsDatabase extends Model{

    @Column(name = "Food_name")
    public String food_name;

    @Column(name = "Shop_price")
    public String shop_price;

    @Column(name = "MenuManagementGroupDatabase")
    public MenuManagementGroupDatabase menuManagementGroupDatabase;

    public MenuManagementKidsDatabase(){
        super();
    }

    public MenuManagementKidsDatabase(String food_name, String shop_price, MenuManagementGroupDatabase menuManagementGroupDatabase){
        super();
        this.food_name = food_name;
        this.shop_price = shop_price;
        this.menuManagementGroupDatabase = menuManagementGroupDatabase;
    }

}
