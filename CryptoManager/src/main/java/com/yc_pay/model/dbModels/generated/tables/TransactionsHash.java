/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables;


import com.yc_pay.model.dbModels.generated.Keys;
import com.yc_pay.model.dbModels.generated.Public;
import com.yc_pay.model.dbModels.generated.tables.records.TransactionsHashRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function8;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TransactionsHash extends TableImpl<TransactionsHashRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.transactions_hash</code>
     */
    public static final TransactionsHash TRANSACTIONS_HASH = new TransactionsHash();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TransactionsHashRecord> getRecordType() {
        return TransactionsHashRecord.class;
    }

    /**
     * The column <code>public.transactions_hash.hash</code>.
     */
    public final TableField<TransactionsHashRecord, String> HASH = createField(DSL.name("hash"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.transactions_hash.crypto_wallet_id</code>.
     */
    public final TableField<TransactionsHashRecord, Integer> CRYPTO_WALLET_ID = createField(DSL.name("crypto_wallet_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.transactions_hash.payment_id</code>.
     */
    public final TableField<TransactionsHashRecord, Integer> PAYMENT_ID = createField(DSL.name("payment_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.transactions_hash.received_amount</code>.
     */
    public final TableField<TransactionsHashRecord, Double> RECEIVED_AMOUNT = createField(DSL.name("received_amount"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>public.transactions_hash.withdraw_flg</code>.
     */
    public final TableField<TransactionsHashRecord, Integer> WITHDRAW_FLG = createField(DSL.name("withdraw_flg"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.transactions_hash.delivered_tx_serv_flg</code>.
     */
    public final TableField<TransactionsHashRecord, Integer> DELIVERED_TX_SERV_FLG = createField(DSL.name("delivered_tx_serv_flg"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.transactions_hash.sender</code>.
     */
    public final TableField<TransactionsHashRecord, String> SENDER = createField(DSL.name("sender"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.transactions_hash.return_to_sender</code>.
     */
    public final TableField<TransactionsHashRecord, Integer> RETURN_TO_SENDER = createField(DSL.name("return_to_sender"), SQLDataType.INTEGER, this, "");

    private TransactionsHash(Name alias, Table<TransactionsHashRecord> aliased) {
        this(alias, aliased, null);
    }

    private TransactionsHash(Name alias, Table<TransactionsHashRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.transactions_hash</code> table reference
     */
    public TransactionsHash(String alias) {
        this(DSL.name(alias), TRANSACTIONS_HASH);
    }

    /**
     * Create an aliased <code>public.transactions_hash</code> table reference
     */
    public TransactionsHash(Name alias) {
        this(alias, TRANSACTIONS_HASH);
    }

    /**
     * Create a <code>public.transactions_hash</code> table reference
     */
    public TransactionsHash() {
        this(DSL.name("transactions_hash"), null);
    }

    public <O extends Record> TransactionsHash(Table<O> child, ForeignKey<O, TransactionsHashRecord> key) {
        super(child, key, TRANSACTIONS_HASH);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<TransactionsHashRecord> getPrimaryKey() {
        return Keys.TRANSACTIONS_HASH_PKEY;
    }

    @Override
    public TransactionsHash as(String alias) {
        return new TransactionsHash(DSL.name(alias), this);
    }

    @Override
    public TransactionsHash as(Name alias) {
        return new TransactionsHash(alias, this);
    }

    @Override
    public TransactionsHash as(Table<?> alias) {
        return new TransactionsHash(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public TransactionsHash rename(String name) {
        return new TransactionsHash(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TransactionsHash rename(Name name) {
        return new TransactionsHash(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public TransactionsHash rename(Table<?> name) {
        return new TransactionsHash(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<String, Integer, Integer, Double, Integer, Integer, String, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function8<? super String, ? super Integer, ? super Integer, ? super Double, ? super Integer, ? super Integer, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function8<? super String, ? super Integer, ? super Integer, ? super Double, ? super Integer, ? super Integer, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
