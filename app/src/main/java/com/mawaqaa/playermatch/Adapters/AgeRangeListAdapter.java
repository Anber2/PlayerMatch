package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.AgeRangeListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/11/2017.
 */

public class AgeRangeListAdapter extends BaseAdapter {

    private String TAG = "AgeRangeListAdapter";

    Context context;
    ArrayList<AgeRangeListData> ageRangeListDataArrayList;
    AgeRangeListData ageRangeListData;
    private static LayoutInflater inflater = null;

    public AgeRangeListAdapter(Context context, ArrayList<AgeRangeListData> ageRangeListDataArrayList) {
        this.context = context;
        this.ageRangeListDataArrayList = ageRangeListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ageRangeListDataArrayList.size();
    }

    @Override
    public AgeRangeListData getItem(int i) {

        AgeRangeListData ageRangeListData = ageRangeListDataArrayList.get(i);

        return ageRangeListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_age_range_list_item, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.textView_ageRange_Item = view.findViewById(R.id.textView_ageRange_Item);

        vh.textView_ageRange_Item.setText(ageRangeListDataArrayList.get(i).getAgeRangeName());

        return view;
    }

    private class ViewHolder {


         TextView textView_ageRange_Item;
    }
}
