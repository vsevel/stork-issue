package org.acme.test;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/discovery")
@RegisterRestClient(configKey = "bad-discovery")
public interface DownDiscoveryClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Uni<String> discovery(@QueryParam("delay") Long delay, @QueryParam("error") Boolean error, @QueryParam("addresses") String addresses) ;
}
