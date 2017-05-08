package com.dookay.coral.shop.order.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * 订单退货申请明细的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
@Table(name = "t_order_return_request_item")
public class ReturnRequestItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单退货申请id*/
	private Long returnRequestId;
	
	/*订单明细id*/
	private String orderItemId;
	
	/*客户id*/
	private Long customerId;
	
	/*数量*/
	private Integer num;
	
	/*退货理由json*/
	private String returnReason;
	@Transient
	private String returnReasonText;
	/*退货时间*/
	private Date createTime;
	
	/*退货状态：1待收货 2已收货 3接受退货 4拒绝退货 5取消退货*/
	private Integer status;
	
	/*管理员备注*/
	private String adminMemo;
	

	
	
}
