import '../../styles/globals.css';
import Header from '../../components/client/header/header';
import { Suspense } from 'react';

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <Header />
      <Suspense fallback={<div className="absolute right-1/2 bottom-1/2">loading...</div>}>
        {children}
      </Suspense>
    </>
  );
}
