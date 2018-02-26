package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.GameListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 1/16/2018.
 */

public class SelectMatchListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Fragment fragment;
    ArrayList<GameListData> gameListDataArrayList;
    GameListData gameListData;
    private String TAG = "GameListAdapter";

    public static String selectedMatchId;

    public SelectMatchListAdapter(Context context, ArrayList<GameListData> gameListDataArrayList) {
        this.context = context;
        this.gameListDataArrayList = gameListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gameListDataArrayList.size();
    }

    @Override
    public GameListData getItem(int i) {

        GameListData gameListData = gameListDataArrayList.get(i);

        return gameListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final SelectMatchListAdapter.ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_select_match_list_item, null);

            vh = new SelectMatchListAdapter.ViewHolder();


            view.setTag(vh);

        } else {
            vh = (SelectMatchListAdapter.ViewHolder) view.getTag();
        }

        vh.textView_sportName = view.findViewById(R.id.textView_sportName_selectMatch);
        vh.textView_gameDate = view.findViewById(R.id.textView_gameDate_selectMatch);
        vh.textView_gameLocationName = view.findViewById(R.id.textView_gameLocationName_selectMatch);
        vh.textView_daysToGo = view.findViewById(R.id.textView_daysToGo_selectMatch);
        vh.checkBox_selectMatch = view.findViewById(R.id.checkBox_selectMatch);

        vh.textView_sportName.setText(gameListDataArrayList.get(i).getSportName());
        vh.textView_gameDate.setText(gameListDataArrayList.get(i).getSportDate());
        vh.textView_gameLocationName.setText(gameListDataArrayList.get(i).getSportLocationName());
        vh.textView_daysToGo.setText(gameListDataArrayList.get(i).getSportDaysToGo());

        vh.checkBox_selectMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedMatchId = gameListDataArrayList.get(i).getGameID();

            }
        });


        return view;
    }


    private class ViewHolder {

        CheckBox checkBox_selectMatch;

        TextView textView_sportName, textView_gameDate, textView_gameLocationName, textView_daysToGo;
    }

}
