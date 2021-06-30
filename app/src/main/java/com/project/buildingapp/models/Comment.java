package com.project.buildingapp.models;

public class Comment {

    private String buildingcode, buildingcode_email, buildingcode_certificate, comment, timestamp;

    public Comment() {
    }

    public Comment(String buildingcode, String buildingcode_email, String buildingcode_certificate, String comment, String timestamp) {
        this.buildingcode = buildingcode;
        this.buildingcode_email = buildingcode_email;
        this.buildingcode_certificate = buildingcode_certificate;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getBuildingcode_email() {
        return buildingcode_email;
    }

    public void setBuildingcode_email(String buildingcode_email) {
        this.buildingcode_email = buildingcode_email;
    }

    public String getBuildingcode_certificate() {
        return buildingcode_certificate;
    }

    public void setBuildingcode_certificate(String buildingcode_certificate) {
        this.buildingcode_certificate = buildingcode_certificate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
