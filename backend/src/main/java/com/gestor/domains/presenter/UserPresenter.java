package com.gestor.domains.presenter;

import java.io.Serializable;

import com.gestor.base.Base;
import com.gestor.utils.rolesEnum.Role;

public class UserPresenter extends Base implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private Role role;
    private CandidatePresenter candidate;
    private String firstName;
    private String lastName;
    private String fullName;
	private AvatarPresenter avatar;

    public UserPresenter() {
        super();
    }
    
    public UserPresenter(String userId) {
    	super(userId);
    }
    
    public UserPresenter(String userId, String fullName) {
    	super(userId);
    	this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public CandidatePresenter getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidatePresenter candidate) {
		this.candidate = candidate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public AvatarPresenter getAvatar() {
		return avatar;
	}

	public void setAvatar(AvatarPresenter avatar) {
		this.avatar = avatar;
	}
}
