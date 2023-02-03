"use client";

import { Dialog, Transition } from "@headlessui/react";
import { Fragment, useContext, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { AuthContext, useAuthContext } from "../../../contexts/AuthContext";
import { useGlobalModalContext } from "../../../contexts/ModalContext";
import LoginForm from "./loginform";
import RegistrationForm from "./registrationform";

export default function LoginModal() {
  const [registerMode, setRegisterMode] = useState(false);

  const { isAuthenticated } = useAuthContext();

  const { hideModal } = useGlobalModalContext();

  return (
    <Transition appear show={true} as={Fragment}>
      <Dialog as="div" className="relative z-10" onClose={hideModal}>
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
              <Dialog.Panel className="transform overflow-hidden bg-white p-6 align-middle shadow-xl transition-all border-t-8 border-y-red-600 w-80">
                <Dialog.Title
                  as="h3"
                  className="text-2xl font-extrabold leading-6 text-red-600 tracking-tight"
                >
                  CONTA j1
                </Dialog.Title>
                {!registerMode ? (
                  <>
                    <LoginForm />
                    <div className="text-sm mt-4 text-gray-700">
                      <span>Não tem conta?</span>
                      <button
                        className="ml-1 font-bold text-red-600"
                        onClick={() => setRegisterMode(true)}
                      >
                        CADASTRE-SE.
                      </button>
                    </div>
                  </>
                ) : (
                  <>
                    <RegistrationForm />
                    {!isAuthenticated ? (
                      <div className="text-sm mt-4 text-gray-700">
                        <span>Já tem uma conta?</span>
                        <button
                          className="ml-1 font-bold text-red-600"
                          onClick={() => setRegisterMode(false)}
                        >
                          ENTRE.
                        </button>
                      </div>
                    ) : null}
                  </>
                )}
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </div>
      </Dialog>
    </Transition>
  );
}
