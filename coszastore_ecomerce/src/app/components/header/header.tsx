"use client"
import './header.scss'
import React, { useCallback, useEffect, useState } from 'react'
import MenuItem from './menu-item'
import { AiOutlineContacts, AiOutlineHome, AiOutlineShop } from 'react-icons/ai'
import { BsHandbag } from 'react-icons/bs'
import { MdOutlineFeaturedPlayList, MdRoundaboutLeft } from 'react-icons/md'
import { PiGithubLogoBold } from 'react-icons/pi'
import Link from 'next/link'
import Image from 'next/image'
import { useSession } from 'next-auth/react'


type Props = {}
const TOP_OFFSET = 10;

const Header = (props: Props) => {
    const [showAccountMenu, setShowAccountMenu] = useState<boolean>(false)
    const [showBackGround, setShowBackGround] = useState<boolean>(false)
    const toggleAcountMenu = useCallback(() => {
        setShowAccountMenu((current) => !current)
    }, [])

    useEffect(() => {
        const handleScroll = () => {
            if (window.scrollY >= TOP_OFFSET) {
                setShowBackGround(true)
            } else {
                setShowBackGround(false)
            }
        }

        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', handleScroll);
        }
    }, []);
    const { data: session } = useSession();
    return (
        <>
            <div className='w-full fixed z-40'>
                <div className="top-bar">
                    <div className="flex justify-center md:justify-between items-center sm:px-20 h-[100%]">
                        <div className="left-top-bar hidden md:block">
                            Free shipping for standard order over $100
                        </div>
                        <div className="right-top-bar flex flex-wrap justify-center items-center">
                            <a href="#" className="flex-c-m trans-04 px-4">
                                Help &amp; FAQs
                            </a>
                            <a href="#" className="flex-c-m trans-04 px-4">
                                {session && session?.user ? "My account" : "Sign in"}
                            </a>
                            <a href="#" className="flex-c-m trans-04 px-4">
                                EN
                            </a>
                            <a href="#" className="flex-c-m trans-04 px-4">
                                USD
                            </a>
                            <a href="#" className="flex-c-m trans-04 px-4 py-[3.5px]">
                                <span className='flex items-center justify-center relative'>
                                    <BsHandbag className='md:hidden text-sm' />
                                    <b className='text-amber-600 text-[9px] absolute left-[89%] top-[-6px]'>12+</b>
                                </span>
                                <p className='hidden md:inline tracking-widest'>Cart</p>
                            </a>
                        </div>
                    </div>
                </div>

                <div className={`flex flex-col sm:flex-row justify-between items-center h-[4rem] transition duration-1000  ${showBackGround ? 'bg-amber-500 bg-opacity-80 shadow-xl' : ''}`}>
                    <div className='flex flex-grow justify-evenly max-w-xl'>
                        <MenuItem titile='HOME' address='/' Icon={AiOutlineHome} />
                        <MenuItem titile='SHOP' address='/shop' Icon={AiOutlineShop} />
                        <MenuItem titile='FEATURES' address='/feature' Icon={MdOutlineFeaturedPlayList} />
                        <MenuItem titile='BLOG' address='/blog' Icon={PiGithubLogoBold} />
                        <MenuItem titile='ABOUT' address='/about' Icon={MdRoundaboutLeft} />
                        <MenuItem titile='CONTACT' address='/contact' Icon={AiOutlineContacts} />
                    </div>
                    <div className='flex items-center justify-center sm:space-x-5 space-x-0 sm:me-4 sm:mt-0 mt-3'>
                        <Link href="#" className='hidden sm:inline'>
                            <h2 className='text-2xl'>
                                <span className='font-bold bg-amber-500 py-1 px-2 rounded-lg mr-1'>
                                    COSZA
                                </span>

                                <span className='text-xl hidden lg:inline'>
                                    STORE
                                </span>
                            </h2>
                        </Link>

                        <Image className='object-contain sm:hidden inline' src="https://links.papareact.com/ua6" alt='logo' width={200} height={100}></Image>
                    </div>
                </div>
            </div>
        </>

    )
}

export default Header
