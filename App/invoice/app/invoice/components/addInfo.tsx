import React from "react";

export default function AddInfo() {
  return (
    <div className="items-start self-stretch border-b-[color:var(--Divider,#CFCFCF)] flex grow flex-col py-5 border-b border-solid">
      <h2 className="text-black text-base font-bold self-start whitespace-nowrap">
        Important information
      </h2>
      <div className="text-gray-600 text-sm mt-5">
        Only send one payment per address, please do not send funds twice
        <br />
        <br />
        If rate changes during the payment, new rate will apply and the payment
        might be cancelled
      </div>
    </div>
  );
}
