package com.example.medoguide.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class BaseClass {

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
