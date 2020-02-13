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

public class Si_Ws_DataBad extends AsyncTask<String, Void, String> {

    TextView tv;
    private String ErrLog , us_id, us_pass;
    ArrayList<DataBlok> dataBloks = new ArrayList<DataBlok>();
    ListAdapterBlok boxAdapter;


    private Context context;
    //  private GlobalVar globalVariable = (GlobalVar) context;

    public Si_Ws_DataBad(Context context) {
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
                    String bad_kode,bad_nama ;

                    // fetch data from object
                    bad_kode = obj.get("bad_cd").getAsString();
                    bad_nama = obj.get("bad_nm").getAsString();

                    //dataBloks.add(new DataBlok( blok_kode, blok_nama , false));
                    // Store these values in list or objects you want
                    DatabaseHelper MyDB = new DatabaseHelper(context) ;
                    MyDB.Insert_Bad(bad_kode,bad_nama);
                    MyDB.close();
                }

        }
    }



}