package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReturnRequestMapper;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.service.IReturnRequestService;

/**
 * 订单退货申请的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("returnRequestService")
public class ReturnRequestServiceImpl extends BaseServiceImpl<ReturnRequestDomain> implements IReturnRequestService {
	
	@Autowired
	private ReturnRequestMapper returnRequestMapper;

	@Autowired
	private IReturnRequestItemService returnRequestItemService;
	private final static Integer AGREE_RETURN = 3;
	private final static Integer DISAGREE_RETURN = 4;


	@Override
	public void isAgree(Long id, Long isAgree,String adminMemo) {
		if(id==null){
			throw new ServiceException("传入参数出错");
		}
		ReturnRequestItemDomain returnRequestItemDomain =  returnRequestItemService.get(id);
		returnRequestItemDomain.setAdminMemo(adminMemo);
		if(isAgree!=null&&isAgree==1){
			returnRequestItemDomain.setStatus(AGREE_RETURN);
		}else if(isAgree!=null&&isAgree==2){
			returnRequestItemDomain.setStatus(DISAGREE_RETURN);
		}
		returnRequestItemService.update(returnRequestItemDomain);
	}
}
