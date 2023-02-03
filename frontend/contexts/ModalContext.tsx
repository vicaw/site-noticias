import React, { useState, createContext, useContext } from "react";
import LoginModal from "../components/client/loginmodal/loginmodal";
import CreateArticleModal from "../components/client/createarticlemodal/createarticlemodal";

//import { CreateModal, DeleteModal,UpdateModal } from './components';

export const MODAL_TYPES = {
  LOGIN_MODAL: "LOGIN_MODAL",
  CREATEARTICLE_MODAL: "CREATEARTICLE_MODAL",
  //DELETE_MODAL: “DELETE_MODAL”,
  //UPDATE_MODAL: “UPDATE_MODAL”
};

const MODAL_COMPONENTS: any = {
  [MODAL_TYPES.LOGIN_MODAL]: LoginModal,
  [MODAL_TYPES.CREATEARTICLE_MODAL]: CreateArticleModal,
  //[MODAL_TYPES.DELETE_MODAL]: DeleteModal,
  //[MODAL_TYPES.UPDATE_MODAL]: UpdateModal
};

type GlobalModalContext = {
  showModal: (modalType: string, modalProps?: any) => void;
  hideModal: () => void;
  store: any;
};

const initalState: GlobalModalContext = {
  showModal: () => {},
  hideModal: () => {},
  store: {},
};

const GlobalModalContext = createContext(initalState);
export const useGlobalModalContext = () => useContext(GlobalModalContext);

export default function GlobalModalProvider({ children }: any) {
  const [store, setStore] = useState({ isOpen: false } as any);
  const { modalType, modalProps } = store;

  const showModal = (modalType: string, modalProps: any = {}) => {
    setStore({
      ...store,
      modalType,
      modalProps,
    });
  };

  const hideModal = () => {
    setStore({
      ...store,
      modalType: null,
      modalProps: {},
    });
  };

  const renderComponent = () => {
    const ModalComponent = MODAL_COMPONENTS[modalType];
    if (!modalType || !ModalComponent) {
      return null;
    }

    return <ModalComponent id="global-modal" {...modalProps} />;
  };

  return (
    <GlobalModalContext.Provider value={{ store, showModal, hideModal }}>
      {renderComponent()}
      {children}
    </GlobalModalContext.Provider>
  );
}
