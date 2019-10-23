package com.royal.service.impl;

import com.royal.entity.SymbolRecordM1;
import com.royal.entity.SymbolRecordM30;
import com.royal.mapper.SymbolRecordM1Mapper;
import com.royal.mapper.SymbolRecordM30Mapper;
import com.royal.service.ISymbolRecordM1Service;
import com.royal.service.ISymbolRecordM30Service;
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
public class SymbolRecordM30ServiceImpl extends BaseServiceImpl<SymbolRecordM30> implements ISymbolRecordM30Service {

	private SymbolRecordM30Mapper symbolRecordM30Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordM30Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordM30Mapper = mapper;
	}

}