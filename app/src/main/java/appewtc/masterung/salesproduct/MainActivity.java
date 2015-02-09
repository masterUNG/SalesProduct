package appewtc.masterung.salesproduct;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    private SalesTABLE objSalesTABLE;
    private EditText edtUser, edtPassword;
    private String strUserChoose, strPasswordChoose, strPasswordTrue, strName, strStatus, strLastAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidget();

        objSalesTABLE = new SalesTABLE(this);

        //Tester AddValue
       // objSalesTABLE.addValuetoSales("user", "passsword", "name", "status", "lastaccess");

        //Delete All SQLite
        deleteAllSQLite();

        //Syn JSON to SQLite
        synJSONtoSQLite();

    }   // onCreate

    private void bindWidget() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
    }

    public void clickLogin(View view) {

        strUserChoose = edtUser.getText().toString().trim();
        strPasswordChoose = edtPassword.getText().toString().trim();

        if (strUserChoose.equals("") || strPasswordChoose.equals("") ) {

            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.negativeDialog(MainActivity.this, "Have Space", "Please Fill All Blank");

        } else {

            checkUser();

        }




    }   //clickLogin

    private void checkUser() {

        try {

            String arrayDATA[] = objSalesTABLE.searchUser(strUserChoose);
            strPasswordTrue = arrayDATA[2];
            strName = arrayDATA[3];
            strStatus = arrayDATA[4];
            strLastAccess = arrayDATA[5];

            checkPassword();


        } catch (Exception e) {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.negativeDialog(MainActivity.this, "No User", "No this User in my Database");
        }

    }   // checkUser

    private void checkPassword() {

        if (strPasswordChoose.equals(strPasswordTrue)) {

            checkStatus();

        } else {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.negativeDialog(MainActivity.this, "Password False", "Password Incorrect");
        }

    }   // checkPassword

    private void checkStatus() {

        int intStatus = Integer.parseInt(strStatus);
        switch (intStatus) {
            case 0:
                MyAlertDialog objMyAlert = new MyAlertDialog();
                objMyAlert.negativeDialog(MainActivity.this, "Contact Admin", "Please contact your administrator");
                break;
            case 1:

                Intent objIntent = new Intent(MainActivity.this, ServiceActivity.class);
                objIntent.putExtra("Name", strName);
                startActivity(objIntent);
                finish();

                break;
        }

    }   // checkStatus

    public void clickClear(View view) {

        edtUser.setText("");
        edtPassword.setText("");

    }   //clickClear

    private void deleteAllSQLite() {

        SQLiteDatabase objSQLite = openOrCreateDatabase("Sales.db", MODE_PRIVATE, null);
        Cursor objCursor = objSQLite.rawQuery("SELECT * FROM salesTABLE", null);
        objSQLite.delete("salesTABLE", null, null);

    }   // deleteAllSQlite

    private void synJSONtoSQLite() {

        //Setup New Policy
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }

        InputStream objInputStream = null;
        String strJSON = "";

        //Create InputStream
        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/poy/php_get_data_poy.php");
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {
            Log.d("poy", "InputStream ==> " + e.toString());
        }



        //Create strJSON
        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder strBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null ) {
                strBuilder.append(strLine);
            }

            objInputStream.close();
            strJSON = strBuilder.toString();

        } catch (Exception e) {
            Log.d("poy", "strJSON ==> " + e.toString());
        }


        //Up JSON to SQLite
        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int i = 0; i < objJSONArray.length(); i++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);

                String strUser = objJSONObject.getString("id");
                String strPassword = objJSONObject.getString("password");
                String strName = objJSONObject.getString("name_of_sale");
                String strStatus = objJSONObject.getString("status");
                String strAccess = objJSONObject.getString("last_access");

                long myInsert = objSalesTABLE.addValuetoSales(strUser, strPassword, strName, strStatus, strAccess);

            } // for

        } catch (Exception e) {
            Log.d("poy", "up SQLite ==> " + e.toString());
        }





    }   // sybJSONtoSQLite


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
