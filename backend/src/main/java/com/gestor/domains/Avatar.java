package com.gestor.domains;

import com.gestor.base.Base;

public class Avatar extends Base {

    private User user;

    private String hairColor;

    private String clotheType;

    private String skinColor;

    private String topType;

    private String accessoriesType;

    private String facialHairType;

    private String eyeType;

    private String eyebrowType;

    private String mouthType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
