package com.bombon.mi_challenge.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombon.mi_challenge.App;
import com.bombon.mi_challenge.R;
import com.bombon.mi_challenge.model.Delivery;
import com.bombon.mi_challenge.model.Location;
import com.bombon.mi_challenge.service.DeliveryService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vaughn on 8/18/17.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Inject
    DeliveryService deliveryService;

    @BindView(R.id.description)
    TextView tvDescription;
    @BindView(R.id.address)
    TextView tvAddress;
    @BindView(R.id.distance)
    TextView tvDistance;
    @BindView(R.id.imageView)
    ImageView imageView;

    private int selectedDeliveryId = 0;
    private Delivery delivery;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedDeliveryId = extras.getInt("selectedDeliveryId");
            delivery = deliveryService.getDelivery(selectedDeliveryId);
        }

        // Retrieve data from db
        tvAddress.setText(delivery.getLocation().getAddress());
        tvDescription.setText(delivery.getDescription());
        Picasso.with(this).load(delivery.getImageUrl()).into(imageView);

        // setupMap
        setupMapFragment();
    }

    private void setupMapFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Location deliveryLocation = delivery.getLocation();
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(deliveryLocation.getLat(), deliveryLocation.getLng()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(deliveryLocation.getLat(), deliveryLocation.getLng())));
    }
}
