package com.example.physicalterms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.daquexian.flexiblerichtextview.FlexibleRichTextView;
import com.daquexian.flexiblerichtextview.LaTeXtView;
import com.example.physicalterms.R;

import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;



public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> implements Filterable {
    private List<FormulaRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<FormulaRow> filterableList;
    private boolean isListMode = true;

    // data is passed into the constructor
    public FormulaAdapter(Context context, List<FormulaRow> data, boolean isListMode) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = filterableList = data;
        this.isListMode = isListMode;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(isListMode){
            view = mInflater.inflate(R.layout.item_formula, parent, false);
        } else {
            view = mInflater.inflate(R.layout.item_formula_card, parent, false);
        }
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, int position) {
        FormulaRow current = filterableList.get(position);

        holder.formulaNameTextView.setText(current.getNameLang());
        holder.formulaNameRusTextView.setText(current.getNameRus());
        holder.valueTextView.setText(current.getValue());
        holder.commentTextView.setText(current.getCommentLang());
        holder.commentRusTextView.setText(current.getCommentRus());
        holder.sectionTextView.setText(current.getSection());

        if(isListMode){
            if(filterableList.get(position).isExpanded()){
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
    }

    List<FormulaRow> getFiltered(){
        return filterableList;
    }

    public void setListMode(boolean listMode) {
        isListMode = listMode;
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
        return filterableList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = mData.size();
                    filterResults.values = mData;
                    for(FormulaRow userModel: mData){
                        userModel.setExpanded(false);
                    }
                    filterResults.count = mData.size();
                    filterResults.values = mData;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();

                    List<FormulaRow> resultData = new ArrayList<>();

                    for(FormulaRow userModel: mData){
                        if(userModel.getValue().toLowerCase().contains(searchChr.toLowerCase())
                                || userModel.getCommentLang().toLowerCase().contains(searchChr.toLowerCase())
                                || userModel.getCommentRus().toLowerCase().contains(searchChr.toLowerCase())
                                || userModel.getNameRus().toLowerCase().contains(searchChr.toLowerCase())
                                || userModel.getNameLang().toLowerCase().contains(searchChr.toLowerCase())){
                            FormulaRow copy = new FormulaRow(userModel);
                            copy.setExpanded(true);
                            resultData.add(copy);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                showSearchData((List<FormulaRow>) filterResults.values);
            }
        };
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
                    if(isListMode){
                        int clicked = getBindingAdapterPosition();
                        if(!filterableList.get(clicked).isExpanded()){
                            filterableList.get(clicked).setExpanded(true);
                            getBindingAdapter().notifyItemChanged(clicked, true);
                        } else {
                            filterableList.get(clicked).setExpanded(false);
                            getBindingAdapter().notifyItemChanged(clicked, false);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(isListMode){
                        int clicked = getBindingAdapterPosition();
                        if(!filterableList.get(clicked).isExpanded()){
                            filterableList.get(clicked).setExpanded(true);
                            getBindingAdapter().notifyItemChanged(clicked, true);
                        } else {
                            filterableList.get(clicked).setExpanded(false);
                            getBindingAdapter().notifyItemChanged(clicked, false);
                        }
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

    public void updateAdapterData(List<FormulaRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FormulaDiffUtilCallback(filterableList, newData), true);
        this.mData = filterableList = newData;
        diffResult.dispatchUpdatesTo(this);
    }

    public void showSearchData(List<FormulaRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FormulaDiffUtilCallback(filterableList, newData), true);
        filterableList = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}
