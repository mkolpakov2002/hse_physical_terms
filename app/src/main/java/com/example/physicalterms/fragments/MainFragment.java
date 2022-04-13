package com.example.physicalterms.fragments;

import static com.example.physicalterms.Constants.DEV_EMAIL;
import static com.example.physicalterms.Constants.DEV_SITE;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.physicalterms.App;
import com.example.physicalterms.BuildConfig;
import com.example.physicalterms.MainActivity;
import com.example.physicalterms.R;
import com.example.physicalterms.WelcomeActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MaterialToolbar materialToolbar;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Element versionElement = new Element();
        versionElement.setTitle(getString(R.string.version_name) + BuildConfig.VERSION_NAME);
        Element changeLearningLanguage = new Element();
        changeLearningLanguage.setTitle("Изменить язык обучения");
        changeLearningLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                requireActivity().finish();
            }
        });
        return new AboutPage(requireContext())
                .isRTL(false)
                .setDescription(getString(R.string.app_description))
                .setImage(R.drawable.miem)
                .addItem(versionElement)
                .addItem(changeLearningLanguage)
                .addGroup(getString(R.string.connect_dev))
                .addEmail(DEV_EMAIL)
                .addWebsite(DEV_SITE)
                .addPlayStore(BuildConfig.APPLICATION_ID)
                .create();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

    }

}