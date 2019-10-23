package com.royal.service.impl;

import com.royal.entity.SymbolRecordM1;
import com.royal.entity.SymbolRecordM15;
import com.royal.mapper.SymbolRecordM15Mapper;
import com.royal.mapper.SymbolRecordM1Mapper;
import com.royal.service.ISymbolRecordM15Service;
import com.royal.service.ISymbolRecordM1Service;
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
public class SymbolRecordM15ServiceImpl extends BaseServiceImpl<SymbolRecordM15> implements ISymbolRecordM15Service {

	private SymbolRecordM15Mapper symbolRecordM15Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordM15Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordM15Mapper = mapper;
	}

}