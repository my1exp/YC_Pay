'use client'
import React, { useState } from "react";

function DropdownMenu() {
  const [dropdownMenuActive, setDropdownMenuActive] = useState(false);

  return (
    <div
      className="flex flex-col items-stretch relative shrink-0 box-border h-[50px] w-[100px] mr-auto"
      onMouseEnter={() => setDropdownMenuActive(true)}
      onMouseLeave={() => setDropdownMenuActive(false)}
    >
      <header className="items-stretch relative shrink-0 box-border h-auto text-center mt-4 mb-auto">
        <h2>Dropdown</h2>
      </header>
      <div
        className={`flex flex-col items-stretch relative shrink-0 box-border h-auto w-[200px] transition-all duration-[0.4s] ease-[cubic-bezier(.37,0.01,0,0.98)] shadow-[0_1px_5px_0_rgba(0,0,0,0.19)] z-[5] bg-white rounded mt-4 ${
          dropdownMenuActive ? "opacity-100" : "opacity-0"
        }`}
        style={{
          transform: dropdownMenuActive ? "none" : "translate3d(0, 10px, 0)",
          visibility: dropdownMenuActive ? "visible" : "hidden",
        }}
      >
        <a href="/sub1" className="items-stretch relative shrink-0 box-border h-[30px] text-center w-full cursor-pointer pointer-events-auto pt-2">
          <p>Sub 1</p>
        </a>
        <a href="/sub2" className="items-stretch relative shrink-0 box-border h-[30px] text-center w-full cursor-pointer pointer-events-auto pt-2">
          <p>Sub 2</p>
        </a>
        <a href="/sub3" className="items-stretch relative shrink-0 box-border h-[30px] text-center w-full cursor-pointer pointer-events-auto pt-2">
          <p>Sub 3</p>
        </a>
      </div>
    </div>
  );
}

export default DropdownMenu;
