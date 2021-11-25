package com.example.medoguide.CovidTrackAndVaxFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.medoguide.R;
import com.example.medoguide.ResultActivity;
import com.example.medoguide.Utils.BaseClass;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class VaxFragment extends Fragment {

    MaterialEditText stateName, districtName;
    Button search;
    String state,district;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vax, container, false);

        stateName = view.findViewById(R.id.state_name);
        districtName = view.findViewById(R.id.dist_name);
        search = view.findViewById(R.id.search);
        state = stateName.getText().toString();
        district = districtName.getText().toString();

        search.setOnClickListener(v->{
            DownloadStateDetail task = new DownloadStateDetail();
            task.execute("https://cdn-api.co-vin.in/api/v2/admin/location/states");
        });

        return view;
    }

    public class DownloadStateDetail extends AsyncTask<String, Void, String> {


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
                Log.d("Statename", state);
                String resultID = BaseClass.binarySearching(arr, stateName.getText().toString(), "state_name", "state_id");
                Log.i("Sourav", resultID);

                DownloadDistrictDetails task = new DownloadDistrictDetails();
                task.execute("https://cdn-api.co-vin.in/api/v2/admin/location/districts/" + resultID);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public class DownloadDistrictDetails extends AsyncTask<String, Void, String> {
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
                String resultID = BaseClass.binarySearching(arr, districtName.getText().toString(), "district_name", "district_id");

                Intent intent = new Intent(getContext(), ResultActivity.class);
                intent.putExtra("district_id", resultID);
                intent.putExtra("dist_name", district);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}