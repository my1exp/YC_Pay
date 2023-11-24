export default function getSupportedCurrencies() {
  let result = undefined;

  const res = fetch("http://127.0.0.1:8000/Supported_currencies", {
    method: "GET",
    redirect: "follow",
  })
    .then((response) => response.json())
    .then((data) => {
      return data;
    });
}
