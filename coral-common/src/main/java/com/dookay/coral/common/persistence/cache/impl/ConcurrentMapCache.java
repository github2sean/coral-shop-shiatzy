package com.dookay.coral.common.persistence.cache.impl;

import com.dookay.coral.common.persistence.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapCache implements Cache {

	private static final Logger logger = LoggerFactory.getLogger(ConcurrentMapCache.class);

	private static final ConcurrentMap<Object, Object> cache =
			new ConcurrentHashMap<Object, Object>();

	public int getSize() {
		return cache.size();
	}

	public void putObject(Object key, Object value) {
		cache.put(key, value);
		logger.debug("Storing " + key + " to cache.");
	}

	public Object getObject(Object key) {
		Object value = cache.get(key);
		if (logger.isDebugEnabled()) {
			if (value == null) {
				logger.debug(key + " cache not exists.");
			} else {
				logger.debug("Reading " + key + " from cache.");
			}
		}
		return value;
	}

	public Object removeObject(Object key) {
		logger.debug("Removing " + key + " from cache.");
		return cache.remove(key);
	}

	public void clear(String id) {
		Iterator<Object> iter = cache.keySet().iterator();
		while (iter.hasNext()) {
			String key = String.valueOf(iter.next());
			if (key.indexOf(id) != -1) {
				logger.debug("Clearing " + key + " from cache.");
				cache.remove(key);
			}
		}
		logger.debug("Clearing *" + id + "* from cache.");
	}

	@Override
	public void putObject(Object key, Object value, int seconds) {
		putObject(key, value);
		logger.debug("Storing " + key + " to cache[seconds:" + seconds + "].");
	}

}
