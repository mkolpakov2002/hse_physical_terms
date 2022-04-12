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

import com.example.physicalterms.R;
import com.example.physicalterms.api.DefinitionRow;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.ViewHolder> implements Filterable {

    private List<DefinitionRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<DefinitionRow> filterableList;
    private boolean isListMode = true;

    // data is passed into the constructor
    public DefinitionAdapter(Context context, List<DefinitionRow> data, boolean isListMode) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = filterableList = data;
        this.isListMode = isListMode;
    }

    public DefinitionAdapter(Context context, List<DefinitionRow> data, List<DefinitionRow> filtered, boolean isListMode) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.filterableList = filtered;
        this.isListMode = isListMode;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(isListMode){
            view = mInflater.inflate(R.layout.item_definition, parent, false);
        } else {
            view = mInflater.inflate(R.layout.item_definition_card, parent, false);
        }
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DefinitionRow current = filterableList.get(position);
        holder.definitionNameTextView.setText(current.getNameLang());
        holder.definitionNameRusTextView.setText(current.getNameRus());
        holder.definitionBodyTextView.setText(current.getValueLang());
        holder.definitionBodyRusTextView.setText(current.getValueRus());

        if(isListMode){
            if(filterableList.get(position).isExpanded()){
                holder.definitionBodyTextView.setVisibility(View.VISIBLE);
                holder.definitionBodyRusTextView.setVisibility(View.VISIBLE);
                holder.parentLayout.setCardElevation(0);
            } else {
                holder.definitionBodyTextView.setVisibility(View.GONE);
                holder.definitionBodyRusTextView.setVisibility(View.GONE);
                holder.parentLayout.setCardElevation(8);
            }
        }

    }

    List<DefinitionRow> getFiltered(){
        return filterableList;
    }
    public void setListMode(boolean listMode) {
        isListMode = listMode;
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
                    for(DefinitionRow userModel: mData){
                        userModel.setExpanded(false);
                    }
                    filterableList = mData;

                } else {
                    String searchChr = charSequence.toString().toLowerCase();

                    List<DefinitionRow> resultData = new ArrayList<>();

                    for(DefinitionRow userModel: mData){
                        if(userModel.getValueLang().toLowerCase().contains(searchChr.toLowerCase())
                        || userModel.getValueRus().toLowerCase().contains(searchChr.toLowerCase())
                        || userModel.getNameRus().toLowerCase().contains(searchChr.toLowerCase())
                        || userModel.getNameLang().toLowerCase().contains(searchChr.toLowerCase())){
                            userModel.setExpanded(true);
                            resultData.add(userModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filterableList = (List<DefinitionRow>) filterResults.values;
                notifyDataSetChanged();

            }
        };
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
//            if(isListMode){
//                parentLayout.setLayoutParams(new MaterialCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT));
//            } else {
//                parentLayout.setLayoutParams(new MaterialCardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT));
//            }
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
    DefinitionRow getItem(int id) {
        return mData.get(id);
    }

    public void setData(List<DefinitionRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefinitionDiffUtilCallback(filterableList, newData), true);
        this.mData = filterableList = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}
