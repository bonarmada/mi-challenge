package com.bombon.mi_challenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.bombon.mi_challenge.R;
import com.bombon.mi_challenge.fragment.DeliveryDetailFragment;
import com.bombon.mi_challenge.fragment.DeliveryListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DeliveryListFragment.ActivityInteraction{

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onItemClick(int id) {
        DeliveryDetailFragment deliveryDetailFragment= (DeliveryDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);
        
        // Check whether fragment exists within the activity.. aka multi pane mode.
        if (deliveryDetailFragment != null){
            deliveryDetailFragment.onListItemSelected(id);
            return;
        }

        Intent intent = new Intent(this, DeliveryDetailActivity.class);
        intent.putExtra("deliveryId", id);
        startActivity(intent);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }
}