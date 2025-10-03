import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../../auth/Authcontext";


export default function PrivateRoute({ role }: { role?: "ADMIN" | "USER" }) {
  const { auth } = useAuth();
  
  // MODO DESARROLLO: Desactivar autenticación temporalmente
  const DEVELOPMENT_MODE = true;
  
  if (DEVELOPMENT_MODE) {
    return <Outlet />;
  }
  
  // Código de autenticación normal
  if (!auth.token) return <Navigate to="/login" replace />;
  if (role && !auth.roles.includes(role)) return <Navigate to="/" replace />;
  return <Outlet />;
}
