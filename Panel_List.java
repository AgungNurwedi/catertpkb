package com.tpkb.mascater;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Panel_List extends AppCompatActivity {


    EditText E1, E2, E3 , E4, E5;
    TextView CDnya;
    Button B1;

    ListView L1;
    DatabaseHelper Mydata;
    SQLiteDatabase sqLiteDatabase;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_list);

        E1 = (EditText)findViewById(R.id.panelCD);
        E2 = (EditText)findViewById(R.id.panelNM);
        E3 = (EditText)findViewById(R.id.panelpanel);
        E4 = (EditText)findViewById(R.id.panelpara1);
        E5 = (EditText)findViewById(R.id.panelpara2);

        B1 = (Button)findViewById(R.id.bsimpanPanel) ;
        L1 = (ListView) findViewById(R.id.listViewPanel);
        CDnya = (TextView) findViewById(R.id.textPanelCD);

        Mydata = new DatabaseHelper(this);
        setTitle("Setup Panel");

        vwListPanel();

        L1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                String text = (String) L1.getItemAtPosition(arg2);
                CDnya.setText(text.toString()  );
                String gPanelCD = text.substring(0,3);

                DatabaseHelper dbHandler = new DatabaseHelper(getApplicationContext());

                GS_Panel gs_panel =
                        dbHandler.cariin(gPanelCD.toString());

                if (gs_panel != null) {
                    E1.setText(String.valueOf(gs_panel.getGsPanel_CD()));
                    E2.setText(String.valueOf(gs_panel.getGsPanel_NM()));
                    E3.setText(String.valueOf(gs_panel.getGsPanel()));
                    E4.setText(String.valueOf(gs_panel.getGsPara1()));
                    E5.setText(String.valueOf(gs_panel.getGsPara2()));

                } else {
                    Toast.makeText(Panel_List.this, "Data tidak...!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void vwListPanel(){
        Cursor LVPanel = Mydata.DispPanel();

        if(LVPanel.getCount()== 0){
            Toast.makeText(this,"Data tidak ada...!!!", Toast.LENGTH_LONG).show();
            return;
        }

        LVPanel.moveToFirst();
        String[] col_nm = new String[LVPanel.getCount()];

        int n = 0;
        do{
            col_nm[n] = (
                    LVPanel.getString(0).toString()
                            + " " +
                            LVPanel.getString(1).toString()
            );
            n=n+1;
        } while (LVPanel.moveToNext());
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, col_nm);
        L1.setAdapter(listAdapter);
    }


    public void UpdatePanel(View view){

        boolean cekupdatepanel =
                Mydata.UpdatePanel(
                E1.getText().toString(),  E2.getText().toString(),
                E3.getText().toString(),
                E4.getText().toString(),  E5.getText().toString()
                );
        if ( cekupdatepanel == true) {
            Toast.makeText(Panel_List.this, "Sukses Update...!!!", Toast.LENGTH_LONG).show();
        }
            else {
            Toast.makeText(Panel_List.this, "Update Batal...!!!", Toast.LENGTH_LONG).show();
        }
    }
/*
    public void Panel_Cari (View view){

        String cariInfo = CDnya.getText().toString();
        Mydata = new DatabaseHelper(this);
        sqLiteDatabase  = Mydata.getReadableDatabase();
        Cursor cursor =Mydata.getPanel(cariInfo);
        if(cursor.moveToFirst())
        {
            String cPanel_NM = cursor.getString(1);
            String cPanel = cursor.getString(2);
            String cPara1 = cursor.getString(3);
            String cPara2 = cursor.getString(4);
            E2.setText(cPanel_NM);
            E3.setText(cPanel );
            E4.setText(cPara1);
            E5.setText(cPara2);
        }
    }
*/
    public GS_Panel cariin(String PANELCD){
        SQLiteDatabase db = Mydata.getReadableDatabase();
        String selectQuery =  "SELECT Panel_NM, Panel, Para1, Para2 From Ft_Panel Where Panel_CD =?" ;
        int iCount =0;

        GS_Panel gs_panel = new GS_Panel();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { PANELCD } );

        if (cursor.moveToFirst()) {
            do {
                gs_panel.setGsPanel_CD(cursor.getString(1));
                gs_panel.setGsPanel_NM(cursor.getString(2));
                gs_panel.setGsPanel(cursor.getString(3));
                gs_panel.setGsPara1(cursor.getString(4));
                gs_panel.setGsPara2(cursor.getString(5));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return gs_panel;
    }

}
