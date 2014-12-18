package com.botty.launcher.Adapters;

/**
 * Created by ivanbotty on 27/11/14.
 */
public class DrawerNavItem {

    String ItemName;
    int imgResID;

    public DrawerNavItem(String itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }



}