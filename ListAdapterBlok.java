package com.tpkb.mascater;

/**
 * Created by Agung on 24-11-2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListAdapterBlok extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<DataBlok> objects;
    Context mContext;
    LayoutInflater inflater;
    private List<DataBlok> dataBloks = null;
    private ArrayList<DataBlok> arraylist;

    ListAdapterBlok(Context context, ArrayList<DataBlok> dataBloks) {
        ctx = context;
        objects = dataBloks;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mContext = context;
//        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<DataBlok>();
        this.arraylist.addAll(dataBloks);

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
            view = lInflater.inflate(R.layout.list_blok, parent, false);
        }

        DataBlok p = getProduct(position);
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
        ((TextView) view.findViewById(R.id.tvPrice)).setText(p.kode);
      //  ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(p.image);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(p.box);
        return view;
    }

    DataBlok getProduct(int position) {
        return ((DataBlok) getItem(position));
    }
    ArrayList<DataBlok> getBox() {
        ArrayList<DataBlok> box = new ArrayList<DataBlok>();
        for (DataBlok p : objects) {
            System.out.println("boboboboxx");
            if (p.box)
                box.add(p);
        }
        return box;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
   //     System.out.println(charText);

        objects.clear();
    //    System.out.println("lwat.....");
        if (charText.length() == 0) {
            objects.addAll(arraylist);
        //    System.out.println("clear");
        } else {
            for (DataBlok postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getKode().toLowerCase(Locale.getDefault()).contains(charText)) {
                //    System.out.println("clear111");
                    objects.add(postDetail);
                } else if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                //    System.out.println("clear2222");
                    objects.add(postDetail);
                }
            }
        }
    //    System.out.println("nyasar....");
        notifyDataSetChanged();
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };
}