package com.mycs.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("m")
public class MessageController {
	@Autowired
	private MessageSource messageSource;

	@GetMapping("show")
	public String show(ModelMap model, Locale locale) {
		String wel =messageSource.getMessage("welcome", null, locale);
		model.addAttribute("welcome",wel);
		System.out.println("1111111111111111111111111");
		return "i18message";
	}
}
