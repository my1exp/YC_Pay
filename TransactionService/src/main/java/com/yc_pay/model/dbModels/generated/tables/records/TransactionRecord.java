/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables.records;


import com.yc_pay.model.dbModels.generated.tables.Transaction;

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
public class TransactionRecord extends UpdatableRecordImpl<TransactionRecord> implements Record13<String, String, String, String, String, BigDecimal, String, LocalDateTime, String, String, BigDecimal, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.transaction.payment_id</code>.
     */
    public void setPaymentId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.transaction.payment_id</code>.
     */
    public String getPaymentId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.transaction.merchant_id</code>.
     */
    public void setMerchantId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.transaction.merchant_id</code>.
     */
    public String getMerchantId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.transaction.wallet_from</code>.
     */
    public void setWalletFrom(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.transaction.wallet_from</code>.
     */
    public String getWalletFrom() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.transaction.wallet_to</code>.
     */
    public void setWalletTo(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.transaction.wallet_to</code>.
     */
    public String getWalletTo() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.transaction.currency_crypto</code>.
     */
    public void setCurrencyCrypto(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.transaction.currency_crypto</code>.
     */
    public String getCurrencyCrypto() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.transaction.amount_crypto</code>.
     */
    public void setAmountCrypto(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.transaction.amount_crypto</code>.
     */
    public BigDecimal getAmountCrypto() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>public.transaction.network</code>.
     */
    public void setNetwork(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.transaction.network</code>.
     */
    public String getNetwork() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.transaction.payment_dttm</code>.
     */
    public void setPaymentDttm(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.transaction.payment_dttm</code>.
     */
    public LocalDateTime getPaymentDttm() {
        return (LocalDateTime) get(7);
    }

    /**
     * Setter for <code>public.transaction.category</code>.
     */
    public void setCategory(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.transaction.category</code>.
     */
    public String getCategory() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.transaction.currency_fiat</code>.
     */
    public void setCurrencyFiat(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.transaction.currency_fiat</code>.
     */
    public String getCurrencyFiat() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.transaction.amount_fiat</code>.
     */
    public void setAmountFiat(BigDecimal value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.transaction.amount_fiat</code>.
     */
    public BigDecimal getAmountFiat() {
        return (BigDecimal) get(10);
    }

    /**
     * Setter for <code>public.transaction.income_pocket</code>.
     */
    public void setIncomePocket(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.transaction.income_pocket</code>.
     */
    public String getIncomePocket() {
        return (String) get(11);
    }

    /**
     * Setter for <code>public.transaction.output_pocket</code>.
     */
    public void setOutputPocket(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.transaction.output_pocket</code>.
     */
    public String getOutputPocket() {
        return (String) get(12);
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
    public Row13<String, String, String, String, String, BigDecimal, String, LocalDateTime, String, String, BigDecimal, String, String> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    @Override
    public Row13<String, String, String, String, String, BigDecimal, String, LocalDateTime, String, String, BigDecimal, String, String> valuesRow() {
        return (Row13) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Transaction.TRANSACTION.PAYMENT_ID;
    }

    @Override
    public Field<String> field2() {
        return Transaction.TRANSACTION.MERCHANT_ID;
    }

    @Override
    public Field<String> field3() {
        return Transaction.TRANSACTION.WALLET_FROM;
    }

    @Override
    public Field<String> field4() {
        return Transaction.TRANSACTION.WALLET_TO;
    }

    @Override
    public Field<String> field5() {
        return Transaction.TRANSACTION.CURRENCY_CRYPTO;
    }

    @Override
    public Field<BigDecimal> field6() {
        return Transaction.TRANSACTION.AMOUNT_CRYPTO;
    }

    @Override
    public Field<String> field7() {
        return Transaction.TRANSACTION.NETWORK;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return Transaction.TRANSACTION.PAYMENT_DTTM;
    }

    @Override
    public Field<String> field9() {
        return Transaction.TRANSACTION.CATEGORY;
    }

    @Override
    public Field<String> field10() {
        return Transaction.TRANSACTION.CURRENCY_FIAT;
    }

    @Override
    public Field<BigDecimal> field11() {
        return Transaction.TRANSACTION.AMOUNT_FIAT;
    }

    @Override
    public Field<String> field12() {
        return Transaction.TRANSACTION.INCOME_POCKET;
    }

    @Override
    public Field<String> field13() {
        return Transaction.TRANSACTION.OUTPUT_POCKET;
    }

    @Override
    public String component1() {
        return getPaymentId();
    }

    @Override
    public String component2() {
        return getMerchantId();
    }

    @Override
    public String component3() {
        return getWalletFrom();
    }

    @Override
    public String component4() {
        return getWalletTo();
    }

    @Override
    public String component5() {
        return getCurrencyCrypto();
    }

    @Override
    public BigDecimal component6() {
        return getAmountCrypto();
    }

    @Override
    public String component7() {
        return getNetwork();
    }

    @Override
    public LocalDateTime component8() {
        return getPaymentDttm();
    }

    @Override
    public String component9() {
        return getCategory();
    }

    @Override
    public String component10() {
        return getCurrencyFiat();
    }

    @Override
    public BigDecimal component11() {
        return getAmountFiat();
    }

    @Override
    public String component12() {
        return getIncomePocket();
    }

    @Override
    public String component13() {
        return getOutputPocket();
    }

    @Override
    public String value1() {
        return getPaymentId();
    }

    @Override
    public String value2() {
        return getMerchantId();
    }

    @Override
    public String value3() {
        return getWalletFrom();
    }

    @Override
    public String value4() {
        return getWalletTo();
    }

    @Override
    public String value5() {
        return getCurrencyCrypto();
    }

    @Override
    public BigDecimal value6() {
        return getAmountCrypto();
    }

    @Override
    public String value7() {
        return getNetwork();
    }

    @Override
    public LocalDateTime value8() {
        return getPaymentDttm();
    }

    @Override
    public String value9() {
        return getCategory();
    }

    @Override
    public String value10() {
        return getCurrencyFiat();
    }

    @Override
    public BigDecimal value11() {
        return getAmountFiat();
    }

    @Override
    public String value12() {
        return getIncomePocket();
    }

    @Override
    public String value13() {
        return getOutputPocket();
    }

    @Override
    public TransactionRecord value1(String value) {
        setPaymentId(value);
        return this;
    }

    @Override
    public TransactionRecord value2(String value) {
        setMerchantId(value);
        return this;
    }

    @Override
    public TransactionRecord value3(String value) {
        setWalletFrom(value);
        return this;
    }

    @Override
    public TransactionRecord value4(String value) {
        setWalletTo(value);
        return this;
    }

    @Override
    public TransactionRecord value5(String value) {
        setCurrencyCrypto(value);
        return this;
    }

    @Override
    public TransactionRecord value6(BigDecimal value) {
        setAmountCrypto(value);
        return this;
    }

    @Override
    public TransactionRecord value7(String value) {
        setNetwork(value);
        return this;
    }

    @Override
    public TransactionRecord value8(LocalDateTime value) {
        setPaymentDttm(value);
        return this;
    }

    @Override
    public TransactionRecord value9(String value) {
        setCategory(value);
        return this;
    }

    @Override
    public TransactionRecord value10(String value) {
        setCurrencyFiat(value);
        return this;
    }

    @Override
    public TransactionRecord value11(BigDecimal value) {
        setAmountFiat(value);
        return this;
    }

    @Override
    public TransactionRecord value12(String value) {
        setIncomePocket(value);
        return this;
    }

    @Override
    public TransactionRecord value13(String value) {
        setOutputPocket(value);
        return this;
    }

    @Override
    public TransactionRecord values(String value1, String value2, String value3, String value4, String value5, BigDecimal value6, String value7, LocalDateTime value8, String value9, String value10, BigDecimal value11, String value12, String value13) {
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
     * Create a detached TransactionRecord
     */
    public TransactionRecord() {
        super(Transaction.TRANSACTION);
    }

    /**
     * Create a detached, initialised TransactionRecord
     */
    public TransactionRecord(String paymentId, String merchantId, String walletFrom, String walletTo, String currencyCrypto, BigDecimal amountCrypto, String network, LocalDateTime paymentDttm, String category, String currencyFiat, BigDecimal amountFiat, String incomePocket, String outputPocket) {
        super(Transaction.TRANSACTION);

        setPaymentId(paymentId);
        setMerchantId(merchantId);
        setWalletFrom(walletFrom);
        setWalletTo(walletTo);
        setCurrencyCrypto(currencyCrypto);
        setAmountCrypto(amountCrypto);
        setNetwork(network);
        setPaymentDttm(paymentDttm);
        setCategory(category);
        setCurrencyFiat(currencyFiat);
        setAmountFiat(amountFiat);
        setIncomePocket(incomePocket);
        setOutputPocket(outputPocket);
        resetChangedOnNotNull();
    }
}
