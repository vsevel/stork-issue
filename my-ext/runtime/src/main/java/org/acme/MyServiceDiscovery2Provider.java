package org.acme;

import io.smallrye.stork.api.ServiceDiscovery;
import io.smallrye.stork.api.config.ServiceConfig;
import io.smallrye.stork.api.config.ServiceDiscoveryAttribute;
import io.smallrye.stork.api.config.ServiceDiscoveryType;
import io.smallrye.stork.spi.ServiceDiscoveryProvider;
import io.smallrye.stork.spi.StorkInfrastructure;

@ServiceDiscoveryType("my2")
@ServiceDiscoveryAttribute(name = "url", description = "URL", required = true)
public class MyServiceDiscovery2Provider implements ServiceDiscoveryProvider<Object> {

    @Override
    public ServiceDiscovery createServiceDiscovery(Object config, String s, ServiceConfig serviceConfig, StorkInfrastructure storkInfrastructure) {
        return new MyServiceDiscovery2();
    }
}
