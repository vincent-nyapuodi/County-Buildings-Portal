package com.project.buildingapp.models;

public class Session {

    private String email, buildingcode, email_status;
    private boolean status;

    public Session() {

    }

    public Session(String email, String buildingcode, String email_status, boolean status) {
        this.email = email;
        this.buildingcode = buildingcode;
        this.email_status = email_status;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getEmail_status() {
        return email_status;
    }

    public void setEmail_status(String email_status) {
        this.email_status = email_status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
