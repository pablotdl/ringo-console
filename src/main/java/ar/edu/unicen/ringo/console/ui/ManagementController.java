package ar.edu.unicen.ringo.console.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.unicen.ringo.console.model.Node;
import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.NodeManagementService;
import ar.edu.unicen.ringo.console.service.SlaManagementService;

/**
 * Controller that handles the CRUD operations related to SLAs and nodes.
 * @author psaavedra
 */
@Controller
@RequestMapping("/admin")
public class ManagementController {

    private static final Logger logger = LoggerFactory
            .getLogger(ManagementController.class);

    @Autowired
    private SlaManagementService slaManagementService;

    @Autowired
    private NodeManagementService nodeManagementService;

    @RequestMapping(value = "/sla/new", method = RequestMethod.GET)
    public String newSla(@ModelAttribute("sla") Sla sla) {
        logger.info("Entering new SLA form");
        return "sla.form";
    }

    @RequestMapping(value = "/sla/{id}", method = RequestMethod.GET)
    public String editSla(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
        sla.loadFrom(slaManagementService.get(id));
        logger.info("About to render form");
        return "sla.form";
    }
    
    @RequestMapping(value = "/sla/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
    	sla.setId(id);
        slaManagementService.save(sla);
        System.out.println("Updated an SLA: " + sla);
        return "redirect:/admin/sla";
    }
    

    @RequestMapping(value = "/sla/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("sla") Sla sla) {
        slaManagementService.save(sla);
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
        return new ListWrapper(slaManagementService.list());
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/sla/{id}", method = RequestMethod.DELETE)
    public void deleteSla(@PathVariable("id") String id) {
        slaManagementService.delete(id);
        System.out.println("Deleted SLA " + id);
    }

    @RequestMapping(value = "/node", method = RequestMethod.GET)
    public String showNodeList() {
        return "node.list";
    }

    @ResponseBody
    @RequestMapping(value = "/node/list", method = RequestMethod.GET)
    public ListWrapper listNodes() {
        return new ListWrapper(nodeManagementService.list());
    }

    @RequestMapping(value = "/node/new", method = RequestMethod.GET)
    public String newNode(@ModelAttribute("node") Node node, ModelMap map) {
        System.out.println("Entering new Node form");
        map.put("slas", this.slaManagementService.list());
        return "node.form";
    }

    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public String editNode(@ModelAttribute("node") Node node,
            @PathVariable("id") String id, ModelMap map) {
        node.loadFrom(nodeManagementService.get(id));
        System.out.println("About to render form");
        map.put("slas", this.slaManagementService.list());
        return "node.form";
    }

    @RequestMapping(value = "/node/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("node") Node node) {
        nodeManagementService.save(node);
        System.out.println("Saved an SLA: " + node);
        return "redirect:/admin/node";
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public void deleteNode(@PathVariable("id") String id) {
        nodeManagementService.delete(id);
        System.out.println("Deleted Node " + id);
    }
}
