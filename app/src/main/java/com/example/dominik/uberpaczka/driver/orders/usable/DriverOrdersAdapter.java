package com.example.dominik.uberpaczka.driver.orders.usable;

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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * Created by snadev on 17.01.2019.
 */

public class DriverOrdersAdapter extends RecyclerView.Adapter<DriverOrdersAdapter.MyViewHolder> {

    private List<OrderInfo> orderList;
    private Context mContext;
    private CustomClickListener clickListener;
    private String to;


    public DriverOrdersAdapter(List<OrderInfo> orderList, Context context, CustomClickListener clickListner, String to) {
        this.orderList = orderList;
        this.mContext = context;
        this.clickListener = clickListner;
        this.to = to;
    }

    public void add(OrderInfo s, int position) {
        position = position == -1 ? getItemCount() : position;
        orderList.add(position, s);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            orderList.remove(position);
            notifyItemRemoved(position);
        }
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
        holder.priceTextView.setText(getPriceForDriver(orderInfo.getPrice()));

        switch (orderInfo.getPackageStatus()) {
            case driver_is_coming:
                holder.statusValueTextView.setText(mContext.getString(R.string.driver_is_coming));
                break;
            case package_delivered:
                holder.statusValueTextView.setText(mContext.getString(R.string.package_delivered));
                break;
            case package_on_the_way:
                holder.statusValueTextView.setText(mContext.getString(R.string.package_on_the_way));
                break;
            case waiting_for_driver:
                holder.statusValueTextView.setText(mContext.getString(R.string.waiting_for_driver));
                break;
            default:
                holder.statusValueTextView.setText(mContext.getString(R.string.unknown_package_status));
                break;
        }
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

    private String getPriceForDriver(String price) {
        DecimalFormat twoDForm = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

        Double priceForDriver = null;
        try {
            priceForDriver = twoDForm.parse(price).doubleValue() * 0.8;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return twoDForm.format(priceForDriver);
    }
}




