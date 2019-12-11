package com.codespurt.mvvmexample;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codespurt.mvvmexample.adapter.RecyclerAdapter;
import com.codespurt.mvvmexample.model.NicePlace;
import com.codespurt.mvvmexample.viewmodel.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // views
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;

    private RecyclerAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();

        viewModel.getNicePlaces().observe(this, new Observer<List<NicePlace>>() {
            @Override
            public void onChanged(List<NicePlace> nicePlaces) {
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                    recyclerView.smoothScrollToPosition(viewModel.getNicePlaces().getValue().size() - 1);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = viewModel.getNicePlaces().getValue().size();
                viewModel.addNewNicePlace(new NicePlace(String.valueOf(size + 1), ""));
            }
        });

        initRecyclerView();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initRecyclerView() {
        adapter = new RecyclerAdapter(this, viewModel.getNicePlaces().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
