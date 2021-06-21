package com.example.hellochat;

public class User {
  private String uid;
  private String about;
  private String imageUrl;
  private String name;

    public User(String uid, String about, String imageUrl, String name) {
        this.uid = uid;
        this.about = about;
        this.imageUrl = imageUrl;
        this.name = name;
    }
    public User(){}
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
