package com.mawaqaa.playermatch.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mawaqaa.playermatch.Adapters.GameListAdapter;
import com.mawaqaa.playermatch.Data.GameListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/11/2017.
 */

public class GamesPendingFragment extends Fragment  implements AdapterView.OnItemClickListener{

    String TAG = "GamesPendingFragment";

    //Layout contents
    ListView listView_game;

    //Games
    GameListAdapter gameListAdapter;
    ArrayList<GameListData> gameListDataArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);
        initview(v);

        setGamesList();


        return v;
    }

    private void setGamesList() {

        gameListDataArrayList = new ArrayList<GameListData>();

        gameListDataArrayList.add(new GameListData("0","Sport Name 1","Sport Date 1","Location Name 1","0"));
        gameListDataArrayList.add(new GameListData("0","Sport Name 2","Sport Date 2","Location Name 2","0"));
        gameListDataArrayList.add(new GameListData("0","Sport Name 3","Sport Date 3","Location Name 3","0"));
        gameListDataArrayList.add(new GameListData("0","Sport Name 4","Sport Date 4","Location Name 4","0"));
        gameListDataArrayList.add(new GameListData("0","Sport Name 5","Sport Date 5","Location Name 5","0"));

        gameListAdapter = new GameListAdapter(getActivity(), gameListDataArrayList);
        listView_game.setAdapter(gameListAdapter);
        gameListAdapter.notifyDataSetChanged();

        listView_game.setOnItemClickListener(this);

    }

    public void loadThisFragment(Fragment fragment) {


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();


    }

    private void initview(View v) {

        listView_game = v.findViewById(R.id.listView_game);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
