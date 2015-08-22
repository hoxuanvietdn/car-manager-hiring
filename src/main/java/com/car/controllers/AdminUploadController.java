package com.car.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.car.mysql.models.Car;
import com.car.mysql.models.Event;
import com.car.mysql.models.Image;
import com.car.mysql.repositories.MysqlCarRepository;
import com.car.mysql.repositories.MysqlEventRepository;
import com.car.mysql.repositories.MysqlImageRepository;
import com.car.mysql.repositories.MysqlRoleRepository;
import com.car.services.CarService;
import com.car.services.EventService;
import com.car.services.MailService;

import antlr.HTMLCodeGenerator;


@Controller
@RequestMapping(value = "/admin/car")
public class AdminUploadController {

    @Autowired
    private MysqlCarRepository mysqlCarRepository;

    @Autowired
    private MysqlImageRepository mysqlImageRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    CarService carService;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
    
    Logger logger = LoggerFactory.getLogger(AdminUploadController.class);
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String listCars(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex,
                           @RequestParam(value = "type", required = false, defaultValue = "1")long type) {
        model = putCars(model, pageIndex, type);

        Car car = new Car();
        model.addAttribute("car", car);

        return "admin/adminCar";
    }

    @RequestMapping(value = "/saveCar", method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_USER")
    public String saveCar(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex,
                           @Valid Car car, BindingResult results,
                           @RequestParam("file")MultipartFile[] files,
                           HttpServletRequest request) {
    	
    	//logger.info("car: " +  car);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String userName = auth.getName();
    	String contextPath = request.getSession().getServletContext().getRealPath("/");
    	List<String> urls = saveFiles(files, contextPath);

        if (car != null && car.getId() != null && car.getId() > 0) {
        	Car originCar = mysqlCarRepository.findOne(car.getId());
        	car.setCreateDate(originCar.getCreateDate());
        	car.setModifiedDate(new Date());
        	
        	if (urls != null && !urls.isEmpty()) {
            	Set<Image> images = createImages(files, car);
            	car.setImages(images);
            	
        	} else {
        		car.setImages(originCar.getImages());
        	}
        	
        	mysqlCarRepository.save(car);

        } else {
        	car.setCreateDate(new Date());
        	
        	if (urls != null && !urls.isEmpty()) {
            	Set<Image> images = createImages(files, car);
            	car.setImages(images);
        	}

        	mysqlCarRepository.save(car);
        }

        return "redirect:/admin/car?type=" + car.getType();
    }

    @RequestMapping(value = "/deleteCar", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String deleteCar(@RequestParam(value = "carId", required = true)long carId, HttpServletRequest request) {
    	Car car = mysqlCarRepository.findOne(carId);
    	mysqlCarRepository.delete(carId);

    	if (car != null && car.getImages() != null && !car.getImages().isEmpty()) {
    		for (Image image : car.getImages()) {
    			deleteFile(image.getPath(), request);
    		}
    	}
    	
        return "redirect:/admin/car?type=" + car.getType();
    }
    
    @RequestMapping(value = "/deleteImage", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String deleteImage(@RequestParam(value = "imageId", required = true)long imageId, HttpServletRequest request) {
    	logger.info("delete image: " + imageId);
    	Image image = mysqlImageRepository.findOne(imageId);
    	Car car = image.getCar();
    	if (image != null) {
        	image.setCar(null);
        	mysqlImageRepository.delete(image);
        	
        	deleteFile(image.getPath(), request);
    	}

        return "redirect:/admin/car?type=" + car.getType();
    }

    private boolean deleteFile(String fileName, HttpServletRequest request) {
    	String contextPath = request.getSession().getServletContext().getRealPath("/");
    	String directory = contextPath + "\\resources\\images\\cars\\";
    	File file = new File(directory + fileName);
    	return file.delete();
    }
    
    @RequestMapping(value = "/validateEmailWithUserId", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public @ResponseBody  String validateEmail(@RequestParam("userEmail")String userEmail,
                                               @RequestParam("userId")long userId) {
        return "true";
    }


    private Model putCars(Model model, int index, long type){
        //Page<Car> page = carService.findAllCars(index, null);
        //List<Role> roles = mysqlRoleRepository.findAll();
        //model.addAttribute("page", page);
        //model.addAttribute("roles", roles);

        List<Car> cars = carService.findCarsByType(type);
        model.addAttribute("cars", cars);
        model.addAttribute("type", type);
        
        return model;
    }
    
	
	private List<String> saveFiles(MultipartFile[] files, String contextPath) {
	    List<String> fileUrl = new ArrayList<String>();

	    String saveDirectory = contextPath+"\\resources\\images\\cars\\";

	    List<String> fileNames = new ArrayList<String>();

	    if(null != files && files.length > 0) {
	        for (MultipartFile multipartFile : files) {

	            String fileName = multipartFile.getOriginalFilename();
	            if(!"".equalsIgnoreCase(fileName)){
	                   fileUrl.add(new String(saveDirectory + fileName));

                   try {  
						multipartFile.transferTo(new File(saveDirectory + fileName));
						logger.info("multipartFile.getContentType(): " + multipartFile.getContentType());
						
						
					} catch (IllegalStateException e) {
						e.printStackTrace();

					} catch (IOException e) {
						e.printStackTrace();
					}  
	                   //Here I Added
	                fileNames.add(fileName);
	             }
	        }
	    }

	    return fileNames;
	}
	
	
	private Set<Image> createImages(MultipartFile[] files, Car car) {
		Set<Image> images = new HashSet<Image>();
		for (MultipartFile file : files) {
			Image image = new Image();
			image.setCreateDate(new Date());
			String fileName = file.getOriginalFilename();
			String type = file.getContentType();
			
            if(!"".equalsIgnoreCase(fileName)){
    			if (type.contains("image")) {
    				image.setImage(true);
    				
    			} else {
    				image.setImage(false);
    			}
    			
    			image.setPath(fileName);
    			image.setCar(car);
    			images.add(image);
            }

		}
		return images;
	}
}
