"use client";
import React, { useState, useEffect, useRef } from "react";

export default function OrderDetails() {
  const [activeCoin, setActiveCoin] = useState("XRP");

  const availableCurrencies = ["XRP", "BTC", "ETH"];

  let [showOpen, setShowOpen] = useState(false);

  return (
    <div className="items-start self-stretch border-b-[color:var(--Divider,#CFCFCF)] flex flex-col py-4 border-b border-solid">
      <h2 className="text-black text-base font-bold self-start whitespace-nowrap">
        Order details
      </h2>
      <div className="text-gray-600 text-sm mt-4 self-start whitespace-nowrap">
        Order price
      </div>
      <div className="items-center border border-[color:var(--Divider,#CFCFCF)] shadow-[0px_0px_2px_0px_rgba(0,0,0,0.25)] flex w-[408px] max-w-full flex-col mt-4 pr-5 py-1.5 rounded-xl border-solid self-start">
        <div className="flex w-[135px] max-w-full items-start gap-5 self-start">
          <div className="items-center border-r-[color:var(--Divider,#CFCFCF)] self-stretch flex flex-col px-5 py-2.5 border-r border-solid">
            <div className="text-gray-600 text-sm self-center whitespace-nowrap">
              USD
            </div>
          </div>
          <div className="text-gray-600 text-sm self-center my-auto whitespace-nowrap">
            2543
          </div>
        </div>
      </div>
      <div className="text-gray-600 text-sm mt-4 self-start whitespace-nowrap">
        Amount to pay in Crypto
      </div>

      <div className="items-center border border-[color:var(--Divider,#CFCFCF)] shadow-[0px_0px_2px_0px_rgba(0,0,0,0.25)] flex w-[408px] max-w-full flex-col mt-4 pr-5 py-1.5 rounded-xl border-solid self-start">
        <div className="flex w-[193px] max-w-full items-start gap-5 self-start">
          <div className="items-center border-r-[color:var(--Divider,#CFCFCF)] self-stretch flex flex-col px-5 py-2.5 border-r border-solid">
            <div className="text-gray-600 text-sm self-center whitespace-nowrap">
              <select
                onChange={(e) => {
                  setActiveCoin(e.target.value);
                }}
              >
                {availableCurrencies.map((currency) => (
                  <option key={currency}>{currency}</option>
                ))}
              </select>
            </div>
          </div>
          <div className="text-gray-600 text-sm self-center my-auto whitespace-nowrap">
            0.0023124141
          </div>
        </div>
      </div>
      <div className="text-gray-600 text-sm font-light mt-4 self-start whitespace-nowrap">
        Minimal amount to pay is
      </div>
    </div>
  );
}
