package com.project.buildingapp.models;

public class PublicComments {

    private String buildingcode, useremail, buildingcode_useremail, commenttype, comment, timestamp;

    public PublicComments() {
    }

    public PublicComments(String buildingcode, String useremail, String buildingcode_useremail, String commenttype, String comment, String timestamp) {
        this.buildingcode = buildingcode;
        this.useremail = useremail;
        this.buildingcode_useremail = buildingcode_useremail;
        this.commenttype = commenttype;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getBuildingcode_useremail() {
        return buildingcode_useremail;
    }

    public void setBuildingcode_useremail(String buildingcode_useremail) {
        this.buildingcode_useremail = buildingcode_useremail;
    }

    public String getComment() {
        return comment;
    }

    public String getCommenttype() {
        return commenttype;
    }

    public void setCommenttype(String commenttype) {
        this.commenttype = commenttype;
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
