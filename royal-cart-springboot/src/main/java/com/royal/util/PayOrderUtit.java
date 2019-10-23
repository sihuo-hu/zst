package com.royal.util;

import com.royal.entity.AmountRecord;

import java.util.*;

public class PayOrderUtit {

    private static Set<AmountRecord> amountRecordSet = new HashSet<AmountRecord>();

    public static void putList(List<AmountRecord> amountRecordList) {
        amountRecordSet.addAll(amountRecordList);
    }

    public static boolean put(AmountRecord amountRecord) {
        if (amountRecordSet.contains(amountRecord)) {
            return false;
        } else {
            amountRecordSet.add(amountRecord);
            return true;
        }
    }

    public static boolean del(AmountRecord amountRecord) {
        if (amountRecordSet.contains(amountRecord)) {
            return false;
        } else {
            amountRecordSet.remove(amountRecord);
            return true;
        }
    }
}
