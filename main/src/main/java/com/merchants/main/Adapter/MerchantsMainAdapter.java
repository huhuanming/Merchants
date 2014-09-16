package com.merchants.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.merchants.main.R;

import java.util.ArrayList;

/**
 * Created by chen on 14-9-12.
 */
public class MerchantsMainAdapter extends BaseAdapter{
    //	内部类实现BaseAdapter  ，自定义适配器
    private Context context;
    private ArrayList<String> menuEntries;
    int fragmentPosition;

    public MerchantsMainAdapter(Context context,ArrayList<String> menuEntries,int fragmentPosition)
    {
        this.context = context;
        this.menuEntries = menuEntries;
        this.fragmentPosition = fragmentPosition;
    }


    @Override
    public int getViewTypeCount() {
        //包含有两个视图，所以返回值为2
        return 1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return menuEntries.size();
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
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.merchants_main_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView
                    .findViewById(R.id.merchants_main_text);
//            holder.vertical_textview = (TextView) convertView
//                    .findViewById(R.id.merchants_main_vertical_text);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
//            holder.vertical_textview.setVisibility(View.GONE);
        }

        holder.textView.setText(menuEntries.get(position));

//        if(position == fragmentPosition-1)
//        {
//            holder.vertical_textview.setVisibility(View.VISIBLE);
//        }

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        TextView vertical_textview;
    }
}
