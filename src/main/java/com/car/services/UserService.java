package com.car.services;

import com.car.mysql.models.Permission;
import com.car.mysql.models.Role;
import com.car.mysql.models.User;
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
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private MysqlRoleRepository mysqlRoleRepository;

    @Autowired
    private MysqlPermissionRepository mysqlPermissionRepository;

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;

    public Collection<Permission> getPermissionsByUser(User user) {
        Collection<Permission> permissions = new ArrayList<Permission>();

        if (user != null) {
            Collection<Role> roles = user.getRoles();

            if (roles != null && !roles.isEmpty()) {
                for (Role role : roles) {
                    if(role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                        permissions.addAll(role.getPermissions());
                    }
                }
            }
        }

        return permissions;
    }

    public Collection<Permission> getPermissionsByUserName(String userName) {
        Collection<Permission> permissions = new ArrayList<Permission>();

        User user = mysqlUserRepository.findByUserName(userName);

        if (user != null) {
            Collection<Role> roles = user.getRoles();

            if (roles != null && !roles.isEmpty()) {
                for (Role role : roles) {
                    if(role.getPermissions() != null && !role.getPermissions().isEmpty()) {
                        permissions.addAll(role.getPermissions());
                    }
                }
            }
        }

        return permissions;
    }

    public User checkRegisterConfirm(long userId, String activationCode){
        User user = mysqlUserRepository.findOne(userId);
        if (user != null && user.getActivationCode() != null && user.getActivationCode().equals(activationCode)){
            return user;
        }
        return null;
    }

    public User changePassword(long userId, String token, String newPassword){
        User user = mysqlUserRepository.findOne(userId);
        if (user != null && user.getResetPwdToken() != null && user.getResetPwdToken().equals(token)){
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            user.setModifiedDate(new Date());
            user.setResetPwdToken(null);
            user.setResetPwdDateExpire(null);
            mysqlUserRepository.saveAndFlush(user);
            return user;
        }
        else{
            return null;
        }
    }

    public String sendResetPasswordLink(String userEmail){
        User user = mysqlUserRepository.findByUserEmail(userEmail);
        if (user != null){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +3);
            user.setResetPwdToken(UUID.randomUUID().toString());
            user.setResetPwdDateExpire(cal.getTime());
            mysqlUserRepository.saveAndFlush(user);

            //Send email forgot password
            mailService.sendMailForgotPassword(userEmail);

            return messageSource.getMessage("service.user.sendemail.success", null, Locale.ENGLISH);
        }
        return messageSource.getMessage("service.user.email.not.exist", null, Locale.ENGLISH);
    }

    public boolean checkResetPasswordToken(long userId, String token){
        User user = mysqlUserRepository.findOne(userId);
        if (user != null && user.getResetPwdToken() != null && user.getResetPwdToken().equals(token)){
            Calendar cal = Calendar.getInstance();
            if (cal.getTime().compareTo(user.getResetPwdDateExpire()) <= 0){
                return true;
            }
        }
        return false;
    }

    public User updateProfile(User user, long userId, String activationCode){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(mysqlUserRepository.findOne(userId).getRoles());

        User user_ = mysqlUserRepository.findOne(userId);
        if((user_ != null) && (user_.getActivationCode() != null) &&(user_.getActivationCode().equals(activationCode))) {
            user_.setPassword(user.getPassword());
            user_.setRoles(user.getRoles());
            user_.setActivationCode(null);
            user_.setFirstName(user.getFirstName());
            user_.setLastName(user.getLastName());
            user_.setUserName(user.getUserName());
            user_.setIsEnable(true);
            user_.setUserEmail(user.getUserEmail());
            user_.setModifiedDate(new Date());

            mysqlUserRepository.saveAndFlush(user_);
        }
        return user_;
    }

    public Page<User> findAllUser(int index, String sortFieldName) {
        if (sortFieldName == null || sortFieldName.isEmpty()) {
            return mysqlUserRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE));
        }

        return mysqlUserRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE, new Sort(sortFieldName)));
    }

    public User initUser(User user, long roleId) {
        user.setFirstName(Constant.STRING_EMPTY);
        user.setLastName(Constant.STRING_EMPTY);
        String userName = user.getUserEmail().substring(0, user.getUserEmail().indexOf('@')) + "_" +
                mysqlUserRepository.getMaxIdUser() + 1;
        user.setUserName(userName);
        user.setCreateDate(new Date());
        user.setPassword(UUID.randomUUID().toString());
        user.setActivationCode(UUID.randomUUID().toString());
        Role role = mysqlRoleRepository.findOne(roleId);
        user.setRoles(role);

        return user;
    }

    public void changeUserSkin(String userSkin, String userName) {
        User user = mysqlUserRepository.findByUserName(userName);
        if (user != null) {
            user.setThemeColor(userSkin);
            mysqlUserRepository.saveAndFlush(user);
        }
    }
}
