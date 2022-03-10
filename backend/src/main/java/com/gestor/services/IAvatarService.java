package com.gestor.services;

import com.gestor.domains.Avatar;
import com.gestor.domains.presenter.AvatarPresenter;

public interface IAvatarService {
    AvatarPresenter save(Avatar avatar);
}
