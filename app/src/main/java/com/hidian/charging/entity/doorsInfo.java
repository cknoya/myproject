package com.hidian.charging.entity;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/30.
 */

public class doorsInfo {

    /**
     * area : string
     * id : string
     * siteAddress : string
     * siteGeoLatitude : 0
     * siteGeoLongitude : 0
     * siteName : string
     * totalDeviceAvailable : 0
     * totalDeviceDeployed : 0
     */

    private String area;
    private String id;
    private String siteAddress;
    private double siteGeoLatitude;
    private double siteGeoLongitude;
    private String siteName;
    private int totalDeviceAvailable;
    private int totalDeviceDeployed;

    private String siteDetailInEn;

    public String getJsonObject() {
        return siteDetailInEn;
    }

    public void setJsonObject(String jsonObject) {
        this.siteDetailInEn = jsonObject;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public double getSiteGeoLatitude() {
        return siteGeoLatitude;
    }

    public void setSiteGeoLatitude(double siteGeoLatitude) {
        this.siteGeoLatitude = siteGeoLatitude;
    }

    public double getSiteGeoLongitude() {
        return siteGeoLongitude;
    }

    public void setSiteGeoLongitude(double siteGeoLongitude) {
        this.siteGeoLongitude = siteGeoLongitude;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getTotalDeviceAvailable() {
        return totalDeviceAvailable;
    }

    public void setTotalDeviceAvailable(int totalDeviceAvailable) {
        this.totalDeviceAvailable = totalDeviceAvailable;
    }

    public int getTotalDeviceDeployed() {
        return totalDeviceDeployed;
    }

    public void setTotalDeviceDeployed(int totalDeviceDeployed) {
        this.totalDeviceDeployed = totalDeviceDeployed;
    }

}
