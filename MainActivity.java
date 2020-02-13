package com.tpkb.mascater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity

{

    DatabaseHelper MyDB ;
    Button login, OK ;
    EditText username,password ,password1;
    ProgressBar progressBar;
    String Servernya, ipnya, portnya    , ipPortnya , iptxt   ,  url , us_id , us_pass, pathnya, phpnya ;
    String IPOnOff,  PortOnOff, OnOFFLine;
    int kiri  ;
    TextView Versiku, tv ;
    ImageView iView;
    CheckBox mCbShowPwd;
    Switch swoffon1 ;

    protected static final long DOUBLE_CLICK_MAX_DELAY = 1000L;
    private long thisTime = 0;
    private long prevTime = 0;
    private boolean firstTap = true;


    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    String cversi = "Cater Versi :" ;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PermissionHelper.hasCameraPermission(this)) {
        } else {
            PermissionHelper.requestCameraPermission (this ,true);
        }

        if (PermissionHelper.hasWriteStoragePermission(this)) {

        } else {
            PermissionHelper.requestWriteStoragePermission(this );
        }

        if (PermissionHelper.hasLocatioPermission(this)) {

        } else {
            PermissionHelper.requestLocationPermission(this );
        }

/*
        if (Build.VERSION.SDK_INT > 19)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
*/

        login = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        iView = (ImageView) findViewById(R.id.imgIcon);
        Versiku = (TextView) findViewById(R.id.Versiku);
        Versiku.setText(cversi  +   versionName + "." + versionCode );
        swoffon1 = (Switch) findViewById(R.id.swoffon);

//
//        mCbShowPwd = (CheckBox) findViewById(R.id.chkPassword);
//        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!isChecked) {
//                    // show password
//                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                } else {
//                    // hide password
//                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                }
//            }
//        });

        iView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialogpass, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //  result1.setText(userInput.getText());
                                        String user_text = (userInput.getText()).toString();
                                        if ((user_text.toUpperCase().equals("A603NG")))
                                        {
                                            Intent intent = new Intent(getApplicationContext(),Panel_List.class);
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            dialog.cancel();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                //Intent intent = new Intent(getApplicationContext(),Panel_List.class);
                //startActivity(intent);

            }
        });

//        isInternetOn();
    }

    public void signup(View v) {


        // progressBar.setVisibility(View.GONE);

        // Declaring Server ip, username, database name and password
        MyDB = new DatabaseHelper(this);
        String ip1 = MyDB.getPanel("001");
        String port1 = MyDB.getPanel("002");
        String db1 = MyDB.getPanel("003");
        String dbinstan1 = MyDB.getPanel("004");
        String HAPEid = MyDB.getPanel("006");
        String ip2 = MyDB.getPanel("014");
        String port2 = MyDB.getPanel("015");

        IPOnOff = ip1 ;
        PortOnOff = port1 ;
        OnOFFLine = "0";
        if (swoffon1.isChecked()) {
            isInternetOn();
            IPOnOff = ip2 ;
            PortOnOff = port2 ;
            OnOFFLine = "1";
        }

        ipPortnya =   IPOnOff + ":" + PortOnOff ; //+ "/" + db1   ;
        Servernya = dbinstan1 ;
        ipnya = IPOnOff ;
        portnya = PortOnOff  ;
        kiri = ipnya.length() - 1 ;
        iptxt = ipnya.substring(0, kiri);
        us_id = username.getText().toString();
        us_pass = password.getText().toString();
        pathnya  =   "absen"  ;
        phpnya  =   "wsdbconn.php"  ;

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        globalVariable.setgIpNYA(ipnya);
        globalVariable.setgportNYA(portnya);
        globalVariable.setgPathNYA(pathnya);
        globalVariable.setgTR_IDNYA(us_id);
        globalVariable.setgIdUser(HAPEid);
        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();

        if (swoffon1.isChecked()) {
            isInternetOn();
            ONLine();
        } else {
            OffLine();
        }

        //  Update status On/Offline
        MyDB.UpdatePanel( "000","0=Offline , 1=Online","0",OnOFFLine,"") ;
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " Internet Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    void OffLine()  {
        MyDB = new DatabaseHelper(this);
        System.out.println("offline");
        String xUSER = us_id ;
        String xPass = MyDB.getPassword(us_id)  ;
        String xNAMA = MyDB.getNm_US(us_id)  ;

        System.out.println(us_id);
        System.out.println(xPass);
        System.out.println(xNAMA);

        if (xPass.equals(us_pass)){

            System.out.println("OFFLINEEEEEEEE");
            Toast.makeText(this, "Login Sukses...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, MenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }

    void ONLine()  {

        new SignupActivity(this).execute(ipnya , portnya, pathnya,  phpnya, us_id, us_pass );

        //  Ganti User Aktif Device
        MyDB = new DatabaseHelper(this);
        MyDB.Insert_User(us_id,us_pass);

    }


}
