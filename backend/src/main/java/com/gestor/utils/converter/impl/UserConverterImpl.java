package com.gestor.utils.converter.impl;

import com.gestor.domains.User;
import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.domains.presenter.UserPresenter;
import com.gestor.model.UserModel;
import com.gestor.utils.converter.IAvatarConverter;
import com.gestor.utils.converter.IUserConverter;
import com.gestor.utils.rolesEnum.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userConverterImpl")
public class UserConverterImpl implements IUserConverter {

    @Autowired
    IAvatarConverter avatarConverter;

    @Override
    public UserModel convert(User user) {

        UserModel userModel = new UserModel();
        
        if(user.getEmail() != null)
        	userModel.setEmail(user.getEmail());
        
        userModel.setPassword(user.getPassword());
        userModel.setRole(Role.ROLE_CANDIDATE);
        if(user.getFirstName() != null)
        	userModel.setFirstName(user.getFirstName());
        
        if(user.getLastName() != null)
        	userModel.setLastName(user.getLastName());
        
        if(user.getId() != null)
        	userModel.setId(user.getId());

        if(user.getAvatar() != null)
            userModel.setAvatar(
                    avatarConverter.convert(user.getAvatar())
            );

        return userModel;
    }

    @Override
    public UserPresenter convert(UserModel userModel) {

        UserPresenter userPresenter = new UserPresenter();
        userPresenter.setEmail(userModel.getEmail());
        userPresenter.setId(userModel.getId());
        userPresenter.setCreateAt(userModel.getCreateAt());
        userPresenter.setUpdatedAt(userModel.getUpdatedAt());
        userPresenter.setRole(userModel.getRole());
        
        if(userModel.getCandidate() != null)
        	userPresenter.setCandidate(new CandidatePresenter(userModel.getCandidate().getId()));
        
        userPresenter.setFirstName(userModel.getFirstName());
        userPresenter.setLastName(userModel.getLastName());
        userPresenter.setFullName(userModel.getFirstName() + " " + userModel.getLastName());

        if(userModel.getAvatar() != null){
            userPresenter.setAvatar(
                    avatarConverter.convert(userModel.getAvatar())
            );
        }

        return userPresenter;
    }
}
