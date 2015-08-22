package com.car.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

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

import com.car.mysql.models.Event;
import com.car.mysql.models.ImageEvent;
import com.car.mysql.repositories.MysqlEventRepository;
import com.car.mysql.repositories.MysqlImageEventRepository;
import com.car.services.EventService;
import com.car.services.MailService;

@Controller
@RequestMapping(value = "/admin/event")
public class AdminEventController {

    @Autowired
    private MysqlEventRepository mysqlEventRepository;

    @Autowired
    private MysqlImageEventRepository mysqlImageEventRepository;
    
    @Autowired
    private MailService mailService;

    @Autowired
    EventService eventService;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
    
    Logger logger = LoggerFactory.getLogger(AdminEventController.class);
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String listEvents(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex,
                           @RequestParam(value = "type", required = false, defaultValue = "1")int type) {
        model = putEvents(model, pageIndex, type);

        Event event = new Event();
        model.addAttribute("event", event);

        ImageEvent imageEvent =  new ImageEvent();
        model.addAttribute("imageEvent", imageEvent);
        
        return "admin/adminEvent";
    }

    @RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_USER")
    public String saveEvent(Model model, Principal principal,
                           @RequestParam(value = "index", required = false, defaultValue = "0")int pageIndex,
                           @Valid Event event, BindingResult results) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String userName = auth.getName();
    	
    	logger.info("event: " +  event);
        if (event != null && event.getId() != null && event.getId() > 0) {
        	Event originEvent = mysqlEventRepository.findOne(event.getId());
        	event.setCreateDate(originEvent.getCreateDate());
        	event.setModifiedDate(new Date());
        	event.setAuthor(userName);
            mysqlEventRepository.save(event);

        } else {
        	event.setAuthor(userName);
        	event.setCreateDate(new Date());
        	mysqlEventRepository.save(event);
        }

        return "redirect:/admin/event?type=" + event.getType();
    }

    
    @RequestMapping(value = "/saveImageEvent", method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_USER")
    public String saveCar(Model model, Principal principal,
    						@RequestParam(value = "eventImageId", required = true)long eventId,
                           @Valid ImageEvent imageEvent, BindingResult results,
                           @RequestParam("file")MultipartFile file,
                           HttpServletRequest request) {

    	String contextPath = request.getSession().getServletContext().getRealPath("/");
    	
    	String fileName = saveFile(file, contextPath);

    	Event event = mysqlEventRepository.findOne(eventId);
    	
    	ImageEvent image = createImage(file, event, imageEvent);
    	
    	mysqlImageEventRepository.save(image);

        return "redirect:/admin/event?type=" + event.getType();
    }
    
	private String saveFile(MultipartFile file, String contextPath) {

	    String saveDirectory = contextPath+"\\resources\\images\\events\\";

        String fileName = file.getOriginalFilename();
        if(!"".equalsIgnoreCase(fileName)){
           try {  
        	   file.transferTo(new File(saveDirectory + fileName));
				logger.info("multipartFile.getContentType(): " + file.getContentType());
				
				
			} catch (IllegalStateException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}  
               //Here I Added
         }
	   
	    return fileName;
	}
	
	
	private ImageEvent createImage(MultipartFile file, Event event, ImageEvent image) {
			if (image == null){
				image = new ImageEvent();
			}
			
			image.setCreateDate(new Date());
			String fileName = file.getOriginalFilename();
			String type = file.getContentType();
			if (type.contains("image")) {
				image.setImage(true);
				
			} else {
				image.setImage(false);
			}
			
			image.setPath(fileName);
			image.setEvent(event);
		return image;
	}
	
    @RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String deleteUser(@RequestParam(value = "eventId", required = true)long eventId, 
    		HttpServletRequest request) {
    	Event event = mysqlEventRepository.findOne(eventId);
    
        mysqlEventRepository.delete(eventId);

    	if (event != null && event.getImageEvents() != null && !event.getImageEvents().isEmpty()) {
    		for (ImageEvent image : event.getImageEvents()) {
    			deleteFile(image.getPath(), request);
    		}
    	}
    	
        return "redirect:/admin/event?type=" + event.getType();
    }

    @RequestMapping(value = "/validateEmailWithUserId", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public @ResponseBody  String validateEmail(@RequestParam("userEmail")String userEmail,
                                               @RequestParam("userId")long userId) {

        return "true";
    }


    private Model putEvents(Model model, int index, long type){
        //Page<Event> page = eventService.findAllEvents(index, null);
        //List<Role> roles = mysqlRoleRepository.findAll();
        //model.addAttribute("page", page);
        //model.addAttribute("roles", roles);

    	List<Event> events = mysqlEventRepository.findByType(type);
    	model.addAttribute("events", events);
    	model.addAttribute("type", type);
        return model;
    }
    
    
    
    @RequestMapping(value = "/deleteImage", method = RequestMethod.GET)
    @Secured("ROLE_MANAGE_USER")
    public String deleteImage(@RequestParam(value = "imageId", required = true)long imageId, 
    		HttpServletRequest request) {
    	
    	logger.info("delete image: " + imageId);
    	ImageEvent image = mysqlImageEventRepository.findOne(imageId);
    	Event event = image.getEvent();
    	if (image != null) {
        	image.setEvent(null);
        	mysqlImageEventRepository.delete(image);
        	
        	deleteFile(image.getPath(), request);
    	}

        return "redirect:/admin/event?type=" + event.getType();
    }

    private boolean deleteFile(String fileName, HttpServletRequest request) {
    	String contextPath = request.getSession().getServletContext().getRealPath("/");
    	String directory = contextPath + "\\resources\\images\\events\\";
    	File file = new File(directory + fileName);
    	return file.delete();
    }
}
