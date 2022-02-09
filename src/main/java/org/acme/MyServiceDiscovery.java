package org.acme;

import io.quarkus.arc.Arc;
import io.smallrye.mutiny.Uni;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.impl.CachingServiceDiscovery;
import io.smallrye.stork.impl.DefaultServiceInstance;
import io.smallrye.stork.utils.ServiceInstanceIds;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyServiceDiscovery extends CachingServiceDiscovery {

    Logger log = Logger.getLogger(MyServiceDiscovery.class);

    public MyServiceDiscovery() {
        super("10S");
    }

    @Override
    public Uni<List<ServiceInstance>> fetchNewServiceInstances(List<ServiceInstance> previousInstances) {
        log.info("fetching new instances");
        log.info("old instances: " + previousInstances);
        // long timeout = previousInstances.isEmpty() ? 30000L : 100L;
        DiscoveryClient discoveryClient = Arc.container().instance(DiscoveryClientProvider.class).get().getClient();
        DownDiscoveryClient downDiscoveryClient = Arc.container().instance(DownDiscoveryClientProvider.class).get().getClient();
        DiscoveryParams discoveryParams = Arc.container().instance(DiscoveryParams.class).get();

        Uni<String> discovery = discoveryParams.down
                ? downDiscoveryClient.discovery(discoveryParams.delay, discoveryParams.error, discoveryParams.addresses)
                : discoveryClient.discovery(discoveryParams.delay, discoveryParams.error, discoveryParams.addresses);

        return discovery.onItem().transform(this::map);
    }

    private List<ServiceInstance> map(String list) {
        return Arrays.stream(list.split(",")).map(this::createInstance).collect(Collectors.toList());
    }

    private ServiceInstance createInstance(String hostPort) {
        log.info("hostPort=" + hostPort);
        String[] split = hostPort.split(":");
        String host = split[0];
        int port = Integer.parseInt(split[1]);
        return new DefaultServiceInstance(ServiceInstanceIds.next(), host, port, false);
    }
}
