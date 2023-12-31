/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated;


import com.yc_pay.model.dbModels.generated.tables.Cryptoprice;
import com.yc_pay.model.dbModels.generated.tables.records.CryptopriceRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CryptopriceRecord> CRYPTOPRICE_PKEY = Internal.createUniqueKey(Cryptoprice.CRYPTOPRICE, DSL.name("cryptoprice_pkey"), new TableField[] { Cryptoprice.CRYPTOPRICE.TICKER }, true);
}
