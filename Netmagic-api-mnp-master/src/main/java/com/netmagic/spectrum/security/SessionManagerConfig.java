package com.netmagic.spectrum.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * SessionManagerConfig describes Redis connection using JedisConnectionFactory
 * extends
 * {@link org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer}
 * 
 * @author webonise
 * @version Spectrum 1.0.0
 */
@Configuration
@EnableRedisHttpSession
@PropertySource(value = { "classpath:/default.properties" })
public class SessionManagerConfig extends AbstractHttpSessionApplicationInitializer {

    @Value("${redis.hostname}")
    private String redis_host_name;

    @Value("${redis.port}")
    private int redis_port;

    @Value("${redis.session.timeout}")
    private int redis_session_timeout;

    @Primary
    @Bean
    public RedisOperationsSessionRepository redisOperationsSessionRepository(
            JedisConnectionFactory jedisConnectionFactory) {
        RedisOperationsSessionRepository redisOperationsSessionRepository = new RedisOperationsSessionRepository(
                jedisConnectionFactory);
        redisOperationsSessionRepository.setDefaultMaxInactiveInterval(redis_session_timeout);
        return redisOperationsSessionRepository;
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redis_host_name);
        jedisConnectionFactory.setPort(redis_port);
        return jedisConnectionFactory;
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}
