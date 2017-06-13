package com.dookay.coral.shop.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.BannerMapper;
import com.dookay.coral.shop.content.domain.BannerDomain;
import com.dookay.coral.shop.content.service.IBannerService;

/**
 * banner的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("bannerService")
public class BannerServiceImpl extends BaseServiceImpl<BannerDomain> implements IBannerService {
	
	@Autowired
	private BannerMapper bannerMapper;
	  
}
