package com.car.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.car.mysql.models.User;

/**
 * Created by nvtien2 on 10/06/2015.
 */
public interface MysqlUserRepository extends JpaRepository<User, Long> {
    public User findByUserName(String userName);
    public User findByUserEmail(String userEmail);

    @Query(value="select max(id) from User")
    public long getMaxIdUser();
}
