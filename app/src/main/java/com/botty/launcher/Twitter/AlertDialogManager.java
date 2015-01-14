package com.botty.launcher.Twitter;

/**
 * Created by ivanbotty on 17/12/14.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.botty.launcher.R;

public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
            // Setting Dialog Message
        alertDialog.setMessage(message);
        if(status != null)
        // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.ic_launcher : R.drawable.ic_launcher);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}