package com.tpkb.mascater;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.util.ArrayList;

import static com.tpkb.mascater.R.menu.menu_main_cater;

public class Tr_Cater extends AppCompatActivity
{

    Connection conDSML ;
    String DIRApp = Constants.DIRApp;
    String APPName = Constants.AppName;
    String Servernya, ipnya    ;
    DatabaseHelper MyDB ;
    ArrayList<DataCater> DataCaters = new ArrayList<DataCater>();
    ListAdapterCater caterAdapter;
    ListView listViewSurvey;
    ImageView imgPhotoSurvey;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_cater);
        inputSearch = (EditText) findViewById(R.id.menuSearch);

        //-- Define DSML
        Intent in = getIntent();
        String xStatus = (in.getStringExtra(("xStatus"))) ;
    //    String xPwd = (in.getStringExtra(("xPwd"))) ;

   //     System.out.println(xUid );
  //     System.out.println(xPwd );

     //   MyDB = new DatabaseHelper(this);
     //   MyDB.Insert_User(xUid,xPwd);

        //  '0=All, 1=Good, 2=Bad, 3=BlmCtt'
        MyDB = new DatabaseHelper(this);
        MyDB.UpdateATUR("0",xStatus);
        Segerin();


    }

    public  void Segerin( )
    {
        Constants.Posisi_Awal = 1;
        // inputSearch = (EditText) findViewById(R.id.menuSearch);

        listViewSurvey = (ListView)findViewById(R.id.lstsurvey);
        imgPhotoSurvey = (ImageView)findViewById(R.id.photoSurvey);

        DataCaters.clear();
        listViewSurvey.setAdapter(null);

        ArrayList<String> rowsurvey = new ArrayList<>();

        Cursor data = MyDB.DispCater();
        if(data.getCount() == 0 ) {
            Toast.makeText(Tr_Cater.this , "Data blm/tidak ada...!!!" , Toast.LENGTH_LONG).show();
        }else
        {
            int tandanya ;
            //data.moveToFirst();
            while (data.moveToNext())
            { if      (data.getString(0).equals("0"))   {   tandanya = R.drawable.good;
            } else if (data.getString(0).equals("X"))   {   tandanya = R.drawable.gobad;
            } else                                      {   tandanya = R.drawable.bad; }
                DataCaters.add(
                        new DataCater(          tandanya,
                                data.getString(1),      data.getString(2),      data.getString(3),
                                data.getString(4),      data.getString(5),      data.getString(6),
                                data.getString(7),      data.getString(8),      data.getString(9),
                                data.getString(10),     data.getString(11),     data.getString(12),
                                data.getString(13),     data.getString(14),     data.getString(15),
                                data.getString(16),     data.getString(17),     data.getString(18),
                                data.getString(19),     data.getString(20),     data.getString(21),
                                data.getString(22),     data.getString(23)
                                    )
                                );

//                              int imagesur,
//                              String  data_ID,                    String data_Nopel,                  String data_Nama,
//                              String data_Alamat,                 String data_Tarif_CD,               String data_No_HP,
//                              String data_Nometer,                String data_Lat_CD,                 String data_Long_CD,
//                              String data_Bad_CD,                 String data_Awal,                   String data_Akhir,
//                              String data_Kubik,                  String data_Nominal,                String data_Piutang,
//                              String data_Total,                  String data_Digit,                  String data_Minim,
//                              String data_tgal_Catat )


//                              "select SUBSTR(Bad_CD,1,1) as GoBad,
//                              TR_ID,                              Nopel,                              Nama,
//                              Alamat,                             Tarif_CD,                           No_HP,
//                              Nometer, " +                        Lat_CD,                             Long_CD,
//                              Bad_CD,                             Awal,                               Akhir,
//                              Kubik,                              Nominal,                            Piutang,
//                              Total,                              Digit,                              Minim, " +
//                        " strftime('%Y%m%d', Tr_Date) as Tr_Date ,
//                              Tarif1,                             Tarif2,                             Angsuran,
//                              Administrasi from " +

            }

            MyDB.close();
        }

        caterAdapter = new ListAdapterCater(this, DataCaters);

        listViewSurvey.setAdapter(caterAdapter);
        listViewSurvey.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewSurvey.setTextFilterEnabled(true);
        listViewSurvey.setLongClickable(true);

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        final int GloPosisi = globalVariable.getgPosisiNYA();
        System.out.println("awalan ccc " + GloPosisi);
        listViewSurvey.setSelection(GloPosisi);

        listViewSurvey.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView vTRID = (TextView) view.findViewById(R.id.ltxtrid);
                TextView vnopel = (TextView) view.findViewById(R.id.ltxnopel);
                TextView vnama = (TextView) view.findViewById(R.id.ltxnama);
                TextView valamat = (TextView) view.findViewById(R.id.ltxalamat);
                TextView vtarif = (TextView) view.findViewById(R.id.ltxtarif_cd);
                TextView vNoHP = (TextView) view.findViewById(R.id.ltxtarif_nohp);
                TextView vLat = (TextView) view.findViewById(R.id.ltxalat);
                TextView vLong = (TextView) view.findViewById(R.id.ltxalong);
                TextView vNomet = (TextView) view.findViewById(R.id.ltxnomet);
                TextView vAwal = (TextView) view.findViewById(R.id.ltxawal);
                TextView vAkhir = (TextView) view.findViewById(R.id.ltxakhir);
                TextView vKubik = (TextView) view.findViewById(R.id.ltxkubik);
                TextView vNominal = (TextView) view.findViewById(R.id.ltxnominal);
                TextView vPiutang = (TextView) view.findViewById(R.id.ltxpiutang);
                TextView vTotal = (TextView) view.findViewById(R.id.ltxtotal);
                TextView vBadcd = (TextView) view.findViewById(R.id.ltxbad_cd);
                TextView vDigit = (TextView) view.findViewById(R.id.ltxDigit);
                TextView vMinim = (TextView) view.findViewById(R.id.ltxMinim);
                TextView vtgalctt = (TextView) view.findViewById(R.id.ltxtgalctt);

                TextView vtarif1 = (TextView) view.findViewById(R.id.ltxtarif1);
                TextView vtarif2 = (TextView) view.findViewById(R.id.ltxtarif2);
                TextView vangsuran  = (TextView) view.findViewById(R.id.ltxangsuran);
                TextView vadministrasi = (TextView) view.findViewById(R.id.ltxtadminstrasi);



                String pilTRID = vTRID.getText().toString();
                String pilNopel = vnopel.getText().toString();
                String pilNama = vnama.getText().toString();
                String pilAlamat = valamat.getText().toString();
                String pilTarif = vtarif.getText().toString();
                String pilNOHP = vNoHP.getText().toString();
                String pilLat = vLat.getText().toString();
                String pilLong = vLong.getText().toString();
                String pilnomet = vNomet.getText().toString();
                String pilAwal = vAwal.getText().toString();
                String pilAkhir = vAkhir.getText().toString();
                String pilKubik = vKubik.getText().toString();
                String pilNominal = vNominal.getText().toString();
                String pilPiutang = vPiutang.getText().toString();
                String pilTotal = vTotal.getText().toString();
                String pilBad_CD = vBadcd.getText().toString();
                String pilDigit = vDigit.getText().toString();
                String pilMinim = vMinim.getText().toString();
                String pilTgalCtt = vtgalctt.getText().toString();

                String pilTarif1 = vtarif1.getText().toString();
                String pilTarif2 = vtarif2.getText().toString();
                String pilAngsuran = vangsuran.getText().toString();
                String pilAdministrasi = vadministrasi.getText().toString();

                System.out.println("awalan " + position);
                System.out.println("Tanggal catat NYA : " + pilTgalCtt);
                final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                globalVariable.setgPosisiNYA(position);

                Intent myIntent = new Intent(Tr_Cater.this, PhotoCater.class);
                myIntent.putExtra("TR_ID", pilTRID);
                myIntent.putExtra("Nopel", pilNopel);
                myIntent.putExtra("Nama", pilNama);
                myIntent.putExtra("Alamat", pilAlamat);
                myIntent.putExtra("Tarif_CD", pilTarif);
                myIntent.putExtra("No_HP", pilNOHP);
                myIntent.putExtra("Lat_CD", pilLat);
                myIntent.putExtra("Long_CD", pilLong);
                myIntent.putExtra("NoMet", pilnomet);
                myIntent.putExtra("Awal", pilAwal);
                myIntent.putExtra("Akhir", pilAkhir);
                myIntent.putExtra("Kubik", pilKubik);
                myIntent.putExtra("Nominal", pilNominal);
                myIntent.putExtra("Piutang", pilPiutang);
                myIntent.putExtra("Total", pilTotal);
                myIntent.putExtra("Bad_CD", pilBad_CD);
                myIntent.putExtra("Digit", pilDigit);
                myIntent.putExtra("Minim", pilMinim);
                myIntent.putExtra("TgalCtt", pilTgalCtt);
                myIntent.putExtra("Tarif1", pilTarif1);
                myIntent.putExtra("Tarif2", pilTarif2);
                myIntent.putExtra("Angsuran", pilAngsuran);
                myIntent.putExtra("Administrasi", pilAdministrasi);

                System.out.println("kiriiiiiiiiiiiiiiiiiiiimmmm");
                System.out.println(pilAdministrasi);
                System.out.println(pilMinim);
                System.out.println(pilPiutang);
                System.out.println(pilNama);

                startActivity(myIntent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(menu_main_cater , menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.menuSearch);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                caterAdapter.filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println( id );

        if (id == R.id.action_All) {
            MyDB.UpdateATUR("0","0");
            Segerin();
        }

        if (id == R.id.action_Good) {
            MyDB.UpdateATUR("0","1");
            Segerin();
        }

        if (id == R.id.action_Bad) {
            MyDB.UpdateATUR("0","2");
            Segerin();
        }

        if (id == R.id.action_BlmCtt) {
            MyDB.UpdateATUR("0","3");
            Segerin();
        }



        if (id == R.id.action_download) {
            Intent intent1 = new Intent(getApplicationContext(),Down_Blok.class);   //Down_Blok
            startActivity(intent1);
            //return true;
        }



        if (id == R.id.action_Upload) {

       //     Intent intent1 = new Intent(getApplicationContext(),UploadCater.class);   //Down_Blok
       //     startActivity(intent1);
            //return true;

//            UploadDSML uploadDSML = new UploadDSML();
  //          uploadDSML.execute("");

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        Balik();
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

    public void Balik(){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}
