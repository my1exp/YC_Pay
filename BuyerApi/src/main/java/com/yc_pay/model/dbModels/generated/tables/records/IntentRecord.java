/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables.records;


import com.yc_pay.model.dbModels.generated.tables.Intent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record13;
import org.jooq.Record2;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntentRecord extends UpdatableRecordImpl<IntentRecord> implements Record13<String, String, String, BigDecimal, String, String, String, LocalDateTime, String, String, Integer, BigDecimal, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.intent.request_id</code>.
     */
    public void setRequestId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.intent.request_id</code>.
     */
    public String getRequestId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.intent.session_id</code>.
     */
    public void setSessionId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.intent.session_id</code>.
     */
    public String getSessionId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.intent.currency</code>.
     */
    public void setCurrency(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.intent.currency</code>.
     */
    public String getCurrency() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.intent.amount_fiat</code>.
     */
    public void setAmountFiat(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.intent.amount_fiat</code>.
     */
    public BigDecimal getAmountFiat() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>public.intent.network</code>.
     */
    public void setNetwork(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.intent.network</code>.
     */
    public String getNetwork() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.intent.merchant_id</code>.
     */
    public void setMerchantId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.intent.merchant_id</code>.
     */
    public String getMerchantId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.intent.wallet_to</code>.
     */
    public void setWalletTo(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.intent.wallet_to</code>.
     */
    public String getWalletTo() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.intent.create_date</code>.
     */
    public void setCreateDate(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.intent.create_date</code>.
     */
    public LocalDateTime getCreateDate() {
        return (LocalDateTime) get(7);
    }

    /**
     * Setter for <code>public.intent.category</code>.
     */
    public void setCategory(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.intent.category</code>.
     */
    public String getCategory() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.intent.status</code>.
     */
    public void setStatus(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.intent.status</code>.
     */
    public String getStatus() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.intent.wallet_id</code>.
     */
    public void setWalletId(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.intent.wallet_id</code>.
     */
    public Integer getWalletId() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>public.intent.amount_crypto</code>.
     */
    public void setAmountCrypto(BigDecimal value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.intent.amount_crypto</code>.
     */
    public BigDecimal getAmountCrypto() {
        return (BigDecimal) get(11);
    }

    /**
     * Setter for <code>public.intent.destination_tag</code>.
     */
    public void setDestinationTag(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.intent.destination_tag</code>.
     */
    public Integer getDestinationTag() {
        return (Integer) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row13<String, String, String, BigDecimal, String, String, String, LocalDateTime, String, String, Integer, BigDecimal, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    @Override
    public Row13<String, String, String, BigDecimal, String, String, String, LocalDateTime, String, String, Integer, BigDecimal, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Intent.INTENT.REQUEST_ID;
    }

    @Override
    public Field<String> field2() {
        return Intent.INTENT.SESSION_ID;
    }

    @Override
    public Field<String> field3() {
        return Intent.INTENT.CURRENCY;
    }

    @Override
    public Field<BigDecimal> field4() {
        return Intent.INTENT.AMOUNT_FIAT;
    }

    @Override
    public Field<String> field5() {
        return Intent.INTENT.NETWORK;
    }

    @Override
    public Field<String> field6() {
        return Intent.INTENT.MERCHANT_ID;
    }

    @Override
    public Field<String> field7() {
        return Intent.INTENT.WALLET_TO;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return Intent.INTENT.CREATE_DATE;
    }

    @Override
    public Field<String> field9() {
        return Intent.INTENT.CATEGORY;
    }

    @Override
    public Field<String> field10() {
        return Intent.INTENT.STATUS;
    }

    @Override
    public Field<Integer> field11() {
        return Intent.INTENT.WALLET_ID;
    }

    @Override
    public Field<BigDecimal> field12() {
        return Intent.INTENT.AMOUNT_CRYPTO;
    }

    @Override
    public Field<Integer> field13() {
        return Intent.INTENT.DESTINATION_TAG;
    }

    @Override
    public String component1() {
        return getRequestId();
    }

    @Override
    public String component2() {
        return getSessionId();
    }

    @Override
    public String component3() {
        return getCurrency();
    }

    @Override
    public BigDecimal component4() {
        return getAmountFiat();
    }

    @Override
    public String component5() {
        return getNetwork();
    }

    @Override
    public String component6() {
        return getMerchantId();
    }

    @Override
    public String component7() {
        return getWalletTo();
    }

    @Override
    public LocalDateTime component8() {
        return getCreateDate();
    }

    @Override
    public String component9() {
        return getCategory();
    }

    @Override
    public String component10() {
        return getStatus();
    }

    @Override
    public Integer component11() {
        return getWalletId();
    }

    @Override
    public BigDecimal component12() {
        return getAmountCrypto();
    }

    @Override
    public Integer component13() {
        return getDestinationTag();
    }

    @Override
    public String value1() {
        return getRequestId();
    }

    @Override
    public String value2() {
        return getSessionId();
    }

    @Override
    public String value3() {
        return getCurrency();
    }

    @Override
    public BigDecimal value4() {
        return getAmountFiat();
    }

    @Override
    public String value5() {
        return getNetwork();
    }

    @Override
    public String value6() {
        return getMerchantId();
    }

    @Override
    public String value7() {
        return getWalletTo();
    }

    @Override
    public LocalDateTime value8() {
        return getCreateDate();
    }

    @Override
    public String value9() {
        return getCategory();
    }

    @Override
    public String value10() {
        return getStatus();
    }

    @Override
    public Integer value11() {
        return getWalletId();
    }

    @Override
    public BigDecimal value12() {
        return getAmountCrypto();
    }

    @Override
    public Integer value13() {
        return getDestinationTag();
    }

    @Override
    public IntentRecord value1(String value) {
        setRequestId(value);
        return this;
    }

    @Override
    public IntentRecord value2(String value) {
        setSessionId(value);
        return this;
    }

    @Override
    public IntentRecord value3(String value) {
        setCurrency(value);
        return this;
    }

    @Override
    public IntentRecord value4(BigDecimal value) {
        setAmountFiat(value);
        return this;
    }

    @Override
    public IntentRecord value5(String value) {
        setNetwork(value);
        return this;
    }

    @Override
    public IntentRecord value6(String value) {
        setMerchantId(value);
        return this;
    }

    @Override
    public IntentRecord value7(String value) {
        setWalletTo(value);
        return this;
    }

    @Override
    public IntentRecord value8(LocalDateTime value) {
        setCreateDate(value);
        return this;
    }

    @Override
    public IntentRecord value9(String value) {
        setCategory(value);
        return this;
    }

    @Override
    public IntentRecord value10(String value) {
        setStatus(value);
        return this;
    }

    @Override
    public IntentRecord value11(Integer value) {
        setWalletId(value);
        return this;
    }

    @Override
    public IntentRecord value12(BigDecimal value) {
        setAmountCrypto(value);
        return this;
    }

    @Override
    public IntentRecord value13(Integer value) {
        setDestinationTag(value);
        return this;
    }

    @Override
    public IntentRecord values(String value1, String value2, String value3, BigDecimal value4, String value5, String value6, String value7, LocalDateTime value8, String value9, String value10, Integer value11, BigDecimal value12, Integer value13) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IntentRecord
     */
    public IntentRecord() {
        super(Intent.INTENT);
    }

    /**
     * Create a detached, initialised IntentRecord
     */
    public IntentRecord(String requestId, String sessionId, String currency, BigDecimal amountFiat, String network, String merchantId, String walletTo, LocalDateTime createDate, String category, String status, Integer walletId, BigDecimal amountCrypto, Integer destinationTag) {
        super(Intent.INTENT);

        setRequestId(requestId);
        setSessionId(sessionId);
        setCurrency(currency);
        setAmountFiat(amountFiat);
        setNetwork(network);
        setMerchantId(merchantId);
        setWalletTo(walletTo);
        setCreateDate(createDate);
        setCategory(category);
        setStatus(status);
        setWalletId(walletId);
        setAmountCrypto(amountCrypto);
        setDestinationTag(destinationTag);
        resetChangedOnNotNull();
    }
}
