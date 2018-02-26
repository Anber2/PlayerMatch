package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mawaqaa.playermatch.Data.DrawerListData;
import com.mawaqaa.playermatch.R;

import java.util.ArrayList;

/**
 * Created by HP on 10/9/2017.
 */

public class DrawerListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<DrawerListData> mDrawerListItems;
    ViewHolder holder;
    LayoutInflater inflater;
    DrawerListData mDrawerData;

    public DrawerListViewAdapter(Context context, ArrayList<DrawerListData> mDrawerListItems) {
        this.context = context;
        this.mDrawerListItems = mDrawerListItems;
    }

    @Override
    public int getCount() {
        return mDrawerListItems.size();
    }

    @Override
    public DrawerListData getItem(int position) {
        DrawerListData drawerListData = mDrawerListItems.get(position);

        return drawerListData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawerData = mDrawerListItems.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.z_drawer_listview_layout, parent, false);
            holder.lblListItem = (TextView) convertView.findViewById(R.id.lblDrawerListItem);
            holder.drawer_menu_imageView = convertView.findViewById(R.id.drawer_menu_imageView);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.drawer_menu_imageView.setImageResource(mDrawerListItems.get(position).getImageId());
        holder.lblListItem.setText(mDrawerListItems.get(position).getName());
         return convertView;
    }

    class ViewHolder {
        ImageView drawer_menu_imageView;
        TextView lblListItem;
     }

}
