package com.gestor.utils.converter.impl;

import com.gestor.domains.Avatar;
import com.gestor.domains.User;
import com.gestor.domains.presenter.AvatarPresenter;
import com.gestor.model.AvatarModel;
import com.gestor.utils.converter.IAvatarConverter;
import com.gestor.utils.converter.IUserConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("avatarConverterImpl")
public class AvatarConverterImpl implements IAvatarConverter {

    @Autowired
    IUserConverter userConverter;

    @Override
    public AvatarModel convert(Avatar avatar) {

        if(avatar == null) return null;

        AvatarModel avatarModel = new AvatarModel();

        avatarModel.setId(avatar.getId());

        //Prevents loop
        User user = avatar.getUser();
        user.setAvatar(null);

        avatarModel.setUser(
                userConverter.convert(user)
        );

        avatarModel.setSkinColor(avatar.getSkinColor());
        avatarModel.setTopType(avatar.getTopType());
        avatarModel.setHairColor(avatar.getHairColor());
        avatarModel.setEyebrowType(avatar.getEyebrowType());
        avatarModel.setEyeType(avatar.getEyeType());
        avatarModel.setAccessoriesType(avatar.getAccessoriesType());
        avatarModel.setFacialHairType(avatar.getFacialHairType());
        avatarModel.setMouthType(avatar.getMouthType());
        avatarModel.setClotheType(avatar.getClotheType());

        return avatarModel;
    }


    @Override
    public AvatarPresenter convert(AvatarModel avatarModel) {

        if(avatarModel == null) return null;

        AvatarPresenter avatarPresenter = new AvatarPresenter();

        avatarPresenter.setId(avatarModel.getId());

        //Prevents converter loop
        avatarModel.getUser().setAvatar(null);

        avatarPresenter.setUser(
                userConverter.convert(avatarModel.getUser())
        );

        avatarPresenter.setSkinColor(avatarModel.getSkinColor());
        avatarPresenter.setTopType(avatarModel.getTopType());
        avatarPresenter.setHairColor(avatarModel.getHairColor());
        avatarPresenter.setEyebrowType(avatarModel.getEyebrowType());
        avatarPresenter.setEyeType(avatarModel.getEyeType());
        avatarPresenter.setAccessoriesType(avatarModel.getAccessoriesType());
        avatarPresenter.setFacialHairType(avatarModel.getFacialHairType());
        avatarPresenter.setMouthType(avatarModel.getMouthType());
        avatarPresenter.setClotheType(avatarModel.getClotheType());

        return avatarPresenter;
    }
}
