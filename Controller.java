package com.tpkb.mascater;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class Controller extends Application {

//http://114.141.55.163

    private String gPHPNYA = "http://114.141.55.163:5473/simpati/ws_intipasi.php?kode=";
    private String gPHPNYA01 = "http://103.217.217.186:5473/simpati/ws_intipasi.php?kode=";

    public String getgPHPNYA() { return gPHPNYA; }
    public void setgPHPNYA(String gNamaNYA) { this.gPHPNYA = gPHPNYA; }

    public String getgPHPNYA01() { return gPHPNYA01; }
    public void setgPHPNYA01(String gNamaNYA01) { this.gPHPNYA01 = gPHPNYA01; }

    private String Us_idnya ;
    public static final String TAG = Controller.class.getSimpleName();

    private RequestQueue queue;
    private com.android.volley.toolbox.ImageLoader ImageLoader;

    private static Controller controller;

    @Override
    public void onCreate() {
        super.onCreate();

        if (isConnected()) {
            //   Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        controller = this;
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


    public static synchronized Controller getPermission() {
        return controller;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
        }

        return queue;
    }

    public com.android.volley.toolbox.ImageLoader getImageLoader() {
        getRequestQueue();
        if (ImageLoader == null) {
            ImageLoader = new ImageLoader(this.queue,
                    new BitmapCache());
        }
        return this.ImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }


    public String getUs_idnya() {        return Us_idnya;    }
    public void setUs_idnya(String us_idnya) {        Us_idnya = us_idnya;    }


}
