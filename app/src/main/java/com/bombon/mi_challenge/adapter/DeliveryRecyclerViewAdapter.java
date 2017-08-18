package com.bombon.mi_challenge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombon.mi_challenge.R;
import com.bombon.mi_challenge.model.Delivery;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vaughn on 8/18/17.
 */

public class DeliveryRecyclerViewAdapter extends RecyclerView.Adapter<DeliveryRecyclerViewAdapter.ViewHolder> {

    public interface AdapterListener {
        void onItemClick(int position, int id);
    }

    private static Context context;
    private List<Delivery> deliveries;
    private AdapterListener listener;

    public DeliveryRecyclerViewAdapter(Context context, AdapterListener listener, List<Delivery> deliveries) {
        this.context = context;
        this.deliveries = deliveries;
        this.listener = listener;
    }

    public void refresh(List<Delivery> deliveries){
        this.deliveries = deliveries;
        notifyDataSetChanged();
    }

    @Override
    public DeliveryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_delivery, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Bind ui objects here
        @BindView(R.id.description)
        TextView tvDescription;
        @BindView(R.id.address)
        TextView tvAddress;
        @BindView(R.id.distance)
        TextView tvDistance;
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Delivery delivery = deliveries.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position, (int)delivery.getId());
            }
        });

        holder.tvAddress.setText(delivery.getLocation().getAddress());
        holder.tvDescription.setText(delivery.getDescription());
        Picasso.with(context).load(delivery.getImageUrl()).into(holder.imageView);
    }
}

