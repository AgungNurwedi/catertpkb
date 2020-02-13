package com.tpkb.mascater; /**
 * Created by keagu on 03/09/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import java.util.ArrayList;

public class splas extends AppCompatActivity {

    JSONArray menussplas = null;
    private static final String TAG_ARRAY = "menu";
    private static final String TAG_CD = "menu_cd";
    private static final String TAG_LINK = "menu_link";
    ArrayList<MenuModel> menuModels = new ArrayList<MenuModel>();

    private  String JSON_URL ;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    //private List<ModelVersi> lstModelversi;
    private RecyclerView recyclerView ;

    String CodeNYA, LinkNYA , PhpNYA, ParamNYA ;
    String VerAPK, Ver_APK ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splas);

        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        imageView.startAnimation(animation);

        Thread timer = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(2500);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };

        timer.start();
    }
}
