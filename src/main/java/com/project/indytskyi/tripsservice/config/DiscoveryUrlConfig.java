package com.project.indytskyi.tripsservice.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscoveryUrlConfig {
    private final EurekaClient discoveryClient;
    @Value("${car.service.app.name}")
    private String carServiceAppName;
    @Value("${backoffice.service.app.name}")
    private String backofficeServiceAppName;
    @Value("${user.service.app.name}")
    private String userServiceAppName;

    public String getBackofficeServiceUrl() {
        InstanceInfo instance = discoveryClient
                .getNextServerFromEureka(backofficeServiceAppName, false);
        return instance.getHomePageUrl();
    }

    public String getCarServiceUrl() {
        InstanceInfo instance = discoveryClient
                .getNextServerFromEureka(carServiceAppName, false);
        return instance.getHomePageUrl();
    }

    public String getUserServiceUrl() {
        InstanceInfo instance = discoveryClient
                .getNextServerFromEureka(userServiceAppName, false);
        return instance.getHomePageUrl();
    }
}
