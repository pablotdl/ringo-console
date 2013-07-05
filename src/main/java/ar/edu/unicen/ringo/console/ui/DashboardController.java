package ar.edu.unicen.ringo.console.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class DashboardController {
	
	@RequestMapping("/")
	public String printWelcome(ModelMap model) {
 
		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "index";
 
	}

}
