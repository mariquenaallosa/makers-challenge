

import { createBrowserRouter, RouterProvider } from "react-router-dom";
import LoginPage from "./app/features/auth/LoginPage";
import AdminDashboard from "./app/features/loans/AdminDashboard";
import UserDashboard from "./app/features/loans/UserDashboard";
import PrivateRoute from "./app/features/shared/components/PrivateRoute";


const router = createBrowserRouter([
  { path: "/login", element: <LoginPage /> },
  { element: <PrivateRoute />, children: [{ path: "/", element: <UserDashboard /> }] },
  { element: <PrivateRoute role="ADMIN" />, children: [{ path: "/admin", element: <AdminDashboard /> }] },
]);

function App() {
   return <RouterProvider router={router} />;
}

export default App
