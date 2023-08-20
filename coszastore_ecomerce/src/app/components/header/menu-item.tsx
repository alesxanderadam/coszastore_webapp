"use client"
import { TOKEN_USER_MOVIE, settings } from '@/app/utils/config'
import { signOut } from 'next-auth/react'
import Link from 'next/link'
import React from 'react'
import { IconType } from 'react-icons/lib'

type Props = {
    titile: string,
    address: string,
    Icon: IconType,
}

const MenuItem = ({ titile, address, Icon }: Props) => {
    return (
        <div>
            {
                titile === "LOGOUT"
                    ?
                    <p className='flex flex-col items-center cursor-pointer group w-12 sm:w-20 hover:text-amber-600'
                        onClick={async () => {
                            await signOut();
                            settings.clearStorage(TOKEN_USER_MOVIE);
                        }}>
                        {titile}
                    </p>
                    :
                    <Link href={address} className='flex flex-col items-center cursor-pointer group w-12 sm:w-20 hover:text-amber-600'>
                        <Icon className='md:hidden text-2xl mx-4 group-hover:animate-bounce' />
                        <p className='hidden md:inline text-amber-600 tracking-widest text-xs font-bold'>{titile}</p>
                    </Link>
            }

        </div>
    )
}

export default MenuItem