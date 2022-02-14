package org.acme.test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@Singleton
public class DiscoveryParams {

    public long delay = 0;
    public boolean error = false;
    public String addresses = "localhost:8080";
    public boolean down = false;

}
