/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables;


import com.yc_pay.model.dbModels.generated.Keys;
import com.yc_pay.model.dbModels.generated.Public;
import com.yc_pay.model.dbModels.generated.tables.records.CryptoWalletsRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function9;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row9;
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
public class CryptoWallets extends TableImpl<CryptoWalletsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.crypto_wallets</code>
     */
    public static final CryptoWallets CRYPTO_WALLETS = new CryptoWallets();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CryptoWalletsRecord> getRecordType() {
        return CryptoWalletsRecord.class;
    }

    /**
     * The column <code>public.crypto_wallets.id</code>.
     */
    public final TableField<CryptoWalletsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.crypto_wallets.currency_crypto</code>.
     */
    public final TableField<CryptoWalletsRecord, String> CURRENCY_CRYPTO = createField(DSL.name("currency_crypto"), SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>public.crypto_wallets.network</code>.
     */
    public final TableField<CryptoWalletsRecord, String> NETWORK = createField(DSL.name("network"), SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>public.crypto_wallets.public_key</code>.
     */
    public final TableField<CryptoWalletsRecord, String> PUBLIC_KEY = createField(DSL.name("public_key"), SQLDataType.VARCHAR(120), this, "");

    /**
     * The column <code>public.crypto_wallets.private_key</code>.
     */
    public final TableField<CryptoWalletsRecord, String> PRIVATE_KEY = createField(DSL.name("private_key"), SQLDataType.VARCHAR(150), this, "");

    /**
     * The column <code>public.crypto_wallets.address</code>.
     */
    public final TableField<CryptoWalletsRecord, String> ADDRESS = createField(DSL.name("address"), SQLDataType.VARCHAR(120), this, "");

    /**
     * The column <code>public.crypto_wallets.create_date</code>.
     */
    public final TableField<CryptoWalletsRecord, LocalDateTime> CREATE_DATE = createField(DSL.name("create_date"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.crypto_wallets.type</code>.
     */
    public final TableField<CryptoWalletsRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>public.crypto_wallets.amount_crypto</code>.
     */
    public final TableField<CryptoWalletsRecord, BigDecimal> AMOUNT_CRYPTO = createField(DSL.name("amount_crypto"), SQLDataType.NUMERIC, this, "");

    private CryptoWallets(Name alias, Table<CryptoWalletsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CryptoWallets(Name alias, Table<CryptoWalletsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.crypto_wallets</code> table reference
     */
    public CryptoWallets(String alias) {
        this(DSL.name(alias), CRYPTO_WALLETS);
    }

    /**
     * Create an aliased <code>public.crypto_wallets</code> table reference
     */
    public CryptoWallets(Name alias) {
        this(alias, CRYPTO_WALLETS);
    }

    /**
     * Create a <code>public.crypto_wallets</code> table reference
     */
    public CryptoWallets() {
        this(DSL.name("crypto_wallets"), null);
    }

    public <O extends Record> CryptoWallets(Table<O> child, ForeignKey<O, CryptoWalletsRecord> key) {
        super(child, key, CRYPTO_WALLETS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<CryptoWalletsRecord, Integer> getIdentity() {
        return (Identity<CryptoWalletsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<CryptoWalletsRecord> getPrimaryKey() {
        return Keys.CRYPTO_WALLETS_PKEY;
    }

    @Override
    public CryptoWallets as(String alias) {
        return new CryptoWallets(DSL.name(alias), this);
    }

    @Override
    public CryptoWallets as(Name alias) {
        return new CryptoWallets(alias, this);
    }

    @Override
    public CryptoWallets as(Table<?> alias) {
        return new CryptoWallets(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public CryptoWallets rename(String name) {
        return new CryptoWallets(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CryptoWallets rename(Name name) {
        return new CryptoWallets(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public CryptoWallets rename(Table<?> name) {
        return new CryptoWallets(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, String, String, String, String, String, LocalDateTime, String, BigDecimal> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function9<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super BigDecimal, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function9<? super Integer, ? super String, ? super String, ? super String, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super BigDecimal, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
