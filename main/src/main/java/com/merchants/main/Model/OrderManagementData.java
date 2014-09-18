package com.merchants.main.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chen on 14-9-17.
 */
@Table(name = "OrderData")
public class OrderManagementData extends Model{

    @Column(name = "Order_id")
    public String order_id;

    @Column(name = "Ship_type")
    public String ship_type;

    @Column(name = "Order_type")
    public String order_type;

    @Column(name = "Phone_number")
    public String phone_number;

    @Column(name = "Food_count")
    public String food_count;

    @Column(name = "Shipping_user")
    public String shipping_user;


    @Column(name = "Shipping_address")
    public String shipping_address;

    @Column(name = "Order_remark")
    public String order_remark;

    @Column(name = "Total_price")
    public String total_price;

    @Column(name = "Actual_total_price")
    public String actual_total_price;

    @Column(name = "Created_at")
    public String created_at;

    @Column(name = "Updated_at")
    public String updated_at;

    @Column(name = "Shipping_at")
    public String shipping_at;

}
