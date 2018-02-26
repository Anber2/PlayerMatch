package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.NationalityListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/11/2017.
 */

public class NationalityListAdapter extends BaseAdapter {

    private String TAG = "NationalityListAdapter";

    Context context;
    ArrayList<NationalityListData> nationalityListDataArrayList;
    NationalityListData nationalityListData;
    private static LayoutInflater inflater = null;

    public NationalityListAdapter(Context context, ArrayList<NationalityListData> nationalityListDataArrayList) {
        this.context = context;
        this.nationalityListDataArrayList = nationalityListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nationalityListDataArrayList.size();
    }

    @Override
    public NationalityListData getItem(int i) {

        NationalityListData nationalityListData = nationalityListDataArrayList.get(i);


        return nationalityListData;
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

        vh.textView_NationalityName_item.setText(nationalityListDataArrayList.get(i).getNationalityName());


        return view;
    }

    private class ViewHolder {


         TextView textView_NationalityName_item;
    }
}
