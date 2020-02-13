package com.tpkb.mascater;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.substring;


public class UploadCater extends AppCompatActivity {

    private int cTahun , cBulan , cTangal ;

    Connection conDSML ;
    TextView txtinfo, tgalLPP, vtglLPP ;
    ProgressBar pbbar;
    DatabaseHelper MyDB;
    Button keCM , keCU, keCT;
    String MaxPBnya ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcater);

        txtinfo = (TextView) findViewById(R.id.txtinfo);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        keCM= (Button) findViewById(R.id.butCM);
        keCU= (Button) findViewById(R.id.butCU);
        keCT= (Button) findViewById(R.id.butDSML);
        vtglLPP = (TextView)findViewById(R.id.vtglLPP);
        tgalLPP = (TextView) findViewById(R.id.TangalLPP);
        MyDB = new DatabaseHelper(this);


        String Tgal_Sys =   new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(new Date())  ;
        String Tgl_Sys = new SimpleDateFormat("yyyyMMdd",
                 Locale.getDefault()).format(new Date())  ;

        vtglLPP.setText(Tgl_Sys);
        tgalLPP.setText(Tgal_Sys);

        keCM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder( UploadCater.this );
                builder
                        .setMessage("Yakin Data sudah benar...?" + " \n"
                        )
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

//                                UploadPhotoCater my = new UploadPhotoCater();
//                                my.execute("");
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
        });

        keCU.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder( UploadCater.this );
                builder
                        .setMessage("Yakin Data sudah benar...?" + " \n"
                        )
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

