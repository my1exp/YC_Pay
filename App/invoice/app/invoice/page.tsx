import React from 'react'

import Content from './content';


interface User {
    id: number;
    name: string;
    email: string;
}

const Invoice = async () => {
    const res = await fetch('https://jsonplaceholder.typicode.com/users',
    { cache: 'no-store' })
    const users: User[] = await res.json()

  return (
    <>
        <Content />
    </>
  )
}

export default Invoice
