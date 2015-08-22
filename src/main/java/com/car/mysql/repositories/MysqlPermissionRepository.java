package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.mysql.models.Permission;

/**
 * Created by hxviet on 6/11/2015.
 */
public interface MysqlPermissionRepository extends JpaRepository<Permission, Long> {
    public Permission findByPermissionName(String permissionName);
}
