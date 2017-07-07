package com.dookay.shiatzy.web.admin.form;

import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import lombok.Data;

import java.util.List;

/**
 * Created by admin on 2017/7/7.
 */
@Data
public class ReturnForm {
    List<ReturnRequestItemDomain> returnItemList;
}
