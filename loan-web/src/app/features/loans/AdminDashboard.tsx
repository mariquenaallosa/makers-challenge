// src/features/loans/AdminDashboard.tsx
import { useEffect, useState } from "react";
import { approveLoan, rejectLoan, type Loan } from "./api";
import { api } from "../shared/lib/axios";

export default function AdminDashboard() {
  const [pending, setPending] = useState<Loan[]>([]);
  const load = async () => {
    const { data } = await api.get<Loan[]>("/loans?status=REQUESTED"); // agrega filtro en backend
    setPending(data);
  };
  useEffect(() => { load(); }, []);

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">Panel de Administración</h1>
          <p className="mt-2 text-gray-600">Gestiona las solicitudes de préstamos pendientes</p>
        </div>

        <div className="bg-white shadow rounded-lg">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-xl font-semibold text-gray-900">
              Solicitudes Pendientes ({pending.length})
            </h2>
          </div>
          
          <div className="divide-y divide-gray-200">
            {pending.length === 0 ? (
              <div className="px-6 py-8 text-center">
                <div className="text-gray-400 text-6xl mb-4">📋</div>
                <p className="text-gray-500 text-lg">No hay solicitudes pendientes</p>
                <p className="text-gray-400 text-sm mt-2">Todas las solicitudes han sido procesadas</p>
              </div>
            ) : (
              pending.map((l) => (
                <div key={l.id} className="px-6 py-6 hover:bg-gray-50">
                  <div className="flex items-center justify-between">
                    <div className="flex-1">
                      <div className="flex items-center space-x-6">
                        <div className="flex-shrink-0">
                          <div className="w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center">
                            <span className="text-blue-600 font-semibold text-sm">#{l.id}</span>
                          </div>
                        </div>
                        <div>
                          <h3 className="text-lg font-semibold text-gray-900">
                            ${parseFloat(l.amount).toLocaleString()}
                          </h3>
                          <p className="text-sm text-gray-500">
                            Plazo: {l.termMonths} meses
                          </p>
                          <p className="text-xs text-gray-400 mt-1">
                            Solicitado: {new Date(l.createdAt).toLocaleDateString()}
                          </p>
                        </div>
                      </div>
                    </div>
                    
                    <div className="flex items-center space-x-3">
                      <button
                        onClick={async () => { 
                          await approveLoan(l.id); 
                          load(); 
                        }}
                        className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md text-sm font-medium focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 transition duration-150 ease-in-out"
                      >
                        ✓ Aprobar
                      </button>
                      <button
                        onClick={async () => { 
                          await rejectLoan(l.id); 
                          load(); 
                        }}
                        className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm font-medium focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2 transition duration-150 ease-in-out"
                      >
                        ✗ Rechazar
                      </button>
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
