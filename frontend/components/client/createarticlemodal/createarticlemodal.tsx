'use client';

import { Dialog, Transition } from '@headlessui/react';
import { Fragment, useContext, useState } from 'react';
import { AuthContext } from '../../../contexts/AuthContext';
import { useGlobalModalContext } from '../../../contexts/ModalContext';
import CreateArticleForm from './createarticleform';

export default function CreateArticleModal() {
  const [registerMode, setRegisterMode] = useState(false);

  const { isAuthenticated } = useContext(AuthContext);
  const { hideModal, store } = useGlobalModalContext();
  const { modalProps } = store || {};

  const handleModalToggle = () => {
    hideModal();
  };

  return (
    <Transition appear show={true} as={Fragment}>
      <Dialog as="div" className="relative z-10" onClose={handleModalToggle}>
        <Transition.Child
          as={Fragment}
          enter="ease-out duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in duration-200"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black bg-opacity-25" />
        </Transition.Child>

        <div className="fixed inset-0 overflow-y-auto">
          <div className="flex min-h-full items-center justify-center p-4 text-center">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 scale-95"
              enterTo="opacity-100 scale-100"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 scale-100"
              leaveTo="opacity-0 scale-95"
            >
              <Dialog.Panel className="transform overflow-hidden bg-white p-6 align-middle shadow-xl transition-all border-t-8 border-y-red-600 w-4/5">
                <Dialog.Title
                  as="h3"
                  className="text-2xl font-extrabold leading-6 text-red-600 tracking-tight"
                >
                  {!modalProps.article ? 'CRIAR NOTÍCIA' : 'EDITAR NOTÍCIA'}
                </Dialog.Title>
                <CreateArticleForm
                  article={modalProps.article}
                  addArticle={modalProps.addArticle}
                />
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </div>
      </Dialog>
    </Transition>
  );
}
