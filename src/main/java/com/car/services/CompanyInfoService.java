package com.car.services;

import com.car.mysql.models.CompanyInfo;
import com.car.mysql.models.Event;
import com.car.mysql.models.Permission;
import com.car.mysql.models.Role;
import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlCompanyInfoRepository;
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
public class CompanyInfoService {

    Logger logger = LoggerFactory.getLogger(CompanyInfoService.class);


    @Autowired
    private MysqlCompanyInfoRepository mysqlCompanyInfoRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;


    public Page<CompanyInfo> findAllInfos(int index, String sortFieldName) {
        if (sortFieldName == null || sortFieldName.isEmpty()) {
            return mysqlCompanyInfoRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE));
        }

        return mysqlCompanyInfoRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE, new Sort(sortFieldName)));
    }


}
