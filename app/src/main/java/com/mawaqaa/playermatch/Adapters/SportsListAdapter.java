package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.SportsListData;
import com.mawaqaa.playermatch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by HP on 10/10/2017.
 */

public class SportsListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<SportsListData> sportsListDataArrayList;
    SportsListData sportsListData;
    private String TAG = "SportsListAdapter";

    public SportsListAdapter(Context context, ArrayList<SportsListData> sportsListDataArrayList) {
        this.context = context;
        this.sportsListDataArrayList = sportsListDataArrayList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sportsListDataArrayList.size();
    }

    @Override
    public SportsListData getItem(int i) {
        SportsListData sportsListData = sportsListDataArrayList.get(i);


        return sportsListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.z_sports_list_item, null);
            vh = new ViewHolder();
           view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.RelativeLayout_Sport = view.findViewById(R.id.RelativeLayout_Sport);
        vh.textView_SportName = view.findViewById(R.id.textView_SportName);
        vh.imageView_arrow = view.findViewById(R.id.imageView_arrow);
        vh.imageView_sportsListImage = view.findViewById(R.id.imageView_sportsListImage);

        vh.textView_SportName.setText(sportsListDataArrayList.get(i).getSportName());
        //vh.imageView_sportsListImage.setImageResource(0x7f02005a + i);

        Picasso.with(context).load(sportsListDataArrayList.get(i).getSportImage())
                .into(vh.imageView_sportsListImage);
        return view;
    }

    private class ViewHolder {
        RelativeLayout RelativeLayout_Sport;
        ImageView imageView_arrow, imageView_sportsListImage;
        TextView textView_SportName;
    }
}
