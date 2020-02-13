package com.tpkb.mascater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private TextView mTextMessage;

    ImageView imgGood, imgBad, imgGoBad, imgAll;
    TextView  tGood, tBad, tGoBad, tAll;
    DatabaseHelper MyDB;
    ArrayList<DataDashboard> DataDashboards = new ArrayList<DataDashboard>();
    ListAdapterCater caterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imgGood = (ImageView)findViewById(R.id.iGood);
        imgBad = (ImageView)findViewById(R.id.iBad);
        imgGoBad= (ImageView)findViewById(R.id.iBlmCtt);
        imgAll= (ImageView)findViewById(R.id.iSemua);

        //  '0=All, 1=Good, 2=Bad, 3=BlmCtt'
        TextView tAll = (TextView) findViewById(R.id.tSemua);
        TextView tGood = (TextView) findViewById(R.id.tGood);
        TextView tBad = (TextView) findViewById(R.id.tBad);
        TextView tGoBad = (TextView) findViewById(R.id.tBlmCtt);
        Rupiah rcf = new Rupiah();
        MyDB = new DatabaseHelper(this);

        Cursor JmlAll = MyDB.JumlahDSML("0");
        int jAll = JmlAll.getCount() ;
        tAll.setText(String.format("%s Plg", rcf.toRupiahFormat(Integer.toString(jAll))));
        MyDB.close();

        Cursor JmlGood = MyDB.JumlahDSML("1");
        int jGood = JmlGood.getCount() ;
        tGood.setText(String.format("%s Plg", rcf.toRupiahFormat(Integer.toString(jGood))));
        MyDB.close();

        Cursor JmlBad = MyDB.JumlahDSML("2");
        int jBad = JmlBad.getCount() ;
        tBad.setText(String.format("%s Plg", rcf.toRupiahFormat(Integer.toString(jBad))));
        MyDB.close();

        Cursor JmlBlm = MyDB.JumlahDSML("3");
        int jBlm = JmlBlm.getCount() ;
        tGoBad.setText(String.format("%s Plg", rcf.toRupiahFormat(Integer.toString(jBlm))));
        MyDB.close();

        //  '0=All, 1=Good, 2=Bad, 3=BlmCtt'
        imgAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showResult(view);

                keCater("0");
            }
        });

        imgGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showResult(view);
                keCater("1");
            }
        });

        imgBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showResult(view);
                keCater("2");
            }
        });

        imgGoBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showResult(view);
                keCater("3");
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_download);
                    Intent intent1 = new Intent(getApplicationContext(),Down_Blok.class);   //Down_Blok
                    startActivity(intent1);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                     startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
   //                 mTextMessage.setText(R.string.title_upload);
                    return true;
            }
            return false;
        }
    };

    private void keCater(String xStatusnya ) {

        MyDB = new DatabaseHelper(this);
        MyDB.UpdateATUR("0",xStatusnya);
        MyDB.close();

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        globalVariable.setgStatusCaterNYA(xStatusnya);

        //  '0=All, 1=Good, 2=Bad, 3=BlmCtt'
        Intent intent = new Intent(this, Tr_Cater.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("xStatus", xStatusnya );
        intent.putExtras(b);
        startActivity(intent);

    }





    @Override
    public void onBackPressed() {
        keluar();
    }

    public void keluar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin Mau Keluar Aplikasi ...?")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }
}
