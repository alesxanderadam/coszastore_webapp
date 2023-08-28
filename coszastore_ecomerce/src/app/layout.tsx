import './globals.css'
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import AuthProvider from './components/auth-provider/auth-provider'
import { ReduxProviders } from './global-redux/provider'
import SideBar from "@/app/components/sidebar/side-bar";
import Header from "@/app/components/header/header";

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'COSZA STORE',
  description: 'We are legend',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <AuthProvider>
          <ReduxProviders>
              <Header />
              <SideBar />
              {children}
          </ReduxProviders>
        </AuthProvider>
      </body>
    </html>
  )
}

