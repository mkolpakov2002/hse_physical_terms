package com.example.physicalterms;

import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.physicalterms.adapters.CountryAdapter;

import java.util.Objects;

public class Country {
    private Integer id;

    private String countryName;

    private Drawable flagImage;

    public Country(Integer id, String countryName, Drawable flagImage) {
        this.id = id;
        this.countryName = countryName;
        this.flagImage = flagImage;
    }

    public Drawable getFlagImage() {
        return flagImage;
    }

    public Integer getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setFlagImage(Drawable flagImage) {
        this.flagImage = flagImage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id.equals(country.id) && Objects.equals(countryName, country.countryName) && Objects.equals(flagImage, country.flagImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName, flagImage);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
        CountryAdapter.ViewHolder mViewHolder = (CountryAdapter.ViewHolder) viewHolder;
        mViewHolder.countryName.setText(getCountryName());
        mViewHolder.countryImage.setImageDrawable(getFlagImage());
    }
}
