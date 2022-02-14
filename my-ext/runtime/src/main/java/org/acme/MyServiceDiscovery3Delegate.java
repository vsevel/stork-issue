package org.acme;

import io.smallrye.mutiny.Uni;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.impl.CachingServiceDiscovery;
import io.smallrye.stork.impl.DefaultServiceInstance;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;

public class MyServiceDiscovery3Delegate {

    Logger log = Logger.getLogger(MyServiceDiscovery3Delegate.class);

    public Uni<List<ServiceInstance>> fetchNewServiceInstances(List<ServiceInstance> previousInstances) {
        log.info("fetching new instances for ext discovery");
        ServiceInstance localhost = new DefaultServiceInstance(1, "localhost", 8080, false);
        return Uni.createFrom().item(Arrays.asList(localhost));
    }
}
