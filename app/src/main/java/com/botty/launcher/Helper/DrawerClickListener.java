package com.botty.launcher.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

import com.botty.launcher.FragLayout.Drawer_App;

/**
 * Created by ivanbotty on 10/12/14.
 */
public class DrawerClickListener implements AdapterView.OnItemClickListener{

    Context mContext;
    Drawer_App.Pac[] pacsForAdapter;
    PackageManager pmForListener;
    public DrawerClickListener(Context c,Drawer_App.Pac[] pacs,PackageManager pm){
        mContext = c;
        pacsForAdapter = pacs;
        pmForListener = pm;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent launchIntent = pmForListener.getLaunchIntentForPackage(pacsForAdapter[position].name);
        mContext.startActivity(launchIntent);
    }
}
