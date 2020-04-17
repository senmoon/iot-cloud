package org.iot.iotgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

//import org.springframework.stereotype.Component;

//@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private final static Logger log = LoggerFactory.getLogger(CustomGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***********come in MyLogGateWayFilter: " + new Date());
        // 如果没有注释`Component`，通过请求的地址格式：http://localhost:9200/hystrix/ok?username=de
        // 如果取消了`//@Component`的注释，那么，访问`http://localhost:9200/hystrix/ok`即可看到返回结果
        // 判断带不带username
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if (username == null) {
            log.info("*********非法用户*********");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            // 返回错误response，并退出
            return exchange.getResponse().setComplete();
        }
        // next
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
