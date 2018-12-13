package com.example.dominik.uberpaczka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class DistanceLoader extends AsyncTaskLoader<Long> {


    private GoogMatrixRequest googMatrixRequest;

    public DistanceLoader(@NonNull Context context) {
        super(context);
        googMatrixRequest=new GoogMatrixRequest();

    }

    @Override
    protected void onForceLoad() {
        forceLoad();
    }


    @Nullable
    @Override
    public Long loadInBackground() {
        Long returnValue=googMatrixRequest.transfer();
        return returnValue;
    }
}
