package com.dookay.coral.shop.index.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.index.domain.IndexBlockGroupDomain;

import java.util.List;

/**
 * 首页区块分组的业务层接口
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
public interface IIndexBlockGroupService extends IBaseService<IndexBlockGroupDomain> {

    void withIndexBlock(IndexBlockGroupDomain indexBlockGroupDomain);

    void withIndexBlock(List<IndexBlockGroupDomain> indexBlockGroupDomain);
}
