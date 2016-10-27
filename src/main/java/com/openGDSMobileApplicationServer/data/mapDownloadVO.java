package com.openGDSMobileApplicationServer.data;

/**
 * Created by intruder on 16. 4. 7.
 */
public class mapDownloadVO {


    private String mapTitle="";
    private float latMin;
    private float latMax;
    private float longMin;
    private float longMax;
    private int zoomMin;
    private int zoomMax;

    public String getMapTitle() {
        return mapTitle;
    }

    public float getLatMin() {
        return latMin;
    }

    public float getLatMax() {
        return latMax;
    }

    public float getLongMin() {
        return longMin;
    }

    public float getLongMax() {
        return longMax;
    }

    public int getZoomMin() {
        return zoomMin;
    }

    public int getZoomMax() {
        return zoomMax;
    }

    public void setMapTitle(String mapTitle) {
        this.mapTitle = mapTitle;
    }

    public void setLatMin(float latMin) {
        this.latMin = latMin;
    }

    public void setLatMax(float latMax) {
        this.latMax = latMax;
    }

    public void setLongMin(float longMin) {
        this.longMin = longMin;
    }

    public void setLongMax(float longMax) {
        this.longMax = longMax;
    }

    public void setZoomMin(int zoomMin) {
        this.zoomMin = zoomMin;
    }

    public void setZoomMax(int zoomMax) {
        this.zoomMax = zoomMax;
    }

    @Override
    public String toString() {
        return "{" +
                "latMin=" + latMin +
                ", latMax=" + latMax +
                ", longMin=" + longMin +
                ", longMax=" + longMax +
                ", zoomMin=" + zoomMin +
                ", zoomMax=" + zoomMax +
                '}';
    }
}
