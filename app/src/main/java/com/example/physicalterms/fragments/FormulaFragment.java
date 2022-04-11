package com.example.physicalterms.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.physicalterms.ApiService;
import com.example.physicalterms.App;
import com.example.physicalterms.R;
import com.example.physicalterms.adapters.FormulaAdapter;
import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormulaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormulaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FormulaAdapter.ItemClickListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialToolbar materialToolbar;

    List<FormulaRow> result = new ArrayList<>();
    RecyclerView formulaList;
    FormulaAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FormulaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormulaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormulaFragment newInstance(String param1, String param2) {
        FormulaFragment fragment = new FormulaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formula, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        materialToolbar = view.findViewById(R.id.toolbar_form);

        materialToolbar.setTitle(getString(R.string.formulas));
        materialToolbar.setBackgroundColor(getResources().getColor(R.color.hse_purple));
        materialToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        swipeRefreshLayout = view.findViewById(R.id.formulaSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        formulaList = view.findViewById(R.id.formulaList);

        if(adapter==null){
            if(result.size()==0){
                swipeRefreshLayout.setRefreshing(true);
                downloadData();
            }
            adapter = new FormulaAdapter(requireContext(), result);
            adapter.setClickListener(this);
            formulaList.setAdapter(adapter);
        }

        formulaList.setLayoutManager(new LinearLayoutManager(requireContext()));
        formulaList.setHasFixedSize(false);
    }

    void downloadData(){
        ApiService apiService = App.getApiService();

        Call<List<FormulaRow>> call = apiService.getFormulaListByLang(App.getLearningLanguage(), "all");
        call.enqueue(new Callback<List<FormulaRow>>() {
            @Override
            public void onResponse(@NonNull Call<List<FormulaRow>> call, @NonNull Response<List<FormulaRow>> response) {
                if (response.body() != null) {
                    result = response.body();
                    adapter.setData(result);
                    App.getDatabase().getFormulaRowDao().deleteAll();
                    App.getDatabase().getFormulaRowDao().insertAll(result);
                } else {
                    String s = "No objects";
                    Log.d(TAG, s);
                    List<FormulaRow> saved = App.getDatabase().getFormulaRowDao().getAll();
                    if(saved.size()>0){
                        adapter.setData(saved);
                    }
                    showSnackBar(getString(R.string.connection_error));
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<FormulaRow>> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
                List<FormulaRow> saved = App.getDatabase().getFormulaRowDao().getAll();
                if(saved.size()>0){
                    adapter.setData(saved);
                }
                showSnackBar(getString(R.string.connection_error));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        downloadData();
    }

    @Override
    public void onItemClick(View view) {
        Navigation.findNavController(view).navigate(R.id.dialogCardFragment);
    }

    private void showSnackBar(String message){
        Snackbar.make(swipeRefreshLayout, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //nothing
                    }
                })
                .show();
    }

}