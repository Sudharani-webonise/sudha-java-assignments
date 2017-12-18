package com.netmagic.spectrum.cache.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.cache.Cacheable;
import com.netmagic.spectrum.exception.CacheServiceException;

@Service
public class CacheServiceImpl<T extends Cacheable> implements CacheService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    @SuppressWarnings("unchecked")
    public T get(T cacheable) throws CacheServiceException {
        return (T) redisTemplate.opsForHash().get(cacheable.getObjectKey(), cacheable.getCacheKey());
    }

    public void save(T cacheable) throws CacheServiceException {
        LOG.debug("Saving object for key: {}", cacheable.getCacheKey());
        redisTemplate.opsForHash().put(cacheable.getObjectKey(), cacheable.getCacheKey(), cacheable);
    }

    public void save(T cacheable, long expiryDuration, TimeUnit unit) throws CacheServiceException {
        LOG.debug("Saving object for key: {}", cacheable.getCacheKey());
        redisTemplate.opsForHash().put(cacheable.getObjectKey(), cacheable.getCacheKey(), cacheable);
        redisTemplate.expire(cacheable.getCacheKey(), expiryDuration, unit);
    }

    public void delete(T cacheable) throws CacheServiceException {
        LOG.debug("Deleting object for key: {}", cacheable.getCacheKey());
        redisTemplate.opsForHash().delete(cacheable.getObjectKey(), cacheable.getCacheKey());
    }

    public Map<Object, Object> readAllHash(String hashKey) throws CacheServiceException {
        LOG.debug("Reading all object for hashKey: {}", hashKey);
        return redisTemplate.opsForHash().entries(hashKey);
    }

    public void deleteAll(List<T> cacheableObjList) throws CacheServiceException {
        LOG.debug("Deleting object list {}", cacheableObjList);
        for ( Cacheable cacheable : cacheableObjList ) {
            redisTemplate.opsForHash().delete(cacheable.getObjectKey(), cacheable.getCacheKey());
        }
    }

    public void deleteAll(String hashKey) throws CacheServiceException {
        redisTemplate.delete(hashKey);
    }
}
