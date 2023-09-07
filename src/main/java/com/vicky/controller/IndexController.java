package com.vicky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.vicky.constants.AppConstants;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String loadIndexPage() {
		
		
		return AppConstants.INDEX;
		
	}

}
    