//                                UploadPhotoCU my = new UploadPhotoCU();
//                                my.execute("");
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
        });

        keCT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder( UploadCater.this );
                builder
                        .setMessage("Yakin Data sudah benar...?" + " \n"
                        )
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                UploadDataCater updData = new UploadDataCater();
                                updData.execute("");
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
        });

        tgalLPP.setOnLongClickListener(
                new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub
                        if (v == tgalLPP) {

                            final Calendar tanggalan  = Calendar.getInstance();
                            cTahun = tanggalan.get(Calendar.YEAR);
                            cBulan = tanggalan.get(Calendar.MONTH);
                            cTangal = tanggalan.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(UploadCater.this,
                                    new DatePickerDialog.OnDateSetListener()
                                    {
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                            monthOfYear = monthOfYear + 1;
                                            tgalLPP.setText(dayOfMonth+"-"+ monthOfYear +"-"+ year   );

                                            String ccl = "0" + String.valueOf(monthOfYear);
                                            String tbBul = ccl.substring(ccl.length() - 2, ccl.length());

                                            String ccl1 = "0" + String.valueOf(dayOfMonth);
                                            String tbTgl = ccl1.substring(ccl1.length() - 2, ccl1.length());
                                            System.out.println(ccl );
                                            System.out.println(ccl1 );
                                            System.out.println(tbBul );
                                            System.out.println(tbTgl );

                                            vtglLPP.setText(year +""+ tbBul +""+ tbTgl);
                                            System.out.println(cTahun );
                                            System.out.println(cBulan );
                                            System.out.println(cTangal );
                                            fillDataLPP(year +""+ tbBul +""+ tbTgl);

                                        }
                                    } , cTahun, cBulan, cTangal );
                            datePickerDialog.show();
                        }
                        return true;
                    }
                });
    }

    void fillDataLPP(String TangalLPPnya) {

        vtglLPP.setText(TangalLPPnya);
        System.out.println(TangalLPPnya);
        System.out.println(substring(TangalLPPnya,6,8));
        System.out.println(substring(TangalLPPnya,4,6));
        System.out.println(substring(TangalLPPnya,0,4));
        tgalLPP.setText( (  substring(TangalLPPnya,6,8) + '-' +
                substring(TangalLPPnya,4,6) + '-' +
                substring(TangalLPPnya,0, 4) )  );
    }

    private void Konfirmasinya(String TgCatatnya, String KodeHP2) {
        final String tglCtt = TgCatatnya;
        final String HpId = KodeHP2;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfimasi....");
        builder.setMessage("Yakin data sudah benar...???");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Cursor dataDSML = MyDB.UploadDSML(tglCtt);
                if(dataDSML.getCount() == 0 ) {
                    Toast.makeText(UploadCater.this , "Data blm/tidak ada...!!!" , Toast.LENGTH_LONG).show();
                }else
                {
                    while (dataDSML.moveToNext())
                    {


                        String query1 = "BKS01.dbo.SI_Tr_Andro_2017 '" + HpId  + "','" +
                                dataDSML.getString(0) + "', '" +
                                dataDSML.getString(1) + "', '" +
                                dataDSML.getString(2) + "', " +
                                dataDSML.getInt(3) + ", '" +
                                dataDSML.getString(4) + "', '" +
                                dataDSML.getString(5) + "'" ;



                    }
                }

                //new UploadCater().Isi_DSMLnya.execute( PhpNYA1 , NYA1, NYA2, NYA3, NYA4, NYA5, NYA6 , NYA5, NYA6 );


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private class Isi_DSMLnya extends AsyncTask<String, String, JSONObject>
    {
        JSONParser jParser = new JSONParser();
        private JSONObject json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {

            //	namainfo, alamatinfo, hpinfo, inform,
            String cphpnya = args[0];
            String cpm_cd = args[1];
            String ctglcatat = args[2];
            String cnopel 	= args[3];
            String cnohp = args[4];
            String cakhir = args[5];
            String cbad_cd = args[6];
            String clat_cd = args[7];
            String clong_cd = args[8];
            String data;
            String link;
            //plgModels.clear();
            try {
                data =  cphpnya ;
                data += "?pm_cd=" + URLEncoder.encode(cpm_cd, "UTF-8");
                data += "&tglcatat=" + URLEncoder.encode(ctglcatat, "UTF-8");
                data += "&nopel=" + URLEncoder.encode(cnopel, "UTF-8");
                data += "&nohp=" + URLEncoder.encode(cnohp, "UTF-8");
                data += "&akhir=" + URLEncoder.encode(cakhir, "UTF-8");
                data += "&bad_cd=" + URLEncoder.encode(cbad_cd, "UTF-8");
                data += "&lat_cd=" + URLEncoder.encode(clat_cd, "UTF-8");
                data += "&long_cd=" + URLEncoder.encode(clong_cd, "UTF-8");

                link = data;

                System.out.println("6666666");
                System.out.println(link);
                json = jParser.getJSONFromUrl(link );

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json)
        {

            pbbar.setVisibility(View.GONE);


        }
    }



    public class UploadDataCater extends AsyncTask<String, String, String>
    {
        String z = "Updating Data Cater...";

        Boolean isSuccess = false;

        //MyDB = new DatabaseHelper(this);

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        final String GloIdNya = globalVariable.getgIdUser();
        final String GloPassNya = globalVariable.getgPassUser();
        final String GloServerNya = globalVariable.getgServerNYA();
        final String GloIPNya = globalVariable.getgIpNYA();
        final String GloTabulNya = globalVariable.getgTabulNYA();

        String ip2 = MyDB.getPanel("001");
        String port2 = MyDB.getPanel("002");
        String db2 = MyDB.getPanel("003");
        String KodeHP2 = MyDB.getPanel("006");
        String dbinstan2 = MyDB.getPanel("004");
        String UsNetwork2 = globalVariable.getgIdUser(); //= MyDB.getPanel("011");
        String PassNetwork2 = globalVariable.getgPassUser(); //MyDB.getPanel("012");
        String ipnya2 = ip2 + ":" + port2 + "/" + db2   ;
        String Servernya2 = dbinstan2 ;

        //String usernam = username.getText().toString();
        //String passwordd = password.getText().toString();

        @Override
        protected void onPreExecute()
        {
            z = "Updating Data Cater...";
            pbbar.setVisibility(View.VISIBLE);
            Cursor CdataDSML = MyDB.DispDSML();
            Integer MaxPB =  CdataDSML.getCount() ;
            MaxPBnya = MaxPB.toString();
            pbbar.setMax(CdataDSML.getCount());
        }


        @Override
        protected String doInBackground(String... params)
        {

            try
            {

                    Cursor dataDSML = MyDB.DispDSML();
                    if(dataDSML.getCount() == 0 ) {
                        Toast.makeText(UploadCater.this , "Data blm/tidak ada...!!!" , Toast.LENGTH_LONG).show();
                    }else
                    {
                        while (dataDSML.moveToNext())
                        {


                            String query1 = "BKS01.dbo.SI_Tr_Andro_2017 '" + KodeHP2 + "','" +
                                    dataDSML.getString(0) + "', '" +
                                    dataDSML.getString(1) + "', '" +
                                    dataDSML.getString(2) + "', " +
                                    dataDSML.getInt(3) + ", '" +
                                    dataDSML.getString(4) + "', '" +
                                    dataDSML.getString(5) + "', '" +
                                    dataDSML.getString(6) + "'" ;

                            System.out.println( query1);
                            z = dataDSML.getString(0);
                            String num = String.valueOf(dataDSML.getPosition());
                            publishProgress(num);

                            PreparedStatement preparedStatement = conDSML.prepareStatement(query1);
                            preparedStatement.executeUpdate();
                            //preparedStatement.setEscapeProcessing(true);

                        }
                    }

                    System.out.println("iiiiii");
                    z = "Update Data Cater successfully";
                    conDSML.close();
                    MyDB.close();


            }
            catch (Exception ex)
            {
                isSuccess = false;

                z = ex.getMessage();
            }
            //System.out.println(z);
            return z;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtinfo.setText(z + " ( " + values[0] + " / " + MaxPBnya   +  " )");
            pbbar.setProgress(Integer.parseInt(values[0]));

        }

        @Override
        protected void onPostExecute(String r)
        {
            txtinfo.setText(r);
            pbbar.setVisibility(View.GONE);
        }

    }



    void uploading(String filenya) {
        File imagefile = new File(filenya);
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);
        MultipartBody.Part partImage =
                MultipartBody.Part.createFormData("imageupload", imagefile.getName(),reqBody);

        ApiServices api = RetroClient.getApiServices();
        Call<ResponseApiModel> upload = api.uploadImage(partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @Override
            public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
                //pd.dismiss();
                Log.d("RETRO", "ON RESPONSE  : " + response.body().toString());

                if(response.body().getKode().equals("1"))
                {
                    Toast.makeText(UploadCater.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(UploadCater.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                //pd.dismiss();
            }
        });
    }


}
