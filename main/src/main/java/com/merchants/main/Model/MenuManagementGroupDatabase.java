package com.merchants.main.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by chen on 14-9-12.
 */
@Table(name = "GroupData")
public class MenuManagementGroupDatabase extends Model{

    @Column(name = "Type_name")
    public String type_name;

    public MenuManagementGroupDatabase(){
        super();
    }

    public List<MenuManagementKidsDatabase> KidsData;

    // 使用外键，实现一对多存储
    public List<MenuManagementKidsDatabase> menuManagementKidsDtabases() {
        KidsData = getMany(MenuManagementKidsDatabase.class, "MenuManagementKidsDtabase");
        return KidsData;
    }
}
