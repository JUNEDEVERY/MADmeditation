package com.example.designmobileproject.Masks;

import java.io.File;

public class MaskProfile {
    public int id;
    public String path;
    public File imageProfile;
    public String data;

    public MaskProfile(int id, String path, File imageProfile, String data) {
        this.id = id;
        this.path = path;
        this.imageProfile = imageProfile;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(File imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
