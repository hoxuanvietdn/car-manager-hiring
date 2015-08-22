package com.car.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.mysql.models.Car;
import com.car.mysql.models.CompanyInfo;
import com.car.mysql.models.Customer;
import com.car.mysql.models.Event;
import com.car.mysql.models.Image;
import com.car.mysql.repositories.MysqlCarRepository;
import com.car.mysql.repositories.MysqlCompanyInfoRepository;
import com.car.mysql.repositories.MysqlCustomerRepository;
import com.car.mysql.repositories.MysqlEventRepository;
import com.car.services.CarService;
import com.car.services.MailService;
import com.car.utils.Cons;

/**
 * Created by xhviet on 02/06/2015.
 */
@Controller
public class DashboardController {

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private MysqlCarRepository mysqlCarRepository;
    
    @Autowired
    private MysqlCustomerRepository mysqlCustomerRepository;
    
    @Autowired
    private MysqlCompanyInfoRepository mysqlCompanyInfoRepository;
    
    @Autowired
    private MysqlEventRepository mysqlEventRepository;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private CarService carService;

    @RequestMapping(value="/countVisitor", method = RequestMethod.GET)
    public @ResponseBody long countVisitors() {
        return carService.getCountVisitors();
    }
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

        model = carService.putCommonObject(model, request);
        
        List<Car> carsYeuThich = carService.carsYeuThich();
        model.addAttribute("carsYeuThich", carsYeuThich);
        
        List<Event> eventClips = mysqlEventRepository.findByType(Cons.EVENT_CLIP_NOI_BAT);
        model.addAttribute("eventClips", eventClips);
        
        List<Event> eventOther = mysqlEventRepository.findByType(Cons.EVENT_SU_KIEN_KHAC);
        model.addAttribute("eventOther", eventOther);

        
        return "home";
    }
    
    @RequestMapping(value="/car", method = RequestMethod.GET)
    public String carDetail(Model model, @RequestParam(value="carId", required=false, defaultValue="0")long carId, 
    		@RequestParam(value="type", required=false, defaultValue="0")long type, HttpServletRequest request) {

        model = carService.putCommonObject(model, request);
        
        List<Car> cars = mysqlCarRepository.findAll();
        
        long carTypeId = 0;
        
        for(Car car : cars) {
        	if (type > 0 && carTypeId == 0 && car.getType() == type) {
        		carTypeId = car.getId();
        	}

        }
        
        
        if (carId > 0) {
            Car car = mysqlCarRepository.findOne(carId);
            model.addAttribute("car", car);
            
        } else {
            Car car = mysqlCarRepository.findOne(carTypeId);
            model.addAttribute("car", car);
        }

        
        return "car";
    }
    
    
    @RequestMapping(value="/event", method = RequestMethod.GET)
    public String eventDetail(Model model, @RequestParam(value="eventId", required=false, defaultValue="0")long eventId,
    		@RequestParam(value="type", required=false, defaultValue="0")long type, HttpServletRequest request) {

    	model = carService.putCommonObject(model, request);
    	
        List<Event> events = null;
        if (type > 0) {
        	events = mysqlEventRepository.findByType(type);
        	
        } else {
        	events = mysqlEventRepository.findAll();
        }
        		
        model.addAttribute("events", events);
        
        Event event = null;
        if (eventId > 0) {
             event = mysqlEventRepository.findOne(eventId);
            
        } else {
        	event = events.get(0);
        }

        model.addAttribute("event", event);
        
        return "event";
    }
    
    @RequestMapping(value="/contact", method = RequestMethod.GET)
    public String contact(Model model, HttpServletRequest request) {
        
    	model = carService.putCommonObject(model, request);
        
        model.addAttribute("contact", new Customer());
        
        return "contact-form";
    }
    
    @RequestMapping(value="/saveContact", method = RequestMethod.GET)
    public String saveContact(Model model, @Valid Customer contact, BindingResult results, HttpServletRequest request) {

        if (results.hasErrors()) {
            model.addAttribute("contact", contact);

            return "contact-form";
            
        } else {
        	contact.setCreateDate(new Date());
        	mysqlCustomerRepository.save(contact);
        	
        	mailService.sendContact(contact);
        }
        
        return "redirect:/";
    }
}
