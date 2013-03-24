package org.geekhub.cherkassy.helpers;

import android.content.Context;
import org.geekhub.cherkassy.objects.Info;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    public static void Parse(JSONObject data,Context context) {

        try {
            ParseCategory(data,"fast-food",context);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void ParseCategory (JSONObject data,String category,Context context) throws JSONException {
        JSONArray JSONitem = data.getJSONArray(category);


        for (int i = 1; i < JSONitem.length(); i++) {
            new Info(JSONitem.getJSONObject(i),category,context);
        }

    }
}
