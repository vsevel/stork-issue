package org.acme;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ReactiveGreetingResource {

    @Inject
    @RestClient
    HelloClient hello;

    @Inject
    DiscoveryParams discoveryParams;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy Reactive";
    }

    @GET
    @Path("/stork")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloStork(
            @QueryParam("delay") Long delay,
            @QueryParam("error") Boolean error,
            @QueryParam("addresses") String addresses,
            @QueryParam("down") Boolean down) {
        try {
            before(delay, error, addresses, down);
            return "stork: " + hello.hello();
        } finally {
            after();
        }
    }

    private void after() {
        discoveryParams.delay = 0;
        discoveryParams.error = false;
        discoveryParams.addresses = "localhost:8080";
        discoveryParams.down = false;
    }

    private void before(Long delay, Boolean error, String addresses, Boolean down) {
        if (delay != null) {
            discoveryParams.delay = delay;
        }
        if (error != null) {
            discoveryParams.error = error;
        }
        if (addresses != null) {
            discoveryParams.addresses = addresses;
        }
        if (down != null) {
            discoveryParams.down = down;
        }
    }
}