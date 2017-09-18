package com.example.komputer.peopleapi;

import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.komputer.peopleapi.Adapters.ResultAdapter;
import com.example.komputer.peopleapi.Adapters.ResultAdapterInterface;
import com.example.komputer.peopleapi.ApiService.PeopleListApi;
import com.example.komputer.peopleapi.Fragments.DetailPersonFragment;
import com.example.komputer.peopleapi.Fragments.ErrorFragment;
import com.example.komputer.peopleapi.Model.PeopleList;
import com.example.komputer.peopleapi.Model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ResultAdapterInterface {

    public static final String BASE_URL = "https://randomuser.me/api/";
    private Retrofit retrofit;
    private PeopleListApi peopleListApi;

    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerView;

    List<Result> mainPeopleList;
    List<Result> backupList;
    private ResultAdapter resultAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkConnection();
        inicializeData();
        setMainRecyclerView();
        setRetrofit();
        getPeopleFromApi();
    }

    private void checkConnection() {
        ConnectivityManager connection = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connection.getActiveNetworkInfo() == null || !connection.getActiveNetworkInfo().isConnectedOrConnecting()) {
            ErrorFragment.newInstance().show(getFragmentManager(), "Error occur");
        }
    }

    private void inicializeData() {
        mainPeopleList = new ArrayList<>();
        backupList = new ArrayList<>();
    }

    private void setMainRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        resultAdapter = new ResultAdapter(mainPeopleList);
        recyclerView.setAdapter(resultAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resultAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultAdapter.filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        peopleListApi = retrofit.create(PeopleListApi.class);
    }

    private void getPeopleFromApi() {
        peopleListApi.getPeopleRxJava().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<PeopleList>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull PeopleList peopleList) {
                        mainPeopleList.addAll(peopleList.getResults());
                        backupList.addAll(peopleList.getResults());
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        resultAdapter.notifyDataSetChanged();
                    }
                });
    }

    @NonNull
    private Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .excludeFieldsWithoutExposeAnnotation()
                .setLenient()
                .create();
    }

    @Override
    public List<Result> getCopyList() {
        return backupList;
    }

    @Override
    public void onCardPressed(Result person) {
        int n = backupList.indexOf(person);
        DetailPersonFragment.newInstance(backupList.get(n)).show(getFragmentManager(), "Detail view");
    }

    @Override
    public void notifyFavoriteItemOnCopy(Result person) {
        int n = backupList.indexOf(person);
        if (backupList.get(n).isFavorite()) {
            Result tmp = backupList.get(n);
            tmp.setFavorite(true);
            backupList.set(n, tmp);
        } else {
            Result tmp = backupList.get(n);
            tmp.setFavorite(true);
            backupList.set(n, tmp);
        }
    }
}
