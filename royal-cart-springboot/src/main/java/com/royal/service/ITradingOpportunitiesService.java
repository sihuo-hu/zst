package com.royal.service;

import com.github.pagehelper.PageInfo;
import com.royal.entity.TradingOpportunities;
import com.royal.entity.json.PageData;

/**
* 描述：交易机会 服务实现层接口
* @author Royal
* @date 2019年02月14日 21:09:16
*/
public interface ITradingOpportunitiesService extends BaseService<TradingOpportunities> {


    PageInfo<TradingOpportunities> getMyPageOrderByCreateTime(TradingOpportunities tradingOpportunities, PageData pd);
}