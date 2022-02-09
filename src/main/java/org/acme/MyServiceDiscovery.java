package org.acme;

import io.smallrye.mutiny.Uni;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.impl.CachingServiceDiscovery;
import io.smallrye.stork.impl.DefaultServiceInstance;
import io.smallrye.stork.utils.ServiceInstanceIds;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;

public class MyServiceDiscovery extends CachingServiceDiscovery {

    Logger log = Logger.getLogger(MyServiceDiscovery.class);

    public MyServiceDiscovery() {
        super("10S");
    }

    @Override
    public Uni<List<ServiceInstance>> fetchNewServiceInstances(List<ServiceInstance> previousInstances) {
        log.info("fetching new instances");
        if (System.currentTimeMillis() % 2 == 0) {
            throw new RuntimeException("oops registry not available");
        }
        return Uni.createFrom().item(Arrays.asList(
                new DefaultServiceInstance(ServiceInstanceIds.next(), "localhost", 8080, false),
                new DefaultServiceInstance(ServiceInstanceIds.next(), "localhost", 8080, false)
        ));
    }
}
