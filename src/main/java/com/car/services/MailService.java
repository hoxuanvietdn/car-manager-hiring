package com.car.services;

import com.car.mysql.models.Customer;
import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlUserRepository;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


@Service("mailService")
public class MailService {
    final static Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    MysqlUserRepository mysqlUserRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Configuration freemarkerConfiguration;

    @Value("${HOME_PAGE_URL}")
    String homePageURL;

    @Value("${EMAIL_USER}")
    String senderEmail;

    @Value("${SENDER_NAME}")
    String senderName;

    public void sendMailActivateAccount(final String userEmail) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                User user = mysqlUserRepository.findByUserEmail(userEmail);

                if (user != null){
                    StringBuilder activationLink = new StringBuilder();
                    activationLink.append(homePageURL);
                    activationLink.append("user/registerConfirm/");
                    activationLink.append(user.getUserId());
                    activationLink.append("?activationCode=");
                    activationLink.append(user.getActivationCode());

                    message.setFrom(senderEmail, senderName);
                    message.setTo(userEmail);
                    message.setSubject("[Fusion] Activation Email");

                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("homepageUrl", homePageURL);
                    data.put("fullname", userEmail);
                    data.put("activationLink", activationLink.toString());
                    data.put("emailPreheader", "Activation Email");

                    String plainText = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("activateAccountText.ftl"), data);
                    String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("activateAccountHtml.ftl"), data);
                    message.setText(plainText, htmlText);
                }
                else{
                    return;
                }
            }
        };
        ThreadSendMail thread = new ThreadSendMail(preparator);
        thread.start();
    }

    public void sendMailForgotPassword(final String userEmail) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                User user = mysqlUserRepository.findByUserEmail(userEmail);
                StringBuilder linkSetNewPassword = new StringBuilder();
                linkSetNewPassword.append(homePageURL);
                linkSetNewPassword.append("user/resetPassword?key=");
                linkSetNewPassword.append(user.getUserId());
                linkSetNewPassword.append("&token=");
                linkSetNewPassword.append(user.getResetPwdToken());

                message.setFrom(senderEmail, senderName);
                message.setTo(userEmail);
                message.setSubject("[Fusion] Forgot Password Email");

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("homepageUrl", homePageURL);
                if (user.getFirstName().isEmpty() && user.getLastName().isEmpty()){
                    data.put("fullname", "Sir/Madam");
                } else {
                    data.put("fullname", user.getFirstName() + " " + user.getLastName());
                }
                data.put("linkSetNewPassword", linkSetNewPassword.toString());
                data.put("emailPreheader", "Forgot Password Email");

                String plainText = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("forgotPasswordText.ftl"), data);
                String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("forgotPasswordHtml.ftl"), data);
                message.setText(plainText, htmlText);
            }
        };
        ThreadSendMail thread = new ThreadSendMail(preparator);
        thread.start();
    }
    class ThreadSendMail extends Thread{
            MimeMessagePreparator preparator;

            public ThreadSendMail(MimeMessagePreparator preparator) {
                this.preparator = preparator;
            }
            @Override
            public void run() {
                javaMailSender.send(preparator);
            }
    }
    
    public void sendContact(final Customer customer) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                	
                    message.setFrom(senderEmail, senderName);
                    message.setTo(senderEmail);
                    message.setSubject("Yêu cầu từ khách hàng");

                    logger.info("senderEmail: " + senderEmail);
                    logger.info("senderName: " + senderName);
                    
                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("name", customer.getName());
                    data.put("phone", customer.getPhone());
                    data.put("email", customer.getEmail());
                    data.put("content", customer.getContent());
                    
                    logger.info("message: " + message);
                    logger.info("data: " + data);
                    
                    String plainText = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("contactText.ftl"), data);
                    //String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("activateAccountHtml.ftl"), data);
                    message.setText(plainText);
                
            }
            
        };
        ThreadSendMail thread = new ThreadSendMail(preparator);
        thread.start();
    }
}
