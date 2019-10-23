package com.royal.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.royal.entity.json.PageData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.TradingOpportunities;
import com.royal.mapper.TradingOpportunitiesMapper;
import com.royal.service.ITradingOpportunitiesService;
import tk.mybatis.mapper.entity.Example;


/**
* 描述：交易机会 服务实现层
* @author Royal
* @date 2019年02月14日 21:09:16
*/
@Service
@Transactional
public class TradingOpportunitiesServiceImpl extends BaseServiceImpl<TradingOpportunities> implements ITradingOpportunitiesService {

	private TradingOpportunitiesMapper tradingOpportunitiesMapper;
	
	@Resource
	public void setBaseMapper(TradingOpportunitiesMapper mapper){
		super.setBaseMapper(mapper);
		this.tradingOpportunitiesMapper = mapper;
	}

	@Override
	public PageInfo<TradingOpportunities> getMyPageOrderByCreateTime(TradingOpportunities tradingOpportunities, PageData pd) {
		Page<Object> ph = PageHelper.startPage(pd.getPage(),pd.getPageSize());
		Example ex = new Example(TradingOpportunities.class);
		ex.createCriteria().andEqualTo("opportuntiesStatus","ENABLE");
		ex.setOrderByClause ("create_time DESC");
		PageInfo<TradingOpportunities> list = new PageInfo<TradingOpportunities>(tradingOpportunitiesMapper.selectByExample (ex));
		return list;
	}
}