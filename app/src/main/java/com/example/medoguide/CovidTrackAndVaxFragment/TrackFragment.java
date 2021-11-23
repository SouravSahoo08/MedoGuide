package com.example.medoguide.CovidTrackAndVaxFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Adapters.TrackListAdapter;
import com.example.medoguide.DashBoard;
import com.example.medoguide.Model.TrackListData;
import com.example.medoguide.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrackFragment extends Fragment {

    TrackListAdapter adapter;
    private RecyclerView trackRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        trackRecyclerView = view.findViewById(R.id.tracker_Recycler_View);
        trackRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trackRecyclerView.setHasFixedSize(false);

        view.findViewById(R.id.tr_backBtn).setOnClickListener(v->{
            startActivity(new Intent(getActivity(), DashBoard.class));
            getActivity().finish();
        });

        DownloadTrackRecordData task = new DownloadTrackRecordData();
        task.execute("https://disease.sh/v2/all", "https://disease.sh/v3/covid-19/countries");
        return view;
    }

    public class DownloadTrackRecordData extends AsyncTask<String, String, String> {

        int switchctrl = 1;
        ArrayList<TrackListData> myListData = new ArrayList<>();
        ProgressDialog p;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            p = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_DARK);
            p.setMessage("Setting up data, Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            try {
                for (String s : urls) {
                    Log.i("url", s);
                    result.delete(0, result.length());
                    Log.i("background res next", result.toString());
                    url = new URL(s);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int data = reader.read();

                    while (data != -1) {
                        char current = (char) data;
                        result.append(current);
                        data = reader.read();
                    }
                    Log.i("background res", result.toString());
                    publishProgress(result.toString());
                    Log.i("status", "publishProgress called");

                }
                return null;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onProgressUpdate(String... s) {
            super.onProgressUpdate(s);

            Log.i("progress update", s[0]);
            //interpreting JSON Object and JSON Arrays


            if (switchctrl == 1) {
                worldJSON(s[0]);
            } else {
                try {
                    countryJSON(s[0]);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }

        private void worldJSON(String json) {
            switchctrl = 2;
            try {
                JSONObject jsonObject = new JSONObject(json);
                String populationInString = String.valueOf(jsonObject.getLong("population"));
                String casesInString = String.valueOf(jsonObject.getLong("cases"));
                String activeCaseInString = String.valueOf(jsonObject.getLong("active"));
                String criticalCaseInString = String.valueOf(jsonObject.getLong("critical"));
                String recoveredInString = String.valueOf(jsonObject.getLong("recovered"));
                String deathsInString = String.valueOf(jsonObject.getLong("deaths"));
                String noOfCountriesInString = String.valueOf(jsonObject.getInt("affectedCountries"));
                if (!populationInString.equals("") && !casesInString.equals("") && !activeCaseInString.equals("") && !criticalCaseInString.equals("")
                        && !recoveredInString.equals("") && !deathsInString.equals("") && !noOfCountriesInString.equals("")) {

                    myListData.add(new TrackListData("World", populationInString, casesInString, activeCaseInString, criticalCaseInString, recoveredInString
                            , deathsInString, noOfCountriesInString));
                }
            } catch (JSONException exception) {

                exception.printStackTrace();
            }
        }

        private void countryJSON(String json) throws JSONException {
            switchctrl = 1;
            JSONArray chkArr = new JSONArray(json);
            //myListData = new CountryListData[chkArr.length()];
            for (int i = 0; i < chkArr.length(); i++) {
                JSONObject jsonPart = chkArr.getJSONObject(i);
                String countryName = jsonPart.getString("country");
                String populationInString = String.valueOf(jsonPart.getLong("population"));
                String casesInString = String.valueOf(jsonPart.getLong("cases"));
                String activeCaseInString = String.valueOf(jsonPart.getLong("active"));
                String criticalCaseInString = String.valueOf(jsonPart.getLong("critical"));
                String recoveredInString = String.valueOf(jsonPart.getLong("recovered"));
                String deathsInString = String.valueOf(jsonPart.getLong("deaths"));

                if (!countryName.equals("") && !populationInString.equals("") && !casesInString.equals("") && !activeCaseInString.equals("") && !criticalCaseInString.equals("")
                        && !recoveredInString.equals("") && !deathsInString.equals("")) {
                    myListData.add(new TrackListData(countryName, populationInString, casesInString, activeCaseInString, criticalCaseInString, recoveredInString
                            , deathsInString));
                }
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (myListData != null) {
                p.hide();
                adapter = new TrackListAdapter(getContext(), myListData);
                trackRecyclerView.setAdapter(adapter);
            } else {
                p.show();
            }

        }

    }


}