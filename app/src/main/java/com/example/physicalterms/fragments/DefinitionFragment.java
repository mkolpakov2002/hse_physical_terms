package com.example.physicalterms.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager;
import com.dingmouren.layoutmanagergroup.slide.ItemTouchHelperCallback;
import com.example.physicalterms.ApiService;
import com.example.physicalterms.App;
import com.example.physicalterms.R;
import com.example.physicalterms.adapters.DefinitionAdapter;
import com.example.physicalterms.api.DefinitionRow;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefinitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefinitionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, DefinitionAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<DefinitionRow> result = new ArrayList<>();
    RecyclerView definitionList;
    DefinitionAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    MenuItem menuItem;

    boolean isListMode = true;

    private MaterialToolbar materialToolbar;
    //Поиск среди устройств на главном экране
    androidx.appcompat.widget.SearchView searchView;


    public DefinitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefinitionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefinitionFragment newInstance(String param1, String param2) {
        DefinitionFragment fragment = new DefinitionFragment();
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        materialToolbar = view.findViewById(R.id.toolbar_def);

        materialToolbar.setTitle(getString(R.string.definitions));
        //materialToolbar.setBackgroundColor(getResources().getColor(R.color.hse_purple));
        //materialToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        materialToolbar.getMenu().clear();
        materialToolbar.inflateMenu(R.menu.item_mode_menu);
        MenuItem list = materialToolbar.getMenu().findItem(R.id.changeModeToList);
        list.setVisible(false);
        materialToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        menuItem = materialToolbar.getMenu().findItem(R.id.search_device);
        searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // изменение цвета иконки поиска на белый
//        ImageView icon = searchView.findViewById(R.id.);
//        icon.setColorFilter(getResources().getColor(R.color.white));
        // методы при взаимодействии со строкой поиска
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //запускается, когда будет нажата кнопка поиска
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //вызывается после ввода пользователем каждого символа в текстовом поле
                adapter.getFilter().filter(newText);
                return true;
            }
        });


        definitionList = view.findViewById(R.id.definitionList);
        swipeRefreshLayout = view.findViewById(R.id.definitionSwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        if(adapter==null){
            if(result.size()==0){
                swipeRefreshLayout.setRefreshing(true);
                downloadData();
            }
            definitionList.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new DefinitionAdapter(requireContext(), result, true);
            adapter.setClickListener(this);
            definitionList.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_device) {
            // открытие строки поиска
            Log.d(TAG, "открытие строки поиска");
            return true;
        } else {
            if(!searchView.isIconified()){
                hideKeyboard(requireContext(), searchView);
                MenuItemCompat.collapseActionView(menuItem);
                searchView.setIconified(true);
                adapter.setData(result);
            }
            if(item.getItemId() == R.id.changeModeToCard){
                DefinitionAdapter adapter = new DefinitionAdapter(requireContext(), result, false);
                adapter.setListMode(false);
                ItemTouchHelperCallback mItemTouchHelperCallback = new ItemTouchHelperCallback(adapter, result);
                ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
                SkidRightLayoutManager mSlideLayoutManager = new SkidRightLayoutManager(2F,0.9F);
                mItemTouchHelper.attachToRecyclerView(definitionList);
//            definitionList
                definitionList.setOnFlingListener(null);
                definitionList.setLayoutManager(mSlideLayoutManager);
                definitionList.setAdapter(adapter);
                item.setVisible(false);
                MenuItem list = materialToolbar.getMenu().findItem(R.id.changeModeToList);
                list.setVisible(true);
                return true;
            }
            if(item.getItemId() == R.id.changeModeToList){
                DefinitionAdapter adapter = new DefinitionAdapter(requireContext(), result, false);
                adapter.setListMode(true);
                definitionList.setOnFlingListener(null);
                definitionList.setLayoutManager(new LinearLayoutManager(requireContext()));
                definitionList.setAdapter(adapter);
                item.setVisible(false);
                MenuItem list = materialToolbar.getMenu().findItem(R.id.changeModeToCard);
                list.setVisible(true);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void downloadData(){
        ApiService apiService = App.getApiService();

        Call<List<DefinitionRow>> call = apiService.getDefinitionListByLang(App.getLearningLanguage(), "all");
        call.enqueue(new Callback<List<DefinitionRow>>() {
            @Override
            public void onResponse(@NonNull Call<List<DefinitionRow>> call, @NonNull Response<List<DefinitionRow>> response) {
                if (response.body() != null) {
                    result = response.body();
                    adapter.setData(result);
                    App.getDatabase().getDefinitionRowDao().deleteAll();
                    App.getDatabase().getDefinitionRowDao().insertAll(result);
                } else {
                    String s = "No objects";
                    Log.d(TAG, s);
                    List<DefinitionRow> saved = App.getDatabase().getDefinitionRowDao().getAll();
                    if(saved.size()>0){
                        result = saved;
                        adapter.setData(saved);
                    }
                    showSnackBar(getString(R.string.connection_error));
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<DefinitionRow>> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
                List<DefinitionRow> saved = App.getDatabase().getDefinitionRowDao().getAll();
                if(saved.size()>0){
                    result = saved;
                    adapter.setData(saved);
                }
                showSnackBar(getString(R.string.connection_error));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_definition, container, false);
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
        //Navigation.findNavController(view).navigate(R.id.dialogCardFragment);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!searchView.isIconified()) {
                    //если открыта строка поиска, сворачиваем её
                    searchView.setIconified(true);
                    materialToolbar.collapseActionView();
                } else {
                    this.setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}