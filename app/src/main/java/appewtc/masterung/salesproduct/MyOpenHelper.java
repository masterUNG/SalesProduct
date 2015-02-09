package appewtc.masterung.salesproduct;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 2/9/15 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Sales.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_SALES_TABLE = "create table salesTABLE (_id integer primary key, "+" User text, Password text, Name text, Status text, LastAccess text);";
    private static final String CREATE_RESERVATION_TABLE = "";
    private static final String CREATE_PRODUCTS_TABLE = "";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SALES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}   // Main Class
