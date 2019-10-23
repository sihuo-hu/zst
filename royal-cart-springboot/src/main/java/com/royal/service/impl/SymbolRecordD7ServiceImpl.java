package com.royal.service.impl;

import com.royal.entity.SymbolRecordD7;
import com.royal.mapper.SymbolRecordD7Mapper;
import com.royal.service.ISymbolRecordD7Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* 描述：产品历史记录 服务实现层
* @author Royal
* @date 2019年07月01日 16:39:01
*/
@Service
@Transactional
public class SymbolRecordD7ServiceImpl extends BaseServiceImpl<SymbolRecordD7> implements ISymbolRecordD7Service {

	private SymbolRecordD7Mapper symbolRecordD7Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordD7Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordD7Mapper = mapper;
	}

}