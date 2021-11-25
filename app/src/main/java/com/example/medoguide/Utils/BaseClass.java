package com.example.medoguide.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BaseClass {

    public static void updateHistory(Context mContext, String medicineName, String no_of_doses, String doseType) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference().child("history");

        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        String currentTime = getStringTime();//new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


        Map<String, Object> map = new HashMap<>();
        map.put("consumeDate", currentDate);
        map.put("consumeTime", currentTime);
        map.put("medicine", medicineName);
        map.put("no_of_dose", no_of_doses);
        map.put("doseType", doseType);
        historyRef.child(mUser.getUid()).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(mContext, "Successfully took " + medicineName, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private static String getStringTime() {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        int nonMilitaryHour = hour % 12;
        if (nonMilitaryHour == 0)
            nonMilitaryHour = 12;
        String min = Integer.toString(minute);
        if (minute < 10)
            min = "0" + minute;

        return String.format(Locale.getDefault(), "%d:%s %s", nonMilitaryHour, min, getAm_pmTaken(hour));
    }

    private static String getAm_pmTaken(int hourTaken) {
        return (hourTaken < 12) ? "am" : "pm";
    }

    public static String binarySearching(JSONArray arr, String inputText, String regionType, String regionIDType) throws JSONException {
        int lb = 0, ub = arr.length() - 1;
        Log.d("JsonarrLength", String.valueOf(ub));
        Log.d("Statename", inputText);
        while (lb <= ub) {
            int mid = (lb + ub) / 2;
            JSONObject jsonPart = arr.getJSONObject(mid);
            int res = inputText.compareToIgnoreCase(jsonPart.getString(regionType));
            Log.i("sourav", String.valueOf(res));
            if (res == 0) {
                Log.i(regionType + " id ", jsonPart.getString(regionIDType));
                //Toast.makeText(DashBoard.this, "Id of" + jsonPart.getString(regionType) + "is" + jsonPart.getString(regionIDType), Toast.LENGTH_LONG).show();
                String id;

                //checks if searching in district api or state api
                if (regionIDType.equalsIgnoreCase("district_id")) {
                    id = String.valueOf(jsonPart.getInt(regionIDType));
                } else {
                    id = jsonPart.getString(regionIDType);
                }

                return id;
            } else if (res > 0)
                lb = mid + 1;
            else
                ub = mid - 1;
        }
        return null;
    }

    public static String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public static String makeDateString(int day, int month, int year) {
        return day + "-" + getMonthFormat(month) + "-" + year;
    }

    public static String getMonthFormat(int month) {
        if (month == 1)
            return "01";
        if (month == 2)
            return "02";
        if (month == 3)
            return "03";
        if (month == 4)
            return "04";
        if (month == 5)
            return "05";
        if (month == 6)
            return "06";
        if (month == 7)
            return "07";
        if (month == 8)
            return "08";
        if (month == 9)
            return "09";
        if (month == 10)
            return "10";
        if (month == 11)
            return "11";
        if (month == 12)
            return "12";

        //default should never happen
        return "01";
    }

}
