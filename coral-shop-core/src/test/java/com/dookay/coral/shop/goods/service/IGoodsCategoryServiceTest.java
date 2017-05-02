package com.dookay.coral.shop.goods.service;

import com.dookay.coral.shop.base.BaseTest;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/4/25
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class IGoodsCategoryServiceTest  extends BaseTest {

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @Test
    public void select(){
        goodsCategoryService.getList(new GoodsCategoryQuery());
    }
}