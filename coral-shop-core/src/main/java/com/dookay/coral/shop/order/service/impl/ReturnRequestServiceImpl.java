package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReturnRequestMapper;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.service.IReturnRequestService;

import java.util.List;

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
	public void isAgree(Long id, Integer isAgree,List<ReturnRequestItemDomain> returnRequestItemDomain) {
		if(id==null||isAgree==null){
			throw new ServiceException("传入参数出错");
		}
		ReturnRequestDomain  returnRequestDomain = get(id);
		if(isAgree!=null&&isAgree==2){
			returnRequestDomain.setStatus(isAgree);//同意
		}else if(isAgree!=null&&isAgree==3){
			returnRequestDomain.setStatus(isAgree);//取消
		}
		update(returnRequestDomain);
		for(ReturnRequestItemDomain line: returnRequestItemDomain){
			returnRequestItemService.update(line);
		}
	}

}
