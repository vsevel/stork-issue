package org.acme;

import io.smallrye.mutiny.Uni;
import io.smallrye.stork.api.ServiceInstance;
import io.smallrye.stork.impl.CachingServiceDiscovery;
import io.smallrye.stork.impl.DefaultServiceInstance;
import io.smallrye.stork.utils.ServiceInstanceIds;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MyServiceDiscovery extends CachingServiceDiscovery {

    Logger log = Logger.getLogger(MyServiceDiscovery.class);

    MyServiceDiscoveryProviderConfiguration config;

    private volatile DiscoveryClient initialClient = null;
    private volatile DiscoveryClient refreshClient = null;

    Map<String, Long> ids = new ConcurrentHashMap<>();

    public MyServiceDiscovery(MyServiceDiscoveryProviderConfiguration config) {
        super("5S");
        this.config = config;
    }

    DiscoveryClient createDiscoveryClient(long value, TimeUnit unit) {
        URI baseUri = URI.create(config.getDiscoveryUrl());
        return RestClientBuilder.newBuilder().readTimeout(value, unit).baseUri(baseUri).build(DiscoveryClient.class);
    }

    @Override
    public Uni<List<ServiceInstance>> fetchNewServiceInstances(List<ServiceInstance> previousInstances) {
        String appName = config.getApp();
        if(initialClient == null) {
            initialClient = createDiscoveryClient(10, TimeUnit.SECONDS);
            refreshClient = createDiscoveryClient(100, TimeUnit.MILLISECONDS);
        }
        DiscoveryClient discoveryService = previousInstances.isEmpty() ? initialClient : refreshClient;
        return discoveryService.discovery(appName).map(this::map);
    }

    private List<ServiceInstance> map(String list) {
        return Arrays.stream(list.split(",")).map(this::createInstance).collect(Collectors.toList());
    }

    private ServiceInstance createInstance(String hostPort) {

        String[] split = hostPort.split(":");
        String host = split[0];
        int port = Integer.parseInt(split[1]);

        if (!ids.containsKey(hostPort)) {
            ids.putIfAbsent(hostPort, ServiceInstanceIds.next());
        }
        long id = ids.get(hostPort);

        return new DefaultServiceInstance(id, host, port, false);
    }

    // @Override
//    public Uni<List<ServiceInstance>> fetchNewServiceInstances(List<ServiceInstance> previousInstances) {
//
//        log.info("fetching new instances");
//        log.info("old instances: " + previousInstances);
//
//        DiscoveryClient discoveryClient = Arc.container().instance(DiscoveryClientProvider.class).get().getClient();
//        DownDiscoveryClient downDiscoveryClient = Arc.container().instance(DownDiscoveryClientProvider.class).get().getClient();
//        DiscoveryParams discoveryParams = Arc.container().instance(DiscoveryParams.class).get();
//
//        boolean awaitStyle = true;
//
//        if (awaitStyle) {
//            long timeout = previousInstances.isEmpty() ? 10000L : 100L;
//            String addresses;
//            try {
//                if (discoveryParams.down) {
//                    addresses = downDiscoveryClient.discovery(discoveryParams.delay, discoveryParams.error, discoveryParams.addresses)
//                            .await().atMost(Duration.ofMillis(timeout));
//                } else {
//                    addresses = discoveryClient.discovery(discoveryParams.delay, discoveryParams.error, discoveryParams.addresses)
//                            .await().atMost(Duration.ofMillis(timeout));
//                }
//                return Uni.createFrom().item(map(addresses));
//            } catch (TimeoutException e) {
//                return Uni.createFrom().failure(e);
//            }
//        } else {
//            Uni<String> discovery = discoveryParams.down
//                    ? downDiscoveryClient.discovery(discoveryParams.delay, discoveryParams.error, discoveryParams.addresses)
//                    : discoveryClient.discovery(discoveryParams.delay, discoveryParams.error, discoveryParams.addresses);
//            Uni<List<ServiceInstance>> result = discovery.onItem().transform(this::map);
//            return result;
//        }
//    }


}
