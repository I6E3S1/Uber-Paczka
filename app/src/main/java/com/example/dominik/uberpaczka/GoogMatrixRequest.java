package com.example.dominik.uberpaczka;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class GoogMatrixRequest {


    private static final String API_KEY = "AIzaSyCKouLOaDJOP4pwmLLOOltyX1lr-dT0wRU";
    private String str_from;
    private String str_to;
    private final String URL_REQUEST = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from
            + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=" + API_KEY;
    private OkHttpClient client = new OkHttpClient();

    public Long transfer() {
        Log.i("TRANSFER.F", "begin");
        Log.i("TRANSFER.F", "req");
        String response = run(URL_REQUEST);
        Log.i("TRANSFER.F", "res" + response);
        Long longResult = extractJson(response);
        return longResult;


    }


    public Long extractJson(String response) {


        JSONObject jObject = null;
        try {
            jObject = new JSONObject(response);
        } catch (JSONException e) {
            Log.i("TRANSFER.F", "Json create");
            e.printStackTrace();
        }

        JSONArray jArray = null;
        try {
            jArray = Objects.requireNonNull(jObject).getJSONArray("rows");
        } catch (JSONException e) {
            Log.i("TRANSFER.F", "Json rows");
            e.printStackTrace();
        }

        Long distanceValue = 0L;
        for (int i = 0; i < Objects.requireNonNull(jArray).length(); i++) {
            try {

                JSONObject jObject1 = null;
                try {
                    jObject1 = jArray.getJSONObject(0);
                } catch (JSONException e) {
                    Log.i("TRANSFER.F", "Json empty");
                    e.printStackTrace();
                }

                JSONArray jArray1 = null;
                try {
                    jArray1 = Objects.requireNonNull(jObject1).getJSONArray("elements");
                } catch (JSONException e) {
                    Log.i("TRANSFER.F", "Json elements");
                    e.printStackTrace();
                }
                for (int j = 0; j < Objects.requireNonNull(jArray1).length(); j++) {

                    JSONObject jObject2 = null;
                    try {
                        jObject2 = jArray1.getJSONObject(0);
                    } catch (JSONException e) {
                        Log.i("TRANSFER.F", "Json empty");
                        e.printStackTrace();
                    }

                    JSONObject jObject3 = null;
                    try {
                        jObject3 = Objects.requireNonNull(jObject2).getJSONObject("distance");
                        Log.i("TRANSFER.F", "Zawartosc" + jObject3.toString());
                    } catch (JSONException e) {
                        Log.i("TRANSFER.F", "Json distance");
                        e.printStackTrace();
                    }

                    //JSONObject oneObject = jObject3.getJSONObject("value");
                    distanceValue = Objects.requireNonNull(jObject3).getLong("value");
                    //String oneObjectsItem2 = oneObject.getString("duration");
                    Log.i("JSON1", distanceValue.toString());
                    // Log.i("JSON2", oneObjectsItem2);
                }
            } catch (JSONException e) {
                Log.i("JSONY", "Blad\n" + response);
            }
        }

        return distanceValue;

    }

    private String run(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException();
        } catch (IOException e) {
            Log.i("RUN.F", "ResponseCall");
            e.printStackTrace();
            return "";
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            Log.i("RUN", "ResponseReturn");
            e.printStackTrace();
            return "";
        }
    }


    public void setStr_from(String str_from) {
        this.str_from = str_from;
    }

    public void setStr_to(String str_to) {
        this.str_to = str_to;
    }
}
