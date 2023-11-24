import React from "react";

export default function ChngInformation() {
  return (
    <div className="items-start self-stretch border-b-[color:var(--Divider,#CFCFCF)] flex flex-col pr-5 py-5 border-b border-solid">
      <h2 className="text-black text-base font-bold self-start whitespace-nowrap">
        Exchange information
      </h2>
      <div className="items-start flex w-[294px] max-w-full gap-5 mt-4 py-1.5 self-start">
        <div className="items-start self-stretch flex flex-col">
          <div className="text-gray-600 text-sm self-start whitespace-nowrap">
            Current rate
          </div>
          <div className="text-gray-600 text-sm mt-1.5 self-start whitespace-nowrap">
            0.554989 XRP/USD
          </div>
        </div>
        <div className="items-start self-stretch flex flex-col">
          <div className="text-gray-600 text-sm self-start whitespace-nowrap">
            Fees
          </div>
          <div className="text-gray-600 text-sm mt-1.5 self-start whitespace-nowrap">
            0.001 XRP (0.56 USD)
          </div>
        </div>
      </div>
      <div className="text-gray-600 text-sm mt-5 self-start whitespace-nowrap">
        Additional fees may be imposed
      </div>
    </div>
  );
}
