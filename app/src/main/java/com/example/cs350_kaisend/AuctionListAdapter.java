package com.example.cs350_kaisend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class AuctionListAdapter extends RecyclerView.Adapter<AuctionListAdapter.AuctionViewHolder> implements Filterable {
    private LinkedList<Auction> mAuctionList;
    private LinkedList<Auction> mAuctionListFiltered;
    private LayoutInflater mInflater;
    private Context mContext;

    public AuctionListAdapter(Context context,
                              LinkedList<Auction> auctions) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mAuctionList = auctions;
        this.mAuctionListFiltered = auctions;
    }
    @Override
    public AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.single_auction, parent, false);
        return new AuctionViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionViewHolder holder, int position) {
        Auction mCurrent = mAuctionListFiltered.get(position);
        holder.mAuctionId.setText(String.valueOf(position));
        holder.mAuctionName.setText(String.valueOf(mCurrent.getName()));
        holder.mInitDest.setText(String.valueOf(mCurrent.getInitDest()));
        holder.mFinalDest.setText(String.valueOf(mCurrent.getFinalDest()));
        holder.mDeadline.setText(String.valueOf(mCurrent.getDeadline()));
        holder.mFee.setText(String.valueOf(mCurrent.getFee()));
    }

    @Override
    public int getItemCount() {
        return mAuctionListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()){
                    mAuctionListFiltered = mAuctionList;
                }else{
                    LinkedList<Auction> filteredList = new LinkedList<>();
                    for (Auction auction : mAuctionList){
                        if (auction.getName().contains(charString) || auction.getFinalDest().contains(charString) || auction.getInitDest().contains(charString)){
                            filteredList.addLast(auction);
                        }
                    }mAuctionListFiltered = filteredList;
                }FilterResults filterResults = new FilterResults();
                filterResults.values = mAuctionListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults){
                mAuctionListFiltered = (LinkedList<Auction>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class AuctionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mAuctionId, mAuctionName, mInitDest, mFinalDest, mDeadline, mFee;
        final AuctionListAdapter mAdapter;
        public AuctionViewHolder(View itemView, AuctionListAdapter adapter){
            super(itemView);
            mAuctionId = itemView.findViewById(R.id.auctionId);
            mAuctionName = itemView.findViewById(R.id.auctionName);
            mInitDest = itemView.findViewById(R.id.initDest);
            mFinalDest = itemView.findViewById(R.id.finalDest);
            mDeadline = itemView.findViewById(R.id.deadline);
            mFee = itemView.findViewById(R.id.fee);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, IndividualAuction.class);
            Auction element = mAuctionList.get(mPosition);
            intent.putExtra(IndividualAuction.EXTRA_ID, mPosition);
            intent.putExtra(IndividualAuction.EXTRA_NAME, element.getName());
            intent.putExtra(IndividualAuction.EXTRA_INITDEST, element.getInitDest());
            intent.putExtra(IndividualAuction.EXTRA_FINALDEST, element.getFinalDest());
            intent.putExtra(IndividualAuction.EXTRA_DEAD, element.getDeadline());
            intent.putExtra(IndividualAuction.EXTRA_ACTIVE, element.isActive());
            intent.putExtra(IndividualAuction.EXTRA_FEE, element.getFee());
            mContext.startActivity(intent);



        }
    }
}

