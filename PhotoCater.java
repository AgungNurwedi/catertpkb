package com.tpkb.mascater;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.text.TextUtils.substring;

/**
 * Created by Agung on 26-11-2016.
 */


public class PhotoCater extends AppCompatActivity {

    String DIRApp = Constants.DIRApp;
    String APPName = Constants.AppName;

    Spinner ComboSpinnerBad ;
    ImageView imgPhotoSurvey;
    Button buttonGetPhoto;
    Button buttonGetGPS;
    Double latitudeBest, longitudeBest;
    private static final int REQUEST_PERMISSIONS = 10;

    Intent intent;
    DatabaseHelper MyDB;
    private LocationManager locationManager;
    private LocationListener locationListener;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RequestPermissionCode = 1;
    String[] perms = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
    };

    TextView srLat , srLong , srNoHP;
    ProgressBar pbPhoto;

    String xBad_CD, LebarPhoto, TinggiPhoto, SizePhoto ;
    int xDigit,  xxxPiutang , xxxAngsuran ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocater);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ComboSpinnerBad =  (Spinner)findViewById(R.id.spinnerbad);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        pbPhoto =  (ProgressBar) findViewById(R.id.progressBar2);
        pbPhoto.setVisibility(View.GONE);
        imgPhotoSurvey = (ImageView) findViewById(R.id.photoSurvey);
        buttonGetPhoto = (Button) findViewById(R.id.btnphoto);
        buttonGetGPS = (Button) findViewById(R.id.btnGPS);
        SpinnerBadData();
        Rupiah rcf = new Rupiah();

        MyDB = new DatabaseHelper(this);

        LebarPhoto = MyDB.getPanel("008");
        TinggiPhoto = MyDB.getPanel("009");
        SizePhoto = MyDB.getPanel("010");

        Intent in = getIntent();
        TextView DSML_TRID = (TextView) findViewById(R.id.tDSML_TRID);
        TextView DSML_Nopel = (TextView) findViewById(R.id.tDSML_Nopel);
        TextView DSML_Nama = (TextView) findViewById(R.id.tDSML_Nama);
        TextView DSML_Alamat = (TextView) findViewById(R.id.tDSML_Alamat);
        TextView DSML_Tarif = (TextView) findViewById(R.id.tDSML_tarif);
        TextView DSML_Nomet = (TextView) findViewById(R.id.tDSML_Nomet);
        TextView DSML_Awal = (TextView) findViewById(R.id.tDSML_Awal);
        TextView DSML_Akhir = (TextView) findViewById(R.id.tDSML_Akhir);
        TextView DSML_Kubik = (TextView) findViewById(R.id.tDSML_Kubik);
        TextView DSML_Nominal = (TextView) findViewById(R.id.tDSML_Nominal);
        TextView DSML_Piutang = (TextView) findViewById(R.id.tDSML_piutang);
        //TextView xDSML_Piutang = (TextView) findViewById(R.id.tDSML_piutang);
        TextView DSML_Total = (TextView) findViewById(R.id.tDSML_Total);
        TextView DSML_Bad_CD = (TextView) findViewById(R.id.tDSML_Bad_CD);
        TextView DSML_Digit = (TextView) findViewById(R.id.tDSML_Digit);
        TextView DSML_Minim = (TextView) findViewById(R.id.tDSML_Minim);
        TextView DSML_TgalCtt = (TextView) findViewById(R.id.tDSML_TgalCtt);
        TextView DSML_Tarif1 = (TextView) findViewById(R.id.tDSML_tarif1);
        TextView DSML_Tarif2 = (TextView) findViewById(R.id.tDSML_tarif2);
        TextView DSML_Admin  = (TextView) findViewById(R.id.tDSML_Admin);
        TextView DSML_Angsur = (TextView) findViewById(R.id.tDSML_Angsur);


        srLat = (TextView) findViewById(R.id.tDSML_Lat);
        srLong = (TextView) findViewById(R.id.tDSML_Long);
        srNoHP = (TextView) findViewById(R.id.tDSML_HP);

        DSML_TRID.setText(in.getStringExtra(("TR_ID")));
        DSML_Nopel.setText(in.getStringExtra(("Nopel")));
        DSML_Nama.setText(in.getStringExtra(("Nama")));
        DSML_Alamat.setText(in.getStringExtra(("Alamat")));
        DSML_Tarif.setText(in.getStringExtra(("Tarif_CD")));
        srNoHP.setText(in.getStringExtra(("No_HP")));
        srLat.setText(in.getStringExtra(("Lat_CD")));
        srLong.setText(in.getStringExtra(("Long_CD")));
        DSML_Nomet.setText(in.getStringExtra(("NoMet")));
        DSML_Awal.setText(in.getStringExtra(("Awal")));
        DSML_Akhir.setText(in.getStringExtra(("Akhir")));
        DSML_Kubik.setText(in.getStringExtra(("Kubik")));
        DSML_Nominal.setText(in.getStringExtra(("Nominal")));
        DSML_Piutang.setText(in.getStringExtra(("Piutang")));
        DSML_Total.setText(in.getStringExtra(("Total")));
        DSML_Bad_CD.setText(in.getStringExtra(("Bad_CD")));
        DSML_Digit.setText(in.getStringExtra(("Digit")));

        xxxPiutang = Integer.parseInt(DSML_Piutang.getText().toString());
        xxxAngsuran = Integer.parseInt(in.getStringExtra("Angsuran"));
        System.out.println("Bisaaccccccccccccccccccccccc");
        System.out.println(xxxPiutang);
        System.out.println(xxxAngsuran);

        xDigit = Integer.parseInt(in.getStringExtra("Digit")) ;
        DSML_TgalCtt.setText(in.getStringExtra(("TgalCtt")));
        DSML_Nominal.setText(rcf.toRupiahFormat(in.getStringExtra("Nominal")));
        DSML_Piutang.setText(rcf.toRupiahFormat(in.getStringExtra("Piutang")));
        DSML_Total.setText(rcf.toRupiahFormat(in.getStringExtra("Total")));

        xBad_CD = in.getStringExtra("Bad_CD") ;
        if (xBad_CD.equals("XX") ) {
            xBad_CD = "00";
        }
        ComboSpinnerBad.setSelection(getSpinner(ComboSpinnerBad, xBad_CD ));

        DSML_Minim.setText(in.getStringExtra(("Minim")));
        String UdahCatat =  in.getStringExtra("TgalCtt");
        DSML_Tarif1.setText(in.getStringExtra(("Tarif1")));
        DSML_Tarif2.setText(in.getStringExtra(("Tarif2")));
        DSML_Angsur.setText(rcf.toRupiahFormat(in.getStringExtra("Angsuran")));
        DSML_Admin.setText(in.getStringExtra(("Administrasi")));

        int xxxadmin = Integer.parseInt(DSML_Admin.getText().toString());

        System.out.println("Bisaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(xxxadmin);


        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        globalVariable.setgTR_IDNYA(in.getStringExtra("TR_ID"));
        globalVariable.setgNopelNYA(in.getStringExtra("Nopel"));
        globalVariable.setgAlamatNYA(in.getStringExtra("Alamat"));
        globalVariable.setgLatNYA(in.getStringExtra("Lat_CD"));
        globalVariable.setgLongNYA(in.getStringExtra("Long_CD"));

        String Bulan_Catat =  new SimpleDateFormat("yyyyMM",
                Locale.getDefault()).format(new Date())  ;

        String Tgl_Catat;
        if (UdahCatat.equals("")) {
            Tgl_Catat = new SimpleDateFormat("yyyyMMdd",
                    Locale.getDefault()).format(new Date());
        } else
        {
            Tgl_Catat = UdahCatat;
        }

        File folderBln = new File(DIRApp + File.separator +
                APPName + File.separator + Bulan_Catat);
        boolean success = true;
        if (!folderBln.exists()) {
            success = folderBln.mkdirs();
        }
        if (success) {
            File folderTgl = new File(DIRApp + File.separator +
                    APPName + File.separator + Bulan_Catat  + File.separator +  Tgl_Catat);
            boolean success1 = true;
            if (!folderTgl.exists()) {
                success1 = folderTgl.mkdirs();
            }

        } else {
            // Do something else on failure
        }

        String path = new File(DIRApp + File.separator +
                APPName + File.separator + Bulan_Catat  + File.separator +  Tgl_Catat +
                File.separator + in.getStringExtra("Nopel") + ".jpg").toString();

        /*String path = new File(DIRApp + File.separator +
                APPName + File.separator + "Photo" +
                File.separator + in.getStringExtra("Nopel") + ".jpg").toString();
        */
        System.out.println( "foldernya :" + path);

        File filePhoto = new File(path);
        if (!filePhoto.isFile()) {
            imgPhotoSurvey.setImageResource(R.drawable.silang);
            Photoin(path, in.getStringExtra("Nopel") );
            //     PadON();
        } else {
            imgPhotoSurvey.setImageDrawable(Drawable.createFromPath(path));

        }

        // Photoin("Photo", in.getStringExtra("Nopel") );

        imgPhotoSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                final String GloNopel = globalVariable.getgNopelNYA();
                Photoin("Photo", GloNopel);
                //    PadON();
            }
        });

        buttonGetPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                pbPhoto.setVisibility(View.VISIBLE);
                final TextView DSML_TRID = (TextView) findViewById(R.id.tDSML_TRID);
                String zID =  DSML_TRID.getText().toString();
                final EditText edAkhir =  (EditText) findViewById(R.id.tDSML_Akhir);
                String zAkhir =  edAkhir.getText().toString();
                final TextView DSML_Lat = (TextView) findViewById(R.id.tDSML_Lat);
                String zLat =  DSML_Lat.getText().toString();
                final TextView DSML_Long = (TextView) findViewById(R.id.tDSML_Long);
                String zLong =  DSML_Long.getText().toString();
                String zGoBad =  substring(ComboSpinnerBad.getSelectedItem().toString(),0 , 2);
                final TextView DSML_Kubik = (TextView) findViewById(R.id.tDSML_Kubik);
                String zKubik =  DSML_Kubik.getText().toString();

                final TextView DSML_HP = (TextView) findViewById(R.id.tDSML_HP);
                String zNOHP =  DSML_HP.getText().toString();

                System.out.println("Update....");
                System.out.println(zID);
                System.out.println(zAkhir);
                System.out.println(zKubik);
                System.out.println(zLat);
                System.out.println(zLong);
                System.out.println(zGoBad);
                System.out.println(zNOHP);

                UpdateSurveynya( zID, zGoBad, zAkhir, zKubik, zLat, zLong , zNOHP);

            }
        });

        DSML_Akhir.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int AngkaAwal = 0 ;
                int AngkaAkhir =0;
                int Kubikasi =0;
                int zMinim = 0 ;
                int zTarif1 = 0 ;
                int zTarif2 = 0 ;
                int ancuran =0;

                TextView oAwal = (TextView) findViewById(R.id.tDSML_Awal);
                EditText oAkhir = (EditText) findViewById(R.id.tDSML_Akhir);
                TextView oResult = (TextView) findViewById(R.id.tDSML_Kubik);
                TextView oPiutang = (TextView) findViewById(R.id.tDSML_piutang);
                TextView oAdmin = (TextView) findViewById(R.id.tDSML_Admin);
                TextView oAngsur = (TextView) findViewById(R.id.tDSML_Angsur);
                TextView oMinim = (TextView) findViewById(R.id.tDSML_Minim);
                TextView oTarif1 = (TextView) findViewById(R.id.tDSML_tarif1);
                TextView oTarif2 = (TextView) findViewById(R.id.tDSML_tarif2);

                oResult.setKeyListener(null);

                String strUserName = oAkhir.getText().toString();
 //               System.out.println("ckckckckckckckddddddddddddddddddddddddddddddd");
