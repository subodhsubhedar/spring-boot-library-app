package com.myapp.library.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller("errorController")
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Throwable ex) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("Cause", ex.getCause());
		
		mv.addObject("Message", ex.getMessage());
		
		mv.addObject("url", request.getRequestURL());
		
		mv.addObject("ex", ex);

		mv.setViewName("error");

		return mv;
	}

}
