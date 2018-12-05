package com.example.dominik.uberpaczka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {

    private PlaceAutocompleteFragment autocompleteFragment;
    private String TAG="AutoComplete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_temp);


        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                String g = locationV.getText().toString();
//
//                Geocoder geocoder = new Geocoder(getBaseContext());
//                List<Address> addresses = null;
//
//                try {
//                    // Getting a maximum of 3 Address that matches the input
//                    // text
//                    addresses = geocoder.getFromLocationName(g, 3);
//                    if (addresses != null && !addresses.equals(""))
//                        search(addresses,mMap);
//
//                } catch (Exception e) {
//
//                }
//
//            }
//        });
    }


}
