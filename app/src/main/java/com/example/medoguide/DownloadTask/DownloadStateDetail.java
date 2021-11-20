package com.example.medoguide.DownloadTask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.medoguide.Utils.BaseClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadStateDetail extends AsyncTask<String, Void, String> {

    String stateName, districtName;
    Context mContext;
    public DownloadStateDetail(Context mContext, String stateName, String districtName) {
        this.mContext = mContext;
        this.stateName = stateName;
        this.districtName = districtName;
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder result = new StringBuilder();
        result.delete(0, urls.length);
        URL url;
        HttpURLConnection urlConnection = null;

        try {

            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result.append(current);
                data = reader.read();
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("status 1", "inside vaccine post");
        try {
            JSONObject jsonObject = new JSONObject(s);

            String statesInfo = jsonObject.getString("states");

            Log.i("State content", statesInfo);

            JSONArray arr = new JSONArray(statesInfo);

                        //binary searching
            String resultID = BaseClass.binarySearching(arr, stateName, "state_name", "state_id");
            Log.i("Sourav", resultID);

            DownloadDistrictDetails task = new DownloadDistrictDetails(mContext,stateName, districtName);
            task.execute("https://cdn-api.co-vin.in/api/v2/admin/location/districts/" + resultID);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}