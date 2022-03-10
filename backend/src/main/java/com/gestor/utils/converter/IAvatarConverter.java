package com.gestor.utils.converter;

import com.gestor.domains.Avatar;
import com.gestor.domains.presenter.AvatarPresenter;
import com.gestor.model.AvatarModel;

public interface IAvatarConverter {
    AvatarModel convert(Avatar avatar);

    AvatarPresenter convert(AvatarModel avatarModel);
}
