package com.car.mysql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.car.mysql.models.Car;
import com.car.mysql.models.User;


public interface MysqlCarRepository extends JpaRepository<Car, Long> {
	public List<Car> findByType(long type);
}
