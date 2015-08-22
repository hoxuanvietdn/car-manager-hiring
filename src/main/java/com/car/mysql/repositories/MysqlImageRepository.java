package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.car.mysql.models.Image;
import com.car.mysql.models.User;


public interface MysqlImageRepository extends JpaRepository<Image, Long> {

}