//                System.out.println(oAngsur );
//                System.out.println(strUserName);

                if (strUserName.trim().equals("")) {
                    return;
                }else {
                    AngkaAkhir = Integer.parseInt(oAkhir.getText().toString());
                    AngkaAwal = Integer.parseInt(oAwal.getText().toString());
                    double Result2;

                    int azAdmin = Integer.parseInt(oAdmin.getText().toString());

//                    zAngsuran = Integer.parseInt(oAngsur.getText().toString());
                    zMinim= Integer.parseInt(oMinim.getText().toString());
                    zTarif1= Integer.parseInt(oTarif1.getText().toString());
                    zTarif2= Integer.parseInt(oTarif2.getText().toString());

                    if (AngkaAwal > AngkaAkhir) {
                        Result2 = (Math.pow(10, xDigit) + AngkaAkhir) - AngkaAwal;
                        Kubikasi = (int) Result2;
                        oResult.setText(Integer.toString(Kubikasi));
                    } else {
                        Kubikasi = (AngkaAkhir - AngkaAwal);
                        oResult.setText(Integer.toString(Kubikasi));
                    }
                    System.out.println("cicilllaaaaaaaaaaaaaaaaaannn");
                    System.out.println(xxxAngsuran);
                    ItungTotal( Kubikasi  ,zMinim, xxxPiutang, azAdmin, xxxAngsuran, zTarif1, zTarif2 );
                }
           }
        });
        checkPerms();
    }

    private void Photoin(String valKodeTR, String valNopel) {

        //     System.out.println("Foldre " +  valKodeTR);
        //    System.out.println(" fff " + valNopel );

        File file = getSDPath(valKodeTR);
        File asal = new File(file, valNopel + "temp.jpg");
        System.out.println(asal.toString());
/*
        if(asal.exists())
        {
        asal.delete(); }
*/
        System.out.println(asal.toString());

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        globalVariable.setgNopelNYA(valNopel);
        globalVariable.setgTRNYA(valKodeTR);        // return image_file;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(asal));
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        //  PadON();
    }


    void ItungTotal(int zkubik , int zMinim, int zPiutang, int zAdministrasi, int zAngsuran , int zTarif1, int zTarif2) {
        int zNom1 = 0 ;
        int zNom2 = 0 ;
        int zTotal = 0 ;
        int zNominal = 0 ;

                if (zMinim > zkubik) {
                    zNom1 = zMinim * zTarif1;
                    zNom2 = 0;

                } else {
                    zNom1 = zMinim  * zTarif1;
                    zNom2 = (zkubik - zMinim ) * zTarif2;
                }

                zNominal = zNom1 + zNom2  + zAdministrasi   ;
                zTotal   = zNominal  + zPiutang + zAngsuran ;

                Rupiah rcf = new Rupiah();
                TextView oTotal = (TextView) findViewById(R.id.tDSML_Total);
                TextView oNom = (TextView) findViewById(R.id.tDSML_Nominal);
                oTotal.setText(rcf.toRupiahFormat(Integer.toString(zTotal)));
                oNom.setText(rcf.toRupiahFormat(Integer.toString(zNominal)));

    }

    void UpdateSurveynya(String sID, String sBad_CD, String sAkhir, String sKubik, String sLat, String sLong, String sNOHP) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());

        MyDB.UpdateDSML(sID,  sBad_CD, sAkhir, sKubik, sLat, sLong, strDate, sNOHP );
        MyDB.close();

        String zGoodBad =  substring(ComboSpinnerBad.getSelectedItem().toString(),0 , 1);
        if (zGoodBad.equals("0")){
            MyDB.ProsesDSML(sID);
            MyDB.close();
        }


        pbPhoto.setVisibility(View.GONE);
        finish();

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        final String GloStatusCaterrNya = globalVariable.getgStatusCaterNYA();
        Intent intent = new Intent(this, Tr_Cater.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle b = new Bundle();
        b.putString("xStatus", GloStatusCaterrNya );
        intent.putExtras(b);
        startActivity(intent);

    }

    private File getSDPath(String TR) {
// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
//        System.out.println(TR);

        String Bulan_Catat =  new SimpleDateFormat("yyyyMM",
                Locale.getDefault()).format(new Date())  ;
        String Tgl_Catat =  new SimpleDateFormat("yyyyMMdd",
                Locale.getDefault()).format(new Date())  ;

        File folder = new File(DIRApp + File.separator +
                APPName + File.separator + Bulan_Catat  + File.separator +  Tgl_Catat);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Do something on success
            //         System.out.println("SuksesMK");
            //   Toast.makeText(PhotoCater.this, "Sukses Create Folder...!!!", Toast.LENGTH_LONG).show();
        } else {
            // Do something else on failure
            Toast.makeText(PhotoCater.this, "Gagal Create Folder...!!!", Toast.LENGTH_LONG).show();
        }

        return folder; // image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
        final String GloNopelNya = globalVariable.getgNopelNYA();
        final String GloTRNya = globalVariable.getgTRNYA();

        String Bulan_Catat =  new SimpleDateFormat("yyyyMM",
                Locale.getDefault()).format(new Date())  ;
        String Tgl_Catat =  new SimpleDateFormat("yyyyMMdd",
                Locale.getDefault()).format(new Date())  ;


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //        String path = new File(DIRApp + File.separator +
            //                APPName + File.separator + GloTRNya + File.separator + GloNopelNya + ".jpg").toString();
            //        imgPhotoSurvey.setImageDrawable(Drawable.createFromPath(path));


            String strOldName = DIRApp + File.separator +
                    APPName + File.separator + Bulan_Catat  + File.separator +  Tgl_Catat + File.separator + GloNopelNya + "temp.jpg";
            String strNewName = DIRApp + File.separator +
                    APPName + File.separator + Bulan_Catat  + File.separator +  Tgl_Catat + File.separator + GloNopelNya + ".jpg";
            //   String NewPath = strSDCardPathName + strNewName;
            try {
                ResizeImages(strOldName,strNewName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeFile(strNewName);
            imgPhotoSurvey.setImageBitmap(bitmap);
            PadON();

        }

    }

    void ResizeImages(String sPath, String sTo) throws IOException {

        LebarPhoto = MyDB.getPanel("008");
        TinggiPhoto = MyDB.getPanel("009");
        SizePhoto = MyDB.getPanel("010");
        System.out.println(LebarPhoto);
        System.out.println(TinggiPhoto);
        System.out.println(SizePhoto);

        //Bitmap out = Bitmap.createScaledBitmap(bMap,  lebar, tinggi, false);

        Bitmap photo = BitmapFactory.decodeFile(sPath);

        photo = Bitmap.createScaledBitmap(photo, Integer.parseInt(LebarPhoto), Integer.parseInt(TinggiPhoto), false);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, Integer.parseInt(SizePhoto), bytes);

        File f = new File(sTo);
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();

        File fileApus =  new File(sPath);
        fileApus.delete();
    }

    void PadON() {
        EditText Editakhir= (EditText) findViewById(R.id.tDSML_Akhir);
        Editakhir.requestFocus();
        //  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //  imm.showSoftInput(Editakhir, InputMethodManager.SHOW_IMPLICIT);

        Editakhir.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
        Editakhir.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleBestUpdates(View view) {
        //    if (!checkLocation())
        //       return;
        pbPhoto.setVisibility(View.VISIBLE);
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkLocation();
                //  return;
            }
            locationManager.removeUpdates(locationListenerBest);
            button.setText(R.string.resume);
            pbPhoto.setVisibility(View.GONE);
        }
        else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, true);
            if(provider != null) {
                // locationManager.requestLocationUpdates(provider, 0, 1, locationListenerBest);
                locationManager.requestLocationUpdates(provider, 1 * 60 * 1000, 10, locationListenerBest);
                button.setText(R.string.pause);
                //Toast.makeText(this, "Best Provider is " + provider, Toast.LENGTH_LONG).show();

            }
        }
    }

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {

            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(latitudeBest);
                    System.out.println(longitudeBest);
                    //
                    srLat.setText(latitudeBest + "");
                    srLong.setText(longitudeBest + "");
                    final GlobalVar globalVariable = (GlobalVar) getApplicationContext();
                    globalVariable.setgLatNYA(String.valueOf(latitudeBest));
                    globalVariable.setgLongNYA(String.valueOf(longitudeBest));

                    // Toast.makeText(PhotoCater.this, "Best Provider update", Toast.LENGTH_SHORT).show();
                    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
                    pbPhoto.setVisibility(View.GONE);

                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


    };


    private void SpinnerBadData() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> lables = db.getTabelBAD();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ComboSpinnerBad.setAdapter(dataAdapter);
    }


    private int getSpinner(Spinner spinner, String myStringD) {
        System.out.println("ada spin...");
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (substring(spinner.getItemAtPosition(i).toString(),0,2).equals(myStringD)){
                //        if (spinner.getItemAtPosition(i).toString().equals(myStringD)) {
                index = i;
                System.out.println(spinner.getItemAtPosition(i).toString());
                System.out.println("Cocok  " + substring(spinner.getItemAtPosition(i).toString(),0,2));
            }
            spinner.setSelection(index);
        }
        return index;
    }

    public void checkPerms() {
        // Checking if device version > 22 and we need to use new permission model
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // Checking if we can draw window overlay
            if (!Settings.canDrawOverlays(this)) {
                // Requesting permission for window overlay(needed for all react-native apps)
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                //startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
            for(String perm : perms){
                // Checking each persmission and if denied then requesting permissions
                if(checkSelfPermission(perm) == PackageManager.PERMISSION_DENIED){
                    requestPermissions(perms, RequestPermissionCode );
                    break;
                }
            }
        }
    }
}




