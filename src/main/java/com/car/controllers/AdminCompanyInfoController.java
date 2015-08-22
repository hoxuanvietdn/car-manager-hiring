package com.car.controllers;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.mysql.models.CompanyInfo;
import com.car.mysql.repositories.MysqlCompanyInfoRepository;
import com.car.mysql.repositories.MysqlRoleRepository;
import com.car.services.CompanyInfoService;
import com.car.services.MailService;

@Controller
@RequestMapping(value = "/admin/info")
public class AdminCompanyInfoController {

    @Autowired
    private MysqlCompanyInfoRepository mysqlCompanyInfoRepository;

    @Autowired
    private MysqlRoleRepository mysqlRoleRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    CompanyInfoService companyInfoService;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
    
    Logger logger = LoggerFactory.getLogger(AdminCompanyInfoController.class);
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String listInfos(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex) {
        model = putInfo(model, pageIndex);

        CompanyInfo info = new CompanyInfo();
        model.addAttribute("info", info);

        return "admin/adminCompanyInfo";
    }

    @RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_USER")
    public String saveInfo(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex,
                           @Valid CompanyInfo info, BindingResult results) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String userName = auth.getName();
    	
    	logger.info("info: " +  info);
        if (info != null && info.getId() != null && info.getId() > 0) {
        	CompanyInfo originInfo = mysqlCompanyInfoRepository.findOne(info.getId());
        	info.setCreateDate(originInfo.getCreateDate());
        	info.setModifiedDate(new Date());
        	mysqlCompanyInfoRepository.save(info);

        } else {
        	info.setCreateDate(new Date());
        	mysqlCompanyInfoRepository.save(info);
        }

        return "redirect:/admin/info";
    }

    @RequestMapping(value = "/deleteInfo", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String deleteInfo(@RequestParam(value = "infoId", required = true)long infoId) {
    	mysqlCompanyInfoRepository.delete(infoId);

        return "redirect:/admin/info";
    }

    @RequestMapping(value = "/validateEmailWithUserId", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public @ResponseBody  String validateEmail(@RequestParam("userEmail")String userEmail,
                                               @RequestParam("userId")long userId) {

        return "true";
    }


    private Model putInfo(Model model, int index){
        Page<CompanyInfo> page = companyInfoService.findAllInfos(index, null);
        //List<Role> roles = mysqlRoleRepository.findAll();
        model.addAttribute("page", page);
        //model.addAttribute("roles", roles);

        return model;
    }
}
