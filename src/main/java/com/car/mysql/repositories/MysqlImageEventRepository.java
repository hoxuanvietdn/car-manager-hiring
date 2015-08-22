package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.mysql.models.ImageEvent;


public interface MysqlImageEventRepository extends JpaRepository<ImageEvent, Long> {

}
