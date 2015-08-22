package com.car.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nvtien2 on 30/06/2015.
 */
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        logger.debug("add userSkin Session");
        if (request.getSession().getAttribute("userSkin") == null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            request.getSession().setAttribute("userSkin", userDetails.getThemeColor());
        }

        super.onAuthenticationSuccess(request, response, authentication);

    }

}