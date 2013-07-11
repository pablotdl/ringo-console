package ar.edu.unicen.ringo.console.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.SlaManagementService;

/**
 * Controller that handles the CRUD operations related to SLAs and nodes.
 * @author psaavedra
 */
@Controller
@RequestMapping("/admin")
public class ManagementController {

    @Autowired
    private SlaManagementService service;

    @RequestMapping(value = "/sla/new", method = RequestMethod.GET)
    public String newSla(@ModelAttribute("sla") Sla sla) {
        return "sla/form";
    }

    @RequestMapping(value = "/sla/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("sla") Sla sla) {
        service.save(sla);
        System.out.println("Saved an SLA: " + sla);
        return "redirect:/admin/sla";
    }

    @RequestMapping(value = "/sla", method = RequestMethod.GET)
    public String list(ModelMap map) {
        map.put("slas", service.listSlas());
        return "sla/list";
    }

}
