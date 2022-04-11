package com.example.physicalterms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.daquexian.flexiblerichtextview.FlexibleRichTextView;
import com.example.physicalterms.R;
import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TermRow;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {
    private List<TermRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public TermAdapter(Context context, List<TermRow> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.term_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TermRow current = mData.get(position);

        holder.termNameTextView.setText(current.getNameLang());
        holder.termNameRusTextView.setText(current.getNameRus());

        if(mData.get(position).isExpanded()){
            holder.termNameRusTextView.setVisibility(View.VISIBLE);
            holder.parentLayout.setCardElevation(0);
        } else {
            holder.termNameRusTextView.setVisibility(View.GONE);
            holder.parentLayout.setCardElevation(8);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty()) {
            if (payloads.get(0) instanceof Boolean) {
                if((Boolean) payloads.get(0)){
                    holder.termNameRusTextView.setVisibility(View.VISIBLE);
                    holder.parentLayout.setCardElevation(0);
                } else {
                    holder.termNameRusTextView.setVisibility(View.GONE);
                    holder.parentLayout.setCardElevation(8);
                }
            }
        } else {
            super.onBindViewHolder(holder,position, payloads);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView termNameTextView;
        TextView termNameRusTextView;
        MaterialCardView parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            termNameTextView = itemView.findViewById(R.id.termItemNameText);
            termNameRusTextView = itemView.findViewById(R.id.termItemNameRusText);
            parentLayout = (MaterialCardView) itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view);
            int clicked = getBindingAdapterPosition();
            if(!mData.get(clicked).isExpanded()){
                mData.get(clicked).setExpanded(true);
                getBindingAdapter().notifyItemChanged(clicked, true);
            } else {
                mData.get(clicked).setExpanded(false);
                getBindingAdapter().notifyItemChanged(clicked, false);
            }
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view);
    }

    // convenience method for getting data at click position
    TermRow getItem(int id) {
        return mData.get(id);
    }

    public void setData(List<TermRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TermDiffUtilCallback(mData, newData), true);
        this.mData = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}

