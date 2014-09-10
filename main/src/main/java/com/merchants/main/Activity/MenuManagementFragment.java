package com.merchants.main.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.merchants.main.Model.MenuManagementGroupData;
import com.merchants.main.Model.MenuManagementKidsData;
import com.merchants.main.R;
import com.merchants.main.View.Dialog.AlertDialog;
import com.merchants.main.View.Listview.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 14-8-30.
 */
public class MenuManagementFragment extends Fragment{
    private RotateAnimation rAnimation; //设置旋转
    private View parentView;
    private ArrayList<MenuManagementGroupData> group_list;
    private ArrayList<List<MenuManagementKidsData>> kids_list;
    private MenuManagementExpandableListAdapter adapter;
    private PinnedHeaderExpandableListView expandableListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.menu_management, container, false);
        expandableListView = (PinnedHeaderExpandableListView)parentView.findViewById(R.id.menu_management_expandablelistView);
        group_list = new ArrayList<MenuManagementGroupData>();
        MenuManagementGroupData menuManagementGroupData = null;
        for (int i = 0; i < 5; i++) {
            menuManagementGroupData = new MenuManagementGroupData();
            menuManagementGroupData.setGroup_name("特色菜谱" + i);
            group_list.add(menuManagementGroupData);
        }
        menuManagementGroupData = new MenuManagementGroupData();
        group_list.add(menuManagementGroupData);
        kids_list = new ArrayList<List<MenuManagementKidsData>>();
        for (int i = 0; i < group_list.size()-1; i++) {
            ArrayList<MenuManagementKidsData> childTemp;
            childTemp = new ArrayList<MenuManagementKidsData>();
            for (int j = 0; j < 10; j++) {
                MenuManagementKidsData menuManagementKidsData = new MenuManagementKidsData();
                menuManagementKidsData.setFood_name("鱼香肉丝" + j);
                menuManagementKidsData.setFood_price("5.00元");

                childTemp.add(menuManagementKidsData);
            }
            kids_list.add(childTemp);
        }
        ArrayList<MenuManagementKidsData> childTemp;
        childTemp = new ArrayList<MenuManagementKidsData>();
