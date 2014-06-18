package ar.edu.unicen.ringo.console.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller that manages the virtual machines.
 * @author pablosaavedra
 */
@Controller("vmController")
@RequestMapping("/admin/vms")
public class VmManagementController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "vm.list";
	}
}
