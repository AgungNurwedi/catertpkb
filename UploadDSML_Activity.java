package com.tpkb.mascater;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class UploadDSML_Activity extends AppCompatActivity {
	private static final String TAG_ARRAY = "plg";
	private static final String TAG_KODKEY = "kodkey";

	private static final String TAG_ARRAYm = "menu";
	private static final String TAG_CDm = "menu_cd";
	private static final String TAG_LINKm = "menu_link";
	JSONArray menuss = null;

	DatabaseHelper MyDB;

	static final int RequestCode = 1;
	static int mode = 0;
	ImageView photo ;
	Intent intent ;
	public  static final int RequestPermissionCode  = 1 ;
	TextView latcd, longcd;
	String CodeNYA, LinkNYA, PhpNYA, ParamNYA, NopelNYA;
	EditText namainfo, alamatinfo, hpinfo, inform, para1, para2, para3, para4, para5;
	Button button;
	String PhpNYA1, NYA1, NYA2, NYA3, NYA4, NYA5, NYA6, latnya, longnya;
	ArrayList<PlgModel> plgModels = new ArrayList<PlgModel>();
	JSONArray lppes = null;

	InputStream inputStream;
	String upLoadServerUri = null;
	File destination;

	public static String DIRApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM ).toString() ;
	//public static String DIRApp = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM ).toString(),  MODE_PRIVATE ;
	public static String AppName="InTiPaSi" ;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	String LebarPhoto, TinggiPhoto, SizePhoto ;

	String[] perms = {
			"android.permission.READ_EXTERNAL_STORAGE",
			"android.permission.WRITE_EXTERNAL_STORAGE",
			"android.permission.ACCESS_FINE_LOCATION",
			"android.permission.ACCESS_COARSE_LOCATION"
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploadcater);

//		final Controller goVe = (Controller) getApplicationContext();
//		final String GloPHP = goVe.getgPHPNYA() ;
//
//
//
//		PhpNYA   = GloPHP ;
//		new UploadDSML_Activity.SetupMenu().execute(PhpNYA , "011" );
//
//		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//		StrictMode.setVmPolicy(builder.build());
//
//		Button clickButton = (Button) findViewById(R.id.butCM);
//		clickButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				para1 = (EditText) findViewById(R.id.namainfo);
//				para2 = (EditText) findViewById(R.id.alamatinfo);
//				para3 = (EditText) findViewById(R.id.nohpinfo);
//				para4 = (EditText) findViewById(R.id.informasi);
//
//				latcd = (TextView) findViewById(R.id.latcd);
//				longcd = (TextView) findViewById(R.id.longcd);
//
//				NYA1 = para1.getText().toString();
//				NYA2 = para2.getText().toString();
//				NYA3 = para3.getText().toString();
//				NYA4 = para4.getText().toString();
//				NYA5 = latcd.getText().toString();
//				NYA6 = longcd.getText().toString();
//
//
//					Konfirmasinya() ;
//
//
//			}
//		});
//
//		checkPerms();
//		System.out.println(" cek permition "  );
//		CopyAssets();

	}

