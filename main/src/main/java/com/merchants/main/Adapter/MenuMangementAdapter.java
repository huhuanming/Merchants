package com.merchants.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.merchants.main.Model.MenuManagementGroupDatabase;
import com.merchants.main.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by chen on 14-9-10.
 */
public class MenuMangementAdapter extends BaseAdapter{
    //	内部类实现BaseAdapter  ，自定义适配器
    private Context context;
    private HashMap<String, Object> map;
    private List<MenuManagementGroupDatabase> group_list;
    private int mRightWidth = 0;
    public MenuMangementAdapter(Context context,List<MenuManagementGroupDatabase> group_list)
    {
        this.context = context;
        this.group_list = group_list;
    }


    @Override
    public int getViewTypeCount() {
        //包含有两个视图，所以返回值为2
        return 1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return group_list.size();
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
        final GroupHolder holder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_management_item, null);
            holder = new GroupHolder();
            holder.group_name = (TextView) convertView
                    .findViewById(R.id.menu_management_group_name);
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.menu_management_group_img);
            convertView.setTag(holder);
        }
        else
        {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.group_name.setText(group_list.get(position).type_name);

        return convertView;
    }

    class GroupHolder {
        TextView group_name;
        ImageView imageView;

//        RelativeLayout item_right;
    }
    }

