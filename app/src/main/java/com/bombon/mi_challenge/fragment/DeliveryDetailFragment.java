package com.bombon.mi_challenge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Vaughn on 8/21/17.
 */

public class DeliveryDetailFragment extends Fragment implements OnMapReadyCallback {

    @Inject
    DeliveryService deliveryService;

    @BindView(R.id.empty_state)
    LinearLayout emptyStateContainer;

    @BindView(R.id.detail_container)
    FrameLayout detailContainer;

    @BindView(R.id.action_icon)
    ImageView ivIcon;

    @BindView(R.id.description)
    TextView tvDescription;

    @BindView(R.id.address)
    TextView tvAddress;

    @BindView(R.id.distance)
    TextView tvDistance;

    @BindView(R.id.imageView)
    ImageView imageView;

    private int deliveryId;
    private GoogleMap googleMap;
    private Delivery delivery = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_detail, container, false);
        ButterKnife.bind(this, view);

        ((App) getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get bundle
        Bundle bundle = getArguments();
        if (bundle == null) {
            emptyStateContainer.setVisibility(View.VISIBLE);
            detailContainer.setVisibility(View.GONE);
        } else {
            deliveryId = bundle.getInt("deliveryId");
            delivery = deliveryService.getDelivery(deliveryId);
        }

        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (delivery != null) {
            updateDeliveryDetail();
        }
    }

    public void onListItemSelected(int deliveryId) {
        detailContainer.setVisibility(View.VISIBLE);
        emptyStateContainer.setVisibility(View.GONE);

        delivery = deliveryService.getDelivery(deliveryId);
        updateDeliveryDetail();
    }

    private void updateDeliveryDetail() {
        updateMap();

        ivIcon.setVisibility(View.GONE);
        tvAddress.setText(delivery.getLocation().getAddress());
        tvDescription.setText(delivery.getDescription());
        Picasso.with(getActivity()).load(delivery.getImageUrl()).into(imageView);
    }

    public void updateMap() {
        if (googleMap != null) {
            googleMap.clear();

            Location deliveryLocation = delivery.getLocation();
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(deliveryLocation.getLat(), deliveryLocation.getLng()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(deliveryLocation.getLat(), deliveryLocation.getLng())));
        }
    }
}
