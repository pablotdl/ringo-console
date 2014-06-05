package ar.edu.unicen.ringo.console.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.unicen.ringo.console.model.Node;
import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.InvocationManagementService;
import ar.edu.unicen.ringo.console.service.NodeManagementService;
import ar.edu.unicen.ringo.console.service.SlaManagementService;

@Controller
public class ControlController {
	
    private static final Logger logger = LoggerFactory
            .getLogger(ControlController.class);

	@Autowired
	private SlaManagementService service_sla;
	
	@Autowired
	private NodeManagementService service_node;
	
	@Autowired
	private InvocationManagementService service_invocation;		
	
	@Autowired
	private Client client;	
	
	@RequestMapping("/control")
	public String index(HttpServletRequest request, ModelMap model) {
		HashMap<String, List<Node>> sla_nodes = new HashMap<String, List<Node>>();
		
		
		List<Sla> slas = service_sla.list();
		for(Sla sla: slas){
			sla_nodes.put(sla.getId(), new ArrayList<Node>(0));
		}
		
    	List<Node> nodes = service_node.list();
    	for(Node node: nodes){
    		sla_nodes.get(node.getSla()).add(node);
    	}		
		
		
		model.addAttribute("slas", slas);
		model.addAttribute("sla_nodes", sla_nodes);
		
		return "general.control"; 
	}
}
