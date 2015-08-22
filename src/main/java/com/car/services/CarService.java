package com.car.services;

import com.car.mysql.models.Car;
import com.car.mysql.models.CompanyInfo;
import com.car.mysql.models.Event;
import com.car.mysql.models.Image;
import com.car.mysql.models.Permission;
import com.car.mysql.models.Role;
import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlCarRepository;
import com.car.mysql.repositories.MysqlCompanyInfoRepository;
import com.car.mysql.repositories.MysqlEventRepository;
import com.car.mysql.repositories.MysqlImageRepository;
import com.car.mysql.repositories.MysqlPermissionRepository;
import com.car.mysql.repositories.MysqlRoleRepository;
import com.car.mysql.repositories.MysqlUserRepository;
import com.car.utils.Cons;
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
import org.springframework.ui.Model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by hxviet on 6/11/2015.
 */
@Service
public class CarService {

    Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private MysqlCarRepository mysqlCarRepository;
    
    @Autowired
    private MysqlImageRepository mysqlImageRepository;

    @Autowired
    private MysqlPermissionRepository mysqlPermissionRepository;

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

    @Autowired
    private MysqlCompanyInfoRepository mysqlCompanyInfoRepository;
    
    @Autowired
    private MysqlEventRepository mysqlEventRepository;
    
    @Autowired
    private MailService mailService;

    @Autowired
    private MessageSource messageSource;
    
    private static long countVisitors = 0;
    
    public long getCountVisitors(){
    	return countVisitors;
    }
    public Page<Car> findAllCars(int index, String sortFieldName) {
        if (sortFieldName == null || sortFieldName.isEmpty()) {
            return mysqlCarRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE));
        }

        return mysqlCarRepository.findAll(new PageRequest(index, Constant.PAGING_TOTAL_LINE, new Sort(sortFieldName)));
    }
    
    public List<Car> findCarsByType(long type) {
        return mysqlCarRepository.findByType(type);
    }
    
    public Model putCommonObject(Model model, HttpServletRequest request) {
    	
    	HttpSession session = request.getSession();
    	logger.info("countVisitors: " + countVisitors);
    	if (session.isNew()) {
    		countVisitors ++;
    		logger.info("increa a request");
    	}
    	
        List<Car> cars = mysqlCarRepository.findAll();
        model.addAttribute("cars", cars);
        
        List<Image> images = new ArrayList<Image>();
        for(Car car : cars) {
        	Set<Image> imgs = car.getImages();
        	if (imgs != null && !imgs.isEmpty()) {
        		images.add(imgs.iterator().next());
        	}
        }
        
        model.addAttribute("images", images);
        
        List<CompanyInfo> infos = mysqlCompanyInfoRepository.findAll();
        if (infos != null && !infos.isEmpty()) {
        	model.addAttribute("info", infos.get(0));
        	
        } else {
        	model.addAttribute("info", new CompanyInfo());
        }
        
        List<Event> events = mysqlEventRepository.findAll();
        model.addAttribute("events", events);
        
        List<Car> carsSdlie = mysqlCarRepository.findByType(Cons.CAR_SLIDE);
        model.addAttribute("carsSdlie", carsSdlie);
        
        List<Car> carsQuangCao = mysqlCarRepository.findByType(Cons.CAR_QUANG_CAO);
        model.addAttribute("carsQuangCao", carsQuangCao);
        
        return model;
    }
    
    public List<Car> carsYeuThich() {
        List<Car> carsYeuThich = mysqlCarRepository.findByType(Cons.CAR_YEU_THICH);
        
        if (carsYeuThich != null && carsYeuThich.size() > 3) {
        	return carsYeuThich.subList(0, 3);
        }
        
        return carsYeuThich;
    }
}
