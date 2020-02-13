package com.tpkb.mascater;

/**
 * Created by Agung on 03-08-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignupActivity extends AsyncTask<String, Void, String> {

    TextView tv;
    private String ErrLog , us_id, us_pass;


    private Context context;
    //  private GlobalVar globalVariable = (GlobalVar) context;

    public SignupActivity(Context context) {
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
        String usernya = arg0[4];
        String passWord = arg0[5];
        us_pass = arg0[5];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {

            data = URLEncoder.encode(ipnya, "UTF-8");
            data += ":" + URLEncoder.encode(portnya, "UTF-8");
            data += "/" + URLEncoder.encode(pathnya, "UTF-8");
            data += "/" + URLEncoder.encode(phpnya, "UTF-8");
            data += "?uid=" + URLEncoder.encode(usernya, "UTF-8");
            data += "&pwd=" + URLEncoder.encode(passWord, "UTF-8");

            us_id = usernya;

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
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                System.out.println(query_result);
                int kiri = query_result.length();
                String okeh = query_result.substring(0, 7);
                String namapeg = query_result.substring(7, kiri);
                System.out.println(okeh);
                System.out.println(namapeg);
                System.out.println("ddddd"  );

                ErrLog = query_result ;
                //       if (query_result.substring(0, 7).equals("SUCCESS"))
                if (okeh.equals("SUCCESS"))  {
              //      Toast.makeText(context, "Login Sukses...", Toast.LENGTH_SHORT).show();


                    //        final GlobalVar globalVariable = (GlobalVar) context;
                    //        globalVariable.setLogged(namapeg);

                    Intent intent = new Intent(context, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Login Failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, ErrLog, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, ErrLog, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, ErrLog, Toast.LENGTH_SHORT).show();
        }
    }



}