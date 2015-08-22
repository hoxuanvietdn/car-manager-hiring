package com.car.controllers;

import com.car.mysql.models.Role;
import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlRoleRepository;
import com.car.mysql.repositories.MysqlUserRepository;
import com.car.services.MailService;
import com.car.services.UserService;
import com.car.services.security.UserDetailsImpl;
import com.car.utils.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by hxviet on 6/9/2015.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

    @Autowired
    private MysqlRoleRepository mysqlRoleRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
    
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String listUser(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex) {
        model = putUsers(model, pageIndex);

        User user = new User();
        model.addAttribute("user", user);

        logger.debug("sessionRegistry.getAllPrincipals().size()={}", sessionRegistry.getAllPrincipals().size());

        //DEBUG ONLY
        if(logger.isDebugEnabled()) {
        	for (Object obj : sessionRegistry.getAllPrincipals()) {
        		UserDetailsImpl onlineUser = (UserDetailsImpl)obj;
        		logger.debug("username={}", onlineUser.getUsername());
        		for (SessionInformation session : sessionRegistry.getAllSessions(obj, false)) {
        			logger.debug("sessionId={}", session.getSessionId());
				}
        	}
        }
        
        return "admin/admin";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_USER")
    public String saveUser(Model model, Principal principal,
                           @RequestParam(value = "roleId", required = false) long roleId,
                           @RequestParam(value = "user_status", required = false) String userStatus,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex,
                           @Valid User user, BindingResult results) {

        if (results.hasFieldErrors("userEmail")) {
            model = putUsers(model, pageIndex);

            model.addAttribute("user", user);

            return "admin/admin";
        }

        if (user.getUserId() != null && user.getUserId() > 0) {
            User selectedUser = mysqlUserRepository.findOne(user.getUserId());

            selectedUser.setModifiedDate(new Date());
            selectedUser.setUserEmail(user.getUserEmail());
            if(Constant.ENABLE_CODE.equals(userStatus)){
                selectedUser.setIsEnable(true);

            } else {
                selectedUser.setIsEnable(false);
            }

            Role role = mysqlRoleRepository.findOne(roleId);
            selectedUser.setRoles(role);

            mysqlUserRepository.save(selectedUser);

        } else {
                mysqlUserRepository.save(userService.initUser(user, roleId));

                mailService.sendMailActivateAccount(user.getUserEmail());
            }

        return "redirect:/admin/";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String deleteUser(@RequestParam(value = "userId", required = true)long userId) {
        mysqlUserRepository.delete(userId);

        return "redirect:/admin/";
    }

    @RequestMapping(value = "/validateEmailWithUserId", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public @ResponseBody  String validateEmail(@RequestParam("userEmail")String userEmail,
                                               @RequestParam("userId")long userId) {
        // Check mail duplicate
        User selectedUser = mysqlUserRepository.findOne(userId);
        if(!userEmail.equals(selectedUser.getUserEmail())){
            User existUser = mysqlUserRepository.findByUserEmail(userEmail);
            if(existUser != null){
                return "true";
            }
        }

        return "false";
    }

    @RequestMapping(value = "/validateEmailWithoutUserId", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public @ResponseBody  String validateEmail(@RequestParam("userEmail")String userEmail) {
        // Check mail duplicate
        User existUser = mysqlUserRepository.findByUserEmail(userEmail);
        if(existUser != null){
            return "true";
        }
        return "false";
    }

    private Model putUsers(Model model, int index){
        Page<User> page = userService.findAllUser(index, null);
        List<Role> roles = mysqlRoleRepository.findAll();
        model.addAttribute("page", page);
        model.addAttribute("roles", roles);

        return model;
    }
}
