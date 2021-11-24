package com.example.medoguide;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Adapters.VaccineListAdapter;
import com.example.medoguide.Model.VaccineListData;
import com.example.medoguide.Utils.BaseClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    Button datePickerBtn;
    RecyclerView vaccineList;
    TextView noResultFound;
    DatePickerDialog datePickerDialog;
    VaccineListAdapter adapter;
    String districtId, districtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        districtId = getIntent().getExtras().getString("district_id");
        districtName = getIntent().getExtras().getString("dist_name");

        datePickerBtn = findViewById(R.id.date_picker);
        noResultFound = findViewById(R.id.result_not_found);
        vaccineList = findViewById(R.id.vaccine_List);
        vaccineList.setLayoutManager(new LinearLayoutManager(this));
        vaccineList.setHasFixedSize(false);

        initDatePicker();

        datePickerBtn.setText(BaseClass.getTodaysDate());
        datePickerBtn.setOnClickListener(v -> {
            datePickerDialog.show();
        });
    }

    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = BaseClass.makeDateString(day, month, year);
                datePickerBtn.setText(date);

                DownloadVaccineSlotsData task = new DownloadVaccineSlotsData();
                task.execute("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=" + districtId + "&date=" + date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        cal.add(Calendar.DATE, 7);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 10000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        DownloadVaccineSlotsData task = new DownloadVaccineSlotsData();
        task.execute("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id=" + districtId + "&date=" + BaseClass.getTodaysDate());
    }

    public class DownloadVaccineSlotsData extends AsyncTask<String, Void, String> {
        ArrayList<VaccineListData> vaxListData = new ArrayList<>();

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            result.delete(0, urls.length);
            URL url;
            HttpURLConnection urlConnection;

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

                String sessionsInfo = jsonObject.getString("sessions");

                if (!sessionsInfo.equals("[]")) {

                    Log.i("Dist content", sessionsInfo);

                    JSONArray arr = new JSONArray(sessionsInfo);

                    for (int i = 0; i < arr.length(); i++) {
                        noResultFound.setVisibility(View.GONE);
                        vaccineList.setVisibility(View.VISIBLE);
                        JSONObject jsonPart = arr.getJSONObject(i);
                        String centreName = jsonPart.getString("name");
                        String centreAddress = jsonPart.getString("address");
                        String fee_type = jsonPart.getString("fee_type");
                        String amount = jsonPart.getString("fee");
                        String dose1 = String.valueOf(jsonPart.getInt("available_capacity_dose1"));
                        String dose2 = String.valueOf(jsonPart.getInt("available_capacity_dose2"));
                        String min_age_limit = String.valueOf(jsonPart.getInt("min_age_limit"));
                        String vaccine_type = jsonPart.getString("vaccine");

                        Log.i("centreName", centreName);
                        Log.i("centreAddress", centreAddress);
                        Log.i("fee_type", fee_type);
                        Log.i("amount", amount);
                        Log.i("dose1", dose1);
                        Log.i("dose2", dose2);
                        Log.i("min_age_limit", min_age_limit);
                        Log.i("vaccine_type", vaccine_type);

                        if (!dose1.equals("0") || !dose2.equals("0")) {
                            vaxListData.add(new VaccineListData(centreName, centreAddress, fee_type, amount, dose1, dose2, min_age_limit, vaccine_type));
                            adapter = new VaccineListAdapter(getParent(), vaxListData);
                            vaccineList.setAdapter(adapter);
                        }
                    }
                } else {
                    noResultFound.setVisibility(View.VISIBLE);
                    vaccineList.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}