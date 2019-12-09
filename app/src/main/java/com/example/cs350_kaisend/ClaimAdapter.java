package com.example.cs350_kaisend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClaimAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<claimItem> claimArrayList;

    public ClaimAdapter(Context context, ArrayList<claimItem> claimArrayList) {
        this.context = context;
        this.claimArrayList = claimArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return claimArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return claimArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.claim_item, null, true);

            holder.item = (TextView) convertView.findViewById(R.id.item_text);
            holder.sender = (TextView) convertView.findViewById(R.id.sender_text);
            holder.requester = (TextView) convertView.findViewById(R.id.requester_text);
            holder.price = (TextView) convertView.findViewById(R.id.price_text);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.item.setText(claimArrayList.get(position).getItemName());
        holder.sender.setText(claimArrayList.get(position).getSender());
        holder.requester.setText(claimArrayList.get(position).getRequester());
        holder.price.setText(claimArrayList.get(position).getPrice().toString());

        return convertView;
    }

    private class ViewHolder {

        protected TextView item, sender, requester, price;
    }
}
