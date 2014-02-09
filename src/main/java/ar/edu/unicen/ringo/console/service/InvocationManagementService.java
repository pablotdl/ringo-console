package ar.edu.unicen.ringo.console.service;

import org.springframework.stereotype.Service;

import ar.edu.unicen.ringo.console.model.Invocation;

@Service
public class InvocationManagementService extends AbstractManagementService<Invocation> {

    public InvocationManagementService() {
        super("agent", "invocation", Invocation.class);
    }
}
