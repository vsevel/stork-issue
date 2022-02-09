package org.acme;

import io.quarkus.arc.Unremovable;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Unremovable
public class DownDiscoveryClientProvider {

    @Inject
    @RestClient
    DownDiscoveryClient client;

    public DownDiscoveryClient getClient() {
        return client;
    }
}
