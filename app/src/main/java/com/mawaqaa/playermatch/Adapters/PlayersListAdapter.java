package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.playermatch.Data.PlayersListData;
import com.mawaqaa.playermatch.Fragments.PlayersListFragment;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 2/1/2018.
 */

public class PlayersListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<PlayersListData> playersListDataArrayList;
    PlayersListData playersListData;
    private String TAG = "PlayersListAdapter";

    public PlayersListAdapter(Context context, ArrayList<PlayersListData> playersListDataArrayList) {
        this.context = context;
        this.playersListDataArrayList = playersListDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return playersListDataArrayList.size();
    }

    @Override
    public PlayersListData getItem(int i) {

        PlayersListData playersListData = playersListDataArrayList.get(i);

        return playersListData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final PlayersListAdapter.ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.z_players_list_item, null);
            vh = new PlayersListAdapter.ViewHolder();
            view.setTag(vh);
        } else {
            vh = (PlayersListAdapter.ViewHolder) view.getTag();
        }

        vh.textView_playersList_playerName = view.findViewById(R.id.textView_playersList_playerName);
        vh.textView_playersList_phone = view.findViewById(R.id.textView_playersList_phone);
        vh.textView_playersList_status = view.findViewById(R.id.textView_playersList_status);
        vh. buttonCancel= view.findViewById(R.id.button_playersList_cancel);
        vh.textView_playersList_status.setText(playersListDataArrayList.get(i).getPlayerListStatus());

        vh.textView_playersList_phone.setText(playersListDataArrayList.get(i).getPlayerListPhone());
        vh.textView_playersList_playerName.setText(playersListDataArrayList.get(i).getPlayerListName());

        vh.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Cancel player ... ",Toast.LENGTH_SHORT).show();

                PlayersListFragment.yourDesiredMethod2(playersListDataArrayList.get(i).getPlayerListId());
            }
        });


        return view;
    }

    private class ViewHolder {

        TextView textView_playersList_playerName, textView_playersList_phone, textView_playersList_status;
        Button buttonCancel;

    }
}
