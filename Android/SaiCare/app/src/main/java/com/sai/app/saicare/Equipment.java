package com.sai.app.saicare;

public class Equipment {
    private String e_details;
    private String equipments;

    public Equipment() {
    }

    public Equipment(String name, String url) {
        this.e_details = name;
        this.equipments = url;
    }

    public String getE_details() {
        return e_details;
    }

    public void setE_details(String name) {
        this.e_details = name;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String name) {
        this.equipments = name;
    }
}


