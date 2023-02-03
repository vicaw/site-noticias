'use client';

import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import { useAuthContext } from '../../../contexts/AuthContext';
import PainelHeaderUserInfo from './headeruserinfo';

export default function PainelHeader() {
  const { signOut } = useAuthContext();
  const router = useRouter();

  const handleSignOut = () => {
    signOut();
    router.push('/');
  };

  return (
    <header className="flex items-center h-20 px-6 sm:px-10 bg-white">
      <div className="flex flex-shrink-0 items-center ml-auto">
        <PainelHeaderUserInfo />
        <div className="border-l pl-3 ml-3 space-x-1">
          <button
            onClick={handleSignOut}
            className="relative p-2 text-gray-400 hover:bg-gray-100 hover:text-gray-600 focus:bg-gray-100 focus:text-gray-600 rounded-full"
          >
            <span className="sr-only">Log out</span>
            <svg
              aria-hidden="true"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              className="h-6 w-6"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
              />
            </svg>
          </button>
        </div>
      </div>
    </header>
  );
}
