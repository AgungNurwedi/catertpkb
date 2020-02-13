package com.tpkb.mascater;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Agung on 16-06-2016.
 */


public class DatabaseHelper extends SQLiteOpenHelper {


    String passwordnya, xPanel, xPara1, xPara2 , xTR_NM, SQLNYA1, xPass, xUsNm;;

    private static final String Tgl_Catat1 =  new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault()).format(new Date())  ;
    private static final String Tgl_Catat =  new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault()).format(new Date())  ;

    private static final String DB_NAME  = "SiCater01a.db";
    private static final String TABLE_USER = "Ft_User";
    private static final String TABEL_PANEL  = "Ft_Panel";
    private static final String TABEL_ATUR  = "Ft_Atur";
    private static final String TABEL_DSML  = "Tr_DSML";
    private static final String TABEL_FTBAD  = "FT_Bad";
    private static final String TABEL_FTBLOK  = "FT_Blok";
    private static final String TRIGGER_DSML  = "TU_Tr_DSML_Proses";

    private static final String DROP_TRIGGER_DSML = "DROP TRIGGER IF EXISTS  " + TRIGGER_DSML;
    private static final String DSML_ID = "Tr_ID";
    private static final String DSML_Tabul  = "Tabul";
    private static final String DSML_Nopel  = "Nopel";
    private static final String DSML_Nama  = "Nama";
    private static final String DSML_Alamat = "Alamat";
    private static final String DSML_Tarif_CD = "Tarif_CD";
    private static final String DSML_No_HP = "No_HP";
    private static final String DSML_Lat_CD = "Lat_CD";
    private static final String DSML_Long_CD = "Long_CD";
    private static final String DSML_Nometer = "Nometer";
    private static final String DSML_Awal = "Awal";
    private static final String DSML_Akhir = "Akhir";
    private static final String DSML_Kubik = "Kubik";
    private static final String DSML_Kubik1 = "Kubik1";
    private static final String DSML_Kubik2 = "Kubik2";
    private static final String DSML_Kubik3 = "Kubik3";
    private static final String DSML_Kubik4 = "Kubik4";
    private static final String DSML_Tarif1 = "Tarif1";
    private static final String DSML_Tarif2 = "Tarif2";
    private static final String DSML_Tarif3 = "Tarif3";
    private static final String DSML_Tarif4 = "Tarif4";
    private static final String DSML_Nominal = "Nominal";
    private static final String DSML_Administrasi = "Administrasi";
    private static final String DSML_Danameter = "Danameter";
    private static final String DSML_Angsuran = "Angsuran";
    private static final String DSML_Total = "Total";
    private static final String DSML_Bad_CD = "Bad_CD";
    private static final String DSML_Piutang = "Piutang";
    private static final String DSML_Sts = "Status";
    private static final String DSML_Minim = "Minim";
    private static final String DSML_US = "Tr_Us";
    private static final String DSML_TGL = "Tr_Date";
    private static final String DSML_Digit = "Digit";

    private static final String DROP_TABLE_TR = "DROP TABLE IF EXISTS " + TABEL_DSML;
    private static final String CR_TABLE_TR = "create table " + TABEL_DSML + "(" +
    DSML_ID             +   " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ON CONFLICT ROLLBACK," +
    DSML_Tabul          + " Char(6) not null, " +
    DSML_Nopel          + " Char(12) not null, " +
    DSML_Nama           + " TEXT not null, " +
    DSML_Alamat         + " TEXT not null, " +
    DSML_Tarif_CD       + " TEXT not null, " +
    DSML_No_HP          + " TEXT not null, " +
    DSML_Lat_CD         + " TEXT not null, " +
    DSML_Long_CD        + " TEXT not null, " +
    DSML_Nometer        + " TEXT not null, " +
    DSML_Awal           + " INTEGER DEFAULT 0, " +
    DSML_Akhir          + " INTEGER DEFAULT 0, " +
    DSML_Kubik          + " INTEGER DEFAULT 0, " +
    DSML_Kubik1         + " INTEGER DEFAULT 0, " +
    DSML_Kubik2         + " INTEGER DEFAULT 0, " +
    DSML_Kubik3         + " INTEGER DEFAULT 0, " +
    DSML_Kubik4         + " INTEGER DEFAULT 0, " +
    DSML_Tarif1         + " INTEGER DEFAULT 0, " +
    DSML_Tarif2         + " INTEGER DEFAULT 0, " +
    DSML_Tarif3         + " INTEGER DEFAULT 0, " +
    DSML_Tarif4         + " INTEGER DEFAULT 0, " +
    DSML_Nominal        + " INTEGER DEFAULT 0, " +
    DSML_Administrasi   + " INTEGER DEFAULT 0, " +
    DSML_Danameter      + " INTEGER DEFAULT 0, " +
    DSML_Angsuran       + " INTEGER DEFAULT 0, " +
    DSML_Total          + " INTEGER DEFAULT 0, " +
    DSML_Bad_CD         + " Char(2) DEFAULT 'XX', " +
    DSML_Piutang        + " INTEGER DEFAULT 0, " +
    DSML_Sts            + " Char(1) DEFAULT '0', " +
    DSML_Minim          + " INTEGER DEFAULT 10, " +
    DSML_US             + " TEXT not null, " +
    DSML_Digit          + " INTEGER DEFAULT 4, " +
    DSML_TGL            + " TEXT DEFAULT null  ); ";

    private static final String BAD_CD  = "Bad_CD";
    private static final String BAD_NM  = "Bad_Nm";
    private static final String CR_TABEL_FTBAD = "create table " + TABEL_FTBAD + "(" +
            BAD_CD   + " Char(9) not null, " +
            BAD_NM + " TEXT not null );";
    private static final String DROP_TABEL_FTBAD = "DROP TABLE IF EXISTS " + TABEL_FTBAD;


    private static final String BLOK_CD  = "Blok_Cd";
    private static final String BLOK_NM  = "Blok_Nm";
    private static final String CR_TABEL_FTBLOK = "create table " + TABEL_FTBLOK + "(" +
            BLOK_CD   + " Char(2) not null, " +
            BLOK_NM + " TEXT not null );";
    private static final String DROP_TABEL_FTBLOK = "DROP TABLE IF EXISTS " + TABEL_FTBLOK;



    private static final String AturID  = "Atur_Id";
    private static final String AturCD  = "Atur_CD";
    private static final String AturNM  = "Atur_Nm";
    private static final String CR_TABLE_ATUR = "create table " + TABEL_ATUR + "(" +
            AturID + " Char(1) DEFAULT 0, " +
            AturCD + " Char(1) DEFAULT 0, " +
            AturNM  + " TEXT not null );";
    private static final String DROP_TABLE_ATUR = "DROP TABLE IF EXISTS " + TABEL_ATUR;
    private static final String INS_TABLE_ATUR = "INSERT INTO " + TABEL_ATUR + " VALUES('0','0', '0=All, 1=Good, 2=Bad, 3=BlmCtt');";


    private static final String UsID  = "Us_Id";
    private static final String NmID  = "Us_Nm";
    private static final String PwID  = "Us_Pw";
    private static final String Sec_ID  = "Sec";
    private static final String Aktif_ID  = "Aktif";
    private static final String CR_TABLE_USER = "create table " + TABLE_USER + "(" +
            UsID + " TEXT not null, " +
            NmID  + " TEXT not null, " +
            PwID + " TEXT not null, " +
            Sec_ID + " TEXT DEFAULT 1," +
            Aktif_ID + " TEXT DEFAULT 1 );";
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String INS_TABLE_USER = "INSERT INTO " + TABLE_USER + " VALUES('super','super','moses', '0', '1');";

    private static final String CR_TABLE_PANEL = "CREATE TABLE " + TABEL_PANEL + "(" +
              " Panel_CD Char(3) NOT NULL," +
              " Panel_NM TEXT, " +
              " Panel INTEGER, " +
              " Para1 TEXT," +
              " Para2 TEXT );";

    private static final String DROP_TABLE_PANEL = "DROP TABLE IF EXISTS " + TABEL_PANEL ;
    private static final String INSERT_TABLE_PANEL0 = "INSERT INTO " + TABEL_PANEL + " VALUES('000','0=Offline, 1=Online',0,'0','')";
    private static final String INSERT_TABLE_PANEL1 = "INSERT INTO " + TABEL_PANEL + " VALUES('001','IP Address Off',1,'192.168.3.253','')";
    private static final String INSERT_TABLE_PANEL2 = "INSERT INTO " + TABEL_PANEL + " VALUES('002','IP Port Off',1,'1433','')";
    private static final String INSERT_TABLE_PANEL3 = "INSERT INTO " + TABEL_PANEL + " VALUES('003','Database',1,'BKS01','')";
    private static final String INSERT_TABLE_PANEL4 = "INSERT INTO " + TABEL_PANEL + " VALUES('004','Server',1,'SERVER09','')";
    private static final String INSERT_TABLE_PANEL5 = "INSERT INTO " + TABEL_PANEL + " VALUES('005','Folder photo',1,'/e/BKS01/Photo/','')";
    private static final String INSERT_TABLE_PANEL6 = "INSERT INTO " + TABEL_PANEL + " VALUES('006','Kode User Handphone',1,'00001','')";
    private static final String INSERT_TABLE_PANEL7 = "INSERT INTO " + TABEL_PANEL + " VALUES('007','Status 0=Download, 1=Upload',1,'0','')";
    private static final String INSERT_TABLE_PANEL8 = "INSERT INTO " + TABEL_PANEL + " VALUES('008','Ukuran Lebar Photo',1,'300','')";
    private static final String INSERT_TABLE_PANEL9 = "INSERT INTO " + TABEL_PANEL + " VALUES('009','Ukuran Tinggi Photo',1,'300','')";
    private static final String INSERT_TABLE_PANEL10 = "INSERT INTO " + TABEL_PANEL + " VALUES('010','Resize Resolusi Photo',1,'100','')";
    private static final String INSERT_TABLE_PANEL11 = "INSERT INTO " + TABEL_PANEL + " VALUES('011','User Network',1,'useer','')";
    private static final String INSERT_TABLE_PANEL12 = "INSERT INTO " + TABEL_PANEL + " VALUES('012','Pswd Network',1,'pass','')";
    private static final String INSERT_TABLE_PANEL13 = "INSERT INTO " + TABEL_PANEL + " VALUES('013','photo CU',1,'/e/BKS01/CU/','')";
    private static final String INSERT_TABLE_PANEL14 = "INSERT INTO " + TABEL_PANEL + " VALUES('014','IP Address On',1,'192.168.3.253','')";
    private static final String INSERT_TABLE_PANEL15 = "INSERT INTO " + TABEL_PANEL + " VALUES('015','IP Port On',1,'1433','')";

    public DatabaseHelper(Context context ) {

        // super(context, "/sdcard/"+ folder_main + "/" + DB_NAME, null, 4);
        // SQLiteDatabase.openOrCreateDatabase("/sdcard/"+ folder_main + "/" +DB_NAME,null);

        super(context, DB_NAME, null, 3);
    //    SQLiteDatabase.openOrCreateDatabase(DB_NAME,null);
//        SQLiteDatabase db = this.getWritableDatabase();


}

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
      //  Log.d("sssTAG", CR_TABLE_USER);
        db.execSQL(CR_TABLE_USER);
        db.execSQL(INS_TABLE_USER);
        db.execSQL(CR_TABLE_PANEL);
        db.execSQL(CR_TABLE_TR);
        db.execSQL(CR_TABLE_ATUR);
        db.execSQL(CR_TABEL_FTBLOK);
        db.execSQL(INSERT_TABLE_PANEL0);
        db.execSQL(INSERT_TABLE_PANEL1);
        db.execSQL(INSERT_TABLE_PANEL2);
        db.execSQL(INSERT_TABLE_PANEL3);
        db.execSQL(INSERT_TABLE_PANEL4);
        db.execSQL(INSERT_TABLE_PANEL5);
        db.execSQL(INSERT_TABLE_PANEL6);
        db.execSQL(INSERT_TABLE_PANEL7);
        db.execSQL(INSERT_TABLE_PANEL8);
        db.execSQL(INSERT_TABLE_PANEL9);
        db.execSQL(INSERT_TABLE_PANEL10);
        db.execSQL(INSERT_TABLE_PANEL11);
        db.execSQL(INSERT_TABLE_PANEL12);
        db.execSQL(INSERT_TABLE_PANEL13);
        db.execSQL(INSERT_TABLE_PANEL14);
        db.execSQL(INSERT_TABLE_PANEL15);
        db.execSQL(INS_TABLE_ATUR);
        db.execSQL(CR_TABEL_FTBAD);

        db.execSQL(DROP_TRIGGER_DSML);

        query =
         " CREATE TRIGGER " + TRIGGER_DSML + " UPDATE OF Status ON " + TABEL_DSML +
         " when (new.Status = '1')  and (substr(new.Bad_CD,1,1) = '0') " +
         " BEGIN " +
         " UPDATE 	Tr_DSML " +
         " SET 			Tr_Date = CURRENT_TIMESTAMP , " +
         " Kubik1 =  Minim, " +
         " Kubik2 = "  +
                        " CASE WHEN Kubik <= Minim THEN 0 " +
                        " ELSE Kubik - Minim " +
                        " END,  " +
         " Nominal = ( Minim * Tarif1 ) +  (  " +
         "           ( 	  CASE WHEN Kubik <= Minim THEN 0  " +
                        " ELSE Kubik - Minim " +
                        " END) * Tarif2) + Administrasi + Angsuran, " +
         " Total = 		Piutang +  " +
         "               ( ( Minim * Tarif1 ) +  ( " +
         "                       ( 	CASE WHEN Kubik <= Minim THEN 0 " +
                                  " ELSE Kubik - Minim " +
                                  " END) * Tarif2) + Administrasi + Angsuran ) " +
         " WHERE 	Tr_ID = old.Tr_ID ; " +
         " END; " ;
        System.out.println(query  );
        db.execSQL(query);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PANEL);
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_TR);
        db.execSQL(DROP_TABEL_FTBAD);
        db.execSQL(DROP_TABLE_ATUR);
        db.execSQL(DROP_TABEL_FTBLOK);
        onCreate(db);

    }



    public GS_Panel cariin(String PANELCD){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT Panel_CD, Panel_NM, Panel, Para1, Para2 From Ft_Panel Where Panel_CD =?" ;
        int iCount =0;
        GS_Panel gs_panel = new GS_Panel();
        Cursor cursor = db.rawQuery(selectQuery, new String[] { PANELCD } );
        if (cursor.moveToFirst()) {
            do {
                gs_panel.setGsPanel_CD(cursor.getString(0));
                gs_panel.setGsPanel_NM(cursor.getString(1));
                gs_panel.setGsPanel(cursor.getString(2));
                gs_panel.setGsPara1(cursor.getString(3));
                gs_panel.setGsPara2(cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gs_panel;
    }


    public Cursor DispBlok() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curPanel;
        curPanel = sqLiteDatabase.rawQuery(
                "select Blok_CD, Blok_NM from " +
                        TABEL_FTBLOK + " order by Blok_CD" , null);
        return  curPanel;

    }


    public Cursor DispPanel() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curPanel;
        curPanel = sqLiteDatabase.rawQuery(
                "select Panel_CD, Panel_NM, Panel, Para1, Para2 from " +
                TABEL_PANEL + " order by Panel_CD" , null);
        return  curPanel;

    }

    public List<String> getTabelBAD(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABEL_FTBAD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0) + " " + cursor.getString(1));
            } while (cursor.moveToNext());
        }
        db.close();
        return labels;
    }

    public List<String> getTabelTRCD(){
        List<String> labelstr = new ArrayList<String>();
        String selectQuery = "SELECT  BAD_NM FROM " + TABEL_FTBAD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursortr = db.rawQuery(selectQuery, null);
        if (cursortr.moveToFirst()) {
            do {
                labelstr.add(cursortr.getString(0) );
            } while (cursortr.moveToNext());
        }
        db.close();
        return labelstr;
    }


    String getPanel(String userCD){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABEL_PANEL,null,  "Panel_CD = ?" ,new String[]{userCD}, null, null, null, null);

        if(cursor.getCount()<1){
            cursor.close();
            return "Not Exist";
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){

           //  xPanel = cursor.getString(cursor.getColumnIndex("Panel"));
            xPara1 = cursor.getString(cursor.getColumnIndex("Para1"));
           // xPara2 = cursor.getString(cursor.getColumnIndex("Para2"));
            cursor.close();
        }
        return xPara1 ; //, xPara1, xPara2;
    }

    public boolean UpdatePanel(String pPanelCD, String pPanelNM, String pPanel, String pPanelPara1, String pPanelPara2 ) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase() ;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Panel_CD",pPanelCD);
        contentValues.put("Panel_NM",pPanelNM);
        contentValues.put("Panel",pPanel);
        contentValues.put("Para1",pPanelPara1);
        contentValues.put("Para2",pPanelPara2);
        sqLiteDatabase.update(TABEL_PANEL, contentValues, "Panel_CD = ?" , new String[]{pPanelCD});
        return true;
    }


    public void Insert_User(String UsID , String pwd ) {
        SQLiteDatabase db = this.getReadableDatabase();
     //   System.out.println("DEMEN DAHssssssssssssssssss "  );
        String INS_USER = "Delete from " + TABLE_USER + " where Us_Id <> 'super' ; ";
        db.execSQL(INS_USER);
        String INS_USER1 =
                "INSERT INTO " + TABLE_USER +
                " ( Us_Id ,  Us_Nm , Us_Pw ,  Sec,  Aktif ) " +
                " VALUES( '" + UsID + "','User','" + pwd + "', '0', '0');";
        db.execSQL(INS_USER1);
        System.out.println("ins user : " + INS_USER1 );



    }

    public void Reset_Blok() {
        SQLiteDatabase db = this.getReadableDatabase();
        String DelBlok = "Delete from " + TABEL_FTBLOK ;
        db.execSQL(DelBlok);
    }

    public void Insert_Blok(String BlokCD , String BlokNM) {
        SQLiteDatabase db = this.getReadableDatabase();
        String InsBlok =
                "INSERT INTO " + TABEL_FTBLOK +
                        " ( Blok_Cd ,  Blok_Nm ) " +
                        " VALUES( '" + BlokCD + "','" +BlokNM + "' );";
        db.execSQL(InsBlok);
//        System.out.println("ins blok: " + InsBlok);
    }

    public void ApusDSML(){
     //   System.out.println("DEMEN apussssss "  );
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_DSML,null,null);
        db.close();
      //  System.out.println("DEMEN apussssssaaaaaaaaaannnnnnnnnn "  );
    }



    public void Tambah_TR(String stTR  , String stCD  , String stNM  ,
                          String stALM  , String stTRF , String stLAT  ,
                          String stLONG, String stUS ) {
        System.out.println("DEMEN DAH "  );
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("DEMEN DAHssssssssssssssssss "  );
        String INS_TR = "INSERT INTO " + TABEL_DSML +
                        " VALUES(" + stTR + "','"  + stCD + "','" + stNM + "','" +
                                     stALM + "','" + stTRF + "','"  + stLAT + "','" +
                                     stLONG + "','" + stUS + "', );";
        System.out.println("DEMEN DAH " + INS_TR);
        db.execSQL(INS_TR);
    }


    public boolean UpdateATUR(String pID , String pCD ) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase() ;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Atur_CD",pCD);
        sqLiteDatabase.update(TABEL_ATUR, contentValues, "Atur_ID = ?" , new String[]{pID});
        return true;
    }

    public Cursor DispCater() {
        String SQLNYA;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curAtur;
        curAtur = sqLiteDatabase.rawQuery(
                "select Atur_CD from " +
                        TABEL_ATUR  , null);

        //return  curAtur;
        if(curAtur.getCount() == 0 ) {

        }else {

            curAtur.moveToFirst();

            System.out.println(curAtur.getString(0).toString() );

            if (curAtur.getString(0).equals("1")) {
                SQLNYA1 =
                        "select SUBSTR(Bad_CD,1,1) as GoBad, TR_ID, Nopel, Nama,  Alamat, Tarif_CD, No_HP, Nometer, " +
                                " Lat_CD, Long_CD, Bad_CD, Awal, Akhir, Kubik, Nominal, Piutang, Total, Digit, Minim, " +
                                " strftime('%Y%m%d', Tr_Date) as Tr_Date , Tarif1, Tarif2, Angsuran, Administrasi from " +
                                TABEL_DSML + " where SUBSTR(Bad_CD,1,1) = '0' order by Tr_ID";
            } else if (curAtur.getString(0).equals("2")) {
                SQLNYA1 =
                        "select SUBSTR(Bad_CD,1,1) as GoBad, TR_ID, Nopel, Nama,  Alamat, Tarif_CD, No_HP, Nometer, " +
                                " Lat_CD, Long_CD, Bad_CD, Awal, Akhir, Kubik, Nominal, Piutang, Total, Digit, Minim, " +
                                " strftime('%Y%m%d', Tr_Date) as Tr_Date , Tarif1, Tarif2, Angsuran, Administrasi from " +
                                TABEL_DSML + " where SUBSTR(Bad_CD,1,1) NOT IN ( '0', 'X' ) order by Tr_ID";
            } else if (curAtur.getString(0).equals("3")) {
                SQLNYA1 =
                        "select SUBSTR(Bad_CD,1,1) as GoBad, TR_ID, Nopel, Nama,  Alamat, Tarif_CD, No_HP, Nometer, " +
                                " Lat_CD, Long_CD, Bad_CD, Awal, Akhir, Kubik, Nominal, Piutang, Total, Digit, Minim, " +
                                " strftime('%Y%m%d', Tr_Date) as Tr_Date , Tarif1, Tarif2, Angsuran, Administrasi from " +
                                TABEL_DSML + " where SUBSTR(Bad_CD,1,1) = 'X' order by Tr_ID";
            } else
            {
                SQLNYA1 =
                        "select SUBSTR(Bad_CD,1,1) as GoBad, TR_ID, Nopel, Nama,  Alamat, Tarif_CD, No_HP, Nometer, " +
                                " Lat_CD, Long_CD, Bad_CD, Awal, Akhir, Kubik, Nominal, Piutang, Total, Digit, Minim, " +
                                " strftime('%Y%m%d', Tr_Date) as Tr_Date , Tarif1, Tarif2, Angsuran, Administrasi from " +
                                TABEL_DSML  + " order by Tr_ID";
            }
        }

        SQLNYA = SQLNYA1 ;

        Cursor curPanel;
        curPanel = sqLiteDatabase.rawQuery( SQLNYA , null);
        return  curPanel;

    }

    public Cursor Dashboard(String xCode) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String QLNYA1 ;
        QLNYA1 =
                "select sount()TR_ID) as Jml  from " +
                        TABEL_DSML + " where SUBSTR(Bad_CD,1,1) = " + xCode  ;
        Cursor curJml;
        curJml = sqLiteDatabase.rawQuery( QLNYA1 , null);
        return  curJml;
    }

    public void insertDSML(DSML_Andro dsml_andro ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
     //   System.out.println("DEMEN DAH fffffffffff"  );
      //  values.put(tID, survey.getSrtID());
        values.put(DSML_Tabul, dsml_andro.getDSML_Tabul());
        values.put(DSML_Nopel, dsml_andro.getDSML_Nopel());
        values.put(DSML_Nama, dsml_andro.getDSML_Nama());
        values.put(DSML_Alamat, dsml_andro.getDSML_Alamat());
        values.put(DSML_Tarif_CD, dsml_andro.getDSML_Tarif_CD());
        values.put(DSML_No_HP, dsml_andro.getDSML_No_HP());
        values.put(DSML_Tarif1, dsml_andro.getDSML_Tarif1());
        values.put(DSML_Tarif2, dsml_andro.getDSML_Tarif2());
        values.put(DSML_Tarif3, dsml_andro.getDSML_Tarif3());
        values.put(DSML_Tarif4, dsml_andro.getDSML_Tarif4());
        values.put(DSML_Nometer, dsml_andro.getDSML_Nometer());
        values.put(DSML_Awal, dsml_andro.getDSML_Awal());
        values.put(DSML_Administrasi, dsml_andro.getDSML_Administrasi());
        values.put(DSML_Danameter,  dsml_andro.getDSML_Danameter());
        values.put(DSML_Angsuran, dsml_andro.getDSML_Angsuran());
        values.put(DSML_Piutang, dsml_andro.getDSML_Piutang());
        values.put(DSML_Lat_CD, dsml_andro.getDSML_Lat_CD());
        values.put(DSML_Long_CD, dsml_andro.getDSML_Long_CD());
        values.put(DSML_US, dsml_andro.getDSML_User());
        values.put(DSML_Bad_CD, dsml_andro.getDSML_Bad_CD());
        values.put(DSML_Digit, dsml_andro.getDSML_Digit());
        values.put(DSML_Minim, dsml_andro.getDSML_Minim());

/*
        values.put(DSML_Akhir, dsml_const.getDSML_Akhir());
        values.put(DSML_Kubik, dsml_const.getDSML_Kubik());
        values.put(DSML_Kubik1, dsml_const.getDSML_Kubik1());
        values.put(DSML_Kubik2, dsml_const.getDSML_Kubik2());

        values.put(DSML_Nominal, dsml_const.getDSML_Nominal());
        values.put(DSML_Total, dsml_const.getDSML_Total());
        values.put(DSML_Bad_CD, dsml_const.getDSML_Bad_CD());
*/
        db.insert(TABEL_DSML, null, values);
        db.close();
     //   System.out.println("DEMEN DAHsssssssssssffffffffffffffffffsssssss "  );
    }

    public boolean UpdateDSML(String pID, String pBadCD, String pAkhir, String pKubik,
                              String pLat, String pLong, String TglCatat , String pNo_HP ) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase() ;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Bad_CD",pBadCD);
        contentValues.put("Akhir",pAkhir);
        contentValues.put("Kubik",pKubik);
        contentValues.put("Lat_CD",pLat);
        contentValues.put("Long_CD",pLong);
        contentValues.put("Status","0");
        contentValues.put("Tr_Date",TglCatat);
        contentValues.put("No_HP",pNo_HP);
        sqLiteDatabase.update(TABEL_DSML, contentValues, "TR_ID = ?" , new String[]{pID});
        return true;
    }

    public boolean ProsesDSML( String pID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase() ;
        ContentValues contentValues = new ContentValues();
        contentValues.put("Status","1");
        sqLiteDatabase.update(TABEL_DSML, contentValues, "TR_ID = ?" , new String[]{pID});
        return true;
    }

    public Cursor DispDSML() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curDSML;
        curDSML = sqLiteDatabase.rawQuery(
                "select Nopel, strftime('%Y%m%d', Tr_Date )as Tr_Date, No_HP, Akhir, Lat_CD, Long_CD, Bad_CD from " +
                TABEL_DSML  + " where Bad_CD <> 'XX' ", null);
        return  curDSML;
    }

    public Cursor UploadDSML(String Tglcatat) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor curDSML;
        curDSML = sqLiteDatabase.rawQuery(
                "select Nopel, No_HP, Akhir, Bad_CD, Lat_CD, Long_CD from " +
                        TABEL_DSML  + " where Tanggal = '" + Tglcatat +
                                      "' and Bad_CD <> 'XX' ", null);
        return  curDSML;

