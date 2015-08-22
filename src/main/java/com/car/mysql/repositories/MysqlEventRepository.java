package com.car.mysql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.mysql.models.Event;


public interface MysqlEventRepository extends JpaRepository<Event, Long> {
	List<Event> findByType(long type);
}
