package com.example.dominik.uberpaczka.order.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.maps.MapsActivity;
import com.example.dominik.uberpaczka.maps.usable.MapsInterface;
import com.example.dominik.uberpaczka.order.usable.OrderInfo;


/**
 * Created by marek on 10.12.2018.
 * frontend
 */

public class PackageSizeFragment extends Fragment /**/ {


    private ImageView smallButton;
    private ImageView mediumButtom;
    private ImageView bigButton;
    private OrderInfo orderInfo;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView1:
                    orderInfo.setSmallPackagesQuantity(orderInfo.getSmallPackagesQuantity() + 1);
                    break;
                case R.id.imageView2:
                    orderInfo.setMediumPackagesQuantity(orderInfo.getMediumPackagesQuantity() + 1);
                    break;
                case R.id.imageView3:
                    orderInfo.setBigPackagesQuantity(orderInfo.getMediumPackagesQuantity() + 1);
                    break;
                default:
                    return;
            }

            openOrderSummaryFragment();
        }
    };


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_package_size, container, false);


        orderInfo = (OrderInfo) getArguments().getSerializable("order_info");
        smallButton = view.findViewById(R.id.imageView1);
        mediumButtom = view.findViewById(R.id.imageView2);
        bigButton = view.findViewById(R.id.imageView3);

        smallButton.setOnClickListener(clickListener);
        mediumButtom.setOnClickListener(clickListener);
        bigButton.setOnClickListener(clickListener);


        return view;


    }

    public void openOrderSummaryFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_info", orderInfo);
        Fragment fragment = new SummaryFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .remove(this)
                .replace(R.id.flContent, fragment)
                .addToBackStack(null)
                .commit();


        ((MapsActivity) getActivity()).changeViewOnOrderSummaryOpen();
    }


    public interface SummaryFragmentCallback extends MapsInterface {

        void changeNavigationButtonBehaviourOnOpenFragment();

        void changeNavigationButtonBehaviourOnCloseFragemnt();

        void changeViewOnOrderSummaryOpen();


    }


}
