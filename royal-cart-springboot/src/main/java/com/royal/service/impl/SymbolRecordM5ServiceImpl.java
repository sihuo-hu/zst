package com.royal.service.impl;

import com.royal.entity.SymbolRecordM1;
import com.royal.entity.SymbolRecordM5;
import com.royal.mapper.SymbolRecordM1Mapper;
import com.royal.mapper.SymbolRecordM5Mapper;
import com.royal.service.ISymbolRecordM1Service;
import com.royal.service.ISymbolRecordM5Service;
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
public class SymbolRecordM5ServiceImpl extends BaseServiceImpl<SymbolRecordM5> implements ISymbolRecordM5Service {

	private SymbolRecordM5Mapper symbolRecordM5Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordM5Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordM5Mapper = mapper;
	}

}