package com.project.buildingapp.models;

public class CareTaker {

    private String owneremail, owneremail_caretakeremail, caretakeremail, caretakername;
    private int caretakerphone;

    public CareTaker() {

    }

    public CareTaker(String owneremail, String owneremail_caretakeremail, String caretakeremail, String caretakername, int caretakerphone) {
        this.owneremail = owneremail;
        this.owneremail_caretakeremail = owneremail_caretakeremail;
        this.caretakeremail = caretakeremail;
        this.caretakername = caretakername;
        this.caretakerphone = caretakerphone;
    }

    public String getOwneremail() {
        return owneremail;
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    public String getOwneremail_caretakeremail() {
        return owneremail_caretakeremail;
    }

    public void setOwneremail_caretakeremail(String owneremail_caretakeremail) {
        this.owneremail_caretakeremail = owneremail_caretakeremail;
    }

    public String getCaretakeremail() {
        return caretakeremail;
    }

    public void setCaretakeremail(String caretakeremail) {
        this.caretakeremail = caretakeremail;
    }

    public String getCaretakername() {
        return caretakername;
    }

    public void setCaretakername(String caretakername) {
        this.caretakername = caretakername;
    }

    public int getCaretakerphone() {
        return caretakerphone;
    }

    public void setCaretakerphone(int caretakerphone) {
        this.caretakerphone = caretakerphone;
    }
}
