// src/features/loans/UserDashboard.tsx
import { useEffect, useState } from "react";
import { getMyLoans, type Loan } from "./api";
import LoanForm from "./LoanForm";

export default function UserDashboard() {
  const [items, setItems] = useState<Loan[]>([]);
  const load = async () => setItems(await getMyLoans());
  useEffect(() => { load(); }, []);

  const getStatusBadge = (status: string) => {
    const baseClasses = "px-2 py-1 text-xs font-semibold rounded-full";
    switch (status) {
      case "APPROVED":
        return `${baseClasses} bg-green-100 text-green-800`;
      case "REJECTED":
        return `${baseClasses} bg-red-100 text-red-800`;
      case "REQUESTED":
        return `${baseClasses} bg-yellow-100 text-yellow-800`;
      default:
        return `${baseClasses} bg-gray-100 text-gray-800`;
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">Panel de Usuario</h1>
          <p className="mt-2 text-gray-600">Gestiona tus préstamos y solicitudes</p>
        </div>

        <LoanForm onCreated={load} />

        <div className="bg-white shadow rounded-lg">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-900">Mis Préstamos</h2>
          </div>
          <div className="divide-y divide-gray-200">
            {items.length === 0 ? (
              <div className="px-6 py-8 text-center">
                <p className="text-gray-500">No tienes préstamos registrados aún.</p>
              </div>
            ) : (
              items.map((l) => (
                <div key={l.id} className="px-6 py-4 flex items-center justify-between hover:bg-gray-50">
                  <div className="flex-1">
                    <div className="flex items-center space-x-4">
                      <div>
                        <p className="text-lg font-semibold text-gray-900">${l.amount}</p>
                        <p className="text-sm text-gray-500">{l.termMonths} meses</p>
                      </div>
                    </div>
                  </div>
                  <div className="flex items-center space-x-4">
                    <span className={getStatusBadge(l.status)}>
                      {l.status === "APPROVED" && "Aprobado"}
                      {l.status === "REJECTED" && "Rechazado"}
                      {l.status === "REQUESTED" && "Pendiente"}
                    </span>
                    <div className="text-right">
                      <p className="text-sm text-gray-500">
                        {new Date(l.createdAt).toLocaleDateString()}
                      </p>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
