package com.bombon.mi_challenge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bombon.mi_challenge.App;
import com.bombon.mi_challenge.R;
import com.bombon.mi_challenge.activity.MainActivity;
import com.bombon.mi_challenge.adapter.DeliveryRecyclerViewAdapter;
import com.bombon.mi_challenge.model.Delivery;
import com.bombon.mi_challenge.service.DeliveryService;
import com.bombon.mi_challenge.service.ServiceCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vaughn on 8/21/17.
 */

public class DeliveryListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, DeliveryRecyclerViewAdapter.AdapterListener {

    public interface ActivityInteraction{
        void onItemClick(int id);
    }

    @Inject
    DeliveryService deliveryService;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private DeliveryRecyclerViewAdapter adapter;
    private List<Delivery> deliveries = new ArrayList<>();
    private ActivityInteraction callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.callback = (ActivityInteraction) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
        ButterKnife.bind(this, view);

        ((App) getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupRecyclerView();
        fetchData();
    }

    private void setupRecyclerView() {
        // Refresh Layout
        refreshLayout.setOnRefreshListener(this);

        // Recycler
        adapter = new DeliveryRecyclerViewAdapter(getActivity(), this, deliveries);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(),
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
        Snackbar.make(((MainActivity)getActivity()).getCoordinatorLayout(), R.string.error_network, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchData();
                    }
                })
                .setActionTextColor(ContextCompat.getColor(getActivity(), R.color.malibu))
                .show();
    }

    @Override
    public void onItemClick(int position, int id)    {
        callback.onItemClick(id);
    }
}
