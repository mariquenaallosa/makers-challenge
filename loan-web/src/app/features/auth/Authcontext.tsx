// src/features/auth/AuthContext.tsx
import { createContext, useContext, useEffect, useState } from "react";
import { setAuthToken } from "../shared/lib/axios";


type AuthState = { token: string | null; roles: string[] };
type CtxType = { auth: AuthState; setAuth: (s: AuthState) => void; logout: () => void };

const Ctx = createContext<CtxType>({ auth: { token: null, roles: [] }, setAuth: () => {}, logout: () => {} });
export const useAuth = () => useContext(Ctx);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [auth, setAuth] = useState<AuthState>(() => {
    const raw = localStorage.getItem("auth");
    return raw ? JSON.parse(raw) : { token: null, roles: [] };
  });

  useEffect(() => {
    localStorage.setItem("auth", JSON.stringify(auth));
    setAuthToken(auth.token);
  }, [auth]);

  const logout = () => setAuth({ token: null, roles: [] });

  return <Ctx.Provider value={{ auth, setAuth, logout }}>{children}</Ctx.Provider>;
}
