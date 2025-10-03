// src/features/loans/LoanForm.tsx
import { useForm } from "react-hook-form";
import { createLoan } from "./api";

type F = { amount: number; termMonths: number };
export default function LoanForm({ onCreated }: { onCreated: () => void }) {
  const { register, handleSubmit, reset } = useForm<F>();
  return (
    <div className="bg-white p-6 rounded-lg shadow-md mb-8">
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Solicitar nuevo préstamo</h3>
      <form
        onSubmit={handleSubmit(async (v) => {
          await createLoan(v);
          reset();
          onCreated();
        })}
        className="space-y-4"
      >
        <div>
          <label htmlFor="amount" className="block text-sm font-medium text-gray-700 mb-1">
            Monto del préstamo ($)
          </label>
          <input
            id="amount"
            type="number"
            step="0.01"
            placeholder="10000.00"
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            {...register("amount", { required: true, min: 0 })}
          />
        </div>
        <div>
          <label htmlFor="termMonths" className="block text-sm font-medium text-gray-700 mb-1">
            Plazo en meses
          </label>
          <input
            id="termMonths"
            type="number"
            placeholder="24"
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            {...register("termMonths", { required: true, min: 3, max: 120 })}
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition duration-150 ease-in-out"
        >
          Solicitar Préstamo
        </button>
      </form>
    </div>
  );
}
