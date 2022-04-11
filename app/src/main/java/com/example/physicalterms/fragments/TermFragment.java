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
import com.example.physicalterms.adapters.TermAdapter;
import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TaskRow;
import com.example.physicalterms.api.TermRow;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TermFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TermFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TermAdapter.ItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MaterialToolbar materialToolbar;

    List<TermRow> result = new ArrayList<>();
    RecyclerView termList;
    TermAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public TermFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TermFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TermFragment newInstance(String param1, String param2) {
        TermFragment fragment = new TermFragment();
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
        return inflater.inflate(R.layout.fragment_term, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        materialToolbar = view.findViewById(R.id.toolbar_term);

        materialToolbar.setTitle(getString(R.string.terms));
        materialToolbar.setBackgroundColor(getResources().getColor(R.color.hse_purple));
        materialToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        swipeRefreshLayout = view.findViewById(R.id.termSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        termList = view.findViewById(R.id.termList);

        if(adapter==null){
            if(result.size()==0){
                swipeRefreshLayout.setRefreshing(true);
                downloadData();
            }
            adapter = new TermAdapter(requireContext(), result);
            //adapter.setClickListener(this);
            termList.setAdapter(adapter);
        }

        termList.setLayoutManager(new LinearLayoutManager(requireContext()));
        termList.setHasFixedSize(false);
    }

    void downloadData(){
        ApiService apiService = App.getApiService();

        Call<List<TermRow>> call = apiService.getTermsListByLang(App.getLearningLanguage(), "all");
        call.enqueue(new Callback<List<TermRow>>() {
            @Override
            public void onResponse(@NonNull Call<List<TermRow>> call, @NonNull Response<List<TermRow>> response) {
                if (response.body() != null) {
                    result = response.body();
                    adapter.setData(result);
                    App.getDatabase().getTermRowDao().deleteAll();
                    App.getDatabase().getTermRowDao().insertAll(result);
                } else {
                    String s = "No objects";
                    Log.d(TAG, s);
                    List<TermRow> saved = App.getDatabase().getTermRowDao().getAll();
                    if(saved.size()>0){
                        adapter.setData(saved);
                    }
                    showSnackBar(getString(R.string.connection_error));
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<TermRow>> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
                List<TermRow> saved = App.getDatabase().getTermRowDao().getAll();
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
        Snackbar.make(termList, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //nothing
                    }
                })
                .show();
    }

}