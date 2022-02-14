package org.acme;

import io.smallrye.stork.api.ServiceDiscovery;
import io.smallrye.stork.api.config.ServiceConfig;
import io.smallrye.stork.api.config.ServiceDiscoveryAttribute;
import io.smallrye.stork.api.config.ServiceDiscoveryType;
import io.smallrye.stork.spi.ServiceDiscoveryProvider;
import io.smallrye.stork.spi.StorkInfrastructure;

@ServiceDiscoveryType("my3")
public class MyServiceDiscovery3Provider implements ServiceDiscoveryProvider<MyServiceDiscovery3ProviderConfiguration> {

    @Override
    public ServiceDiscovery createServiceDiscovery(MyServiceDiscovery3ProviderConfiguration config, String s, ServiceConfig serviceConfig, StorkInfrastructure storkInfrastructure) {
        return new MyServiceDiscovery3();
    }
}
