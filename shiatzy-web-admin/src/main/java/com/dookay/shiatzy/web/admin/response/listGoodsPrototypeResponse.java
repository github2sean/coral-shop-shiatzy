package com.dookay.shiatzy.web.admin.response;

import com.dookay.coral.shop.goods.domain.GoodsPrototypeDomain;
import lombok.Data;

import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
@Data
public class listGoodsPrototypeResponse extends SuccessResponse {
    private List<GoodsPrototypeDomain> goodsPrototypeDomainList;

}
