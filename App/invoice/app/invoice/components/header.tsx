import React from 'react'

export default function Header() {
  return (
    <div className="justify-center items-center border-b-[color:var(--Divider,#CFCFCF)] self-stretch flex flex-col pl-1 pr-5 py-8 border-b border-solid max-md:max-w-full">
        <div className="items-start flex w-[528px] max-w-full justify-between gap-5 self-start max-md:flex-wrap max-md:justify-center">
        <h1 className="text-black text-2xl font-bold self-stretch">Coinlink</h1>
        <div className="text-gray-600 text-base my-auto">Order #77431582</div>
        <a href="https://www.adobe.com" className="text-gray-600 text-base self-center my-auto whitespace-nowrap">adobe.com</a>
        </div>
    </div>
  )
}
