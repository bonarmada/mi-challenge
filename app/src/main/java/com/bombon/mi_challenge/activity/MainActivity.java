package com.bombon.mi_challenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bombon.mi_challenge.App;
import com.bombon.mi_challenge.R;
import com.bombon.mi_challenge.adapter.DeliveryRecyclerViewAdapter;
import com.bombon.mi_challenge.model.Delivery;
import com.bombon.mi_challenge.service.DeliveryService;
import com.bombon.mi_challenge.service.ServiceCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DeliveryRecyclerViewAdapter.AdapterListener {

    @Inject
    DeliveryService deliveryService;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private DeliveryRecyclerViewAdapter adapter;
    private List<Delivery> deliveries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        setupRecyclerView();
        fetchData();
    }
    @Override
    public void onItemClick(int position, int id)    {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("selectedDeliveryId", id);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        // Refresh Layout
        refreshLayout.setOnRefreshListener(this);

        // Recycler
        adapter = new DeliveryRecyclerViewAdapter(this, this, deliveries);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL)
        );
    }

    private void fetchData() {
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        deliveryService.getDeliveries(new ServiceCallback<List<Delivery>>() {
            @Override
            public void getResult(int statusCode, List<Delivery> result) {
                refreshLayout.setRefreshing(false);
                if (statusCode == 200) {
                    adapter.refresh(result);
                    return;
                }
                showFetchError();
            }
        });
    }

    private void showFetchError() {
        Snackbar.make(coordinatorLayout, R.string.error_network, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchData();
                    }
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.malibu))
                .show();
    }
}