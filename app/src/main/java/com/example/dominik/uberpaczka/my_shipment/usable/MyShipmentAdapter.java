package com.example.dominik.uberpaczka.my_shipment.usable;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.my_account.usable.CustomClickListener;
import com.example.dominik.uberpaczka.order.usable.OrderInfo;

import java.util.List;

/**
 * Created by marek on 06.01.2019.
 */

public class MyShipmentAdapter extends RecyclerView.Adapter<MyShipmentAdapter.MyViewHolder> {

    private List<OrderInfo> orderList;
    private Context mContext;
    private CustomClickListener clickListener;
    private String to;


    public MyShipmentAdapter(List<OrderInfo> orderList, Context context, CustomClickListener clickListner, String to) {
        this.orderList = orderList;
        this.mContext = context;
        this.clickListener = clickListner;
        this.to = to;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_my_shipment_adapter, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, mViewHolder.getPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        OrderInfo orderInfo = orderList.get(position);
        StringBuilder stringBuilder = new StringBuilder()
                .append(orderInfo.getFromName())
                .append(" ")
                .append(to)
                .append(" ")
                .append(orderInfo.getDestinationName());

        holder.iconImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_delivery_icon));
        holder.value.setText(stringBuilder.toString());
        holder.statusValueTextView.setText(orderInfo.getPackageStatus().toString());
        holder.priceTextView.setText(orderInfo.getPrice());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iconImageView;
        public TextView value;
        public TextView statusValueTextView;
        public TextView priceTextView;

        public MyViewHolder(View view) {
            super(view);
            iconImageView = view.findViewById(R.id.icon);
            value = view.findViewById(R.id.my_shipment_value);
            statusValueTextView = view.findViewById(R.id.my_shipment_status_value);
            priceTextView = view.findViewById(R.id.price_shipment);
        }

    }
}




