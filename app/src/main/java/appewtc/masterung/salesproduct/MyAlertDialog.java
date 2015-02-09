package appewtc.masterung.salesproduct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by masterUNG on 2/9/15 AD.
 */
public class MyAlertDialog {

    private AlertDialog.Builder objAlert;

    public void negativeDialog(Context context, String strTitle, String strMessage) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setTitle(strTitle);
        objAlert.setMessage(strMessage);
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objAlert.show();

    }

}   // Main Class
