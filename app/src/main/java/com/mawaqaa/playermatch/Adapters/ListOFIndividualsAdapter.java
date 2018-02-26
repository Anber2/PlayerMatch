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

import com.mawaqaa.playermatch.Activities.MainActivity;
import com.mawaqaa.playermatch.Activities.SignInActivity;
import com.mawaqaa.playermatch.Data.ListOFIndividualsData;
import com.mawaqaa.playermatch.Fragments.ListOFIndividualsFragment;
import com.mawaqaa.playermatch.R;
import com.mawaqaa.playermatch.Utilities.PreferenceUtil;

import java.util.ArrayList;

/**
 * Created by HP on 10/17/2017.
 */

public class ListOFIndividualsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<ListOFIndividualsData> listOFIndividualsDataArrayList;
    ListOFIndividualsData listOFIndividualsData;
    //TAG
    private String TAG = "ListOFIndividualsAdapter";
    //
    private static MainActivity myContext;

    public static String IndividualId;


    public ListOFIndividualsAdapter(Context context, ArrayList<ListOFIndividualsData> listOFIndividualsDataArrayList) {
        this.context = context;
        this.listOFIndividualsDataArrayList = listOFIndividualsDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       // myContext = (MainActivity)  context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return listOFIndividualsDataArrayList.size();
    }

    @Override
    public ListOFIndividualsData getItem(int i) {
        ListOFIndividualsData listOFIndividualsData = listOFIndividualsDataArrayList.get(i);

        return listOFIndividualsData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_list_of_individuals_item, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.imageView_ListOFIndividuals_arrowList = view.findViewById(R.id.imageView_ListOFIndividuals_arrowList);

        vh.button_ListOFIndividuals_invitePlayer = view.findViewById(R.id.button_ListOFIndividuals_invitePlayer);

        vh.textView_ListOFIndividuals_time = view.findViewById(R.id.textView_ListOFIndividuals_time);
        vh.textView_ListOFIndividuals_date = view.findViewById(R.id.textView_ListOFIndividuals_date);
        vh.textView_ListOFIndividuals_phone = view.findViewById(R.id.textView_ListOFIndividuals_phone);
        vh.textView_ListOFIndividuals_ageRange = view.findViewById(R.id.textView_ListOFIndividuals_ageRange);
        vh.textView_ListOFIndividuals_playerName = view.findViewById(R.id.textView_ListOFIndividuals_playerName);

        vh.textView_ListOFIndividuals_playerName.setText(listOFIndividualsDataArrayList.get(i).getListOFIndividualsPlayerName());
        vh.textView_ListOFIndividuals_ageRange.setText(listOFIndividualsDataArrayList.get(i).getListOFIndividualsAgeRange());
        vh.textView_ListOFIndividuals_phone.setText(listOFIndividualsDataArrayList.get(i).getListOFIndividualsPhone());
        vh.textView_ListOFIndividuals_date.setText(listOFIndividualsDataArrayList.get(i).getListOFIndividualsDate());
        vh.textView_ListOFIndividuals_time.setText(listOFIndividualsDataArrayList.get(i).getListOFIndividualsTime());

        vh.imageView_ListOFIndividuals_arrowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListOFIndividualsFragment.yourDesiredMethod(i);

            }
        });

        vh.button_ListOFIndividuals_invitePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceUtil.isUserSignedIn(context)) {
                    ListOFIndividualsFragment.yourDesiredMethod2(i);
                    IndividualId = listOFIndividualsDataArrayList.get(i).getListOFIndividualsId();

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

        ImageView imageView_ListOFIndividuals_arrowList;
        Button button_ListOFIndividuals_invitePlayer;

        TextView textView_ListOFIndividuals_time, textView_ListOFIndividuals_date, textView_ListOFIndividuals_phone, textView_ListOFIndividuals_ageRange, textView_ListOFIndividuals_playerName;
    }
}
