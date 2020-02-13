package com.tpkb.mascater;

/*
 * Created by Agung on 03-08-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Si_Ws_DataBlok extends AsyncTask<String, Void, String> {


    private String ErrLog , us_id, us_pass;
    ArrayList<DataBlok> dataBloks = new ArrayList<DataBlok>();
    ListAdapterBlok boxAdapter;


    private Context context;
    //  private GlobalVar globalVariable = (GlobalVar) context;

    public Si_Ws_DataBlok(Context context) {
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

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {

            data = URLEncoder.encode(ipnya, "UTF-8");
            data += ":" + URLEncoder.encode(portnya, "UTF-8");
            data += "/" + URLEncoder.encode(pathnya, "UTF-8");
            data += "/" + URLEncoder.encode(phpnya, "UTF-8");
//            data += "?uid=" + URLEncoder.encode(usernya, "UTF-8");
//            data += "&pwd=" + URLEncoder.encode(passWord, "UTF-8");

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
                    // get your jsonobject
                    JsonObject obj = notesArr.get(i).getAsJsonObject();

                    // do the same for the rest of the elements like date , author ,authorId
                    String blok_kode,blok_nama ;

                    // fetch data from object
                    blok_kode = obj.get("blok_cd").getAsString();
                    blok_nama = obj.get("blok_nm").getAsString();

                    //dataBloks.add(new DataBlok( blok_kode, blok_nama , false));
                    // Store these values in list or objects you want
                    DatabaseHelper MyDB = new DatabaseHelper(context) ;


                    MyDB.Insert_Blok(blok_kode,blok_nama);
              //      System.out.println(blok_kode);
              //      System.out.println(blok_nama);

                    MyDB.close();
                }

        }
    }



}