package com.volka.relayapi.config;

import com.volka.relayapi.properties.CustomNettyProperties;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.resources.LoopResources;

@Configuration
public class NettyConfig {

    private final CustomNettyProperties customNettyProperties;

    NettyConfig(CustomNettyProperties customNettyProperties) {
        this.customNettyProperties = customNettyProperties;
    }

    /**
     * yml 설정 + 추가 옵션
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> nettyCustomizer() {
        return factory -> factory.addServerCustomizers(httpServer -> httpServer
                .runOn(LoopResources.create(
                        "netty",
                        customNettyProperties.selectorCount(), // selectorCount 는 보스 쓰레드 역할
                        customNettyProperties.workerThreadCount(),
                        customNettyProperties.daemonThread()
                )) // colocate는 true 로 지정. 인자로 안넘길 시 내부적으로 기본 true. (colocate=false 시 BossGroup, WorkerGroup이 완전히 분리된 쓰레드 풀을 사용함)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true) //
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 다이렉트 버퍼풀 사용
                .doOnChannelInit(((connectionObserver, channel, remoteAddress) -> {
                    channel.pipeline().addLast(new ReadTimeoutHandler(customNettyProperties.readTimeout()));
                    channel.pipeline().addLast(new WriteTimeoutHandler(customNettyProperties.writeTimeout()));
                }))
        );
    }
}
