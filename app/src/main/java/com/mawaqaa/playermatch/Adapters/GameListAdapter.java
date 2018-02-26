package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mawaqaa.playermatch.Constants.AppConstants;
import com.mawaqaa.playermatch.Data.GameListData;
import com.mawaqaa.playermatch.Fragments.CalenderFragment;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

import static com.mawaqaa.playermatch.Fragments.CalenderFragment.isCreatedGamesCalled;

/**
 * Created by HP on 10/12/2017.
 */

public class GameListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Fragment fragment;
    ArrayList<GameListData> gameListDataArrayList;
    GameListData gameListData;
    private String TAG = "GameListAdapter";

    public GameListAdapter(Context context, ArrayList<GameListData> gameListDataArrayList) {
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

        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_game_list_item, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.imageView2 = view.findViewById(R.id.imageView2);
        vh.textView_sportName = view.findViewById(R.id.textView_sportName);
        vh.textView_gameDate = view.findViewById(R.id.textView_gameDate);
        vh.textView_gameLocationName = view.findViewById(R.id.textView_gameLocationName);
        vh.textView_daysToGo = view.findViewById(R.id.textView_daysToGo);

        vh.button_calender_listOfGames_delete = view.findViewById(R.id.button_calender_listOfGames_delete);

        vh.textView_sportName.setText(gameListDataArrayList.get(i).getSportName());
        vh.textView_gameDate.setText(gameListDataArrayList.get(i).getSportDate());
        vh.textView_gameLocationName.setText(gameListDataArrayList.get(i).getSportLocationName());
        vh.textView_daysToGo.setText(gameListDataArrayList.get(i).getSportDaysToGo());

        if (isCreatedGamesCalled) {

            vh.imageView2.setVisibility(View.VISIBLE);
            vh.button_calender_listOfGames_delete.setVisibility(View.VISIBLE);
        } else {
            vh.imageView2.setVisibility(View.INVISIBLE);
            vh.button_calender_listOfGames_delete.setVisibility(View.INVISIBLE);
        }

        vh.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConstants.matchId = gameListDataArrayList.get(i).getGameID();

                CalenderFragment.yourDesiredMethod(i);
            }
        });

        vh.button_calender_listOfGames_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete game ... ", Toast.LENGTH_SHORT).show();

                CalenderFragment.yourDesiredMethod2(gameListDataArrayList.get(i).getGameID());

            }
        });


        return view;
    }


    public static class ViewHolder {

        Button button_calender_listOfGames_delete;
        ImageView imageView2;
        LinearLayout linearLayout_gameListItem;
        TextView textView_sportName, textView_gameDate, textView_gameLocationName, textView_daysToGo;
    }

}
