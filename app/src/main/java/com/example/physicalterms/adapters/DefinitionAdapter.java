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
        holder.definitionNameTextView.setText(current.getName());
        holder.definitionNameRusTextView.setText(current.getNameRus());
        holder.definitionBodyTextView.setText(current.getBody());
        holder.definitionBodyRusTextView.setText(current.getBodyRus());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView definitionNameTextView;
        TextView definitionNameRusTextView;
        TextView definitionBodyTextView;
        TextView definitionBodyRusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            definitionNameTextView = itemView.findViewById(R.id.definitionName);
            definitionNameRusTextView = itemView.findViewById(R.id.definitionNameRus);
            definitionBodyTextView = itemView.findViewById(R.id.definitionBody);
            definitionBodyRusTextView = itemView.findViewById(R.id.definitionBodyRus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    DefinitionRow getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setData(List<DefinitionRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefinitionDiffUtilCallback(mData, newData), true);
        this.mData = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}
