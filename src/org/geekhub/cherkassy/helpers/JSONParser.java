package org.geekhub.cherkassy.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import org.geekhub.cherkassy.objects.Const;
import org.geekhub.cherkassy.objects.Info;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    public static void Parse(JSONObject data,Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();



        try {
            long timestamp = data.getLong("timestamp");

            if (preferences.getLong("All",-1) < timestamp) {
                editor.putLong("All",timestamp);
                for (String category : Const.MENU_LIST)
                ParseCategory(data,category,context,preferences);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.commit();

    }

    public static void ParseCategory (JSONObject data,String category,Context context,SharedPreferences preferences) throws JSONException {
        JSONArray JSONitem = data.getJSONArray(category);
        long timestamp = data.getLong("timestamp");

        if (preferences.getLong(category,-1) < timestamp) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(category,timestamp);

            for (int i = 1; i < JSONitem.length(); i++) {
                new Info(JSONitem.getJSONObject(i),category,context);
            }
        }
    }
}
