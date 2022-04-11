package com.example.physicalterms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.physicalterms.Country;
import com.example.physicalterms.R;
import com.example.physicalterms.api.FormulaRow;

import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
    private List<Country> mData;
    private LayoutInflater mInflater;
    private CountryAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public CountryAdapter(Context context, List<Country> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.country_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        mData.get(position).onBindViewHolder(holder);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView countryName;
        public ImageView countryImage;
        public ImageView checkMark;

        ViewHolder(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name);
            countryImage = itemView.findViewById(R.id.country_flag);
            checkMark = itemView.findViewById(R.id.check_mark);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Country getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(CountryAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
