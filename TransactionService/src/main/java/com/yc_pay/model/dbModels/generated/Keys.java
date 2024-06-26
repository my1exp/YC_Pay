/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated;


import com.yc_pay.model.dbModels.generated.tables.Entries;
import com.yc_pay.model.dbModels.generated.tables.Pocket;
import com.yc_pay.model.dbModels.generated.tables.Transaction;
import com.yc_pay.model.dbModels.generated.tables.records.EntriesRecord;
import com.yc_pay.model.dbModels.generated.tables.records.PocketRecord;
import com.yc_pay.model.dbModels.generated.tables.records.TransactionRecord;

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

    public static final UniqueKey<EntriesRecord> ENTRIES_PKEY = Internal.createUniqueKey(Entries.ENTRIES, DSL.name("entries_pkey"), new TableField[] { Entries.ENTRIES.ID }, true);
    public static final UniqueKey<PocketRecord> POCKET_PKEY = Internal.createUniqueKey(Pocket.POCKET, DSL.name("pocket_pkey"), new TableField[] { Pocket.POCKET.ID }, true);
    public static final UniqueKey<TransactionRecord> TRANSACTION_PKEY = Internal.createUniqueKey(Transaction.TRANSACTION, DSL.name("transaction_pkey"), new TableField[] { Transaction.TRANSACTION.PAYMENT_ID, Transaction.TRANSACTION.MERCHANT_ID }, true);
}
