package com.bombon.mi_challenge.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bombon.mi_challenge.R;
import com.bombon.mi_challenge.fragment.DeliveryDetailFragment;

/**
 * Created by Vaughn on 8/18/17.
 */

public class DeliveryDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);

        // Create fragment instance
        DeliveryDetailFragment fragment = new DeliveryDetailFragment();
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, fragment, null)
                .commit();
    }
}
