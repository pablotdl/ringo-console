package ar.edu.unicen.ringo.console.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import ar.edu.unicen.ringo.console.model.Node;
import ar.edu.unicen.ringo.console.service.NodeManagementService;
import ar.edu.unicen.ringo.console.service.SlaManagementService;
import ar.edu.unicen.ringo.console.ui.wrapper.ListWrapper;

/**
 * Node management controller
 * @author pablosaavedra
 */
@Controller
@RequestMapping("/admin/node")
public class NodeManagementController {

    @Autowired
    private SlaManagementService slaManagementService;

    @Autowired
    private NodeManagementService nodeManagementService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setLenient(false);

        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    //Node
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showNodeList(ModelMap map) {
    	map.put("slas", this.slaManagementService.list());
        return "node.list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListWrapper listNodes() {
        return new ListWrapper(nodeManagementService.list());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newNode(@ModelAttribute("node") Node node, ModelMap map) {
        map.put("slas", this.slaManagementService.list());
        return "node.form";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editNode(@ModelAttribute("node") Node node,
            @PathVariable("id") String id, ModelMap map) {
        node.loadFrom(nodeManagementService.get(id));
        map.put("slas", this.slaManagementService.list());
        return "node.form";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("node") Node node) {
        nodeManagementService.save(node);
        return "redirect:/admin/node";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("node") Node node,
            @PathVariable("id") String id) {
    	node.setId(id);
    	nodeManagementService.save(node);
        return "redirect:/admin/node";
    }    

    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Success")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteNode(@PathVariable("id") String id) {
        nodeManagementService.delete(id);
        System.out.println("Deleted Node " + id);
    }

}
