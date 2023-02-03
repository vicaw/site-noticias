import '../../styles/globals.css';
import Header from '../../components/client/header/header';
import { Suspense } from 'react';
import PainelHeader from '../../components/client/painel/painelheader';
import PainelAside from '../../components/client/painel/painelnav';

export default function PainelLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex bg-gray-100 min-h-screen">
      <PainelAside />
      <div className="flex-grow text-gray-800">{children}</div>
    </div>
  );
}

//<Header categories={categories} />
