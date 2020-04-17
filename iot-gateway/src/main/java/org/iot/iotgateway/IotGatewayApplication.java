package org.iot.iotgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IotGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotGatewayApplication.class, args);
    }

}
