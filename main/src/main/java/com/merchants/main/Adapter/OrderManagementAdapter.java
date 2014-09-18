package com.merchants.main.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.merchants.main.Model.OrderManagementData;
import com.merchants.main.R;
import com.merchants.main.Utils.DateUtils;

import java.util.List;

/**
 * Created by chen on 14-9-16.
 */
public class OrderManagementAdapter extends BaseAdapter {
    //	内部类实现BaseAdapter  ，自定义适配器
    private Context context;
    private List<OrderManagementData> list;

    public OrderManagementAdapter(Context context,List<OrderManagementData> kids_list)
    {
        this.context = context;
        this.list = kids_list;
    }


    @Override
    public int getViewTypeCount() {
        //包含有两个视图，所以返回值为2
        return 1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_management_item, null);
            holder = new ViewHolder();
            holder.order_management_item_name = (TextView) convertView
                    .findViewById(R.id.order_management_item_name);
            holder.order_management_item_time = (TextView) convertView
                    .findViewById(R.id.order_management_item_time);
            holder.order_management_item_food_num = (TextView) convertView
                    .findViewById(R.id.order_management_item_food_num);
            holder.order_management_item_number = (TextView) convertView
                    .findViewById(R.id.order_management_item_number);
            holder.order_management_item_address = (TextView) convertView
                    .findViewById(R.id.order_management_item_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.order_management_item_name.setText(list.get(position).shipping_user+"的订单");
        holder.order_management_item_time.setText(DateUtils.getMonthTime(list.get(position).updated_at));
        holder.order_management_item_food_num.setText(list.get(position).food_count);
        holder.order_management_item_number.setText(list.get(position).phone_number);
        holder.order_management_item_address.setText(list.get(position).shipping_address);

        return convertView;
    }

    class ViewHolder {
        TextView order_management_item_name;
        TextView order_management_item_time;
        TextView order_management_item_food_num;
        TextView order_management_item_number;
        TextView order_management_item_address;
    }
}
