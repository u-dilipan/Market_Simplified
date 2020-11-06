package com.example.market_simplified.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.market_simplified.DBAdapter;
import com.example.market_simplified.MainActivity;
import com.example.market_simplified.R;
import com.example.market_simplified.api.ApiClient;
import com.example.market_simplified.api.ApiInterface;
import com.example.market_simplified.models.UserData;
import com.example.market_simplified.pagination.PaginationAdapter;
import com.example.market_simplified.pagination.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGES = 20;
    private int currentPage = PAGE_START;

    private ApiInterface apiInterface;
    private DBAdapter dbAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        setView(view);
        return view;
    }

    private void setView(View view) {
        dbAdapter = new DBAdapter(getActivity());

        rv = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.main_progress);

        adapter = new PaginationAdapter(getActivity(), FirstFragment.this);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(() -> loadNextPage(), 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        loadFirstPage();
    }

    public void openDetailFragment(UserData userData) {

        MainActivity.detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("name", userData.getName());
        args.putString("user", userData.getOwner().getImageUrl());
        args.putString("type", userData.getOwner().getUserType());
        args.putString("fullName", userData.getFullName());
        args.putString("comments", dbAdapter.getComment(userData.getId()));
        MainActivity.detailFragment.setArguments(args);

        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.main_container, MainActivity.detailFragment, "4");
        ft.hide(FirstFragment.this);
        ft.commit();
    }

    public void insertComments(int id, String comment) {
        dbAdapter.insertComment(id, comment);
    }

    private void loadFirstPage() {

        callUserDataApi().enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserData>> call, @NonNull Response<List<UserData>> response) {
                List<UserData> results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);
                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(@NonNull Call<List<UserData>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private List<UserData> fetchResults(Response<List<UserData>> response) {
        return response.body();
    }

    private void loadNextPage() {

        callUserDataApi().enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserData>> call, @NonNull Response<List<UserData>> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<UserData> results = fetchResults(response);
                adapter.addAll(results);

                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(@NonNull Call<List<UserData>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<List<UserData>> callUserDataApi() {
        return apiInterface.getMovies();
    }
}
