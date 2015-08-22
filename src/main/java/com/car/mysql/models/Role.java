package com.car.mysql.models;

import java.io.Serializable;
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
import javax.persistence.PreRemove;
import javax.persistence.Table;

/**
 * Created by nvtien2 on 10/06/2015.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable{
	private static final long serialVersionUID = 8794941684244361416L;

	@Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
//    @Cascade({CascadeType.DELETE, CascadeType.SAVE_UPDATE})
    @JoinTable(name="role_permission", joinColumns={@JoinColumn(name="role_id")}, inverseJoinColumns={@JoinColumn(name="permission_id")})
    Set<Permission> permissions;

    @PreRemove
    private void removeRolesFromUsers() {
        for (User u : users) {
            u.getRoles().remove(this);
        }
    }

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
