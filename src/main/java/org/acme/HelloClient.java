package org.acme;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@RegisterRestClient(configKey = "hello")
public interface HelloClient {

    @GET
    String hello();
}
