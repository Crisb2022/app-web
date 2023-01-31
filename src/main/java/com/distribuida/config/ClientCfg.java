package com.distribuida.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.springframework.web.client.RestTemplate;

@ApplicationScoped
public class ClientCfg {

    @Produces
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
