"use client";

import { AuthProvider } from "../contexts/AuthContext";
import GlobalModalProvider from "../contexts/ModalContext";

export default function Providers({ children }: any) {
  return (
    <AuthProvider>
      <GlobalModalProvider>{children}</GlobalModalProvider>
    </AuthProvider>
  );
}
