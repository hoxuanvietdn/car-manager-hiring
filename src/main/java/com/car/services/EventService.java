package com.car.services;

import com.car.mysql.models.Event;
import com.car.mysql.models.Permission;
import com.car.mysql.models.Role;
import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlEventRepository;
import com.car.mysql.repositories.MysqlPermissionRepository;
import com.car.mysql.repositories.MysqlRoleRepository;
import com.car.mysql.repositories.MysqlUserRepository;
import com.car.utils.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hxviet on 6/11/2015.
 */
@Service
public class EventService {

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private MysqlRoleRepository mysqlRoleRepository;

    @Autowired
    private MysqlPermissionRepository mysqlPermissionRepository;

    @Autowired
    private MysqlEventRepository mysqlEventRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;


    public Page<Event> findAllEvents(int index, String sortFieldName) {
        if (sortFieldName == null || sortFieldName.isEmpty()) {
            return mysqlEventRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE));
        }

        return mysqlEventRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE, new Sort(sortFieldName)));
    }


}
