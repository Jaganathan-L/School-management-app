package com.eazybytes.eazyschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginController(@RequestParam(required = false) boolean error,
                                         @RequestParam(required = false) boolean logout,
                                         @RequestParam(required = false) boolean register,
                                         Model model){
        String errorMsg = null;
        if(error){
            errorMsg = "Username or Password is incorrect !!";
        }else if(logout){
            errorMsg = "You have been successfully logged out !!";
        } else if (register) {
            errorMsg = "You registration successfull. Login with registered credentials !!";
        }
        model.addAttribute("errorMessge",errorMsg);
        return "login.html";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/login?logout?true";
    }
}
