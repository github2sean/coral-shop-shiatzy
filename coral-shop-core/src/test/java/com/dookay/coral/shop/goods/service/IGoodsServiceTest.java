package com.dookay.coral.shop.goods.service;

import com.dookay.coral.shop.base.BaseTest;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/8
 */
public class IGoodsServiceTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IGoodsService goodsService;

    @Test
    public void updateSizes() throws Exception {
        GoodsDomain goodsDomain = goodsService.get(1L);
        goodsService.updateSizes(goodsDomain);
        goodsService.updateColors(goodsDomain);
        logger.info(goodsDomain.getSizeIds());
    }

}