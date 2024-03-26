/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables.records;


import com.yc_pay.model.dbModels.generated.tables.CryptoPayments;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CryptoPaymentsRecord extends UpdatableRecordImpl<CryptoPaymentsRecord> implements Record9<Integer, Integer, Integer, Double, String, Integer, Double, Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.crypto_payments.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.crypto_payments.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.crypto_payments.crypto_wallet_id</code>.
     */
    public void setCryptoWalletId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.crypto_payments.crypto_wallet_id</code>.
     */
    public Integer getCryptoWalletId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.crypto_payments.status</code>.
     */
    public void setStatus(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.crypto_payments.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.crypto_payments.amount_crypto</code>.
     */
    public void setAmountCrypto(Double value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.crypto_payments.amount_crypto</code>.
     */
    public Double getAmountCrypto() {
        return (Double) get(3);
    }

    /**
     * Setter for <code>public.crypto_payments.request_id</code>.
     */
    public void setRequestId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.crypto_payments.request_id</code>.
     */
    public String getRequestId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.crypto_payments.destination_tag</code>.
     */
    public void setDestinationTag(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.crypto_payments.destination_tag</code>.
     */
    public Integer getDestinationTag() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.crypto_payments.paid_amount</code>.
     */
    public void setPaidAmount(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.crypto_payments.paid_amount</code>.
     */
    public Double getPaidAmount() {
        return (Double) get(6);
    }

    /**
     * Setter for <code>public.crypto_payments.delivered_payment_api_flg</code>.
     */
    public void setDeliveredPaymentApiFlg(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.crypto_payments.delivered_payment_api_flg</code>.
     */
    public Integer getDeliveredPaymentApiFlg() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>public.crypto_payments.session_id</code>.
     */
    public void setSessionId(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.crypto_payments.session_id</code>.
     */
    public String getSessionId() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, Integer, Integer, Double, String, Integer, Double, Integer, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Integer, Integer, Integer, Double, String, Integer, Double, Integer, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return CryptoPayments.CRYPTO_PAYMENTS.ID;
    }

    @Override
    public Field<Integer> field2() {
        return CryptoPayments.CRYPTO_PAYMENTS.CRYPTO_WALLET_ID;
    }

    @Override
    public Field<Integer> field3() {
        return CryptoPayments.CRYPTO_PAYMENTS.STATUS;
    }

    @Override
    public Field<Double> field4() {
        return CryptoPayments.CRYPTO_PAYMENTS.AMOUNT_CRYPTO;
    }

    @Override
    public Field<String> field5() {
        return CryptoPayments.CRYPTO_PAYMENTS.REQUEST_ID;
    }

    @Override
    public Field<Integer> field6() {
        return CryptoPayments.CRYPTO_PAYMENTS.DESTINATION_TAG;
    }

    @Override
    public Field<Double> field7() {
        return CryptoPayments.CRYPTO_PAYMENTS.PAID_AMOUNT;
    }

    @Override
    public Field<Integer> field8() {
        return CryptoPayments.CRYPTO_PAYMENTS.DELIVERED_PAYMENT_API_FLG;
    }

    @Override
    public Field<String> field9() {
        return CryptoPayments.CRYPTO_PAYMENTS.SESSION_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getCryptoWalletId();
    }

    @Override
    public Integer component3() {
        return getStatus();
    }

    @Override
    public Double component4() {
        return getAmountCrypto();
    }

    @Override
    public String component5() {
        return getRequestId();
    }

    @Override
    public Integer component6() {
        return getDestinationTag();
    }

    @Override
    public Double component7() {
        return getPaidAmount();
    }

    @Override
    public Integer component8() {
        return getDeliveredPaymentApiFlg();
    }

    @Override
    public String component9() {
        return getSessionId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getCryptoWalletId();
    }

    @Override
    public Integer value3() {
        return getStatus();
    }

    @Override
    public Double value4() {
        return getAmountCrypto();
    }

    @Override
    public String value5() {
        return getRequestId();
    }

    @Override
    public Integer value6() {
        return getDestinationTag();
    }

    @Override
    public Double value7() {
        return getPaidAmount();
    }

    @Override
    public Integer value8() {
        return getDeliveredPaymentApiFlg();
    }

    @Override
    public String value9() {
        return getSessionId();
    }

    @Override
    public CryptoPaymentsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value2(Integer value) {
        setCryptoWalletId(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value3(Integer value) {
        setStatus(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value4(Double value) {
        setAmountCrypto(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value5(String value) {
        setRequestId(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value6(Integer value) {
        setDestinationTag(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value7(Double value) {
        setPaidAmount(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value8(Integer value) {
        setDeliveredPaymentApiFlg(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord value9(String value) {
        setSessionId(value);
        return this;
    }

    @Override
    public CryptoPaymentsRecord values(Integer value1, Integer value2, Integer value3, Double value4, String value5, Integer value6, Double value7, Integer value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CryptoPaymentsRecord
     */
    public CryptoPaymentsRecord() {
        super(CryptoPayments.CRYPTO_PAYMENTS);
    }

    /**
     * Create a detached, initialised CryptoPaymentsRecord
     */
    public CryptoPaymentsRecord(Integer id, Integer cryptoWalletId, Integer status, Double amountCrypto, String requestId, Integer destinationTag, Double paidAmount, Integer deliveredPaymentApiFlg, String sessionId) {
        super(CryptoPayments.CRYPTO_PAYMENTS);

        setId(id);
        setCryptoWalletId(cryptoWalletId);
        setStatus(status);
        setAmountCrypto(amountCrypto);
        setRequestId(requestId);
        setDestinationTag(destinationTag);
        setPaidAmount(paidAmount);
        setDeliveredPaymentApiFlg(deliveredPaymentApiFlg);
        setSessionId(sessionId);
        resetChangedOnNotNull();
    }
}