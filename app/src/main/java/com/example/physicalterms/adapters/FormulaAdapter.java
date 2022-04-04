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
import com.example.physicalterms.api.FormulaRow;

import java.util.List;

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {
    private List<FormulaRow> mData;
    private LayoutInflater mInflater;
    private FormulaAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public FormulaAdapter(Context context, List<FormulaRow> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.formula_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, int position) {
        FormulaRow current = mData.get(position);
        holder.formulaNameTextView.setText(current.getName());
        holder.formulaNameRusTextView.setText(current.getNameRus());
        holder.valueTextView.setText(current.getValue());
        holder.commentTextView.setText(current.getValue());
        holder.sectionTextView.setText(current.getSection());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView formulaNameTextView;
        TextView formulaNameRusTextView;
        TextView valueTextView;
        TextView commentTextView;
        TextView sectionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            formulaNameTextView = itemView.findViewById(R.id.formulaItemNameText);
            formulaNameRusTextView = itemView.findViewById(R.id.formulaItemNameRusText);
            valueTextView = itemView.findViewById(R.id.formulaItemValueText);
            commentTextView = itemView.findViewById(R.id.formulaItemCommentText);
            sectionTextView = itemView.findViewById(R.id.formulaItemSectionText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    FormulaRow getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(FormulaAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setData(List<FormulaRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FormulaDiffUtilCallback(mData, newData), true);
        this.mData = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}