//	public void checkPerms() {
//		// Checking if device version > 22 and we need to use new permission model
//		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
//			// Checking if we can draw window overlay
//			if (!Settings.canDrawOverlays(this)) {
//				// Requesting permission for window overlay(needed for all react-native apps)
//				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//						Uri.parse("package:" + getPackageName()));
//				//startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
//			}
//			for(String perm : perms){
//				// Checking each persmission and if denied then requesting permissions
//				if(checkSelfPermission(perm) == PackageManager.PERMISSION_DENIED){
//					requestPermissions(perms, RequestPermissionCode );
//					break;
//				}
//			}
//		}
//	}
//
//	private void Konfirmasinya() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle("Konfimasi....");
//		builder.setMessage("Yakin data sudah benar...???");
//		builder.setCancelable(false);
//		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Cursor dataDSML = MyDB.UploadDSML();
//				if(dataDSML.getCount() == 0 ) {
//					Toast.makeText(UploadDSML_Activity.this , "Data blm/tidak ada...!!!" , Toast.LENGTH_LONG).show();
//				}else
//				{
//					while (dataDSML.moveToNext())
//					{
//
//
//						String query1 = "BKS01.dbo.SI_Tr_Andro_2017 '" + KodeHP2 + "','" +
//								dataDSML.getString(0) + "', '" +
//								dataDSML.getString(1) + "', '" +
//								dataDSML.getString(2) + "', " +
//								dataDSML.getInt(3) + ", '" +
//								dataDSML.getString(4) + "', '" +
//								dataDSML.getString(5) + "', '" +
//								dataDSML.getString(6) + "'" ;
//
//
//
//					}
//				}
//
//				new UploadDSML_Activity.Isi_DSMLnya().execute(PhpNYA1 , NYA1, NYA2, NYA3, NYA4, NYA5, NYA6 );
//
//
//			}
//		});
//
//		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				//Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		builder.show();
//	}
//	private void Photoin(String valKodeTR ) {
//
//		//     System.out.println("Foldre " +  valKodeTR);
//		//    System.out.println(" fff " + valNopel );
//
//		File file = getSDPath(valKodeTR );
//		File asal = new File(file,  "temp.jpg" ) ;
//		System.out.println(asal.toString());
//
//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(asal));
//		startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//
//		//  PadON();
//	}
//
//	private File getSDPath(String TR) {
// 	//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM );
//        //System.out.println(TR);
//
//
//		File folder = new File(DIRApp + File.separator + AppName  );
//
//		//File file = MyApplication.getInstance().getDir("BUZZ",MODE_PRIVATE);
//
//		//final File folder  = new File(new File(Environment.DIRECTORY_DCIM, DIRApp ), AppName);
//
//		if (PermissionHelper.hasWriteStoragePermission(this)) {
//
//		} else {
//			PermissionHelper.requestWriteStoragePermission(this );
//		}
//
///*		if (PermissionHelper.hasLocatioPermission(this)) {
//
//		} else {
//			PermissionHelper.requestLocationPermission(this );
//		}
//*/
//		boolean success = true;
//		if (!folder.exists()) {
//
//			int code = getApplicationContext().getPackageManager().checkPermission(
//					Manifest.permission.WRITE_EXTERNAL_STORAGE,
//					getApplicationContext().getPackageName());
//			if (code == PackageManager.PERMISSION_GRANTED) {
//				// todo create directory
//				success = folder.mkdir();
//				CopyAssets();
//			}
//
//		}
//		if (success) {
//			// Do something on success
//			//         System.out.println("SuksesMK");
//			//   Toast.makeText(PhotoCater.this, "Sukses Create Folder...!!!", Toast.LENGTH_LONG).show();
//		} else {
//			// Do something else on failure
//			Toast.makeText(LaporActivity.this, "Gagal Create Folder...!!!", Toast.LENGTH_LONG).show();
//		}
//
//		return folder; // image_file;
//	}
//
//	private void CopyAssets() {
//		AssetManager assetManager = getAssets();
//		String[] files = null;
//		try {
//			files = assetManager.list("drawable");
//		} catch (IOException e) {
//			Log.e("tag", e.getMessage());
//		}
//
//		for(String filename : files) {
//			System.out.println("File name => "+filename);
//			InputStream in = null;
//			OutputStream out = null;
//			try {
//				in = assetManager.open("@android:drawable/loading.jpg");   // if files resides inside the "Files" directory itself
//				out = new FileOutputStream(DIRApp + File.separator +
//						AppName   +"/" + "temp.jpg");
//				// out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() +"/" + filename);
//				copyFile(in, out);
//				in.close();
//				in = null;
//				out.flush();
//				out.close();
//				out = null;
//			} catch(Exception e) {
//				Log.e("tag", e.getMessage());
//			}
//		}
//	}
//	private void copyFile(InputStream in, OutputStream out) throws IOException {
//		byte[] buffer = new byte[1024];
//		int read;
//		while((read = in.read(buffer)) != -1){
//			out.write(buffer, 0, read);
//		}
//	}
//
//
//	void  getGPS() {
//
//		if (PermissionHelper.hasLocatioPermission(this)) {
//
//		} else {
//			PermissionHelper.requestLocationPermission(this );
//		}
//
//		GpsTracker gt = new GpsTracker(getApplicationContext());
//		Location l = gt.getLocation();
//		if( l == null){
//			Toast.makeText(getApplicationContext(),"Mohon ijinkan Intipasi untuk mengakses lokasi anda",Toast.LENGTH_SHORT).show();
//
//
//		}else {
//
//			double lat = l.getLatitude();
//			double lon = l.getLongitude();
//			System.out.println(String.valueOf(lat));
//			System.out.println(String.valueOf(lon));
//
//			latcd.setText(String.valueOf(lat));
//			longcd.setText(String.valueOf(lon));
//
//		//	Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//		if (resultCode == RESULT_OK) {
//
//			if (requestCode == 7) {
//				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//				photo.setImageBitmap(bitmap);
//
//				 getGPS();
//			}
//
//			if (requestCode == REQUEST_IMAGE_CAPTURE) {
//				//        String path = new File(DIRApp + File.separator +
//				//                APPName + File.separator + GloTRNya + File.separator + GloNopelNya + ".jpg").toString();
//				//        imgPhotoSurvey.setImageDrawable(Drawable.createFromPath(path));
//
//
//				String strOldName = DIRApp + File.separator +
//						AppName + File.separator + "temp.jpg";
//				String strNewName = DIRApp + File.separator +
//						AppName + File.separator + "laporan.jpg";
//				//   String NewPath = strSDCardPathName + strNewName;
//
//				System.out.println(strOldName);
//				System.out.println(strNewName);
//				try {
//					ResizeImages(strOldName, strNewName);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//
//				Bitmap bitmap = BitmapFactory.decodeFile(strNewName);
//				photo.setImageBitmap(bitmap);
//				getGPS();
//				//PadON();
//
//			}
//		}
///*
//		if( requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK ){
//			try {
//
//				FileInputStream in = new FileInputStream(destination);
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inSampleSize = 10;
//				imagePath = destination.getAbsolutePath();
//				Log.d("INFO", "PATH === " +imagePath);
//				//tvPath.setText(imagePath);
//				Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
//				photo.setImageBitmap(bmp);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//
//		}*/
//	}
//
//	private class Isi_DSMLnya extends AsyncTask<String, String, JSONObject>
//	{
//		JSONParser jParser = new JSONParser();
//		private JSONObject json;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... args)
//		{
//
//			//	namainfo, alamatinfo, hpinfo, inform,
//			String cphpnya = args[0];
//			String cpm_cd = args[1];
//			String ctglcatat = args[2];
//			String cnopel 	= args[3];
//			String cnohp = args[4];
//			String cakhir = args[5];
//			String cbad_cd = args[6];
//			String clat_cd = args[7];
//			String clong_cd = args[8];
//			String data;
//			String link;
//			//plgModels.clear();
//			try {
//				data =  cphpnya ;
//				data += "?pm_cd=" + URLEncoder.encode(cpm_cd, "UTF-8");
//				data += "&tglcatat=" + URLEncoder.encode(ctglcatat, "UTF-8");
//				data += "&nopel=" + URLEncoder.encode(cnopel, "UTF-8");
//				data += "&nohp=" + URLEncoder.encode(cnohp, "UTF-8");
//				data += "&akhir=" + URLEncoder.encode(cakhir, "UTF-8");
//				data += "&bad_cd=" + URLEncoder.encode(cbad_cd, "UTF-8");
//				data += "&lat_cd=" + URLEncoder.encode(clat_cd, "UTF-8");
//				data += "&long_cd=" + URLEncoder.encode(clong_cd, "UTF-8");
//
//				link = data;
//
//				System.out.println("6666666");
//				System.out.println(link);
//				json = jParser.getJSONFromUrl(link );
//
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//
//			return json;
//
//		}
//
//		@Override
//		protected void onPostExecute(JSONObject json)
//		{
//
//			try {
//
//				lppes = json.getJSONArray(TAG_ARRAY);
//				System.out.println(lppes.length());
//				if (lppes.length() < 1) {
//					//Toast.makeText(getApplicationContext(), "DRD sudah lunas / Pelanggan tidak ada...!!!", Toast.LENGTH_LONG).show();
//					Toast toast =
//							Toast.makeText(getApplicationContext(), "DRD sudah lunas / Pelanggan tidak ada...!!!", Toast.LENGTH_LONG
//							);
//					toast.setGravity(Gravity.CENTER,0,0);
//					toast.show();
//				}
//
//				for(int i = 0; i < lppes.length(); i++)
//				{
//					JSONObject c = lppes.getJSONObject(i);
//					String cticket = c.getString(TAG_KODKEY);
//					HashMap<String, String> map = new HashMap<String, String>();
//					map.put(TAG_KODKEY, cticket);
//
//					final Dialog dialog = new Dialog(LaporActivity.this);
//					dialog.setContentView(R.layout.dialog_mess);
//					dialog.setTitle( "" );
//
//					TextView text = (TextView) dialog.findViewById(R.id.text);
//					text.setText("Terima Kasih atas Informasinya" + "\n" +  "No Ticket anda adalah : " +  "\n" +  cticket );
//					text.setScroller(new Scroller(LaporActivity.this));
//					text.setVerticalScrollBarEnabled(true);
//					text.setMovementMethod(new ScrollingMovementMethod());
//
//					Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//					dialogButton.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//
//							para1 = (EditText) findViewById(R.id.namainfo);
//							para2 = (EditText) findViewById(R.id.alamatinfo);
//							para3 = (EditText) findViewById(R.id.nohpinfo);
//							para4 = (EditText) findViewById(R.id.informasi);
//
//							latcd = (TextView) findViewById(R.id.latcd);
//							longcd = (TextView) findViewById(R.id.longcd);
//
//							 para1.setText("") ;
//							 para2.setText("") ;
//							 para3.setText("") ;
//							 para4.setText("") ;
//							 latcd.setText("") ;
//							 longcd.setText("") ;
//
//							dialog.dismiss();
//						}
//					});
//
//					String strOldName = DIRApp + File.separator +
//							AppName + File.separator + "laporan.jpg";
//					String strNewName = DIRApp + File.separator +
//							AppName + File.separator + cticket + ".jpg";
//
//					try {
//						ResizeImages(strOldName, strNewName);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//
//					uploading(strNewName);
//
//					dialog.show();
//
//
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
//
//	void ResizeImages(String sPath, String sTo) throws IOException {
//
//		LebarPhoto = "300";
//		TinggiPhoto = "300";
//		SizePhoto = "100";
//		System.out.println(LebarPhoto);
//		System.out.println(TinggiPhoto);
//		System.out.println(SizePhoto);
//
//		//Bitmap out = Bitmap.createScaledBitmap(bMap,  lebar, tinggi, false);
//
//		Bitmap photo = BitmapFactory.decodeFile(sPath);
//
//		photo = Bitmap.createScaledBitmap(photo, Integer.parseInt(LebarPhoto), Integer.parseInt(TinggiPhoto), false);
//		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//		photo.compress(Bitmap.CompressFormat.JPEG, Integer.parseInt(SizePhoto), bytes);
//
//		File f = new File(sTo);
//		f.createNewFile();
//		FileOutputStream fo = new FileOutputStream(f);
//		fo.write(bytes.toByteArray());
//		fo.close();
//
//		File fileApus =  new File(sPath);
//		fileApus.delete();
//	}
//
//	void uploading(String filenya) {
//		File imagefile = new File(filenya);
//		RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);
//		MultipartBody.Part partImage =
//				MultipartBody.Part.createFormData("imageupload", imagefile.getName(),reqBody);
//
//		ApiServices api = RetroClient.getApiServices();
//		Call<ResponseApiModel> upload = api.uploadImage(partImage);
//		upload.enqueue(new Callback<ResponseApiModel>() {
//			@Override
//			public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
//				//pd.dismiss();
//				Log.d("RETRO", "ON RESPONSE  : " + response.body().toString());
//
//				if(response.body().getKode().equals("1"))
//				{
//					Toast.makeText(LaporActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
//				}else
//				{
//					Toast.makeText(LaporActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
//
//				}
//			}
//
//			@Override
//			public void onFailure(Call<ResponseApiModel> call, Throwable t) {
//				Log.d("RETRO", "ON FAILURE : " + t.getMessage());
//				//pd.dismiss();
//			}
//		});
//	}
//
//	private class SetupMenu extends AsyncTask<String, String, JSONObject>
//	{
//
//		JSONParser jParser = new JSONParser();
//		private JSONObject json;
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//
//		@Override
//		protected JSONObject doInBackground(String... args)
//		{
//
//			String phpnya = args[0];
//			String nopenya = args[1];
//			String data;
//			String link;
//			try {
//				data =  phpnya ;
//				data +=  URLEncoder.encode(nopenya, "UTF-8");
//				link = data;
//				json = jParser.getJSONFromUrl(link );
//
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//
//			return json;
//
//		}
//
//		@Override
//		protected void onPostExecute(JSONObject json)
//		{
//
//			try {
//
//				menuss = json.getJSONArray(TAG_ARRAYm);
//
//				for(int i = 0; i < menuss.length(); i++)
//				{
//					JSONObject c = menuss.getJSONObject(i);
//					System.out.println(TAG_LINKm);
//					String sCode = c.getString(TAG_CDm);
//					String sLink = c.getString(TAG_LINKm);
//
//					// Adding value HashMap key => value
//					HashMap<String, String> map = new HashMap<String, String>();
//					map.put(TAG_CDm, sCode);
//					map.put(TAG_LINKm, sLink);
//
//					CodeNYA = (map.get("menu_cd"));
//					LinkNYA = (map.get("menu_link"));
//					PhpNYA1 = LinkNYA;
//
//					final Controller globalVariable = (Controller) getApplicationContext();
//					globalVariable.setgPHPNYA(LinkNYA) ;
//					System.out.println("11111111111111");
//					System.out.println(PhpNYA1);
//
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
