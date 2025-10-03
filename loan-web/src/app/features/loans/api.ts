// src/features/loans/api.ts
import { api } from "../shared/lib/axios";

export type Loan = {
  id: number;
  amount: string;
  termMonths: number;
  status: "REQUESTED" | "APPROVED" | "REJECTED";
  createdAt: string;
};

export const getMyLoans = async () => (await api.get<Loan[]>("/loans/me")).data;
export const createLoan = async (p: { amount: number; termMonths: number }) => (await api.post("/loans", p)).data;
export const approveLoan = async (id: number) => (await api.patch(`/loans/${id}/approve`, {})).data;
export const rejectLoan = async (id: number) => (await api.patch(`/loans/${id}/reject`, {})).data;
