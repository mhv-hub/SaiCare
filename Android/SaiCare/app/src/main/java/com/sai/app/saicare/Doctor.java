package com.sai.app.saicare;

public class Doctor {
    private String name;
    private String imageurl;
    private String quali;
    private String desg;


    public Doctor() {
    }

    public Doctor(String name, String url,String quali,String desg) {
        this.name = name;
        this.imageurl = url;
        this.quali = quali;
        this.desg = desg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesg() {
        return desg;
    }

    public void setDesg(String name) {
        this.desg = name;
    }

    public String getQuali() {
        return quali;
    }

    public void setQuali(String name) {
        this.quali = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String url) {
        this.imageurl = url;
    }
}
