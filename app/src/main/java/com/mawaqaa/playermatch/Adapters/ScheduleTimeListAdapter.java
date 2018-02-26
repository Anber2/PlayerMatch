package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.ScheduleTimeListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleTimeListAdapter extends BaseAdapter {


    private String TAG = "ScheduleTimeListAdapter";

    Context context;
    ArrayList<ScheduleTimeListData> scheduleTimeListDataArrayList;
    ScheduleTimeListData scheduleTimeListData;
    private static LayoutInflater inflater = null;

    public ScheduleTimeListAdapter(Context context, ArrayList<ScheduleTimeListData> scheduleTimeListDataArrayList) {
        this.context = context;
        this.scheduleTimeListDataArrayList = scheduleTimeListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scheduleTimeListDataArrayList.size();
    }

    @Override
    public ScheduleTimeListData getItem(int i) {
        ScheduleTimeListData scheduleTimeListData = scheduleTimeListDataArrayList.get(i);
        return scheduleTimeListData;
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

        vh.textView_NationalityName_item.setText(scheduleTimeListDataArrayList.get(i).getTimeText());


        return view;
    }

    private class ViewHolder {


        TextView textView_NationalityName_item;
    }
}
