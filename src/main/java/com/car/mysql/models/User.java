package com.car.mysql.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by nvtien2 on 10/06/2015.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 3368363855328388120L;

	@Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty(message = "{error.user.userName.empty}")
    @NotNull(message = "{error.user.userName.null}")
    @Size(min = 1, max = 30, message = "{error.user.userName.length}")
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "{error.user.email.type}")
    @NotNull(message = "{error.user.email.null}")
    @NotEmpty(message = "{error.user.email.empty}")
    @Size(min = 1, max = 30, message = "{error.user.email.length}")
    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @NotNull(message = "{error.user.password.null}")
    @NotEmpty(message = "{error.user.password.empty}")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_enable", nullable = false)
    private boolean isEnable;

    @Column(name = "activation_code", nullable = true)
    private String activationCode;

    @Column(name = "theme_color", nullable = true)
    private String themeColor = "skin-blur-violate";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", nullable = true)
    private Date modifiedDate;

    @Column(name = "reset_pwd_token", nullable = true)
    private String resetPwdToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reset_pwd_date_expire", nullable = true)
    private Date resetPwdDateExpire;

    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade({CascadeType.DELETE, CascadeType.SAVE_UPDATE})
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    Set<Role> roles;

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getResetPwdToken() {
        return resetPwdToken;
    }

    public void setResetPwdToken(String resetPwdToken) {
        this.resetPwdToken = resetPwdToken;
    }

    public Date getResetPwdDateExpire() {
        return resetPwdDateExpire;
    }

    public void setResetPwdDateExpire(Date resetPwdDateExpire) {
        this.resetPwdDateExpire = resetPwdDateExpire;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public long getFirstRoleId() {
        Collection<Role> roles = this.getRoles();

        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                return role.getRoleId();
            }
        }
        return 0;
    }

    public Role getRole() {
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                return role;
            }
        }

        return null;
    }


    public void setRoles(Role role) {
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                ", isEnable=" + isEnable +
                ", activationCode='" + activationCode + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", resetPwdToken='" + resetPwdToken + '\'' +
                ", resetPwdDateExpire=" + resetPwdDateExpire +
                ", roles=" + roles +
                '}';
    }
}
