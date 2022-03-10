package com.gestor.model;

import javax.persistence.*;

import com.gestor.base.Base;
import com.gestor.utils.rolesEnum.Role;

@Entity
@Table(name="USER")
public class UserModel extends Base {

    @Column(unique=true)
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private CandidateModel candidate;

    @Column(length = 40)
    private String firstName;

    @Column(length = 40)
    private String lastName;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserSessionModel userSessionModel;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private AvatarModel avatar;

	public UserSessionModel getUserSession() {
		return userSessionModel;
	}

	public void setUserSession(UserSessionModel userSessionModel) {
		this.userSessionModel = userSessionModel;
	}

	public UserModel() {
		super();
	}
	public UserModel (String userId) {
		super(userId);
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public CandidateModel getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateModel candidate) {
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

	public AvatarModel getAvatar() {
		return avatar;
	}

	public void setAvatar(AvatarModel avatar) {
		this.avatar = avatar;
	}
}
