package com.example.physicalterms.fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.physicalterms.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TextSwitcher textSwitcher;
    private List<String> greetingsArray = new ArrayList<>();
    private int index = 0;
    private MaterialToolbar materialToolbar;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        materialToolbar = view.findViewById(R.id.toolbar_mf);

        materialToolbar.setTitle("Глоссарий по физике");
        materialToolbar.setBackgroundColor(getResources().getColor(R.color.hse_purple));
        materialToolbar.setTitleTextColor(getResources().getColor(R.color.white));


        Animation slideInLeftAnimation = AnimationUtils.loadAnimation(view.getContext(),
                android.R.anim.slide_in_left);
        Animation slideOutRightAnimation = AnimationUtils.loadAnimation(view.getContext(),
                android.R.anim.slide_out_right);

        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Resources r = getResources();
        Configuration c = r.getConfiguration();
        String[] loc = r.getAssets().getLocales();
        for (int i = 0; i < loc.length; i++) {
            Log.d("LOCALE", i + ": " + loc[i]);
            c.locale = new Locale(loc[i]);
            Resources res = new Resources(requireActivity().getAssets(), metrics, c);
            String s1 = res.getString(R.string.greeting_title);
            if(!greetingsArray.contains(s1))
                greetingsArray.add(s1);
        }

        textSwitcher = (TextSwitcher) view.findViewById(R.id.greetingTextSwitcher);
        textSwitcher.setInAnimation(slideInLeftAnimation);
        textSwitcher.setOutAnimation(slideOutRightAnimation);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(view.getContext());
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

    public void onPause(){
        super.onPause();
        textSwitcher.removeCallbacks(onEveryTenSecond);
    }

    public void onResume(){
        super.onResume();
        textSwitcher.postDelayed(onEveryTenSecond,5000);
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

}