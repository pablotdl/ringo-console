package ar.edu.unicen.ringo.console.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ar.edu.unicen.ringo.console.model.Invocation;
import ar.edu.unicen.ringo.console.model.Node;
import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.InvocationManagementService;
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
    
    @Autowired
    private InvocationManagementService invocationManagementService;    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setLenient(false);

        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }    

    //SLA
    @RequestMapping(value = "/sla", method = RequestMethod.GET)
    public String list() {
        return "sla.list";
    }

    @ResponseBody
    @RequestMapping(value = "/sla/list", method = RequestMethod.GET)
    public ListWrapper listSlas() {
        return new ListWrapper(slaManagementService.list());
    }

    @RequestMapping(value = "/sla/new", method = RequestMethod.GET)
    public String newSla(@ModelAttribute("sla") Sla sla) {
        return "sla.form";
    }

    @RequestMapping(value = "/sla/{id}", method = RequestMethod.GET)
    public String editSla(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
        sla.loadFrom(slaManagementService.get(id));
        return "sla.form";
    }
    
    @RequestMapping(value = "/sla/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("sla") Sla sla,
            @PathVariable("id") String id) {
    	sla.setId(id);
        slaManagementService.save(sla);
        return "redirect:/admin/sla";
    }
    

    @RequestMapping(value = "/sla/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("sla") Sla sla) {
        slaManagementService.save(sla);
        return "redirect:/admin/sla";
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/sla/{id}", method = RequestMethod.DELETE)
    public void deleteSla(@PathVariable("id") String id) {
        slaManagementService.delete(id);
    }

    //Node
    @RequestMapping(value = "/node", method = RequestMethod.GET)
    public String showNodeList(ModelMap map) {
    	map.put("slas", this.slaManagementService.list());
        return "node.list";
    }

    @ResponseBody
    @RequestMapping(value = "/node/list", method = RequestMethod.GET)
    public ListWrapper listNodes() {
        return new ListWrapper(nodeManagementService.list());
    }

    @RequestMapping(value = "/node/new", method = RequestMethod.GET)
    public String newNode(@ModelAttribute("node") Node node, ModelMap map) {
        map.put("slas", this.slaManagementService.list());
        return "node.form";
    }

    @RequestMapping(value = "/node/{id}", method = RequestMethod.GET)
    public String editNode(@ModelAttribute("node") Node node,
            @PathVariable("id") String id, ModelMap map) {
        node.loadFrom(nodeManagementService.get(id));
        map.put("slas", this.slaManagementService.list());
        return "node.form";
    }

    @RequestMapping(value = "/node/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("node") Node node) {
        nodeManagementService.save(node);
        return "redirect:/admin/node";
    }
    
    @RequestMapping(value = "/node/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("node") Node node,
            @PathVariable("id") String id) {
    	node.setId(id);
    	nodeManagementService.save(node);
        return "redirect:/admin/node";
    }    

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
    public void deleteNode(@PathVariable("id") String id) {
        nodeManagementService.delete(id);
        System.out.println("Deleted Node " + id);
    }
    
    //Invocation
    @RequestMapping(value = "/invocation", method = RequestMethod.GET)
    public String showInvocationList(ModelMap map) {
        map.put("slas", this.slaManagementService.list());
        map.put("nodes", this.nodeManagementService.list());    	
        return "invocation.list";
    }

    @ResponseBody
    @RequestMapping(value = "/invocation/list", method = RequestMethod.GET)
    public ListWrapper listInvocations() {
        return new ListWrapper(invocationManagementService.list());
    }

    @RequestMapping(value = "/invocation/new", method = RequestMethod.GET)
    public String newInvocation(@ModelAttribute("invocation") Invocation invocation, ModelMap map) {
        map.put("slas", this.slaManagementService.list());
        map.put("nodes", this.nodeManagementService.list());
        return "invocation.form";
    }

    @RequestMapping(value = "/invocation/{id}", method = RequestMethod.GET)
    public String editInvocation(@ModelAttribute("invocation") Invocation invocation,
            @PathVariable("id") String id, ModelMap map) {
        invocation.loadFrom(invocationManagementService.get(id));
        map.put("slas", this.slaManagementService.list());
        map.put("nodes", this.nodeManagementService.list());
        return "invocation.form";
    }

    @RequestMapping(value = "/invocation/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("invocation") Invocation invocation) {
    	invocationManagementService.save(invocation);
        return "redirect:/admin/invocation";
    }
    
    @RequestMapping(value = "/invocation/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("invocation") Invocation invocation,
            @PathVariable("id") String id) {
    	invocation.setId(id);
    	invocationManagementService.save(invocation);
        return "redirect:/admin/invocation";
    }    

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/invocation/{id}", method = RequestMethod.DELETE)
    public void deleteInvocation(@PathVariable("id") String id) {
    	invocationManagementService.delete(id);
        System.out.println("Deleted Invocation " + id);
    }    
}
