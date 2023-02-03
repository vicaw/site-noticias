'use client';
import { PlusIcon } from '@heroicons/react/20/solid';
import { useRouter } from 'next/navigation';
import { useContext, useEffect, useRef } from 'react';
import { AuthContext } from '../../../contexts/AuthContext';
import { MODAL_TYPES, useGlobalModalContext } from '../../../contexts/ModalContext';
import ManageArticlesList from './managearticleslist';

export default function ManageArticles() {
  const { showModal } = useGlobalModalContext();
  const { isAuthenticated, user } = useContext(AuthContext);
  const router = useRouter();

  //useEffect(() => {
  // if (user == null) {
  // router.push("/");
  //}
  //}, [user]);

  return (
    <>
      {!isAuthenticated ? null : (
        <main className="p-6 sm:p-10 space-y-6">
          <ManageArticlesList />
        </main>
      )}
    </>
  );
}
