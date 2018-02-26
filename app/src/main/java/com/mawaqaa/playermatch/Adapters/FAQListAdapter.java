package com.mawaqaa.playermatch.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mawaqaa.playermatch.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 10/16/2017.
 */

public class FAQListAdapter extends BaseExpandableListAdapter {

    //
    private static LayoutInflater inflater = null;
    Context context;
    List<String> faqListDataHeader;
    private HashMap<String, String> faqlistDataChild;
    private String TAG = "FAQListAdapter";



    public FAQListAdapter(Context context, List<String> faqListDataHeader, HashMap<String, String> faqlistDataChild) {
        this.context = context;
        this.faqListDataHeader = faqListDataHeader;
        this.faqlistDataChild = faqlistDataChild;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return faqListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
        // return this.faqlistDataChild.get(this.faqListDataHeader.get(i)).size();

    }

    @Override
    public Object getGroup(int i) {
        return this.faqListDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.faqlistDataChild.get(this.faqListDataHeader.get(i))
                ;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_faq_list_group, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }


        vh.textView_list_group = view.findViewById(R.id.textView_list_group);
        vh.textView_list_group.setText(Html.fromHtml(getGroup(i).toString()));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final ViewHolder vh;


        if (view == null) {
            view = inflater.inflate(R.layout.z_faq_list_item, null);

            vh = new ViewHolder();


            view.setTag(vh);

        } else {
            vh = (ViewHolder) view.getTag();
        }
        //  textView_aboutUS_details.setText(Html.fromHtml(aboutUSDetails));

        vh.textView_fAQ_chiled = view.findViewById(R.id.textView_fAQ_chiled);
        vh.textView_fAQ_chiled.setText(Html.fromHtml(getChild(i, i1).toString()));

        return view;
    }

    private class ViewHolder {


        TextView textView_list_group, textView_fAQ_chiled;
    }


}
