package com.example.commande.config;

import com.example.commande.web.CommandeResource;
import com.fasterxml.jackson.core.util.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(CommandeResource.class);
        register(JacksonFeature.class); // ← IMPORTANT

        packages("com.example.commande.web");
    }
}