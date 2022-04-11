package com.example.physicalterms;

import static com.example.physicalterms.Constants.languages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.physicalterms.adapters.CountryAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity implements CountryAdapter.ItemClickListener {

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    ArrayList<Country> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setLocale(this);
        WelcomeActivity ma = this;
        if(!App.isIsLanguageSelected()) {
            setContentView(R.layout.activity_welcome);
            RecyclerView countryListView = findViewById(R.id.country_recycler);
            RecyclerViewLayoutManager
                    = new LinearLayoutManager(
                    getApplicationContext());

            // Set LayoutManager on Recycler View
            countryListView.setLayoutManager(
                    RecyclerViewLayoutManager);

            Resources resources = ma.getResources();

            Country eng = new Country(0, languages[0][0], ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[0][1], "drawable",
                    ma.getPackageName()), null));
            Country arab = new Country(0, languages[1][0], ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[1][1], "drawable",
                    ma.getPackageName()), null));
            Country es = new Country(0, languages[2][0], ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[2][1], "drawable",
                    ma.getPackageName()), null));
            Country jap = new Country(0, languages[3][0], ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[3][1], "drawable",
                    ma.getPackageName()), null));
            Country fr = new Country(0, languages[4][0], ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[4][1], "drawable",
                    ma.getPackageName()), null));
            countries = new ArrayList<>();
            countries.add(eng);
            countries.add(arab);
            countries.add(es);
            countries.add(jap);
            countries.add(fr);
            CountryAdapter adapter = new CountryAdapter(ma, countries);

            // Set Horizontal Layout Manager
            // for Recycler view
            HorizontalLayout
                    = new LinearLayoutManager(
                    WelcomeActivity.this,
                    LinearLayoutManager.HORIZONTAL,
                    false);

            countryListView.setLayoutManager(HorizontalLayout);
            adapter.setClickListener(this);
            countryListView.setAdapter(adapter);

        }

    }

    @Override
    public void onItemClick(View view, int position) {
        TextView nameText = view.findViewById(R.id.country_name);
        String selectedLearningLanguage = nameText.getText().toString();
        if(selectedLearningLanguage.equals(languages[0][0])){
            App.setLearningLanguage(languages[0][1]);
        } else if(selectedLearningLanguage.equals(languages[1][0])){
            App.setLearningLanguage(languages[1][1]);
        } else if(selectedLearningLanguage.equals(languages[2][0])){
            App.setLearningLanguage(languages[2][1]);
        } else if(selectedLearningLanguage.equals(languages[3][0])){
            App.setLearningLanguage(languages[3][1]);
        } else if(selectedLearningLanguage.equals(languages[4][0])){
            App.setLearningLanguage(languages[4][1]);
        }
        App.setIsLanguageSelected(true);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}