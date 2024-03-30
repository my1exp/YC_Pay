package com.yc_pay.service;

import com.google.common.primitives.UnsignedInteger;
import com.yc_pay.model.DetailsForSendXrp;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.crypto.keys.Entropy;
import org.xrpl.xrpl4j.crypto.keys.PrivateKey;
import org.xrpl.xrpl4j.crypto.keys.Seed;
import org.xrpl.xrpl4j.crypto.signing.SignatureService;
import org.xrpl.xrpl4j.crypto.signing.SingleSignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.bc.BcSignatureService;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Singleton
public class CryptoTxService {

    @SneakyThrows
    public static void XrpSendToColdWallet(DetailsForSendXrp detailsForSendXrp) {

        String addressTo = detailsForSendXrp.getAddressTo();
        String privateKeyAddressFrom = detailsForSendXrp.getPrivateKeyAddressFrom();
        Double AmountToSend = detailsForSendXrp.getAmountToSend();

        HttpUrl rippledUrl = HttpUrl.get("https://xrplcluster.com/");
        XrplClient xrplClient = new XrplClient(rippledUrl);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String[] pk = privateKeyAddressFrom.substring(1).replaceAll("]", "").split(", ");

        for (int i = 0; i < pk.length; i++) {
            output.write(Integer.parseInt(pk[i]));
        }
        byte[] out = output.toByteArray();

        Entropy entropy = Entropy.of(out);
        Seed seed = Seed.secp256k1SeedFromEntropy(entropy);


        // Prepare transaction --------------------------------------------------------
        // Look up your Account Info
        AccountInfoRequestParams requestParams = AccountInfoRequestParams.builder()
                .account(Address.of(seed.deriveKeyPair().publicKey().deriveAddress().toString()))
                .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                .build();
        AccountInfoResult accountInfoResult = xrplClient.accountInfo(requestParams);
        UnsignedInteger sequence = accountInfoResult.accountData().sequence();

        // Request current fee information from rippled
        FeeResult feeResult = xrplClient.fee();
        XrpCurrencyAmount openLedgerFee = feeResult.drops().openLedgerFee();

        // Get the latest validated ledger index
        LedgerIndex validatedLedger = xrplClient.ledger(
                        LedgerRequestParams.builder()
                                .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                                .build()
                )
                .ledgerIndex()
                .orElseThrow(() -> new RuntimeException("LedgerIndex not available."));

        // LastLedgerSequence is the current ledger index + 4
        UnsignedInteger lastLedgerSequence = validatedLedger.plus(UnsignedInteger.valueOf(4)).unsignedIntegerValue();

        Payment payment = Payment.builder()
                .account(Address.of(seed.deriveKeyPair().publicKey().deriveAddress().toString()))
                .amount(XrpCurrencyAmount.ofXrp(BigDecimal.valueOf(AmountToSend - 0.00001).setScale(6, RoundingMode.CEILING)))
                .destination(Address.of(addressTo))
                .sequence(sequence)
                .fee(openLedgerFee)
                .signingPublicKey(seed.deriveKeyPair().publicKey())
                .lastLedgerSequence(lastLedgerSequence)
                .build();
//        System.out.println("Constructed Payment: " + payment);

        // Sign transaction -----------------------------------------------------------
        // Construct a SignatureService to sign the Payment
        SignatureService<PrivateKey> signatureService = new BcSignatureService();

        // Sign the Payment
        SingleSignedTransaction<Payment> signedPayment = signatureService.sign(seed.deriveKeyPair().privateKey(), payment);
        System.out.println("Signed Payment: " + signedPayment.signedTransaction());

        // Submit transaction ---------------------------------------------------------
        SubmitResult<Payment> paymentSubmitResult = xrplClient.submit(signedPayment);
        System.out.println(paymentSubmitResult);
    }
}
