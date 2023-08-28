"use client"
import { TOKEN_USER_MOVIE, settings } from '@/app/utils/config'
import { signOut } from 'next-auth/react'
import Link from 'next/link'
import React, {useEffect, useState} from 'react'
import { IconType } from 'react-icons/lib'

type Props = {
    titile: string,
    address: string,
    Icon: IconType,
}

const MenuItem = ({ titile, address, Icon }: Props) => {
    const [showBackGround, setShowBackGround] = useState<boolean>(false)

    useEffect(() => {
        const handleScroll = () => {
            if (window.scrollY >= 10) {
                setShowBackGround(true)
            } else {
                setShowBackGround(false)
            }
        }
        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', handleScroll);
        }
    },[])
    return (
        <div>
            {
                    <Link href={address} className={`flex flex-col items-center cursor-pointer group w-12 sm:w-20 hover:text-amber-600`}>
                        <Icon className='md:hidden text-2xl mx-4 group-hover:animate-bounce' />
                        <p className={`hidden md:inline ${showBackGround ? "text-black" :  "text-amber-600"} tracking-widest text-xs font-bold`}>{titile}</p>
                    </Link>
            }
        </div>
    )
}

export default MenuItem
