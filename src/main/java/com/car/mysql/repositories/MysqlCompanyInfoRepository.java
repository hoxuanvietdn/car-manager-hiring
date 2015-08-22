package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.mysql.models.CompanyInfo;
import com.car.mysql.models.Event;


public interface MysqlCompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {

}
