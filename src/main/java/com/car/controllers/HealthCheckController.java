package com.car.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

@Controller
@RequestMapping("/hc")
public class HealthCheckController {
	
	private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);
	
	@Value("${healthcheck.enabled}")
	private String healthCheckEnabled;

	@RequestMapping("/git-info")
	public String gitInfo(Model model) {

		try {
			
			if(!"true".equalsIgnoreCase(healthCheckEnabled)) {
				logger.error("Healcheck is not enabeld");
				return "error/404";
			}
			
			Properties properties = new Properties();
			properties.load(new ClassPathResource("git.properties").getInputStream());
			model.addAttribute("properties", properties.entrySet());
			
			return "healthcheck/properties";
		} catch (Exception e) {
			logger.error("", e);
			return "error/500";
		}
	}
	
	@RequestMapping("/app-config")
	public String checkGitRevision (
			Model model, 
			@Value("${MYSQL_URI}")	String mySqlUri, 
			@Value("${MONGO_HOST}")	String mongoHost,
			@Value("${MONGO_DATABASE_NAME}") String mongoDBName,
			@Value("${HOME_PAGE_URL}")	String homePageURL) {

		try {
			
			if(!"true".equalsIgnoreCase(healthCheckEnabled)) {
				logger.error("Healcheck is not enabeld");
				return "error/404";
			}
			
			Properties properties = new Properties();
			properties.put("MYSQL_URI", mySqlUri);
			properties.put("MONGO_HOST", mongoHost);
			properties.put("MONGO_DATABASE_NAME", mongoDBName);
			properties.put("HOME_PAGE_URL", homePageURL);
			
			model.addAttribute("properties", properties.entrySet());
			return "healthcheck/properties";
		} catch (Exception e) {
			logger.error("", e);
			return "error/500";
		}
	}
}
