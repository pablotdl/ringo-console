package ar.edu.unicen.ringo.console.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

import ar.edu.unicen.ringo.console.model.Generate;
import ar.edu.unicen.ringo.console.model.Invocation;
import ar.edu.unicen.ringo.console.model.Node;
import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.InvocationManagementService;
import ar.edu.unicen.ringo.console.service.NodeManagementService;
import ar.edu.unicen.ringo.console.service.SlaManagementService;
import ar.edu.unicen.ringo.console.ui.wrapper.ListWrapper;

/**
 * Handles method invocation management.
 * @author pablosaavedra
 */
@Controller
@RequestMapping("/admin/invocation")
public class InvocationManagementController {

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

    //Invocation
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showInvocationList(ModelMap map) {
        map.put("slas", this.slaManagementService.list());
        map.put("nodes", this.nodeManagementService.list());    	
        return "invocation.list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListWrapper listInvocations() {
        return new ListWrapper(invocationManagementService.list());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newInvocation(@ModelAttribute("invocation") Invocation invocation, ModelMap map) {
        map.put("slas", this.slaManagementService.list());
        map.put("nodes", this.nodeManagementService.list());
        return "invocation.form";
    }
    
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public String newInvocation(@ModelAttribute("generate") Generate generate) {
        return "invocation.generate";
    }    

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editInvocation(@ModelAttribute("invocation") Invocation invocation,
            @PathVariable("id") String id, ModelMap map) {
        invocation.loadFrom(invocationManagementService.get(id));
        map.put("slas", this.slaManagementService.list());
        map.put("nodes", this.nodeManagementService.list());
        return "invocation.form";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("invocation") Invocation invocation) {
    	invocationManagementService.save(invocation);
        return "redirect:/admin";
    }
    
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public String create(@ModelAttribute("generate") Generate generate) {
    	Random randomGenerator = new Random();    	
    	List<Sla> slas = this.slaManagementService.list();
    	List<Node> nodes = this.nodeManagementService.list();

    	for(int i = 0; i < generate.getInvocations(); i++) {
    		Invocation invocation = new Invocation();
    		invocation.setMethod(generate.getMethod());
    		invocation.setExecution_time(randomGenerator.nextInt(1000));
    		invocation.setNode(nodes.get(randomGenerator.nextInt(nodes.size())).getId());
    		invocation.setSla(slas.get(randomGenerator.nextInt(slas.size())).getId());
    		invocation.setTimestamp(generate.getTimestamp());
    		
    		invocationManagementService.save(invocation);
    	}
        return "redirect:/admin/invocation";
    }    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("invocation") Invocation invocation,
            @PathVariable("id") String id) {
    	invocation.setId(id);
    	invocationManagementService.save(invocation);
        return "redirect:/admin/invocation";
    }    

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteInvocation(@PathVariable("id") String id) {
    	invocationManagementService.delete(id);
        System.out.println("Deleted Invocation " + id);
    }
}
