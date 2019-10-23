package com.royal.service.impl;

import com.royal.entity.SymbolRecordM1;
import com.royal.entity.SymbolRecordM60;
import com.royal.mapper.SymbolRecordM1Mapper;
import com.royal.mapper.SymbolRecordM60Mapper;
import com.royal.service.ISymbolRecordM1Service;
import com.royal.service.ISymbolRecordM60Service;
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
public class SymbolRecordM60ServiceImpl extends BaseServiceImpl<SymbolRecordM60> implements ISymbolRecordM60Service {

	private SymbolRecordM60Mapper symbolRecordM60Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordM60Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordM60Mapper = mapper;
	}

}