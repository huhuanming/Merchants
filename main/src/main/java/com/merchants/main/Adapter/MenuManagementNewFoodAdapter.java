package com.merchants.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.merchants.main.Model.MenuManagementKidsDatabase;
import com.merchants.main.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chen on 14-9-10.
 */
public class MenuManagementNewFoodAdapter extends BaseAdapter{
    //	内部类实现BaseAdapter  ，自定义适配器
    private Context context;
    private HashMap<String, Object> map;
    private List<MenuManagementKidsDatabase> kids_list;

    public MenuManagementNewFoodAdapter(Context context,List<MenuManagementKidsDatabase> kids_list)
    {
        this.context = context;
        this.kids_list = kids_list;
    }


    @Override
    public int getViewTypeCount() {
        //包含有两个视图，所以返回值为2
        return 1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return kids_list.size();
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
        final ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_management_new_food_item, null);
            holder = new ChildHolder();
            holder.food_name = (TextView) convertView
                    .findViewById(R.id.menu_management_group_kids_name);
            holder.food_price = (TextView) convertView
                    .findViewById(R.id.menu_management_group_kids_price);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.food_name.setText(kids_list.get(position).food_name);
        holder.food_price.setText(kids_list.get(position).shop_price+"元");
        return convertView;
    }

    class ChildHolder {
        TextView food_name;
        TextView food_price;
    }
}
