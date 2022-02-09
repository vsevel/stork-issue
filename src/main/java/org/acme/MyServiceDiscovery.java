package org.acme;

import io.smallrye.mutiny.Uni;
import io.smallrye.stork.api.ServiceDiscovery;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.impl.DefaultServiceInstance;
import io.smallrye.stork.utils.ServiceInstanceIds;

import java.util.Arrays;
import java.util.List;

public class MyServiceDiscovery implements ServiceDiscovery {

    @Override
    public Uni<List<ServiceInstance>> getServiceInstances() {
        return Uni.createFrom().item(Arrays.asList(
                new DefaultServiceInstance(ServiceInstanceIds.next(), "localhost", 8081, false),
                new DefaultServiceInstance(ServiceInstanceIds.next(), "localhost", 8080, false)
        ));
    }
}
