package com.project.buildingapp.models;

public class Approval {

    private String email, buildingcode, countyname, buildingcode_certificate, timestamp, comments;

    public Approval() {
    }

    public Approval(String email, String buildingcode, String countyname, String buildingcode_certificate, String timestamp) {
        this.email = email;
        this.buildingcode = buildingcode;
        this.countyname = countyname;
        this.buildingcode_certificate = buildingcode_certificate;
        this.timestamp = timestamp;
    }

    public Approval(String email, String buildingcode, String countyname, String buildingcode_certificate, String timestamp, String comments) {
        this.email = email;
        this.buildingcode = buildingcode;
        this.countyname = countyname;
        this.buildingcode_certificate = buildingcode_certificate;
        this.timestamp = timestamp;
        this.comments = comments;
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

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public String getBuildingcode_certificate() {
        return buildingcode_certificate;
    }

    public void setBuildingcode_certificate(String buildingcode_certificate) {
        this.buildingcode_certificate = buildingcode_certificate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
