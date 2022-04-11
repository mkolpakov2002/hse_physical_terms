package com.example.physicalterms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.physicalterms.R;
import com.example.physicalterms.api.DefinitionRow;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.ViewHolder> {

    private List<DefinitionRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public DefinitionAdapter(Context context, List<DefinitionRow> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.definition_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DefinitionRow current = mData.get(position);
        holder.definitionNameTextView.setText(current.getNameLang());
        holder.definitionNameRusTextView.setText(current.getNameRus());
        holder.definitionBodyTextView.setText(current.getValueLang());
        holder.definitionBodyRusTextView.setText(current.getValueRus());

        if(mData.get(position).isExpanded()){
            holder.definitionBodyTextView.setVisibility(View.VISIBLE);
            holder.definitionBodyRusTextView.setVisibility(View.VISIBLE);
            holder.parentLayout.setCardElevation(0);
        } else {
            holder.definitionBodyTextView.setVisibility(View.GONE);
            holder.definitionBodyRusTextView.setVisibility(View.GONE);
            holder.parentLayout.setCardElevation(8);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty()) {
            if (payloads.get(0) instanceof Boolean) {
                if((Boolean) payloads.get(0)){
                    holder.definitionBodyTextView.setVisibility(View.VISIBLE);
                    holder.definitionBodyRusTextView.setVisibility(View.VISIBLE);
                    holder.parentLayout.setCardElevation(0);
                } else {
                    holder.definitionBodyTextView.setVisibility(View.GONE);
                    holder.definitionBodyRusTextView.setVisibility(View.GONE);
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
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView definitionNameTextView;
        TextView definitionNameRusTextView;
        TextView definitionBodyTextView;
        TextView definitionBodyRusTextView;
        MaterialCardView parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            definitionNameTextView = itemView.findViewById(R.id.definitionName);
            definitionNameRusTextView = itemView.findViewById(R.id.definitionNameRus);
            definitionBodyTextView = itemView.findViewById(R.id.definitionBody);
            definitionBodyRusTextView = itemView.findViewById(R.id.definitionBodyRus);
            parentLayout = (MaterialCardView) itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onItemClick(view);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int clicked = getBindingAdapterPosition();
                    if(!mData.get(clicked).isExpanded()){
                        mData.get(clicked).setExpanded(true);
                        getBindingAdapter().notifyItemChanged(clicked, true);
                    } else {
                        mData.get(clicked).setExpanded(false);
                        getBindingAdapter().notifyItemChanged(clicked, false);
                    }
                    return true;
                }
            });
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
    DefinitionRow getItem(int id) {
        return mData.get(id);
    }

    public void setData(List<DefinitionRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefinitionDiffUtilCallback(mData, newData), true);
        this.mData = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}
