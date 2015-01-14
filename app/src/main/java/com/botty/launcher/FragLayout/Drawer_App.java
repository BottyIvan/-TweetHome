package com.botty.launcher.FragLayout;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.botty.launcher.Adapters.DrawerAdapter;
import com.botty.launcher.Helper.DrawerClickListener;
import com.botty.launcher.R;
import com.botty.launcher.Helper.SortsApp;

import java.util.List;

public class Drawer_App extends Fragment {

    DrawerAdapter drawerAdapter;
    GridView DrawerApp;
    public class Pac{
        public Drawable icon;
        public String name;
        public String label;
    }

    Pac[] pacs;
    PackageManager pm;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.grid_frag, container, false);

        DrawerApp = (GridView)rootView.findViewById(R.id.grid_drawer);
        pm =  getActivity().getPackageManager();
        setPacs();
        drawerAdapter = new DrawerAdapter(getActivity(),pacs);
        DrawerApp.setAdapter(drawerAdapter);
        DrawerApp.setOnItemClickListener(new DrawerClickListener(getActivity(),pacs,pm));

        return rootView;
    }

    public void setPacs(){
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo>pacslist = pm.queryIntentActivities(mainIntent,0);
        pacs = new Pac[pacslist.size()];
        for (int I=0;I<pacslist.size();I++){
            pacs[I]=new Pac();
            pacs[I].icon=pacslist.get(I).loadIcon(pm);
            pacs[I].name=pacslist.get(I).activityInfo.packageName;
            pacs[I].label=pacslist.get(I).loadLabel(pm).toString();
        }
        new SortsApp().exchange_sort(pacs);
    }

}
