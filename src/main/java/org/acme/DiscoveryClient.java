package org.acme;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/discovery")
@RegisterRestClient(configKey = "discovery")
public interface DiscoveryClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Uni<String> discovery(@QueryParam("app") String app);

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Uni<String> discovery(@QueryParam("delay") Long delay, @QueryParam("error") Boolean error, @QueryParam("addresses") String addresses) ;
}
