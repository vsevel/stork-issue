package org.acme;

import io.smallrye.stork.api.ServiceDiscovery;
import io.smallrye.stork.api.config.ServiceConfig;
import io.smallrye.stork.api.config.ServiceDiscoveryAttribute;
import io.smallrye.stork.api.config.ServiceDiscoveryType;
import io.smallrye.stork.spi.ServiceDiscoveryProvider;
import io.smallrye.stork.spi.StorkInfrastructure;

@ServiceDiscoveryType("my")
@ServiceDiscoveryAttribute(name = "app", description = "app name", required = true)
public class MyServiceDiscoveryProvider implements ServiceDiscoveryProvider<MyServiceDiscoveryProviderConfiguration> {

    @Override
    public ServiceDiscovery createServiceDiscovery(MyServiceDiscoveryProviderConfiguration config, String s, ServiceConfig serviceConfig, StorkInfrastructure storkInfrastructure) {
        return new MyServiceDiscovery(config);
    }
}
