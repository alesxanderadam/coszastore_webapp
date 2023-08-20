import './globals.css'
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import AuthProvider from './components/auth-provider/auth-provider'
import { ReduxProviders } from './global-redux/provider'

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
            {children}
          </ReduxProviders>
        </AuthProvider>
      </body>
    </html>
  )
}
