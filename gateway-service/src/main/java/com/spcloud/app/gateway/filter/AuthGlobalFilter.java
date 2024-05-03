package com.spcloud.app.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.spcloud.app.gateway.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class AuthGlobalFilter {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);
    // 白名单列表
    private static final String[] whiteListProperties = {"/"};

    @Bean
    @Order(-101)
    public GlobalFilter jwtAuthGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            /*
            String path = request.getURI().getPath();
            for (String s : whiteListProperties) {
                if(path.startsWith(s)){
                    return chain.filter(exchange);
                }
            }
            String token = request.getHeaders().getFirst("token");
            if(token == null || token.isEmpty()){
                return unauthorizedResponse(exchange,response);
            }
             */
            return chain.filter(exchange);
        };
    }

    //jwt鉴权失败处理类
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ServerHttpResponse serverHttpResponse) {
        log.warn("token异常处理,请求路径:{}", exchange.getRequest().getPath());
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONBytes(R.error("没有相关权限", 1001)));
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }
}
