package com.netmagic.spectrum.cache.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.netmagic.spectrum.cache.Cacheable;
import com.netmagic.spectrum.exception.CacheServiceException;

@Service
public interface CacheService<T extends Cacheable> {

    T get(T key) throws CacheServiceException;

    void save(T cacheable) throws CacheServiceException;

    void save(T cacheable, long timeDuration, TimeUnit unit) throws CacheServiceException;

    void delete(T cacheable) throws CacheServiceException;

    Map<Object, Object> readAllHash(String hashKey) throws CacheServiceException;

    void deleteAll(List<T> cacheable) throws CacheServiceException;

    void deleteAll(String hashKey) throws CacheServiceException;
}
