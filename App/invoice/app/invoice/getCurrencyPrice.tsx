export default function getCurrencyPrice(
  currencyCrypto: string,
  amountFiat: string,
  currencyFiat: string
) {
  let result = undefined;
  var myHeaders = new Headers();
  myHeaders.append("currencyCrypto", currencyCrypto);
  myHeaders.append("amountFiat", amountFiat);
  myHeaders.append("currencyFiat", currencyFiat);

  const res = fetch("http://127.0.0.1:8000/price", {
    method: "GET",
    headers: myHeaders,
    redirect: "follow",
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      result = data;
    });
  return result;
}
