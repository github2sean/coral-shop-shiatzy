package com.dookay.coral.common.oss;

import java.io.Serializable;

/**
 * oss存储对象基本信息
 * @author : kezhan
 * @since : 2017年1月21日
 * @version : v0.0.1
 */
public class OSSObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*oss 静态资源域名 */
	private String ossResourceDomainName;

	/*oss Endpoint各区域的内外网设置*/
	private String endpoint;

	/*accessKeyId*/
	private String accessKeyId;

	/*accessKeySecret*/
	private String accessKeySecret;

	/*securityToken*/
	private String securityToken;

	/* oss 默认Bucket名 */
	private String defaultBucketName;

	public String getOssResourceDomainName() {
		return ossResourceDomainName;
	}

	public void setOssResourceDomainName(String ossResourceDomainName) {
		this.ossResourceDomainName = ossResourceDomainName;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	public String getDefaultBucketName() {
		return defaultBucketName;
	}

	public void setDefaultBucketName(String defaultBucketName) {
		this.defaultBucketName = defaultBucketName;
	}

}
