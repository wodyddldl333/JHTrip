package com.ssafy.jhtrip.user.model.dto;

public class ResetToken {
    private int id;
    private String userId;
    private String token;
    private String expiry;

    public  ResetToken() {}
    public ResetToken(int id, String userId, String token, String expiry) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiry = expiry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "ResetToken{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", expiry='" + expiry + '\'' +
                '}';
    }
}
