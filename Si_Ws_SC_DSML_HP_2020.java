package com.tpkb.mascater;

/**
 * Created by Agung on 03-08-2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Si_Ws_SC_DSML_HP_2020 extends AsyncTask<String, Void, String> {

    TextView tv;
    private String ErrLog , us_id, us_pass;
    ArrayList<DataBlok> dataBloks = new ArrayList<DataBlok>();
    ListAdapterBlok boxAdapter;


    private Context context;
    //  private GlobalVar globalVariable = (GlobalVar) context;

    public Si_Ws_SC_DSML_HP_2020(Context context) {
        this.context = context;

        //  this.context = context.getApplicationContext();
    }

    protected void onPreExecute() {


    }

    @Override
    protected String doInBackground(String... arg0) {

        String ipnya= arg0[0];
        String portnya = arg0[1];
        String pathnya = arg0[2];
        String phpnya = arg0[3];
        String nopelnya = arg0[4];
        String hapeidnya = arg0[5];

        String link;
        String data ;
        BufferedReader bufferedReader;
        String result;

        try {

            data = URLEncoder.encode(ipnya, "UTF-8");
            data += ":" + URLEncoder.encode(portnya, "UTF-8");
            data += "/" + URLEncoder.encode(pathnya, "UTF-8");
            data += "/" + URLEncoder.encode(phpnya, "UTF-8");
            data += "?nopel=" + URLEncoder.encode(nopelnya, "UTF-8");
            data += "&hapeid=" + URLEncoder.encode(hapeidnya, "UTF-8");

            link = "http://" + data;
            System.out.println(link);
            URL url = new URL(link);
            System.out.println("11111111111");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            System.out.println("2222222222222");
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            System.out.println("333333333333");
            return result  ;
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result ) {

        String jsonStr = result;

        if (jsonStr != null)
        {
            System.out.println("ccccc");
            System.out.println(jsonStr);

                JsonParser parser  = new JsonParser();
                JsonElement notes  = parser.parse(jsonStr);
                JsonArray notesArr = notes.getAsJsonArray();
                for (int i = 0; i < notesArr.size(); i++) {

                    String rsNopel ,rsNama , rsAlamat , rsTarif_CD, rsNo_HP, rsMeter_No, rsLat_CD, rsLong_CD ,rshapeid, rstabul;
                    int rsTarif1, rsTarif2, rsTarif3, rsTarif4 , rsAwal,rsAdministrasi, rsSewaMeter;
                    int rsAngsuran,  rsPiutang , rsDigit, rsMinim;

                    // get your jsonobject
                    JsonObject obj = notesArr.get(i).getAsJsonObject();

                    // fetch data from object
                    rshapeid = obj.get("hapeid").getAsString();
                    rstabul = obj.get("tabul").getAsString();
                    rsNopel = obj.get("nopel").getAsString();
                    rsNama = obj.get("nama").getAsString();
                    rsAlamat = obj.get("alamat").getAsString();
                    rsTarif_CD = obj.get("tarif_cd").getAsString();
                    rsNo_HP = obj.get("no_hp").getAsString();
                    rsTarif1 = obj.get("tarif1").getAsInt() ;
                    rsTarif2 = obj.get("tarif2").getAsInt() ;
                    rsTarif3 = obj.get("tarif3").getAsInt() ;
                    rsTarif4 = obj.get("tarif4").getAsInt() ;
                    rsMeter_No = obj.get("meter_no").getAsString();
                    rsAwal = obj.get("awal").getAsInt() ;
                    rsAdministrasi = obj.get("administrasi").getAsInt() ;
                    rsSewaMeter = obj.get("sewameter").getAsInt() ;
                    rsAngsuran = obj.get("angsuran").getAsInt() ;
                    rsPiutang = obj.get("piutang").getAsInt() ;
                    rsLat_CD = obj.get("lat_cd").getAsString();
                    rsLong_CD = obj.get("long_cd").getAsString();
                    rsDigit = obj.get("digit").getAsInt() ;
                    rsMinim = obj.get("minim").getAsInt() ;

                    DatabaseHelper MyDB = new DatabaseHelper(context) ;
                    MyDB.insertDSML(new DSML_Andro(
                            rstabul, rsNopel  , rsNama , rsAlamat ,  rsTarif_CD , rsNo_HP ,
                            rsTarif1 , rsTarif2 , rsTarif3 , rsTarif4 , rsMeter_No ,
                            rsAwal , rsAdministrasi , rsSewaMeter , rsAngsuran,
                            rsPiutang, rsLat_CD,  rsLong_CD , rshapeid, "XX", rsDigit, rsMinim ));
                    System.out.println("okeh.......");


                    MyDB.close();
                }

        }
    }



}