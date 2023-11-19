/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables;


import com.yc_pay.model.dbModels.generated.Keys;
import com.yc_pay.model.dbModels.generated.Public;
import com.yc_pay.model.dbModels.generated.tables.records.CryptoPaymentsRecord;

import java.util.Arrays;
import java.util.List;
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
public class CryptoPayments extends TableImpl<CryptoPaymentsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.crypto_payments</code>
     */
    public static final CryptoPayments CRYPTO_PAYMENTS = new CryptoPayments();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CryptoPaymentsRecord> getRecordType() {
        return CryptoPaymentsRecord.class;
    }

    /**
     * The column <code>public.crypto_payments.id</code>.
     */
    public final TableField<CryptoPaymentsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.crypto_payments.crypto_wallet_id</code>.
     */
    public final TableField<CryptoPaymentsRecord, Integer> CRYPTO_WALLET_ID = createField(DSL.name("crypto_wallet_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.crypto_payments.status</code>.
     */
    public final TableField<CryptoPaymentsRecord, Integer> STATUS = createField(DSL.name("status"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.crypto_payments.amount_crypto</code>.
     */
    public final TableField<CryptoPaymentsRecord, Double> AMOUNT_CRYPTO = createField(DSL.name("amount_crypto"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.crypto_payments.merchant_id</code>.
     */
    public final TableField<CryptoPaymentsRecord, String> MERCHANT_ID = createField(DSL.name("merchant_id"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.crypto_payments.request_id</code>.
     */
    public final TableField<CryptoPaymentsRecord, String> REQUEST_ID = createField(DSL.name("request_id"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.crypto_payments.amount_fiat</code>.
     */
    public final TableField<CryptoPaymentsRecord, Double> AMOUNT_FIAT = createField(DSL.name("amount_fiat"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.crypto_payments.currency_fiat</code>.
     */
    public final TableField<CryptoPaymentsRecord, String> CURRENCY_FIAT = createField(DSL.name("currency_fiat"), SQLDataType.VARCHAR(30), this, "");

    /**
     * The column <code>public.crypto_payments.destination_tag</code>.
     */
    public final TableField<CryptoPaymentsRecord, Integer> DESTINATION_TAG = createField(DSL.name("destination_tag"), SQLDataType.INTEGER, this, "");

    private CryptoPayments(Name alias, Table<CryptoPaymentsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CryptoPayments(Name alias, Table<CryptoPaymentsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.crypto_payments</code> table reference
     */
    public CryptoPayments(String alias) {
        this(DSL.name(alias), CRYPTO_PAYMENTS);
    }

    /**
     * Create an aliased <code>public.crypto_payments</code> table reference
     */
    public CryptoPayments(Name alias) {
        this(alias, CRYPTO_PAYMENTS);
    }

    /**
     * Create a <code>public.crypto_payments</code> table reference
     */
    public CryptoPayments() {
        this(DSL.name("crypto_payments"), null);
    }

    public <O extends Record> CryptoPayments(Table<O> child, ForeignKey<O, CryptoPaymentsRecord> key) {
        super(child, key, CRYPTO_PAYMENTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<CryptoPaymentsRecord, Integer> getIdentity() {
        return (Identity<CryptoPaymentsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<CryptoPaymentsRecord> getPrimaryKey() {
        return Keys.CRYPTO_PAYMENTS_PKEY;
    }

    @Override
    public List<ForeignKey<CryptoPaymentsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CRYPTO_PAYMENTS__CRYPTO_PAYMENTS_CRYPTO_WALLET_ID_FKEY);
    }

    private transient CryptoWallets _cryptoWallets;

    /**
     * Get the implicit join path to the <code>public.crypto_wallets</code>
     * table.
     */
    public CryptoWallets cryptoWallets() {
        if (_cryptoWallets == null)
            _cryptoWallets = new CryptoWallets(this, Keys.CRYPTO_PAYMENTS__CRYPTO_PAYMENTS_CRYPTO_WALLET_ID_FKEY);

        return _cryptoWallets;
    }

    @Override
    public CryptoPayments as(String alias) {
        return new CryptoPayments(DSL.name(alias), this);
    }

    @Override
    public CryptoPayments as(Name alias) {
        return new CryptoPayments(alias, this);
    }

    @Override
    public CryptoPayments as(Table<?> alias) {
        return new CryptoPayments(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public CryptoPayments rename(String name) {
        return new CryptoPayments(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CryptoPayments rename(Name name) {
        return new CryptoPayments(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public CryptoPayments rename(Table<?> name) {
        return new CryptoPayments(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, Integer, Integer, Double, String, String, Double, String, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function9<? super Integer, ? super Integer, ? super Integer, ? super Double, ? super String, ? super String, ? super Double, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function9<? super Integer, ? super Integer, ? super Integer, ? super Double, ? super String, ? super String, ? super Double, ? super String, ? super Integer, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
