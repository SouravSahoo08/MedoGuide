package com.example.medoguide.DownloadTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.medoguide.ResultActivity;
import com.example.medoguide.Utils.BaseClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadDistrictDetails extends AsyncTask<String, Void, String> {

    Context mContext;
    String stateName, districtName;

    public DownloadDistrictDetails(Context mContext, String stateName, String districtName) {
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

        try {
            JSONObject jsonObject = new JSONObject(s);

            String distInfo = jsonObject.getString("districts");

            Log.i("Dist content", distInfo);

            JSONArray arr = new JSONArray(distInfo);
           // txtIn2 = findViewById(R.id.txtIn2);
            //binary searching
            String resultID = BaseClass.binarySearching(arr, districtName, "district_name", "district_id");

            Intent intent = new Intent(mContext, ResultActivity.class);
            intent.putExtra("district_id", resultID);
            intent.putExtra("state_name", stateName);
            intent.putExtra("dist_name", districtName);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}