package appewtc.masterung.salesproduct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/9/15 AD.
 */
public class SalesTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String SALES_TABLE = "salesTABLE";
    public static final String COLUMN_ID_SALES = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMLN_LASTACCESS = "LastAccess";

    public SalesTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public String[] searchUser(String strUser) {

        try {

            String arrayDATA[] = null;
            Cursor objCursor = readSQLite.query(SALES_TABLE, new String[]{COLUMN_ID_SALES, COLUMN_USER, COLUMN_PASSWORD, COLUMN_NAME, COLUMN_STATUS, COLUMLN_LASTACCESS}, COLUMN_USER + "=?", new String[]{String.valueOf(strUser)}, null, null, null, null);

            if (objCursor != null) {

                if (objCursor.moveToFirst()) {

                    arrayDATA = new String[objCursor.getColumnCount()];

                    arrayDATA[0] = objCursor.getString(0);
                    arrayDATA[1] = objCursor.getString(1);
                    arrayDATA[2] = objCursor.getString(2);
                    arrayDATA[3] = objCursor.getString(3);
                    arrayDATA[4] = objCursor.getString(4);
                    arrayDATA[5] = objCursor.getString(5);

                }   // if2

            }   // if1

            objCursor.close();
            return arrayDATA;

        } catch (Exception e) {
            return null;
        }

        //return new String[0];
    }   // searchUser


    public long addValuetoSales(String strUser, String strPassword, String strName, String strStatus, String strLastAccess) {

        ContentValues objContentCalues = new ContentValues();
        objContentCalues.put(COLUMN_USER, strUser);
        objContentCalues.put(COLUMN_PASSWORD, strPassword);
        objContentCalues.put(COLUMN_NAME, strName);
        objContentCalues.put(COLUMN_STATUS, strStatus);
        objContentCalues.put(COLUMLN_LASTACCESS, strLastAccess);

        return writeSQLite.insert(SALES_TABLE, null, objContentCalues);
    }   // addValueToSales

}   // Main Class
