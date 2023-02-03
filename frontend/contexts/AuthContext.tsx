import { createContext, useContext, useEffect, useState } from "react";
import { setCookie, parseCookies, destroyCookie } from "nookies";

import accountServices from "../services/AccountServices";
import { api } from "../services/axios/api";
import parseJwt from "../utils/parseJwt";
import User from "../models/User";

type AuthContextType = {
  isAuthenticated: boolean;
  user: User | null;
  setSession: (token: string, user: User) => void;
  signOut: () => void;
};

export const AuthContext = createContext({} as AuthContextType);
export const useAuthContext = () => useContext(AuthContext);

export function AuthProvider({ children }: any) {
  const [user, setUser] = useState<User | null>(null);

  const isAuthenticated = !!user;

  useEffect(() => {
    const { "nextauth.token": token } = parseCookies();

    if (token) {
      const parsedToken = parseJwt(token);
      console.log(parsedToken);
      accountServices
        .recoverUserInformation(parsedToken.sub)
        .then((response) => {
          setUser(response);
        });
    }
  }, []);

  function setSession(token: string, user: User) {
    setCookie(undefined, "nextauth.token", token, {
      maxAge: 60 * 60 * 1, // 1 hour
    });

    api.defaults.headers["Authorization"] = `Bearer ${token}`;

    setUser(user);
  }

  function signOut() {
    destroyCookie(undefined, "nextauth.token");
    setUser(null);
  }

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated, signOut, setSession }}
    >
      {children}
    </AuthContext.Provider>
  );
}
