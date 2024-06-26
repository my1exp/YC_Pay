/*
 * This file is generated by jOOQ.
 */
package com.yc_pay.model.dbModels.generated.tables.records;


import com.yc_pay.model.dbModels.generated.tables.TransactionsHash;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TransactionsHashRecord extends UpdatableRecordImpl<TransactionsHashRecord> implements Record8<String, Integer, Integer, Double, Integer, Integer, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.transactions_hash.hash</code>.
     */
    public void setHash(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.transactions_hash.hash</code>.
     */
    public String getHash() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.transactions_hash.crypto_wallet_id</code>.
     */
    public void setCryptoWalletId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.transactions_hash.crypto_wallet_id</code>.
     */
    public Integer getCryptoWalletId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.transactions_hash.payment_id</code>.
     */
    public void setPaymentId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.transactions_hash.payment_id</code>.
     */
    public Integer getPaymentId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.transactions_hash.received_amount</code>.
     */
    public void setReceivedAmount(Double value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.transactions_hash.received_amount</code>.
     */
    public Double getReceivedAmount() {
        return (Double) get(3);
    }

    /**
     * Setter for <code>public.transactions_hash.withdraw_flg</code>.
     */
    public void setWithdrawFlg(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.transactions_hash.withdraw_flg</code>.
     */
    public Integer getWithdrawFlg() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.transactions_hash.delivered_tx_serv_flg</code>.
     */
    public void setDeliveredTxServFlg(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.transactions_hash.delivered_tx_serv_flg</code>.
     */
    public Integer getDeliveredTxServFlg() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.transactions_hash.sender</code>.
     */
    public void setSender(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.transactions_hash.sender</code>.
     */
    public String getSender() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.transactions_hash.return_to_sender</code>.
     */
    public void setReturnToSender(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.transactions_hash.return_to_sender</code>.
     */
    public Integer getReturnToSender() {
        return (Integer) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<String, Integer, Integer, Double, Integer, Integer, String, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<String, Integer, Integer, Double, Integer, Integer, String, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return TransactionsHash.TRANSACTIONS_HASH.HASH;
    }

    @Override
    public Field<Integer> field2() {
        return TransactionsHash.TRANSACTIONS_HASH.CRYPTO_WALLET_ID;
    }

    @Override
    public Field<Integer> field3() {
        return TransactionsHash.TRANSACTIONS_HASH.PAYMENT_ID;
    }

    @Override
    public Field<Double> field4() {
        return TransactionsHash.TRANSACTIONS_HASH.RECEIVED_AMOUNT;
    }

    @Override
    public Field<Integer> field5() {
        return TransactionsHash.TRANSACTIONS_HASH.WITHDRAW_FLG;
    }

    @Override
    public Field<Integer> field6() {
        return TransactionsHash.TRANSACTIONS_HASH.DELIVERED_TX_SERV_FLG;
    }

    @Override
    public Field<String> field7() {
        return TransactionsHash.TRANSACTIONS_HASH.SENDER;
    }

    @Override
    public Field<Integer> field8() {
        return TransactionsHash.TRANSACTIONS_HASH.RETURN_TO_SENDER;
    }

    @Override
    public String component1() {
        return getHash();
    }

    @Override
    public Integer component2() {
        return getCryptoWalletId();
    }

    @Override
    public Integer component3() {
        return getPaymentId();
    }

    @Override
    public Double component4() {
        return getReceivedAmount();
    }

    @Override
    public Integer component5() {
        return getWithdrawFlg();
    }

    @Override
    public Integer component6() {
        return getDeliveredTxServFlg();
    }

    @Override
    public String component7() {
        return getSender();
    }

    @Override
    public Integer component8() {
        return getReturnToSender();
    }

    @Override
    public String value1() {
        return getHash();
    }

    @Override
    public Integer value2() {
        return getCryptoWalletId();
    }

    @Override
    public Integer value3() {
        return getPaymentId();
    }

    @Override
    public Double value4() {
        return getReceivedAmount();
    }

    @Override
    public Integer value5() {
        return getWithdrawFlg();
    }

    @Override
    public Integer value6() {
        return getDeliveredTxServFlg();
    }

    @Override
    public String value7() {
        return getSender();
    }

    @Override
    public Integer value8() {
        return getReturnToSender();
    }

    @Override
    public TransactionsHashRecord value1(String value) {
        setHash(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value2(Integer value) {
        setCryptoWalletId(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value3(Integer value) {
        setPaymentId(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value4(Double value) {
        setReceivedAmount(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value5(Integer value) {
        setWithdrawFlg(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value6(Integer value) {
        setDeliveredTxServFlg(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value7(String value) {
        setSender(value);
        return this;
    }

    @Override
    public TransactionsHashRecord value8(Integer value) {
        setReturnToSender(value);
        return this;
    }

    @Override
    public TransactionsHashRecord values(String value1, Integer value2, Integer value3, Double value4, Integer value5, Integer value6, String value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TransactionsHashRecord
     */
    public TransactionsHashRecord() {
        super(TransactionsHash.TRANSACTIONS_HASH);
    }

    /**
     * Create a detached, initialised TransactionsHashRecord
     */
    public TransactionsHashRecord(String hash, Integer cryptoWalletId, Integer paymentId, Double receivedAmount, Integer withdrawFlg, Integer deliveredTxServFlg, String sender, Integer returnToSender) {
        super(TransactionsHash.TRANSACTIONS_HASH);

        setHash(hash);
        setCryptoWalletId(cryptoWalletId);
        setPaymentId(paymentId);
        setReceivedAmount(receivedAmount);
        setWithdrawFlg(withdrawFlg);
        setDeliveredTxServFlg(deliveredTxServFlg);
        setSender(sender);
        setReturnToSender(returnToSender);
        resetChangedOnNotNull();
    }
}
