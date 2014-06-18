package ar.edu.unicen.ringo.console.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.SlaManagementService;
import ar.edu.unicen.ringo.console.ui.wrapper.ListWrapper;

/**
 * SLA management controller
 * @author pablosaavedra
 */
@Controller
@RequestMapping("/admin/sla")
public class SlaManagementController {

    @Autowired
    private SlaManagementService slaManagementService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setLenient(false);

        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    //SLA
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "sla.list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListWrapper listSlas() {
        return new ListWrapper(slaManagementService.list());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newSla(@ModelAttribute("sla") Sla sla) {
        return "sla.form";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editSla(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
        sla.loadFrom(slaManagementService.get(id));
        return "sla.form";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
    	sla.setId(id);
        slaManagementService.save(sla);
        return "redirect:/admin/sla";
    }
    

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("sla") Sla sla) {
        slaManagementService.save(sla);
        return "redirect:/admin/sla";
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteSla(@PathVariable("id") String id) {
        slaManagementService.delete(id);
    }
}
