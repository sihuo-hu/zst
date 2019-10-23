package com.royal.service.impl;

import com.royal.entity.SymbolRecordH4;
import com.royal.mapper.SymbolRecordH4Mapper;
import com.royal.service.ISymbolRecordH4Service;
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
public class SymbolRecordH4ServiceImpl extends BaseServiceImpl<SymbolRecordH4> implements ISymbolRecordH4Service {

	private SymbolRecordH4Mapper symbolRecordH4Mapper;
	
	@Resource
	public void setBaseMapper(SymbolRecordH4Mapper mapper){
		super.setBaseMapper(mapper);
		this.symbolRecordH4Mapper = mapper;
	}

}