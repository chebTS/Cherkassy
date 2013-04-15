package org.geekhub.cherkassy.objects;

import android.content.Context;
import org.geekhub.cherkassy.db.ImageTable;
import org.json.JSONException;
import org.json.JSONObject;

public class ImagesObj {
    private String ImageURL;
    private int InfoID;

    public ImagesObj(JSONObject data, Context context, int infoID) throws JSONException {
        setImageURL(data.getString("url"));
        setInfoID(infoID);

        ImageTable.saveToDB(context,this);
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public int getInfoID() {
        return InfoID;
    }

    public void setInfoID(int infoID) {
        InfoID = infoID;
    }
}
