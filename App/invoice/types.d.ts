type TransactionOptions = {
    CurrencyFiat: string;
    FiatPrice: number;
  };

type InvoiceOptions = {
    ActiveCoin: string;
    ActiveCoinPrice: string;
    ActiveFiatPrice: string;
    CurrencyFiat: string;
  };

type PriceResponse = {
    "amountCrypto": number
}
