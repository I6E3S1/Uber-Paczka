package com.example.dominik.uberpaczka;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MapsActivity extends FragmentActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private HashMap<String, String> locationHashMap = new HashMap<>();

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private String TAG = "MAPS";
    private CardView destinationCardView;
    private PlaceAutocompleteFragment autocompleteFragment;
    private PlaceAutocompleteFragment autocompleteFragmentDestination;


    /**
     * TODO
     * OBSŁUG KILKU MARKERÓW
     * FRONT
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //google map support
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_from);

        autocompleteFragmentDestination = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_destination);


//        //frontend
//        final FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Bundle bundle=new Bundle();
////                bundle.putString("from", locationHashMap.get(1));
////                bundle.putString("destination", locationHashMap.get(2));
//                SummaryFragment fragment = new SummaryFragment();
//                fragment.setArguments(bundle);
//                fragmentTransaction.add(R.id.summary_container, fragment);
//                fragmentTransaction.commit();
//                view.setVisibility(View.GONE);
//            }
//        });


        initUpNavigationDrawer();
        initAutoCompleteFragments();
        initOnSelectedListener();

    }


    //using search tool in front end
    protected String search(List<Address> addresses, GoogleMap map) {

        map.clear();

        String addressText;
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        addressText = String.format(
                "%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address
                        .getAddressLine(0) : "", address.getCountryName());


        MarkerOptions markerOptions = new MarkerOptions();


        markerOptions.position(latLng);
        markerOptions.title(addressText);

        map.clear();
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));


        return address.getFeatureName();

    }

    //start cord for map
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        LatLng warsaw = new LatLng(52.227, 21.021);
        map.moveCamera(CameraUpdateFactory.newLatLng(warsaw));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    //sharing location
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    /**
     * change place langtitude and longtitude to string adreess
     * @param place
     * @return adreess as String
     * @throws Exception
     */
    public String decodeAdress(Place place) throws Exception {

        String g = String.valueOf(place.getName());

        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses;

        addresses = geocoder.getFromLocationName(g, 3);
        if (addresses != null && !addresses.equals("")) {
            String res = search(addresses, mMap);
            return res;
        }

        Log.i(TAG, "geocoder error: " + g);
        Log.i(TAG, "Place: " + place.getName());

        return null;
    }

    /**
     * change appearance of placeautocomplete fragemnts
     * setiing hints and new icon
     */

    public void initAutoCompleteFragments() {

        autocompleteFragment.setHint(getString(R.string.hint_place_autocompletefragment_from));
        autocompleteFragmentDestination.setHint(getString(R.string.hint_place_autocompletefragment_destination));

        ImageView searchIcon = (ImageView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(0);
        if (searchIcon != null) {
            searchIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext() ,R.drawable.ic_location_pin));
        }

        ImageView searchIconDestination = (ImageView) ((LinearLayout) autocompleteFragmentDestination.getView()).getChildAt(0);
        if (searchIcon != null) {
            searchIconDestination.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_location_pin_destination));
        }

        destinationCardView = findViewById(R.id.container_place_autocomplete_fragment_destination);
    }


    /**
     * init listeners for placeautocomplete fragments
     * display information on autocomplete fragemnts
     */
    public void initOnSelectedListener() {

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                String result = null;
                try {
                    result = decodeAdress(place);
                    if(result==null) throw new Exception();
                    locationHashMap.put("from", result);
                    destinationCardView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        autocompleteFragmentDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                String result = null;
                try {
                    result = decodeAdress(place);
                    if(result==null) throw new Exception();
                    locationHashMap.put("destination", result);
                    openSummaryFragment();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

            }

            @Override
            public void onError(Status status) {

            }
        });

    }


    /**
     * dynamically open summary fragment after picking destination fragment
     */
    public void openSummaryFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("from", locationHashMap.get("from"));
        bundle.putString("destination", locationHashMap.get("destination"));
        SummaryFragment fragment = new SummaryFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.summary_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * setting a listner for naigation item click
     */
    public void initUpNavigationDrawer() {

        this.navigationView = findViewById(R.id.navigation_view);
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        onItemSelectectedNavigation(menuItem.getItemId());


                        return true;
                    }
                });

    }


    /**
     * method changing application flow depending on navigation item clicked
     * used in on navigationItemSelectedListner
     *
     * @param id
     */
    public void onItemSelectectedNavigation(int id) {

        switch (id) {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent homeIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(homeIntent);
                finish();
                break;

        }

    }

}
