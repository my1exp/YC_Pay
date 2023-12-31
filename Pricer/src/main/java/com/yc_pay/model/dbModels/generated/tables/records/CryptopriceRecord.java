/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables.records;


import com.yc_pay.model.dbModels.generated.tables.Cryptoprice;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CryptopriceRecord extends UpdatableRecordImpl<CryptopriceRecord> implements Record4<String, String, BigDecimal, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.cryptoprice.ticker</code>.
     */
    public void setTicker(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.cryptoprice.ticker</code>.
     */
    public String getTicker() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.cryptoprice.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.cryptoprice.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.cryptoprice.price</code>.
     */
    public void setPrice(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.cryptoprice.price</code>.
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>public.cryptoprice.update_dttm</code>.
     */
    public void setUpdateDttm(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.cryptoprice.update_dttm</code>.
     */
    public LocalDateTime getUpdateDttm() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, BigDecimal, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, String, BigDecimal, LocalDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Cryptoprice.CRYPTOPRICE.TICKER;
    }

    @Override
    public Field<String> field2() {
        return Cryptoprice.CRYPTOPRICE.NAME;
    }

    @Override
    public Field<BigDecimal> field3() {
        return Cryptoprice.CRYPTOPRICE.PRICE;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Cryptoprice.CRYPTOPRICE.UPDATE_DTTM;
    }

    @Override
    public String component1() {
        return getTicker();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public BigDecimal component3() {
        return getPrice();
    }

    @Override
    public LocalDateTime component4() {
        return getUpdateDttm();
    }

    @Override
    public String value1() {
        return getTicker();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public BigDecimal value3() {
        return getPrice();
    }

    @Override
    public LocalDateTime value4() {
        return getUpdateDttm();
    }

    @Override
    public CryptopriceRecord value1(String value) {
        setTicker(value);
        return this;
    }

    @Override
    public CryptopriceRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public CryptopriceRecord value3(BigDecimal value) {
        setPrice(value);
        return this;
    }

    @Override
    public CryptopriceRecord value4(LocalDateTime value) {
        setUpdateDttm(value);
        return this;
    }

    @Override
    public CryptopriceRecord values(String value1, String value2, BigDecimal value3, LocalDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CryptopriceRecord
     */
    public CryptopriceRecord() {
        super(Cryptoprice.CRYPTOPRICE);
    }

    /**
     * Create a detached, initialised CryptopriceRecord
     */
    public CryptopriceRecord(String ticker, String name, BigDecimal price, LocalDateTime updateDttm) {
        super(Cryptoprice.CRYPTOPRICE);

        setTicker(ticker);
        setName(name);
        setPrice(price);
        setUpdateDttm(updateDttm);
        resetChangedOnNotNull();
    }
}
