package com.project.buildingapp.models;

public class Building {

    private String owneremail, owneremail_buildingcode, buildingcode, buildingtype, buildingname, buildingcounty, buildingtown, buildingdesc, buildingphoto;
    private int check_status;

    public Building() {

    }

    public Building(String owneremail, String owneremail_buildingcode, String buildingcode, String buildingtype, String buildingname, String buildingcounty, String buildingtown, String buildingdesc, String buildingphoto, int check_status) {
        this.owneremail = owneremail;
        this.owneremail_buildingcode = owneremail_buildingcode;
        this.buildingcode = buildingcode;
        this.buildingtype = buildingtype;
        this.buildingname = buildingname;
        this.buildingcounty = buildingcounty;
        this.buildingtown = buildingtown;
        this.buildingdesc = buildingdesc;
        this.buildingphoto = buildingphoto;
        this.check_status = check_status;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    public String getOwneremail_buildingcode() {
        return owneremail_buildingcode;
    }

    public void setOwneremail_buildingcode(String owneremail_buildingcode) {
        this.owneremail_buildingcode = owneremail_buildingcode;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getBuildingtype() {
        return buildingtype;
    }

    public void setBuildingtype(String buildingtype) {
        this.buildingtype = buildingtype;
    }

    public String getBuildingname() {
        return buildingname;
    }

    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }

    public String getBuildingcounty() {
        return buildingcounty;
    }

    public void setBuildingcounty(String buildingcounty) {
        this.buildingcounty = buildingcounty;
    }

    public String getBuildingtown() {
        return buildingtown;
    }

    public void setBuildingtown(String buildingtown) {
        this.buildingtown = buildingtown;
    }

    public String getBuildingdesc() {
        return buildingdesc;
    }

    public void setBuildingdesc(String buildingdesc) {
        this.buildingdesc = buildingdesc;
    }

    public String getBuildingphoto() {
        return buildingphoto;
    }

    public void setBuildingphoto(String buildingphoto) {
        this.buildingphoto = buildingphoto;
    }

    public int getCheck_status() {
        return check_status;
    }

    public void setCheck_status(int check_status) {
        this.check_status = check_status;
    }
}
