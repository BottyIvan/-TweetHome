package com.botty.launcher.FragLayout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.botty.launcher.R;

/**
 * Created by ivanbotty on 10/12/14.
 */
public class WorksSpace_HOME extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.works_home, container, false);

        return rootView;
    }
}
