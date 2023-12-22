package com.app.ivans.ghimli.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    @Expose
    private String name;
    private String email;
    @SerializedName("phone_number")
    private String noPhone;
    private int status;
    @SerializedName("email_verified_at")
    private String emailVerifiedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private Role role;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNoPhone() {
        return noPhone;
    }

    public int getStatus() {
        return status;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Role getRole() {
        return role;
    }
}
