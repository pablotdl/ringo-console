package ar.edu.unicen.ringo.console.service;

import org.springframework.stereotype.Service;

import ar.edu.unicen.ringo.console.model.Sla;

/**
 * Service class that provides methods for adding, removing or modifying SLAs
 * and nodes.
 * @author psaavedra
 */
@Service
public class SlaManagementService extends AbstractManagementService<Sla> {

    public SlaManagementService() {
        super("agent", "sla", Sla.class);
    }
}
