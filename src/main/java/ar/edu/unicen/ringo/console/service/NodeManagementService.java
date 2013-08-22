package ar.edu.unicen.ringo.console.service;

import org.springframework.stereotype.Service;

import ar.edu.unicen.ringo.console.model.Node;

/**
 * Service class for managing node instances.
 * @author psaavedra
 */
@Service
public class NodeManagementService extends AbstractManagementService<Node>{

    public NodeManagementService() {
        super("agent", "node", Node.class);
    }
}
