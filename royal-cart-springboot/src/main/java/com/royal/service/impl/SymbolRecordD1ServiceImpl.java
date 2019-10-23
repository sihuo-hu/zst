package com.royal.service.impl;

import com.royal.entity.SymbolRecordD1;
import com.royal.mapper.SymbolRecordD1Mapper;
import com.royal.service.ISymbolRecordD1Service;
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
public class SymbolRecordD1ServiceImpl extends BaseServiceImpl<SymbolRecordD1> implements ISymbolRecordD1Service {

	private SymbolRecordD1Mapper symbolRecordD1Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordD1Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordD1Mapper = mapper;
	}

}