//        MenuManagementKidsData menuManagementKidsData = new MenuManagementKidsData();
//        childTemp.add(menuManagementKidsData);
        kids_list.add(childTemp);

        adapter = new MenuManagementExpandableListAdapter(getActivity());
        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                new AlertDialog(getActivity()).builder()
                        .setMsg("你现在无法接收到新消息提醒。请到系统-设置-通知中开启消息提醒")
                        .setNegativeButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                return false;
            }
        });
        return parentView;
    }
    /***
     * 数据源
     *
     * @author Administrator
     *
     */
    class MenuManagementExpandableListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private LayoutInflater inflater;
        private static final int LAST_ITEM = 1;
        private static final int NOT_LAST_ITEM = 2;

        public MenuManagementExpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getChildTypeCount() {
            return 2;
        }

        @Override
        public void notifyDataSetInvalidated() {
            super.notifyDataSetInvalidated();
        }

        @Override
        public int getGroupTypeCount() {
            return 2;
        }

        // 返回父列表个数
        @Override
        public int getGroupCount() {
            return group_list.size();
        }

        // 返回子列表个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return kids_list.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return group_list.get(groupPosition);
        }
        public int getItemViewType(int groupPosition) {
            if(group_list.size()-1 == groupPosition)
                return LAST_ITEM;
            else
                return NOT_LAST_ITEM;
        }
        public int getChildItemViewType(int groupPosition, int childPosition) {
            if(kids_list.get(groupPosition).size()-1 == childPosition)
                return LAST_ITEM;
            else
                return NOT_LAST_ITEM;
        }
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return kids_list.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {

            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            ViewHolder viewHolder = null;
            int type = getItemViewType(groupPosition);
            switch(type){
                case LAST_ITEM:
//                    if (convertView == null) {
                        viewHolder = new ViewHolder();
                        convertView = inflater.inflate(R.layout.menu_management_lastitem, null);
                        viewHolder.textView = (TextView) convertView
                                .findViewById(R.id.menu_management_lastitem_text);

                        convertView.setTag(viewHolder);
//                    } else {
//                        viewHolder = (ViewHolder) convertView.getTag();
//                    }
                    viewHolder.textView.setText(R.string.new_classification);

                    break;
                case NOT_LAST_ITEM:
//                    if (convertView == null) {
                        groupHolder = new GroupHolder();
                        convertView = inflater.inflate(R.layout.menu_management_group, null);
                        groupHolder.group_name = (TextView) convertView
                                .findViewById(R.id.menu_management_group_name);
                        groupHolder.imageView = (ImageView) convertView
                                .findViewById(R.id.menu_management_group_img);
                        convertView.setTag(groupHolder);
//                    } else {
//                        groupHolder = (GroupHolder) convertView.getTag();
//                    }

                    groupHolder.group_name.setText(group_list.get(groupPosition).getGroup_name());

                    if (isExpanded)// ture is Expanded or false is not isExpanded
                        setRotateToNighty(groupHolder.imageView);
                    else
                        setRotateToZero(groupHolder.imageView);
                    break;
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
            ViewHolder viewHolder = null;
            int type = getChildItemViewType(groupPosition,childPosition);
            switch (type)
            {
                case LAST_ITEM:
//                    if (convertView == null) {
                        viewHolder = new ViewHolder();
                        convertView = inflater.inflate(R.layout.menu_management_lastitem, null);
                        viewHolder.menu_management_lastitem_layout = (RelativeLayout)convertView
                                .findViewById(R.id.menu_management_lastitem_layout);
                        viewHolder.textView = (TextView) convertView
                                .findViewById(R.id.menu_management_lastitem_text);


                        convertView.setTag(viewHolder);
//                    } else {
//                        viewHolder = (ViewHolder) convertView.getTag();
//                    }
                    if(groupPosition == group_list.size()-1)
                        viewHolder.menu_management_lastitem_layout.setVisibility(View.GONE);
                    else
                        viewHolder.textView.setText(R.string.new_food);

                    break;
                case NOT_LAST_ITEM:
//                    if (convertView == null) {
                        childHolder = new ChildHolder();
                        convertView = inflater.inflate(R.layout.menu_management_group_kids, null);

                        childHolder.food_name = (TextView) convertView
                                .findViewById(R.id.menu_management_group_kids_name);
                        childHolder.food_price = (TextView) convertView
                                .findViewById(R.id.menu_management_group_kids_price);

                        convertView.setTag(childHolder);
//                    } else {
//                        childHolder = (ChildHolder) convertView.getTag();
//                    }

                    childHolder.food_name.setText(kids_list.get(groupPosition).get(childPosition).getFood_name());
                    childHolder.food_price.setText(kids_list.get(groupPosition).get(childPosition).getFood_price());
                    break;

            }


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class GroupHolder {
        TextView group_name;
        ImageView imageView;
    }
    class ViewHolder{
       TextView textView;
       RelativeLayout menu_management_lastitem_layout;
    }

    class ChildHolder {
        TextView food_name;
        TextView food_price;
    }

    //分别设置旋转角度
    private void setRotateToNighty(ImageView menu_management_group_img)
    {
        rAnimation = new RotateAnimation(0,90, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //设置执行时间
        rAnimation.setDuration(500);
        //是否保持动画完时的状态
        rAnimation.setFillAfter(true);
        //是否动画完了返回原状态
        rAnimation.setFillBefore(false);
        //隔多长时间动画显示
        rAnimation.setStartOffset(0);
        //动画重复执行次数
        rAnimation.setRepeatCount(0);
        //设置动画效果
        menu_management_group_img.setAnimation(rAnimation);
    }

    //分别设置旋转角度
    private void setRotateToZero(ImageView menu_management_group_img)
    {
        rAnimation = new RotateAnimation(45,0, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        //设置执行时间
        rAnimation.setDuration(500);

        //是否保持动画完时的状态
        rAnimation.setFillAfter(true);
        //是否动画完了返回原状态
        rAnimation.setFillBefore(false);
        //隔多长时间动画显示
        rAnimation.setStartOffset(0);
        //动画重复执行次数
        rAnimation.setRepeatCount(0);
        //设置动画效果
        menu_management_group_img.setAnimation(rAnimation);
    }
}