//        $pm_cd 			= $_GET['pm_cd'];
//        $tglcatat		= $_GET['tglCctat'];
//        $nopel			= $_GET['nopel'];
//        $nohp			= $_GET['nohp'];
//        $akhir			= $_GET['akhir'];
//        $bad_cd			= $_GET['bad_cd'];
//        $lat_cd 		= $_GET['lat_cd'];
//        $long_cd		= $_GET['long_cd'];
    }

    public Cursor JumlahDSML(String zCode) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor curDSML;
//        curDSML = sqLiteDatabase.rawQuery(
//                "select Nopel  from " +
//                        TABEL_DSML  + " where SUBSTR(Bad_CD,1,1) = '" + zCode + "' ", null);
//        return  curDSML;


        if (zCode.equals("1")) {
            SQLNYA1 =
                    "select  TR_ID from " +
                            TABEL_DSML + " where SUBSTR(Bad_CD,1,1) = '0'  ";
        } else if (zCode.equals("2")) {
            SQLNYA1 =
                    "select  TR_ID from " +
                            TABEL_DSML + " where SUBSTR(Bad_CD,1,1) NOT IN ( '0', 'X' )  ";
        } else if (zCode.equals("3")) {
            SQLNYA1 =
                    "select  TR_ID from " +
                            TABEL_DSML + " where SUBSTR(Bad_CD,1,1) = 'X' ";
        } else
        {
            SQLNYA1 =
                    "select TR_ID from " +
                            TABEL_DSML   ;
        }

        Cursor curPanel;
        curPanel = sqLiteDatabase.rawQuery( SQLNYA1 , null);
        return  curPanel;

    }

    public void ApusTabelBAD(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_FTBAD,null,null);
        db.close();
    }

    public void Insert_Bad(String BadCD , String BadNM) {
        SQLiteDatabase db = this.getReadableDatabase();
        String InsBlok =
                "INSERT INTO " + TABEL_FTBAD +
                        " ( Bad_CD ,  Bad_Nm ) " +
                        " VALUES( '" + BadCD + "','" + BadNM + "' );";
        db.execSQL(InsBlok);
//        System.out.println("ins blok: " + InsBlok);
    }



    public void insertTabelBAD(Cont_Ft_Bad cont_ft_bad ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BAD_CD, cont_ft_bad.getBad_CD() );
        values.put(BAD_NM, cont_ft_bad.getBad_Nm());
        db.insert(TABEL_FTBAD, null, values);
        db.close();
     //   System.out.println("DEMENMMMMMMM"  );
    }

    String getPassword(String USNya){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USER, null,  "Us_Id = ?" ,new String[]{USNya}, null, null, null, null);

        if(cursor.getCount()<1){
            cursor.close();
            return "User tidak ada / Password salah...!!!";
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){

            xPass = cursor.getString(cursor.getColumnIndex("Us_Pw")) ;
            cursor.close();
        }
        return xPass ;
    }

    String getNm_US(String USnya){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USER, null,  "Us_Id = ?" ,new String[]{USnya}, null, null, null, null);

        if(cursor.getCount()<1){
            cursor.close();
            return "User tidak ada / Password salah...!!!";
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){

            xUsNm = cursor.getString(cursor.getColumnIndex("Us_Nm")) ;
            cursor.close();
        }
        return xUsNm ;
    }

    String getPassUser(String username, String passs ){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USER,null,  "Us_Id=?",new String[]{username},null, null, null, null);

        if(cursor.getCount()<1){
            cursor.close();
            return "Not Exist";
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){

            passwordnya = cursor.getString(cursor.getColumnIndex(PwID));
            cursor.close();
        }
        return passwordnya;
    }

    String getFolderDownload(String TR1){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABEL_DSML,null,  "TR=?" ,new String[]{TR1}, null, null, null, null);
        if(cursor.getCount()<1){
            cursor.close();
            return "Not Exist";
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){
     //       xPara1 = cursor.getString(cursor.getColumnIndex(FtDW));
            cursor.close();
        }
        return xPara1 ; //, xPara1, xPara2;
    }

}
