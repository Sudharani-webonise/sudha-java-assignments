package com.netmagic.spectrum.security;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.netmagic.spectrum.security.SessionManagerConfig;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(SessionManagerConfig.class)
public class SessionManagerConfigTest {

    @InjectMocks
    private SessionManagerConfig sessionManagerConfigSpy;

    @Test
    public void testHttpSessionSecurityContextRepository() {
        assertNotNull(sessionManagerConfigSpy.httpSessionSecurityContextRepository());
    }

    @Test
    public void testHttpRedisOperationsSessionRepository() {
        JedisConnectionFactory jedisConnectionFactory = sessionManagerConfigSpy.jedisConnectionFactory();
        assertNotNull(sessionManagerConfigSpy.redisOperationsSessionRepository(jedisConnectionFactory));
    }

    @Test
    public void testHttpSessionStrategy() {
        assertNotNull(sessionManagerConfigSpy.httpSessionStrategy());
    }

    @Test
    public void testConfigureRedisAction() {
        assertNotNull(SessionManagerConfig.configureRedisAction());
    }

}
