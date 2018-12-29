package com.example.dominik.uberpaczka.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class DistanceLoader extends AsyncTaskLoader<Long> {

    private GoogMatrixRequest googMatrixRequest;

    public DistanceLoader(@NonNull Context context, String from, String destination) {
        super(context);
        googMatrixRequest = new GoogMatrixRequest();
        googMatrixRequest.setStr_from(from);
        googMatrixRequest.setStr_to(destination);


    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Long loadInBackground() {
        Long returnValue = googMatrixRequest.transfer();
        return returnValue;
    }
}
