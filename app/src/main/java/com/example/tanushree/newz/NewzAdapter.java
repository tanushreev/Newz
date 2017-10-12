package com.example.tanushree.newz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tanushree on 22/01/17.
 */

// Adapter and ViewHolder for the RecyclerView.

public class NewzAdapter extends RecyclerView.Adapter<NewzAdapter.NewzViewHolder>
{
    private List<NewzItem> mNewzItemList;
    private final NewzListFragment.OnNewzItemSelectedInterface mListener;

    public NewzAdapter(List<NewzItem> newzItemList, NewzListFragment.OnNewzItemSelectedInterface listener)
    {
        mNewzItemList = newzItemList;
        mListener = listener;
    }

    @Override
    public NewzViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.newz_list_item, parent, false);
        return new NewzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewzViewHolder holder, int position)
    {
        NewzItem newzItem = mNewzItemList.get(position);
        holder.bindNewz(newzItem);
    }

    @Override
    public int getItemCount() {
        return mNewzItemList.size();
    }

    public class NewzViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mHeadlineView;
        private NewzItem mItem;

        public NewzViewHolder(View itemView) {
            super(itemView);

            mHeadlineView = (TextView) itemView.findViewById(R.id.tvHeadline);
            itemView.setOnClickListener(this);
        }

        public void bindNewz(NewzItem newzItem)
        {
            mHeadlineView.setText(newzItem.getHeadline());

            mItem = newzItem;
        }

        @Override
        public void onClick(View view)
        {
            mListener.onNewzItemSelected(mItem);
        }
    }
}