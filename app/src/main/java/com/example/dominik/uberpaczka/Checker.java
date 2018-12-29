package com.example.dominik.uberpaczka;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.dominik.uberpaczka.registration.registration_fragment.NoInternetConnectrionDialogFragment;

import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by marek on 29.12.2018.
 */

public class Checker {

    public static boolean checkInternetConnection(Context context, FragmentManager fragmentManager){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        boolean result = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!result) {

            openNoConnectionFragment(fragmentManager);
            Toast.makeText(context, "No internet connection.",
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }

    public static void openNoConnectionFragment(FragmentManager fragmentManager) {
        NoInternetConnectrionDialogFragment dialogFragment = new NoInternetConnectrionDialogFragment();
        dialogFragment.show(fragmentManager, "No internet connection");
    }
}
