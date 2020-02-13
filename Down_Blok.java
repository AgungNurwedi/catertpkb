package com.tpkb.mascater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Locale;

public class Down_Blok extends Activity {

        DatabaseHelper MyDB = new DatabaseHelper(this);
        Connection con2, con3, con4;
        ArrayList<DataBlok> dataBloks = new ArrayList<DataBlok>();
        ListAdapterBlok boxAdapter;
        ListView listViewBlok;
        ProgressBar proBar;
        Button buttabel, butbaru, buttambah, btnBad;
        EditText editsearch;

        /** Called when the activity is first created. */
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_down__blok);


                proBar = (ProgressBar) findViewById(R.id.Pbblok);
                proBar.setVisibility(View.VISIBLE);
                vwDataBlok();
                boxAdapter = new ListAdapterBlok(this, dataBloks);

                ListView lvMain = (ListView) findViewById(R.id.lvMain);
                lvMain.setAdapter(boxAdapter);
                lvMain.requestFocus();
                proBar.setVisibility(View.GONE);

                btnBad= (Button) findViewById(R.id.btn_Bad);
                btnBad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                TanyaIsiDSMLbad(view);
                        }
                });

                butbaru = (Button) findViewById(R.id.btn_dsml);
                butbaru.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                //showResult(view);
                                TanyaIsiDSML(view);
                        }
                });

                buttambah = (Button) findViewById(R.id.btn_cu);
                buttambah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                proBar.setVisibility(View.VISIBLE);
                                TanyaIsiDSMLcu(view);
                                proBar.setVisibility(View.GONE);

                        }
                });

                buttabel = (Button) findViewById(R.id.ButTable);
                buttabel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                                TanyaIsiMaster(view);


                        }
                });

                editsearch = (EditText) findViewById(R.id.search);
                editsearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable arg0) {
                                // TODO Auto-generated method stub
                                String textcari = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                                //     System.out.println("clear" + textcari );
                                boxAdapter.filter(textcari);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1,
                                                      int arg2, int arg3) {
                                // TODO Auto-generated method stub
                        }

                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                                // TODO Auto-generated method stub
                        }
                });
        }


        void vwDataBlok(){

                dataBloks.clear();

                Cursor LVBlok = MyDB.DispBlok();
                if(LVBlok.getCount() > 0 ) {
                        LVBlok.moveToFirst();
                        do{
                                dataBloks.add(
                                        new DataBlok(
                                        LVBlok.getString(0).toString(),
                                        LVBlok.getString(1).toString(),
                                        false));

                        } while (LVBlok.moveToNext());
                }
        }




        public void TanyaIsiMaster(View v)
        {

                AlertDialog.Builder builder = new AlertDialog.Builder( this );
                builder
                        .setIcon(R.drawable.tanyain)
                        .setTitle("Download Master Data")
                        .setMessage(
                                "Proses ini hanya sekali dilakukan," + " \n" + "\n"+
                                        "jika sudah data blok dan  " + " \n" +
                                        "data masalah  " + " \n" +
                                        "tidak perlu dilakukan lagi." + " \n" + "\n"+
                                        "Yakin dilanjutkan...???"
                        )
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                        proBar.setVisibility(View.VISIBLE);

                                        MasterData();

                                        // proBar.setVisibility(View.GONE);

                                }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                }
                        })
                        .show();
        }

        void MasterData() {
                final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                final String GloIPNya = globalVariable.getgIpNYA();
                final String GloPORTNya = globalVariable.getgportNYA();

                MyDB.Reset_Blok();
                new Si_Ws_DataBlok(this).execute(GloIPNya , GloPORTNya, "android",  "Sp_Blok_2020.php"  );
                MyDB.ApusTabelBAD();
                new Si_Ws_DataBad(this).execute(GloIPNya , GloPORTNya, "android",  "Sp_Bad_2020.php"  );
                proBar.setVisibility(View.GONE);
        }



        public void TanyaIsiDSML(View v)
        {
            new AlertDialog.Builder(Down_Blok.this)

                    .setIcon(R.drawable.tanyain)
                    .setTitle("Download Data DSML")

                    .setMessage("Anda yakin ...???" + " \n" + "\n"+
                            "Sudah di UPLOAD " + " \n" +
                            "Hasil CATER nya...!?!?!" + " \n" + "\n"+
                            "Data CATER sebelumnya " + " \n" +
                            "akan dihapus lhooo..???")

                    .setPositiveButton("Baru", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            proBar.setVisibility(View.VISIBLE);
                            DatabaseHelper MyDB = new DatabaseHelper(Down_Blok.this) ;
                            MyDB.ApusDSML();
                            MyDB.close();
                            isiDSML();
                            proBar.setVisibility(View.GONE);
                        }
                    })
                    .setNegativeButton("Tambah", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            proBar.setVisibility(View.VISIBLE);
                            isiDSML();
                            proBar.setVisibility(View.GONE);
                        }
                    })
                    .setNeutralButton("Batal", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                            //Toast.makeText(Down_Blok.this, "You Clicked on NO", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();

        }

        public void TanyaIsiDSMLbad(View v)
        {
                new AlertDialog.Builder(Down_Blok.this)

                        .setIcon(R.drawable.tanyain)
                        .setTitle("Download Data DSML")

                        .setMessage("Anda yakin ...???" + " \n" + "\n"+
                                "Sudah di UPLOAD " + " \n" +
                                "Hasil CATER nya...!?!?!" + " \n" + "\n"+
                                "Data CATER sebelumnya " + " \n" +
                                "akan dihapus lhooo..???")

                        .setPositiveButton("Baru", new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                        proBar.setVisibility(View.VISIBLE);
                                        DatabaseHelper MyDB = new DatabaseHelper(Down_Blok.this) ;
                                        MyDB.ApusDSML();
                                        MyDB.close();
                                        isiDSMLbad();
                                        proBar.setVisibility(View.GONE);
                                }
                        })
                        .setNegativeButton("Tambah", new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                        proBar.setVisibility(View.VISIBLE);
                                        isiDSMLbad();
                                        proBar.setVisibility(View.GONE);
                                }
                        })
                        .setNeutralButton("Batal", new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                        dialog.cancel();
                                        //Toast.makeText(Down_Blok.this, "You Clicked on NO", Toast.LENGTH_SHORT).show();
                                }
                        })
                        .show();

        }

        public void TanyaIsiDSMLcu(View v)
        {
                new AlertDialog.Builder(Down_Blok.this)

                        .setIcon(R.drawable.tanyain)
                        .setTitle("Download Data DSML")

                        .setMessage("Anda yakin ...???" + " \n" + "\n"+
                                "Sudah di UPLOAD " + " \n" +
                                "Hasil CATER nya...!?!?!" + " \n" + "\n"+
                                "Data CATER sebelumnya " + " \n" +
                                "akan dihapus lhooo..???")

                        .setPositiveButton("Baru", new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                        proBar.setVisibility(View.VISIBLE);
                                        DatabaseHelper MyDB = new DatabaseHelper(Down_Blok.this) ;
                                        MyDB.ApusDSML();
                                        MyDB.close();
                                        isiDSMLcu();
                                        proBar.setVisibility(View.GONE);
                                }
                        })
                        .setNegativeButton("Tambah", new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                        proBar.setVisibility(View.VISIBLE);
                                        isiDSMLcu();
                                        proBar.setVisibility(View.GONE);
                                }
                        })
                        .setNeutralButton("Batal", new DialogInterface.OnClickListener()
                        {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                        dialog.cancel();
                                        //Toast.makeText(Down_Blok.this, "You Clicked on NO", Toast.LENGTH_SHORT).show();
                                }
                        })
                        .show();

        }

        void isiDSML() {
                final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                final String GloIPNya = globalVariable.getgIpNYA();
                final String GloPORTNya = globalVariable.getgportNYA();
                final String GloIDNya = globalVariable.getgTR_IDNYA();
                for (DataBlok p : boxAdapter.getBox()) {
                        proBar.setVisibility(View.VISIBLE);
                        if (p.box){
                            new Si_Ws_SC_DSML_HP_2020(this).execute(
                                GloIPNya , GloPORTNya, "android",  "sc_dsml_hp_2020.php" , p.name, GloIDNya  );


                        }
                }

                Intent intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//
//                Intent intent = new Intent(this, Tr_Cater.class);
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Bundle b = new Bundle();
//                b.putString("xStatus", "0" );
//                intent.putExtras(b);
//                startActivity(intent);
        }

        void isiDSMLbad() {
                final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                final String GloIPNya = globalVariable.getgIpNYA();
                final String GloPORTNya = globalVariable.getgportNYA();
                final String GloIDNya = globalVariable.getgTR_IDNYA();
                for (DataBlok p : boxAdapter.getBox()) {
                        proBar.setVisibility(View.VISIBLE);
                        if (p.box){
                                new Si_Ws_SC_DSML_HP_2020(this).execute(
                                        GloIPNya , GloPORTNya, "android",  "sc_dsml_hp_2020_bad.php" , p.name, GloIDNya  );
                        }
                }

                Intent intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }

        void isiDSMLcu() {
                final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                final String GloIPNya = globalVariable.getgIpNYA();
                final String GloPORTNya = globalVariable.getgportNYA();
                final String GloIDNya = globalVariable.getgTR_IDNYA();
                for (DataBlok p : boxAdapter.getBox()) {
                        proBar.setVisibility(View.VISIBLE);
                        if (p.box){
                                new Si_Ws_SC_DSML_HP_2020(this).execute(
                                        GloIPNya , GloPORTNya, "android",  "sc_dsml_hp_2020_cu.php" , p.name, GloIDNya  );
                        }
                }

                Intent intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
}
