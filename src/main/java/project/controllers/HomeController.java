package project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	
	@GetMapping( {"/","/home","index","inicio"} )
	public String homePage(){
		return "home";
	}

}
