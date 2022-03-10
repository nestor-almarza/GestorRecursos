package com.gestor.model;

import javax.persistence.*;

import com.gestor.base.Base;

@Entity
@Table(name="AVATAR")
public class AvatarModel extends Base {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column
    private String hairColor;

    @Column
    private String clotheType;

    @Column
    private String skinColor;

    @Column
    private String topType;

    @Column
    private String accessoriesType;

    @Column
    private String facialHairType;

    @Column
    private String eyeType;

    @Column
    private String eyebrowType;

    @Column
    private String mouthType;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getClotheType() {
        return clotheType;
    }

    public void setClotheType(String clotheType) {
        this.clotheType = clotheType;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getTopType() {
        return topType;
    }

    public void setTopType(String topType) {
        this.topType = topType;
    }

    public String getAccessoriesType() {
        return accessoriesType;
    }

    public void setAccessoriesType(String accessoriesType) {
        this.accessoriesType = accessoriesType;
    }

    public String getFacialHairType() {
        return facialHairType;
    }

    public void setFacialHairType(String facialHairType) {
        this.facialHairType = facialHairType;
    }

    public String getEyeType() {
        return eyeType;
    }

    public void setEyeType(String eyeType) {
        this.eyeType = eyeType;
    }

    public String getEyebrowType() {
        return eyebrowType;
    }

    public void setEyebrowType(String eyebrowType) {
        this.eyebrowType = eyebrowType;
    }

    public String getMouthType() {
        return mouthType;
    }

    public void setMouthType(String mouthType) {
        this.mouthType = mouthType;
    }
}
