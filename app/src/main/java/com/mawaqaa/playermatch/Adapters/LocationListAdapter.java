package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.LocationListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/19/2017.
 */

public class LocationListAdapter extends BaseAdapter {

    private String TAG = "LocationListAdapter";

    Context context;
    ArrayList<LocationListData> locationListDataArrayList;
    LocationListData locationListData;
    private static LayoutInflater inflater = null;

    public LocationListAdapter(Context context, ArrayList<LocationListData> locationListDataArrayList) {
        this.context = context;
        this.locationListDataArrayList = locationListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return locationListDataArrayList.size();
    }

    @Override
    public LocationListData getItem(int i) {

        LocationListData locationListData = locationListDataArrayList.get(i);

        return locationListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.z_nationality_list_item, null);
            vh = new ViewHolder();
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.textView_NationalityName_item = view.findViewById(R.id.textView_NationalityName_item);

        vh.textView_NationalityName_item.setText(locationListDataArrayList.get(i).getLocationName());


        return view;
    }

    private class ViewHolder {


        TextView textView_NationalityName_item;
    }

}
