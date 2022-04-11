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

import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;
import com.google.android.material.card.MaterialCardView;

import java.util.List;



public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> {
    private List<FormulaRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

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

        holder.formulaNameTextView.setText(current.getNameLang());
        holder.formulaNameRusTextView.setText(current.getNameRus());
        holder.valueTextView.setText(current.getValue());
        holder.commentTextView.setText(current.getCommentLang());
        holder.commentRusTextView.setText(current.getCommentRus());
        holder.sectionTextView.setText(current.getSection());

        if(mData.get(position).isExpanded()){
            holder.valueTextView.setVisibility(View.VISIBLE);
            holder.commentTextView.setVisibility(View.VISIBLE);
            holder.commentRusTextView.setVisibility(View.VISIBLE);
            holder.parentLayout.setCardElevation(0);
        } else {
            holder.valueTextView.setVisibility(View.GONE);
            holder.commentTextView.setVisibility(View.GONE);
            holder.commentRusTextView.setVisibility(View.GONE);
            holder.parentLayout.setCardElevation(8);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FormulaAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty()) {
            if (payloads.get(0) instanceof Boolean) {
                if((Boolean) payloads.get(0)){
                    holder.valueTextView.setVisibility(View.VISIBLE);
                    holder.commentTextView.setVisibility(View.VISIBLE);
                    holder.commentRusTextView.setVisibility(View.VISIBLE);
                    holder.parentLayout.setCardElevation(0);
                } else {
                    holder.valueTextView.setVisibility(View.GONE);
                    holder.commentTextView.setVisibility(View.GONE);
                    holder.commentRusTextView.setVisibility(View.GONE);
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
        TextView formulaNameTextView;
        TextView formulaNameRusTextView;
        FlexibleRichTextView valueTextView;
        FlexibleRichTextView commentTextView;
        TextView sectionTextView;
        FlexibleRichTextView commentRusTextView;
        MaterialCardView parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            formulaNameTextView = itemView.findViewById(R.id.formulaItemNameText);
            formulaNameRusTextView = itemView.findViewById(R.id.formulaItemNameRusText);
            valueTextView = itemView.findViewById(R.id.formulaItemValueText);
            commentTextView = itemView.findViewById(R.id.formulaItemCommentText);
            commentRusTextView = itemView.findViewById(R.id.formulaItemCommentRusText);
            parentLayout = (MaterialCardView) itemView;

//            valueTextView.getSettings().setDisplayZoomControls(false);
//            commentTextView.getSettings().setDisplayZoomControls(false);
//            valueTextView.getSettings().setSupportZoom(false);
//            commentTextView.getSettings().setSupportZoom(false);
            valueTextView.setVerticalScrollBarEnabled(false);
            commentTextView.setVerticalScrollBarEnabled(false);

            sectionTextView = itemView.findViewById(R.id.formulaItemSectionText);
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
    FormulaRow getItem(int id) {
        return mData.get(id);
    }

    public void setData(List<FormulaRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FormulaDiffUtilCallback(mData, newData), true);
        this.mData = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}
