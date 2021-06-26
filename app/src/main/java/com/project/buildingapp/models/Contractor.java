package com.project.buildingapp.models;

public class Contractor {

    private String owneremail, buildingcode, owneremail_buildingcode, architectname, supervisorname, contractorname;

    public Contractor() {
    }

    public Contractor(String owneremail, String buildingcode, String owneremail_buildingcode, String architectname, String supervisorname, String contractorname) {
        this.owneremail = owneremail;
        this.buildingcode = buildingcode;
        this.owneremail_buildingcode = owneremail_buildingcode;
        this.architectname = architectname;
        this.supervisorname = supervisorname;
        this.contractorname = contractorname;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getOwneremail_buildingcode() {
        return owneremail_buildingcode;
    }

    public void setOwneremail_buildingcode(String owneremail_buildingcode) {
        this.owneremail_buildingcode = owneremail_buildingcode;
    }

    public String getArchitectname() {
        return architectname;
    }

    public void setArchitectname(String architectname) {
        this.architectname = architectname;
    }

    public String getSupervisorname() {
        return supervisorname;
    }

    public void setSupervisorname(String supervisorname) {
        this.supervisorname = supervisorname;
    }

    public String getContractorname() {
        return contractorname;
    }

    public void setContractorname(String contractorname) {
        this.contractorname = contractorname;
    }
}
