package com.dookay.coral.security.session;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * redis 会话处理  将shiro会话放入redis中
 * @since : 2016年11月16日
 * @author : kezhan
 * @version : v0.0.1
 */
public class RedisSessionDao extends CachingSessionDAO {

	private final static Logger log = Logger.getLogger(RedisSessionDao.class);

	private RedisTemplate<String, Object> redisTemplate;

	private int defaultExpireTime = 3600;
	
	public RedisSessionDao() {
		super();
	}

	public RedisSessionDao(RedisTemplate<String, Object> redisTemplate, int defaultExpireTime) {
		this.redisTemplate = redisTemplate;
		this.defaultExpireTime = defaultExpireTime;
	}
	
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		redisTemplate.opsForValue().set(sessionId.toString(), session);
		redisTemplate.expire(sessionId.toString(), this.defaultExpireTime, TimeUnit.SECONDS);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		log.debug("---doReadSession--" + sessionId.toString());
		return null;
	}

	@Override
	protected void doUpdate(Session session) {
		// 该方法交给父类去执行
	}

	@Override
	protected void doDelete(Session session) {
		Serializable sessionId = session.getId();
		redisTemplate.delete(sessionId.toString());
	}

}
