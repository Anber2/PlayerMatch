package com.mawaqaa.playermatch.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mawaqaa.playermatch.Activities.MainActivity;
import com.mawaqaa.playermatch.Adapters.GameListAdapter;
import com.mawaqaa.playermatch.Data.GameListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/11/2017.
 */

public class GamesCreatedFragment extends Fragment  {
    static String TAG = "GamesCreatedFragment";
    private static MainActivity myContext;
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

        gameListDataArrayList.add(new GameListData("0", "Sport Name 1", "Sport Date 1", "Location Name 1", "0"));
        gameListDataArrayList.add(new GameListData("0", "Sport Name 2", "Sport Date 2", "Location Name 2", "0"));
        gameListDataArrayList.add(new GameListData("0", "Sport Name 3", "Sport Date 3", "Location Name 3", "0"));
        gameListDataArrayList.add(new GameListData("0", "Sport Name 4", "Sport Date 4", "Location Name 4", "0"));
        gameListDataArrayList.add(new GameListData("0", "Sport Name 5", "Sport Date 5", "Location Name 5", "0"));

        gameListAdapter = new GameListAdapter(myContext, gameListDataArrayList);
//        gameListAdapter.notifyDataSetChanged();

        listView_game.setAdapter(gameListAdapter);
        //listView_game.setOnItemClickListener(this);


    }

    public static void yourDesiredMethod(int i) {
       // myContext.loadThisFragment(new GameDetailsFragment());
        Log.d(TAG," yourDesiredMethod ");


    }
    @Override
    public void onAttach(Activity activity) {
        myContext = (MainActivity) activity;
        super.onAttach(activity);
    }
    private void initview(View v) {

        listView_game = v.findViewById(R.id.listView_game);


    }

    /*@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        loadThisFragment(new GameDetailsFragment());

    }*/

    public void loadThisFragment(Fragment fragment) {


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();


    }
}
