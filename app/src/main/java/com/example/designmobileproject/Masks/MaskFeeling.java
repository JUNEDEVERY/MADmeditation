package com.example.designmobileproject.Masks;

public class MaskFeeling {

    private int id;
    private String title;
    private String image;
    private int position;

    public MaskFeeling(int id, String title, String image, int position) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
