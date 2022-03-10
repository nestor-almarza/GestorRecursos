package com.gestor.services.impl;

import com.gestor.domains.Avatar;
import com.gestor.domains.presenter.AvatarPresenter;
import com.gestor.model.AvatarModel;
import com.gestor.repository.IAvatarRepository;
import com.gestor.services.IAvatarService;
import com.gestor.utils.converter.IAvatarConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("avatarServiceImpl")
public class AvatarServiceImpl implements IAvatarService {

    @Autowired
    IAvatarRepository avatarRepository;

    @Autowired
    IAvatarConverter avatarConverter;

    @Override
    public AvatarPresenter save(Avatar avatar) {

        AvatarModel avatarModel = avatarConverter.convert(avatar);

        avatarModel = avatarRepository.save(avatarModel);

        AvatarPresenter avatarPresenter = avatarConverter.convert(avatarModel);

        return avatarPresenter;
    }
}
