import { AuthProvider } from "./features/auth/Authcontext";

export default function Providers({ children }: { children: React.ReactNode }) {
  return <AuthProvider>{children}</AuthProvider>;
}
