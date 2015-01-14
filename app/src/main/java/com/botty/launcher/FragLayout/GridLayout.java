package com.botty.launcher.FragLayout;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.botty.launcher.Adapters.DrawerAdapter;
import com.botty.launcher.R;

/**
 * Created by ivanbotty on 27/11/14.
 */
public class GridLayout extends Fragment {

    DrawerAdapter drawerAdapter;
    GridView DrawerApp;
    public class Pac{
        public Drawable icon;
        public String name;
        public String label;
    }

    Pac[] pacs;
    PackageManager pm;
    public GridLayout() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.grid_frag, container,
                false);
        DrawerApp = (GridView)view.findViewById(R.id.grid_drawer);

        DrawerApp.setAdapter(drawerAdapter);
        return view;
    }

}