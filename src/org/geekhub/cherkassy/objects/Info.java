package org.geekhub.cherkassy.objects;

import android.content.Context;
import org.geekhub.cherkassy.db.InfoTable;
import org.json.JSONException;
import org.json.JSONObject;

public class Info {
    private String name;
    private String address;
    private String logoURL;
    private String phone;
    private String email;
    private String websiteURL;
    private float latitude;
    private float longitude;
    private String category;

    public Info(JSONObject data,String category,Context context) throws JSONException {
        setName(data.getString("name"));
        setAddress(data.getString("Address"));
        setLogoURL(data.getString("logo"));
        setPhone("null");
        setEmail(data.getString("email"));
        setWebsiteURL(data.getString("websiteURL"));
        setLatitude(data.getLong("latitude"));
        setLongitude(data.getLong("longitude"));
        setCategory(category);

        InfoTable.saveArticleToDB(context,this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}