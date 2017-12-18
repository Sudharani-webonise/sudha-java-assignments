package com.netmagic.spectrum;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(AppConfig.class)
public class AppConfigTest {

    @InjectMocks
    private AppConfig appConfigSpy;

    @Test
    public void testRestTemplate() {
        assertNotNull(appConfigSpy.restTemplate());
    }

    @Test
    public void testStringRedisSerializer() {
        assertNotNull(appConfigSpy.stringRedisSerializer());
    }

    @Test
    public void testJedisConnectionFactory() {
        assertNotNull(appConfigSpy.jedisConnectionFactory());
    }

    @Test
    public void testRedisTemplate() {
        assertNotNull(appConfigSpy.redisTemplate());
    }

    @Test
    public void testApplicationProperties() {
        assertNotNull(AppConfig.applicationProperties());
    }

}
