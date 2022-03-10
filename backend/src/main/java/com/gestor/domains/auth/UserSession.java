package com.gestor.domains.auth;

import com.gestor.domains.presenter.UserPresenter;

public class UserSession {

    UserPresenter user;
    String hash;

    public UserSession(String hash, UserPresenter userPresenter){
        this.hash = hash;
        this.user = userPresenter;
    }

    public UserPresenter getUser() {
        return user;
    }

    public void setUser(UserPresenter user) {
        this.user = user;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
