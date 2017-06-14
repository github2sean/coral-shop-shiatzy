package com.dookay.coral.shop.temp.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dookay.coral.shop.temp.mapper.TempMemberMapper;
import com.dookay.coral.shop.temp.domain.TempMemberDomain;
import com.dookay.coral.shop.temp.service.ITempMemberService;

/**
 * 临时会员的业务实现类
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("tempMemberService")
public class TempMemberServiceImpl extends BaseServiceImpl<TempMemberDomain> implements ITempMemberService {
	
	@Autowired
	private TempMemberMapper tempMemberMapper;
	  
}
