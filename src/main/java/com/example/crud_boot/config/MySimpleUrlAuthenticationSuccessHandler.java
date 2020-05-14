package com.example.crud_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


public class MySimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if((AuthorityUtils.authorityListToSet(
                authentication.getAuthorities()).contains("ADMIN"))){
            response.sendRedirect("/admin/");
        }else {
            response.sendRedirect("/user/");
        }
    }
}
