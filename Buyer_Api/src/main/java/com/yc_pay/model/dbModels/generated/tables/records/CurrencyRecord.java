/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables.records;


import com.yc_pay.model.dbModels.generated.tables.Currency;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CurrencyRecord extends UpdatableRecordImpl<CurrencyRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.currency.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.currency.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.currency.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.currency.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.currency.network</code>.
     */
    public void setNetwork(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.currency.network</code>.
     */
    public String getNetwork() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Currency.CURRENCY.ID;
    }

    @Override
    public Field<String> field2() {
        return Currency.CURRENCY.NAME;
    }

    @Override
    public Field<String> field3() {
        return Currency.CURRENCY.NETWORK;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getNetwork();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getNetwork();
    }

    @Override
    public CurrencyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public CurrencyRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public CurrencyRecord value3(String value) {
        setNetwork(value);
        return this;
    }

    @Override
    public CurrencyRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CurrencyRecord
     */
    public CurrencyRecord() {
        super(Currency.CURRENCY);
    }

    /**
     * Create a detached, initialised CurrencyRecord
     */
    public CurrencyRecord(Integer id, String name, String network) {
        super(Currency.CURRENCY);

        setId(id);
        setName(name);
        setNetwork(network);
        resetChangedOnNotNull();
    }
}
