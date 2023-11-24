import React from "react";

export default function PayAddress() {
  return (
    <div className="items-start self-stretch border-b-[color:var(--Divider,#CFCFCF)] flex flex-col pr-5 py-5 border-b border-solid">
      <h2 className="text-black text-base font-bold self-start whitespace-nowrap">
        Address to send fund to
      </h2>
      <div className="text-gray-600 text-sm mt-5 self-start whitespace-nowrap">
        bc1qtet8c5qvfu9ee0ez8cmcaw7ahxeqvke55fpzju
      </div>
      <div className="items-start flex w-[161px] max-w-full flex-col mt-6 self-start">
        <div className="text-gray-600 text-sm self-start whitespace-nowrap">
          The invoice will expire in
        </div>
        <div className="text-gray-600 text-sm mt-1.5 self-start whitespace-nowrap">
          10:50:11
        </div>
      </div>
    </div>
  );
}
