package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.mysql.models.Role;

/**
 * Created by hxviet on 6/11/2015.
 */
public interface MysqlRoleRepository extends JpaRepository<Role, Long> {
    public Role findByRoleName(String roleName);
}
