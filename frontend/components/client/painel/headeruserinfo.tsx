'use client';

import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import { useAuthContext } from '../../../contexts/AuthContext';

export default function PainelHeaderUserInfo() {
  const { isAuthenticated, user, signOut } = useAuthContext();
  const router = useRouter();

  return (
    <>
      {user ? (
        <button className="inline-flex items-center p-2 hover:bg-gray-100 focus:bg-gray-100 rounded-lg">
          <span className="sr-only">{user.name}</span>
          <div className="hidden md:flex md:flex-col md:items-end md:leading-tight">
            <span className="font-semibold">{user.name}</span>
            <span className="text-sm text-gray-600">{user.role}</span>
          </div>
          <span className="h-12 w-12 ml-2 sm:ml-3 mr-2 bg-gray-100 rounded-full overflow-hidden">
            <svg viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
              <path
                d="M10 0C4.48 0 0 4.48 0 10s4.48 10 10 10 10-4.48 10-10S15.52 0 10 0zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"
                fillRule="nonzero"
                fill="#333"
              ></path>
            </svg>
          </span>
        </button>
      ) : null}
    </>
  );
}
