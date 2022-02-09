package org.acme;

import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/discovery")
public class DiscoveryResource {

    Logger log = Logger.getLogger(DiscoveryResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> discovery(@QueryParam("delay") Long delay, @QueryParam("error") Boolean error, @QueryParam("addresses") String addresses) throws InterruptedException {

        if (delay != null && delay != 0) {
            log.info("wait " + delay + " secs");
            Thread.sleep(delay * 1000);
        }
        if(error != null && error) {
            throw new RuntimeException("oops registry error");
        }
        return Uni.createFrom().item(addresses);
    }
}