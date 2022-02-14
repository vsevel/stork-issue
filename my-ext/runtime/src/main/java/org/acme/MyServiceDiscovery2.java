package org.acme;

import io.smallrye.mutiny.Uni;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.impl.CachingServiceDiscovery;
import io.smallrye.stork.impl.DefaultServiceInstance;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyServiceDiscovery2 extends CachingServiceDiscovery {

    Logger log = Logger.getLogger(MyServiceDiscovery2.class);

    public MyServiceDiscovery2() {
        super("5S");
    }

    @Override
    public Uni<List<ServiceInstance>> fetchNewServiceInstances(List<ServiceInstance> previousInstances) {
        log.info("fetching new instances for ext discovery");
        ServiceInstance localhost = new DefaultServiceInstance(1, "localhost", 8080, false);
        return Uni.createFrom().item(Arrays.asList(localhost));
    }
}
