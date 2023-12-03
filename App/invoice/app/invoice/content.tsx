"use client";
import React, { useState, useEffect } from "react";
import type { InferGetStaticPropsType, GetStaticProps } from "next";

export const getStaticProps = (async (context) => {
  let currency_map = new Map();
  const res = await fetch("http://127.0.0.1:8080/Supported_currencies", {
    method: "GET",
    redirect: "follow",
  })
    .then((response) => response.json())
    .then((data) => {
      data.map((item) => {
        let networks = [];
        item.networkList.forEach((e) => {
          networks.push(e.name);
        });
        currency_map.set(item.name, networks);
      });
    });
  return { props: { currency_map } };
}) satisfies GetStaticProps<{
  repo: Map<any, any>;
}>;

export default function content() {
  //Параметры передаваемые селлером при создании счета
  const randomNumberInRange = (min: number, max: number) => {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  };

  const [CurrencyMap, setCurrencyMap] = useState<any>(null);

  //Состояния фиата
  const [ActualFiatCurrency, setActualFiatCurrency] = useState<string>(null);
  const [ActualFiatPrice, setActualFiatPrice] = useState<number>(null);

  //Состояния крипто
  const [ActualCryptoCurrency, setActualCryptoCurrency] =
    useState<string>(null);
  const [ActualCryptoNetworks, setActualCryptoNetworks] =
    useState<Array<string>>(null);
  const [ActiveCryptoPrice, setActiveCryptoPrice] = useState<number>(null);

  const [IsConfirmed, setIsConfirmed] = useState<boolean>(false);

  //Инициализация от селлера
  useEffect(() => {
    setActualFiatCurrency("USD");
    setActualFiatPrice(randomNumberInRange(10, 5000));
  }, []);

  //Получаем список валют при рендере страницы
  useEffect(() => {
    let currency_map = new Map();
    console.log("Fetching currencies");
    fetch("http://127.0.0.1:8000/Supported_currencies", {
      method: "GET",
      next: { revalidate: 60 },
      redirect: "follow",
    })
      .then((response) => response.json())
      .then((data) => {
        data.map((item) => {
          let networks = [];
          item.networkList.forEach((e) => {
            networks.push(e.name);
          });
          currency_map.set(item.name, networks);
          setCurrencyMap(currency_map);
        });
      });
  }, []);

  //Получаем список валют при рендере страницы
  useEffect(() => {
    console.log(CurrencyMap);
  }, []);

  //Получаем новый прайс при смене селектора валюты
  useEffect(() => {
    var myHeaders = new Headers();
    myHeaders.append("currency_crypto", ActualCryptoCurrency);
    myHeaders.append("amount_fiat", ActualFiatPrice);
    myHeaders.append("currency_fiat", ActualFiatCurrency);

    fetch("http://127.0.0.1:8000/amountToPay", {
      method: "GET",
      headers: myHeaders,
      redirect: "follow",
    })
      .then((response) => response.json())
      .then((data) => {
        setActiveCryptoPrice(data.amount_crypto);
        console.log("Price fetched: " + data.amount_crypto);
      });
  }, [ActualCryptoCurrency]);

  function handleConfirmIntent() {
    setIsConfirmed(true);
  }

  //селектор монеты
  function handleChangeCurrency(e: React.ChangeEvent<HTMLInputElement>) {
    if (Array.from(CurrencyMap.keys()).includes(e.target.value)) {
      setActualCryptoCurrency(e.target.value);
      setActualCryptoNetworks(CurrencyMap.get(ActualCryptoCurrency));
    }
  }

  //селектор сети
  function handleChangeNetwork(e: React.ChangeEvent<HTMLInputElement>) {
    if (
      Array.from(CurrencyMap.get(ActualCryptoCurrency)).includes(e.target.value)
    ) {
      setActualCryptoNetwork(e.target.value);
    }
  }

  return (
    <body>
      <div className="container flex flex-col mx-auto justify-center items-center  rounded-lg pt-12 my-5">
        <div className="flex w-[651px] flex-col items-center shrink-0 shadow-[0px_10px_78.2px_-16px_rgba(0,0,0,0.25)] px-[100px] container py-5 rounded-[20px]">
          <header className="text-indigo-400 text-3xl font-bold whitespace-nowrap justify-center items-stretch border-b-[color:var(--Divider,#CFCFCF)] w-full max-w-[451px] mt-5 py-1.5 border-b border-solid max-md:max-w-full">
            Coinlink
          </header>
          <section className="items-stretch border-b-[color:var(--Divider,#CFCFCF)] flex w-full max-w-[451px] flex-col mt-2.5 py-4 border-b border-solid max-md:max-w-full">
            <div className="text-neutral-700 text-xl font-bold whitespace-nowrap max-md:max-w-full">
              Order details
            </div>
            <div className="items-stretch flex flex-col mt-4 max-md:max-w-full">
              <div className="text-gray-600 text-sm whitespace-nowrap max-md:max-w-full">
                Order price
              </div>
              <div className="items-stretch border border-[color:var(--Divider,#CFCFCF)] shadow-sm flex justify-between gap-5 mt-2.5 pr-80 py-1.5 rounded-xl border-solid max-md:max-w-full max-md:flex-wrap max-md:pr-5">
                <div className="text-zinc-400 text-base whitespace-nowrap items-center border-r-[color:var(--Divider,#CFCFCF)] grow px-5 py-2.5 border-r border-solid">
                  {ActualFiatCurrency}
                </div>
                <div className="text-zinc-400 text-sm self-center whitespace-nowrap my-auto">
                  {ActualFiatPrice}
                </div>
              </div>
            </div>
            <div className="items-stretch flex flex-col mt-4 max-md:max-w-full">
              <div className="text-gray-600 text-sm whitespace-nowrap max-md:max-w-full">
                Amount to pay in Crypto
              </div>
              <div className="flex items-center gap-2.5 flex-[1_0_0] border border-[color:var(--Divider,#CFCFCF)] shadow-[0px_0px_2px_0px_rgba(0,0,0,0.25)] px-0 py-[5px] rounded-[10px] border-solid">
                <div className="flex w-20 items-center gap-2.5 border-r-[color:var(--Divider,#CFCFCF)] px-4 py-[9px] border-r border-solid">
                  <select onChange={handleChangeCurrency}>
                    {CurrencyMap &&
                      Array.from(CurrencyMap.keys()).map((currency) => (
                        <option key={currency} value={currency}>
                          {currency}
                        </option>
                      ))}
                  </select>
                </div>
                <div className="text-gray-600 text-sm self-center whitespace-nowrap my-auto">
                  {ActiveCryptoPrice}
                </div>
              </div>
            </div>
            <div className="text-zinc-400 text-sm font-light whitespace-nowrap mt-4 max-md:max-w-full">
              Minimal amount to pay is
            </div>
          </section>
          <section className="items-stretch border-b-[color:var(--Divider,#CFCFCF)] flex w-full max-w-[451px] flex-col py-4 border-b border-solid max-md:max-w-full">
            <div className="text-neutral-700 text-xl font-bold whitespace-nowrap max-md:max-w-full">
              Exchange information
            </div>
            <div className="items-stretch flex justify-between gap-5 mt-2.5 py-1.5 max-md:max-w-full max-md:flex-wrap">
              <div className="items-stretch flex grow basis-[0%] flex-col">
                <div className="text-gray-600 text-sm whitespace-nowrap">
                  Current rate
                </div>
                <div className="text-gray-600 text-sm font-semibold whitespace-nowrap mt-1.5">
                  {"0.554989 " +
                    ActualCryptoCurrency +
                    "/" +
                    ActualFiatCurrency}
                </div>
              </div>
              <div className="items-stretch flex grow basis-[0%] flex-col">
                <div className="text-gray-600 text-sm whitespace-nowrap">
                  Fees
                </div>
                <div className="text-gray-600 text-sm font-semibold whitespace-nowrap mt-1.5">
                  {"0.001 " +
                    ActualCryptoCurrency +
                    "(0.56" +
                    ActualFiatCurrency +
                    ")"}
                </div>
              </div>
            </div>
            <div className="text-zinc-400 text-sm whitespace-nowrap mt-1.5 max-md:max-w-full">
              Additional fees may be imposed
            </div>
          </section>
          <section className="items-stretch flex w-full max-w-[451px] flex-col mt-4 mb-12 gap-2.5 max-md:max-w-full max-md:mb-10">
            <div className="flex flex-col items-start self-stretch">
              <div className="text-neutral-700 text-xl font-bold whitespace-nowrap max-md:max-w-full">
                Payment confirmation
              </div>
              <div className="text-gray-600 text-sm mt-2.5 max-md:max-w-full">
                Pressing the button below will freeze current payment options.
                The address will be provided.
              </div>
            </div>
            <button className="text-white text-base font-semibold whitespace-nowrap justify-center items-center bg-indigo-400 hover:bg-indigo-300 duration-[500ms,800ms] mt-4 px-5 py-3.5 rounded-xl max-md:max-w-full">
              onClick={}
              Confirm
            </button>
          </section>
        </div>
      </div>
    </body>
  );
}
