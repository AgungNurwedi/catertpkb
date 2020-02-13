package com.tpkb.mascater;

/**
 * Created by Agung on 24-11-2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListAdapterCater extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<DataCater> objects;
    private Context mContext1;
    private LayoutInflater inflater;
    private List<DataCater> dataSurveys = null;
    private ArrayList<DataCater> caridataCaters ;

    ListAdapterCater(Context context, ArrayList<DataCater> dataSurveys) {
        ctx = context;
        objects = dataSurveys;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mContext1 = context;
        inflater = LayoutInflater.from(mContext1);
        this.caridataCaters = new ArrayList<DataCater>();
        this.caridataCaters.addAll(dataSurveys);

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.row_cater, parent, false);
        }

        DataCater p = getSurvey(position);
        ((ImageView) view.findViewById(R.id.imgkode)).setImageResource(p.imagesur);
        ((TextView) view.findViewById(R.id.ltxnopel)).setText(p.Data_Nopel );
        ((TextView) view.findViewById(R.id.ltxnama)).setText(p.Data_Nama);
        ((TextView) view.findViewById(R.id.ltxalamat)).setText(p.Data_Alamat);
        ((TextView) view.findViewById(R.id.ltxalat)).setText(p.Data_Lat_CD);
        ((TextView) view.findViewById(R.id.ltxalong)).setText(p.Data_Long_CD);
        ((TextView) view.findViewById(R.id.ltxtrid)).setText(p.Data_ID);
        ((TextView) view.findViewById(R.id.ltxtarif_cd)).setText(p.Data_Tarif_CD);
        ((TextView) view.findViewById(R.id.ltxtarif_nohp)).setText(p.Data_No_HP);
        ((TextView) view.findViewById(R.id.ltxawal)).setText(p.Data_Awal);
        ((TextView) view.findViewById(R.id.ltxakhir)).setText(p.Data_Akhir);
        ((TextView) view.findViewById(R.id.ltxkubik)).setText(p.Data_Kubik);
        ((TextView) view.findViewById(R.id.ltxnomet)).setText(p.Data_Nometer);
        ((TextView) view.findViewById(R.id.ltxawal)).setText(p.Data_Awal);
        ((TextView) view.findViewById(R.id.ltxakhir)).setText(p.Data_Akhir);
        ((TextView) view.findViewById(R.id.ltxkubik)).setText(p.Data_Kubik);
        ((TextView) view.findViewById(R.id.ltxnominal)).setText(p.Data_Nominal);
        ((TextView) view.findViewById(R.id.ltxpiutang)).setText(p.Data_Piutang);
        ((TextView) view.findViewById(R.id.ltxMinim)).setText(p.Data_Minim);
        ((TextView) view.findViewById(R.id.ltxtotal)).setText(p.Data_Total);
        ((TextView) view.findViewById(R.id.ltxbad_cd)).setText(p.Data_Bad_CD);
        ((TextView) view.findViewById(R.id.ltxDigit)).setText(p.Data_Digit);
        ((TextView) view.findViewById(R.id.ltxtgalctt)).setText(p.Data_Tgal_Catat);
        ((TextView) view.findViewById(R.id.ltxtarif1)).setText(p.Data_Tarif1);
        ((TextView) view.findViewById(R.id.ltxtarif2)).setText(p.Data_Tarif2);
        ((TextView) view.findViewById(R.id.ltxangsuran)).setText(p.Data_Angsuran);
        ((TextView) view.findViewById(R.id.ltxtadminstrasi)).setText(p.Data_Administrasi);


        TextView surnopel = (TextView) view.findViewById(R.id.ltxnopel);
        surnopel.setTag(position);

        return view;
    }

    DataCater getSurvey(int position) {
        return ((DataCater) getItem(position));
    }
    ArrayList<DataCater> getsurnopel() {
        ArrayList<DataCater> boxsurvey = new ArrayList<DataCater>();
        for (DataCater p : objects) {
 //           if (p.box)
 //           System.out.println("boboboboxx");
            boxsurvey.add(p);
        }
        return boxsurvey;
    }


    public void filter(String charText1) {

        charText1 = charText1.toLowerCase(Locale.getDefault());
        //     System.out.println(charText);

        objects.clear();
     //   System.out.println("lwat.....");
        if (charText1.length() == 0) {
            objects.addAll(caridataCaters);
       //         System.out.println("clear");
        } else {
            for (DataCater postDetail1 : caridataCaters) {
                if (charText1.length() != 0 && postDetail1.getData_Nama().toLowerCase(Locale.getDefault()).contains(charText1)) {
       //                 System.out.println("clear111");
                    objects.add(postDetail1);
                } else if (charText1.length() != 0 && postDetail1.getData_Alamat().toLowerCase(Locale.getDefault()).contains(charText1)) {
       //                 System.out.println("clear2222");
                    objects.add(postDetail1);
                } else if (charText1.length() != 0 && postDetail1.getData_Nopel().toLowerCase(Locale.getDefault()).contains(charText1)) {
        //            System.out.println("clear2222333");
                    objects.add(postDetail1);
                }
            }
        }
        //    System.out.println("nyasar....");
        notifyDataSetChanged();
    }
}