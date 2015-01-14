package com.botty.launcher.Helper;

import com.botty.launcher.FragLayout.Drawer_App;

/**
 * Created by ivanbotty on 27/11/14.
 */
public class SortsApp {

    public void exchange_sort(Drawer_App.Pac[] pacs){
            int i, j;
            Drawer_App.Pac temp;

            for ( i = 0;  i < pacs.length - 1;  i++ )
            {
                for ( j = i + 1;  j < pacs.length;  j++ )
                {
                    if ( pacs [ i ].label.compareToIgnoreCase( pacs [ j ].label ) > 0 )
                    {                                             // ascending sort
                        temp = pacs [ i ];
                        pacs [ i ] = pacs [ j ];    // swapping
                        pacs [ j ] = temp;

                    }
                }
            }
    }
}
