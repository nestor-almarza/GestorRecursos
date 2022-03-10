package com.gestor.utils.converter;


import com.gestor.domains.User;
import com.gestor.domains.presenter.UserPresenter;
import com.gestor.model.UserModel;

public interface IUserConverter {

    public UserModel convert(User user);

    public UserPresenter convert(UserModel userModel);
}
