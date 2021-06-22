package com.project.buildingapp.models;

public class Session {

    private String buildingcode, buildingcode_status;
    private boolean status;

    public Session(String buildingcode, String buildingcode_status, boolean status) {
        this.buildingcode = buildingcode;
        this.buildingcode_status = buildingcode_status;
        this.status = status;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getBuildingcode_status() {
        return buildingcode_status;
    }

    public void setBuildingcode_status(String buildingcode_status) {
        this.buildingcode_status = buildingcode_status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
