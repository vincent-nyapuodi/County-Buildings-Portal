package com.project.buildingapp.models;

public class Certifications {

    private String buildingcode, buildingcode_certificate, certificateno, certificateurl, buildingcode_certificate_status;
    private int status;

    public Certifications() {
    }

    public Certifications(String buildingcode, String buildingcode_certificate, String certificateno, String buildingcode_certificate_status, int status) {
        this.buildingcode = buildingcode;
        this.buildingcode_certificate = buildingcode_certificate;
        this.certificateno = certificateno;
        this.buildingcode_certificate_status = buildingcode_certificate_status;
        this.status = status;
    }

    public Certifications(String buildingcode, String buildingcode_certificate, String certificateno, String buildingcode_certificate_status, String certificateurl, int status) {
        this.buildingcode = buildingcode;
        this.buildingcode_certificate = buildingcode_certificate;
        this.certificateno = certificateno;
        this.certificateurl = certificateurl;
        this.buildingcode_certificate_status = buildingcode_certificate_status;
        this.status = status;
    }

    public String getBuildingcode() {
        return buildingcode;
    }

    public void setBuildingcode(String buildingcode) {
        this.buildingcode = buildingcode;
    }

    public String getBuildingcode_certificate() {
        return buildingcode_certificate;
    }

    public void setBuildingcode_certificate(String buildingcode_certificate) {
        this.buildingcode_certificate = buildingcode_certificate;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }

    public String getCertificateurl() {
        return certificateurl;
    }

    public void setCertificateurl(String certificateurl) {
        this.certificateurl = certificateurl;
    }

    public String getBuildingcode_certificate_status() {
        return buildingcode_certificate_status;
    }

    public void setBuildingcode_certificate_status(String buildingcode_certificate_status) {
        this.buildingcode_certificate_status = buildingcode_certificate_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
