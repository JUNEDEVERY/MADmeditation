package com.example.designmobileproject.Masks;

public class MaskUser {

    private String id;
    private String email;
    private String nickName;
    private String avatar;
    private String token;

    public MaskUser(String id, String email, String nickName, String avatar, String token) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.avatar = avatar;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getToken() {
        return token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
