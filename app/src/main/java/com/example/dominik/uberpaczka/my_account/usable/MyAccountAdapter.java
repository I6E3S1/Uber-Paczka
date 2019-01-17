package com.example.dominik.uberpaczka.my_account.usable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dominik.uberpaczka.R;

import java.util.List;

/**
 * Created by marek on 06.01.2019.
 */

public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.MyViewHolder> {

    private List<UserData> userDataList;
    private Context mContext;
    private CustomClickListener clickListener;


    public MyAccountAdapter(List<UserData> userDataList, Context context, CustomClickListener clickListner) {
        this.userDataList = userDataList;
        this.mContext = context;
        this.clickListener = clickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_myaccount_adapter, parent, false);
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
        UserData userData = userDataList.get(position);
        holder.description.setText(userData.getDescription());
        holder.value.setText(userData.getValue());

    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView description;
        public TextView value;

        public MyViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.my_account_description);
            value = view.findViewById(R.id.my_account_value);

        }

    }
}




