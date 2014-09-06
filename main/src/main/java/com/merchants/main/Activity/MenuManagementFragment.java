package com.merchants.main.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.merchants.main.Model.MenuManagementGroupData;
import com.merchants.main.Model.MenuManagementKidsData;
import com.merchants.main.R;
import com.merchants.main.ResideMenu.ResideMenu;
import com.merchants.main.View.Listview.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 14-8-30.
 */
public class MenuManagementFragment extends Fragment{
    private View parentView;
    private ResideMenu resideMenu;
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
        for (int i = 0; i < 3; i++) {
            menuManagementGroupData = new MenuManagementGroupData();
            menuManagementGroupData.setGroup_name("特色菜谱" + i);
            group_list.add(menuManagementGroupData);
        }
        kids_list = new ArrayList<List<MenuManagementKidsData>>();
        for (int i = 0; i < group_list.size(); i++) {
            ArrayList<MenuManagementKidsData> childTemp;
            childTemp = new ArrayList<MenuManagementKidsData>();
            for (int j = 0; j < 5; j++) {
                MenuManagementKidsData menuManagementKidsData = new MenuManagementKidsData();
                menuManagementKidsData.setFood_name("鱼香肉丝" + j);


                childTemp.add(menuManagementKidsData);
            }
            kids_list.add(childTemp);
        }

        adapter = new MenuManagementExpandableListAdapter(getActivity());
        expandableListView.setAdapter(adapter);
        parentView.findViewById(R.id.menu_management_menu_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu();
            }
        });
        return parentView;
    }
    public MenuManagementFragment(ResideMenu resideMenu)
    {
        this.resideMenu = resideMenu;
    }
    public MenuManagementFragment(){
        super();
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

        public MenuManagementExpandableListAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
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
            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.menu_management_group, null);
                groupHolder.group_name = (TextView) convertView
                        .findViewById(R.id.menu_management_group_name);
                groupHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.menu_management_group_img);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.group_name.setText(group_list.get(groupPosition).getGroup_name());
//            if (isExpanded)// ture is Expanded or false is not isExpanded
//                groupHolder.imageView.setImageResource(R.drawable.expanded);
//            else
//                groupHolder.imageView.setImageResource(R.drawable.collapse);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder = null;
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.menu_management_group_kids, null);

                childHolder.food_name = (TextView) convertView
                        .findViewById(R.id.menu_management_group_kids_name);
                childHolder.food_price = (TextView) convertView
                        .findViewById(R.id.menu_management_group_kids_price);

                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }

            childHolder.food_name.setText(kids_list.get(groupPosition).get(childPosition).getFood_name());
            childHolder.food_price.setText(kids_list.get(groupPosition).get(childPosition).getFood_price());

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

    class ChildHolder {
        TextView food_name;
        TextView food_price;
    }
}
