package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.playermatch.Activities.SignInActivity;
import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.ListOFGamesListData;
import com.mawaqaa.playermatch.Fragments.ListOFGamesFragment;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;

import java.util.ArrayList;

/**
 * Created by HP on 10/17/2017.
 */

public class ListOFGamesAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<ListOFGamesListData> listOFGamesListDataArrayList;
    ListOFGamesListData listOFGamesListData;
    //TAG
    private String TAG = "GameListAdapter";

    public ListOFGamesAdapter(Context context, ArrayList<ListOFGamesListData> listOFGamesListDataArrayList) {
        this.context = context;
        this.listOFGamesListDataArrayList = listOFGamesListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listOFGamesListDataArrayList.size();
    }

    @Override
    public ListOFGamesListData getItem(int i) {

        ListOFGamesListData listOFGamesListData = listOFGamesListDataArrayList.get(i);
        return listOFGamesListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_list_of_games__list_item, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.imageView_ListOFGames_arrowList = view.findViewById(R.id.imageView_ListOFGames_arrowList);

        vh.button_ListOFGames_joinGame = view.findViewById(R.id.button_ListOFGames_joinGame);
        
        vh.textView_ListOFGames_date = view.findViewById(R.id.textView_ListOFGames_date);
        vh.textView_ListOFGames_time = view.findViewById(R.id.textView_ListOFGames_time);
        vh.textView_ListOFGames_location = view.findViewById(R.id.textView_ListOFGames_location);
        vh.textView_ListOFGames_playersJoined = view.findViewById(R.id.textView_ListOFGames_playersJoined);
        vh.textView_ListOFGames_playersRequaier = view.findViewById(R.id.textView_ListOFGames_playersRequaier);

        vh.textView_ListOFGames_date.setText(listOFGamesListDataArrayList.get(i).getListOFGamesData());
        vh.textView_ListOFGames_time.setText(listOFGamesListDataArrayList.get(i).getListOFGamesTime());
        vh.textView_ListOFGames_location.setText(listOFGamesListDataArrayList.get(i).getListOFGameslocationName());
        vh.textView_ListOFGames_playersJoined.setText(listOFGamesListDataArrayList.get(i).getListOFGamesPlayersJoined());
        vh.textView_ListOFGames_playersRequaier.setText(listOFGamesListDataArrayList.get(i).getListOFGamesPlayersTotal());

        vh.imageView_ListOFGames_arrowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppConstants.matchId = listOFGamesListDataArrayList.get(i).getListOFGamesId();

                ListOFGamesFragment.yourDesiredMethod();

            }
        });

        vh.button_ListOFGames_joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Joining request is being handled", Toast.LENGTH_SHORT).show();

                if (PreferenceUtil.isUserSignedIn(context)) {
                    ListOFGamesFragment. joinGame(listOFGamesListDataArrayList.get(i).getListOFGamesId());

                } else {
                    Intent ii = new Intent(context, SignInActivity.class);
                    context. startActivity(ii);
                    //context.overridePendingTransition(0, 0);
                }
            }
        });

        return view;
    }

    private class ViewHolder {

        ImageView imageView_ListOFGames_arrowList;
        Button button_ListOFGames_joinGame;

        TextView textView_ListOFGames_date, textView_ListOFGames_time, textView_ListOFGames_location, textView_ListOFGames_playersJoined, textView_ListOFGames_playersRequaier;
    }

}
