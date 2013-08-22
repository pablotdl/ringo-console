package ar.edu.unicen.ringo.console.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.unicen.ringo.console.model.Identificable;
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
        System.out.println("Entering new SLA form");
        return "sla.form";
    }

    @RequestMapping(value = "/sla/{id}", method = RequestMethod.GET)
    public String editSla(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
        sla.loadFrom(service.get(id));
        System.out.println("About to render form");
        return "sla.form";
    }

    @RequestMapping(value = "/sla/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("sla") Sla sla) {
        service.save(sla);
        System.out.println("Saved an SLA: " + sla);
        return "redirect:/admin/sla";
    }

    @RequestMapping(value = "/sla", method = RequestMethod.GET)
    public String list() {
        System.out.println("Showing list page");
        return "sla.list";
    }

    @ResponseBody
    @RequestMapping(value = "/sla/list", method = RequestMethod.GET)
    public ListWrapper listSlas() {
        return new ListWrapper(service.list());
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/sla/{id}", method = RequestMethod.DELETE)
    public void deleteSla(@PathVariable("id") String id) {
        service.delete(id);
        System.out.println("Deleted SLA " + id);
    }
}
