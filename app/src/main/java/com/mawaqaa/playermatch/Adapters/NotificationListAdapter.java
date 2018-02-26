package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.NotificationData;
import com.mawaqaa.playermatch.Fragments.NotificationFragment;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 12/19/2017.
 */

public class NotificationListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<NotificationData> notificationDataArrayList;
    NotificationData notificationData;
    private String TAG = "NotificationListAdapter";

    public NotificationListAdapter(Context context, ArrayList<NotificationData> notificationDataArrayList) {
        this.context = context;
        this.notificationDataArrayList = notificationDataArrayList;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notificationDataArrayList.size() ;
    }

    @Override
    public NotificationData getItem(int i) {

        NotificationData notificationData = notificationDataArrayList.get(i);
        return notificationData;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.z_notification_list_item, null);
            vh = new ViewHolder();
            view.setTag(vh);

            //LinearLayout
            vh.Layout_AcceptReject= view.findViewById(R.id.Layout_AcceptReject);
            vh.layout_PayNow= view.findViewById(R.id.layout_PayNow);

            //TextView
            vh.textView_notoficationTitle = view.findViewById(R.id.textView_notoficationTitle);
            vh.textView_notoficationDate = view.findViewById(R.id.textView_notoficationDate);
            vh.textView_notoficationDescription = view.findViewById(R.id.textView_notoficationDescription);

            //Button
            vh.button_notificationPay = view.findViewById(R.id.button_notificationPay);
            vh.button_notificationAccept = view.findViewById(R.id.button_notificationAccept);
            vh.button_notificationDecline = view.findViewById(R.id.button_notificationDecline);

//            vh.button_notificationPay.setVisibility(View.GONE);
//            vh.button_notificationAccept.setVisibility(View.GONE);
//            vh.button_notificationDecline.setVisibility(View.GONE);

            vh.Layout_AcceptReject.setVisibility(View.GONE);
            vh.layout_PayNow.setVisibility(View.GONE);


        } else {
            vh = (ViewHolder) view.getTag();
        }

        if(notificationDataArrayList.get(i).getNotificationType().equals("1")
                ||notificationDataArrayList.get(i).getNotificationType().equals("3")){
           vh. Layout_AcceptReject.setVisibility(View.VISIBLE);
        }else if(notificationDataArrayList.get(i).getNotificationType().equals("4")){
            vh.layout_PayNow.setVisibility(View.VISIBLE);
        }

        String[] loc = notificationDataArrayList.get(i).getNotificationDate().split("T");

        vh.textView_notoficationTitle.setText(notificationDataArrayList.get(i).getNotificationTitle());
        vh.textView_notoficationDate.setText(loc[0]);
        vh.textView_notoficationDescription.setText(notificationDataArrayList.get(i).getNotificationDescription());

        vh.button_notificationAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationFragment.acceptJoinMatch(notificationDataArrayList.get(i).getNotificationId());
            }
        });

        vh.button_notificationDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationFragment.rejectJoinMatch(notificationDataArrayList.get(i).getNotificationId());
            }
        });

        vh.button_notificationPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationFragment.PayJoinMatch(notificationDataArrayList.get(i).getNotificationId(),notificationDataArrayList.get(i).getMatchId() );
            }
        });

        return view;
    }

    private class ViewHolder {


        TextView textView_notoficationTitle, textView_notoficationDate, textView_notoficationDescription;
        Button button_notificationPay, button_notificationAccept, button_notificationDecline;
        LinearLayout Layout_AcceptReject, layout_PayNow;
    }
}
