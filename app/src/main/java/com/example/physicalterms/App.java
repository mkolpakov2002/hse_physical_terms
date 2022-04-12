package com.example.physicalterms;

import static com.example.physicalterms.Constants.languages;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import com.google.android.material.color.DynamicColors;

import org.scilab.forge.jlatexmath.core.AjLatexMath;

import java.util.Locale;

import io.github.kbiakov.codeview.classifier.CodeProcessor;
import retrofit2.Retrofit;

public class App extends Application {

    private static App instance;

    private static Retrofit retrofit = null;

    private static ApiService apiService;

    private static SharedPreferences preferences;

    private static String mainLanguage;

    private static String learningLanguage;

    private static boolean isLanguageSelected = false;

    private static AppDataBase database;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (retrofit == null) {
            retrofit = ApiClient.getAdapterRefresh();
            apiService = retrofit.create(ApiService.class);
        }

        database = Room.databaseBuilder(this, AppDataBase.class, "dataRows")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        DynamicColors.applyToActivitiesIfAvailable(this);
        AjLatexMath.init(this);
        CodeProcessor.init(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("isLanguageSelected", "0");
        isLanguageSelected = name.equals("1");
        mainLanguage = preferences.getString("mainLanguage", languages[5][1]);
        learningLanguage = preferences.getString("learningLanguage", languages[0][1]);

    }

    public static void setLocale(Activity activity) {
        Locale locale;
        if(mainLanguage.equals(languages[0][1])){
            locale = new Locale(languages[0][2]);
        } else if(mainLanguage.equals(languages[1][1])){
            locale = new Locale(languages[1][2]);
        } else if(mainLanguage.equals(languages[2][1])){
            locale = new Locale(languages[2][2]);
        } else if(mainLanguage.equals(languages[3][1])){
            locale = new Locale(languages[3][2]);
        } else if(mainLanguage.equals(languages[4][1])){
            locale = new Locale(languages[4][2]);
        } else if(mainLanguage.equals(languages[5][1])){
            locale = new Locale(languages[5][2]);
        } else {
            locale = new Locale(languages[0][2]);
        }

        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static App getInstance() {
        return instance;
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

    public static ApiService getApiService() {
        return apiService;
    }

    public static boolean isIsLanguageSelected() {
        return isLanguageSelected;
    }

    public static void setIsLanguageSelected(boolean isLanguageSelected) {
        App.isLanguageSelected = isLanguageSelected;
        SharedPreferences.Editor editor = preferences.edit();
        if(isLanguageSelected)
            editor.putString("isLanguageSelected","1");
        else editor.putString("isLanguageSelected","0");
        editor.putString("mainLanguage",mainLanguage);
        editor.putString("learningLanguage",learningLanguage);
        editor.apply();
    }

    public static String getLearningLanguage() {
        return learningLanguage;
    }

    public static Drawable getLearningLanguageIcon(Context c) {
        Resources resources = c.getResources();
        Drawable d = ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[0][1], "drawable",
                c.getPackageName()), null);
        if(learningLanguage.equals(languages[0][1])){
            return ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[0][1], "drawable",
                    c.getPackageName()), null);
        } else if(learningLanguage.equals(languages[1][1])){
            return ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[1][1], "drawable",
                    c.getPackageName()), null);
        } else if(learningLanguage.equals(languages[2][1])){
            return ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[2][1], "drawable",
                    c.getPackageName()), null);
        } else if(learningLanguage.equals(languages[3][1])){
            return ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[3][1], "drawable",
                    c.getPackageName()), null);
        } else if(learningLanguage.equals(languages[4][1])){
            return ResourcesCompat.getDrawable(resources, resources.getIdentifier(languages[4][1], "drawable",
                    c.getPackageName()), null);
        } else {
            return null;
        }
    }

    public static String getMainLanguage() {
        return mainLanguage;
    }

    public static void setLearningLanguage(String learningLanguage) {
        App.learningLanguage = learningLanguage;
    }

    public static void setMainLanguage(String mainLanguage) {
        App.mainLanguage = mainLanguage;
    }

    public static AppDataBase getDatabase() {
        return database;
    }
}
