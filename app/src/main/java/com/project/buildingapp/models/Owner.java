package com.project.buildingapp.models;

public class Owner {

    private String email, name, owner_id;
    private int phone;

    public Owner() {
    }

    public Owner(String email, String name, String owner_id, int phone) {
        this.email = email;
        this.name = name;
        this.owner_id = owner_id;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
