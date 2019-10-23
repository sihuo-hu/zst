package com.royal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.royal.entity.PaySwitch;
import com.royal.mapper.PaySwitchMapper;
import com.royal.service.IPaySwitchService;


/**
* 描述：支付通道开关 服务实现层
* @author Royal
* @date 2019年07月08日 10:46:27
*/
@Service
@Transactional
public class PaySwitchServiceImpl extends BaseServiceImpl<PaySwitch> implements IPaySwitchService {

	private PaySwitchMapper paySwitchMapper;
	
	@Resource
	public void setBaseMapper(PaySwitchMapper mapper){
		super.setBaseMapper(mapper);
		this.paySwitchMapper = mapper;
	}

}