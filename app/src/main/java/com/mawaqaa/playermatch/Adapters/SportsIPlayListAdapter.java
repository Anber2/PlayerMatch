package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.SportsIPlayListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

import static com.mawaqaa.playermatch.R.id.textView_sportIPlay;

/**
 * Created by HP on 10/11/2017.
 */

public class SportsIPlayListAdapter extends BaseAdapter {
    private String TAG = "SportsIPlayListAdapter";

    public static ArrayList<Boolean> status = new ArrayList<Boolean>();
    private static LayoutInflater inflater = null;
    Context context;
    //
    ArrayList<SportsIPlayListData> sportsIPlayListDataArrayList;
    SportsIPlayListData sportsIPlayListData;


    public SportsIPlayListAdapter(Context context, ArrayList<SportsIPlayListData> sportsIPlayListDataArrayList) {
        this.context = context;
        this.sportsIPlayListDataArrayList = sportsIPlayListDataArrayList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < sportsIPlayListDataArrayList.size(); i++) {
            status.add(false);
        }
    }

    @Override
    public int getCount() {
        return sportsIPlayListDataArrayList.size();
    }

    @Override
    public SportsIPlayListData getItem(int i) {
        SportsIPlayListData sportsIPlayListData = sportsIPlayListDataArrayList.get(i);
        return sportsIPlayListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_sports_i_play_list_item, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }


        vh.checkBox_sportIPlay_item = view.findViewById(R.id.checkBox_sportIPlay_item);
        vh.textView_sportIPlay = view.findViewById(textView_sportIPlay);

        vh.checkBox_sportIPlay_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    status.set(i, true);
                } else {
                    status.set(i, false);
                }
            }
        });
        vh.checkBox_sportIPlay_item.setChecked(status.get(i));

        vh.textView_sportIPlay.setText(sportsIPlayListDataArrayList.get(i).getSportName());


        return view;
    }


    public static class ViewHolder {


        CheckBox checkBox_sportIPlay_item;
        TextView textView_sportIPlay;
    }
}
