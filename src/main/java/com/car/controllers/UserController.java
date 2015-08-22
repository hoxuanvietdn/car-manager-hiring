package com.car.controllers;

import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlUserRepository;
import com.car.services.UserService;
import com.car.services.security.UserDetailsImpl;
import com.car.services.security.UserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by hxviet on 5/25/2015.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl authUserService;

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/registerConfirm/{userId}", method = RequestMethod.GET)
    public String registerConfirm(@PathVariable("userId") long userId, @RequestParam("activationCode") String activationCode, final RedirectAttributes redirectAttributes, ModelMap modelMap) {
        User userConfirm = userService.checkRegisterConfirm(userId, activationCode);
        if (userConfirm == null) {
            redirectAttributes.addFlashAttribute("messageType", "Danger");
            redirectAttributes.addFlashAttribute("message", "Expired activation link");
            return "redirect:/login";
        }
        modelMap.addAttribute("user", userConfirm);
        modelMap.addAttribute("activationCode", userConfirm.getActivationCode());
        return "authentication/registerconfirm";
    }

    @RequestMapping(value = "/saveProfileInfo", method = RequestMethod.POST)
    public String saveProfileInfo(@Valid User user, BindingResult results, long userId,
                                  String activationCode, final RedirectAttributes redirectAttributes) {
        if (results.hasErrors()) {
            return "authentication/registerconfirm";
        }
        User userExist = mysqlUserRepository.findByUserName(user.getUserName());
        if (userExist != null) {
            FieldError error = new FieldError("user", "userName", "userName already existed !");
            results.addError(error);
            return "authentication/registerconfirm";
        }
        User user_ = userService.updateProfile(user, userId, activationCode);
        if (user_ == null) {
            redirectAttributes.addFlashAttribute("messageType", "Danger");
            redirectAttributes.addFlashAttribute("message", "Token is invalid");
            return "redirect:/login";
        }

        UserDetails userDetails = authUserService.loadUserByUsername(user_.getUserName());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/";
    }

    @RequestMapping(value = "/sendResetPasswordLink", method = RequestMethod.POST)
    public String sendResetPasswordLink(@RequestParam("user-email-reset") String userEmail, final RedirectAttributes redirectAttributes) {
        String statusReset = userService.sendResetPasswordLink(userEmail);
        if (statusReset.equals(messageSource.getMessage("service.user.sendemail.success", null, Locale.ENGLISH)))
            redirectAttributes.addFlashAttribute("messageType", "Success");
        else
            redirectAttributes.addFlashAttribute("messageType", "Danger");

        redirectAttributes.addFlashAttribute("message", statusReset);
        return "redirect:/login";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(@RequestParam("key") long userId, @RequestParam("token") String token, final RedirectAttributes redirectAttributes, ModelMap modelMap) {
        boolean result = userService.checkResetPasswordToken(userId, token);
        if (!result) {
            redirectAttributes.addFlashAttribute("messageType", "Danger");
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("service.user.resetpwd.link.expired", null, Locale.ENGLISH));
            return "redirect:/login";
        }
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("token", token);
        return "authentication/settingpassword";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam("user-id") long userId, @RequestParam("token") String token, @RequestParam("new-password") String newPassword, final RedirectAttributes redirectAttributes) {
        User user = userService.changePassword(userId, token, newPassword);
        if (user == null) {
            redirectAttributes.addFlashAttribute("messageType", "Danger");
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("service.user.resetpwd.token.invalid", null, Locale.ENGLISH));
            return "redirect:/login";
        }
        UserDetails userDetails = authUserService.loadUserByUsername(user.getUserName());
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), authUserService.loadUserByUsername(user.getUserName()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("service.user.changepassword.success", null, Locale.ENGLISH));
        return "redirect:/";
    }

    @RequestMapping(value = "/changeUserSkin", method = RequestMethod.GET)
    @ResponseBody
    public String changeUserSkin(@RequestParam("userSkin") String userSkin, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( !(auth instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            String userName = userDetails.getUsername();
            userService.changeUserSkin(userSkin, userName);
            session.setAttribute("userSkin", userSkin);
        }
        return "Success";
    }

}
