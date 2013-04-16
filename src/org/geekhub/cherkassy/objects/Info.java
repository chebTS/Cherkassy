package org.geekhub.cherkassy.objects;

import android.content.Context;
import org.geekhub.cherkassy.db.InfoTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Info {
    private String name;
    private String address;
    private String description;
    private String logoURL;
    private String phone;
    private String email;
    private String websiteURL;
    private double latitude;
    private double longitude;
    private String category;

    public Info(JSONObject data,String category,Context context) throws JSONException {
        setName(data.getString("name"));
        setAddress(data.getString("Address"));
        setLogoURL(data.getString("logo"));
        setDescription(data.getString("description"));
        JSONArray JSONitem = data.getJSONArray("images");
        setPhone(data.getString("phone"));
        setEmail(data.getString("email"));
        setWebsiteURL(data.getString("websiteURL"));
        setLatitude(data.getDouble("latitude"));
        setLongitude(data.getDouble("longitude"));
        setCategory(category);

        int id = InfoTable.saveArticleToDB(context,this);

        for (int i = 0; i < JSONitem.length(); i++) {
            new ImagesObj(JSONitem.getJSONObject(i),context,id);
        }
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
