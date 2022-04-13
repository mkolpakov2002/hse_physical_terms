package com.example.physicalterms;

import static com.example.physicalterms.Constants.languages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.physicalterms.adapters.CountryAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity implements CountryAdapter.ItemClickListener {

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    ArrayList<Country> countries;

    private TextSwitcher textSwitcher;
    private List<String> greetingsArray = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setLocale(this);
        WelcomeActivity ma = this;
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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Resources r = getResources();
        Configuration c = r.getConfiguration();
        String[] loc = r.getAssets().getLocales();
        for (int i = 0; i < loc.length; i++) {
            Log.d("LOCALE", i + ": " + loc[i]);
            c.locale = new Locale(loc[i]);
            Resources res = new Resources(getAssets(), metrics, c);
            String s1 = res.getString(R.string.greeting_title);
            if(!greetingsArray.contains(s1))
                greetingsArray.add(s1);
        }

        textSwitcher = (TextSwitcher) findViewById(R.id.greetingTextSwitcher);
        Animation slideInLeftAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        Animation slideOutRightAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        textSwitcher.setInAnimation(slideInLeftAnimation);
        textSwitcher.setOutAnimation(slideOutRightAnimation);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(ma);
                textView.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_TitleLarge);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                return textView;
            }
        });
        textSwitcher.setText(greetingsArray.get(index));
        if(greetingsArray.size()>index+1){
            index++;
        } else {
            index = 0;
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

    private final Runnable onEveryTenSecond = new Runnable() {
        @Override
        public void run() {
            textSwitcher.setText(greetingsArray.get(index));
            if(greetingsArray.size()>index+1){
                index++;
            } else {
                index = 0;
            }
            textSwitcher.postDelayed(this,5000);
        }
    };

    public void onPause(){
        super.onPause();
        textSwitcher.removeCallbacks(onEveryTenSecond);
    }

    public void onResume(){
        super.onResume();
        textSwitcher.postDelayed(onEveryTenSecond,5000);
    }

}