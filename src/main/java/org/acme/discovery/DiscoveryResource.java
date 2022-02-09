package org.acme.discovery;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniCreate;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/discovery")
public class DiscoveryResource {

    Logger log = Logger.getLogger(DiscoveryResource.class);

    static Map<String, String> addresses = new HashMap<>();

    static {
        addresses.put("HELLO", "localhost:8080");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> discovery(@QueryParam("app") String app) {
        String address = addresses.get(app);
        UniCreate uni = Uni.createFrom();
        if (address != null) {
            log.info("discovery returning " + address + " for app " + app);
            return uni.item(address);
        } else {
            return uni.failure(new RuntimeException("could not found address for app " + app));
        }
    }

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> discovery(@QueryParam("delay") Long delay, @QueryParam("error") Boolean error, @QueryParam("addresses") String addresses) throws InterruptedException {

        if (delay != null && delay != 0) {
            log.info("wait " + delay + " secs");
            Thread.sleep(delay * 1000);
        }
        if (error != null && error) {
            throw new RuntimeException("oops registry error");
        }

        return Uni.createFrom().item(addresses);
    }
}