package com.dookay.coral.common.persistence.cache.support;


import com.dookay.coral.common.persistence.cache.Cache;
import com.dookay.coral.common.persistence.cache.CacheManager;
import com.dookay.coral.common.persistence.cache.impl.ConcurrentMapCache;
import org.springframework.beans.factory.InitializingBean;

public class SimpleCacheManager implements InitializingBean, CacheManager {
    
    private Cache cache;

    /**
     * Specify the Cache instances to use for this CacheManager.
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

 

    public Cache getCache() {
        return cache;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        if(cache == null){
            cache = new ConcurrentMapCache();
        }
    }

}
