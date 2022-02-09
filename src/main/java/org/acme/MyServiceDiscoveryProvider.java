package org.acme;

import io.smallrye.stork.api.ServiceDiscovery;
import io.smallrye.stork.api.config.ServiceConfig;
import io.smallrye.stork.api.config.ServiceDiscoveryType;
import io.smallrye.stork.spi.ServiceDiscoveryProvider;
import io.smallrye.stork.spi.StorkInfrastructure;

@ServiceDiscoveryType("my")
public class MyServiceDiscoveryProvider implements ServiceDiscoveryProvider<MyServiceDiscoveryProviderConfiguration> {

    @Override
    public ServiceDiscovery createServiceDiscovery(MyServiceDiscoveryProviderConfiguration myServiceDiscoveryConfiguration, String s, ServiceConfig serviceConfig, StorkInfrastructure storkInfrastructure) {
        return new MyServiceDiscovery();
    }
}
