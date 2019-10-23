package com.royal.entity.json;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SymbolRecordJson {
    private Long time;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;

}
