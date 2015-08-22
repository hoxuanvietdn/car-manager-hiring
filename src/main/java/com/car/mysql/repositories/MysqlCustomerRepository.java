package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.mysql.models.Customer;


public interface MysqlCustomerRepository extends JpaRepository<Customer, Long> {

}
