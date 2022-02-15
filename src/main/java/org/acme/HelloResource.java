package org.acme;

import io.smallrye.stork.Stork;
import org.acme.test.DiscoveryParams;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Path("/hello")
public class HelloResource {


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
    @Produces(MediaType.TEXT_PLAIN)
    public String programmatic(@PathParam("country") String country) {
        Map<String, String> params = new HashMap<>();
        params.put("stork.service.discovery.clock-" + country, "my-disc");
        params.put("stork.service.discovery.clock-usa.country", country);
        Stork.getInstance().defineServiceIfAbsent(params);
        Clock clock = RestClientBuilder.newBuilder().baseUri(URI.create("stork://clock-" + country)).build(Clock.class);
        return "" + clock.currentTime();
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