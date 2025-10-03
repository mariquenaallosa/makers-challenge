import axios from "axios";

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
});

// helper para setear/quitar token
export const setAuthToken = (token: string | null) => {
  api.interceptors.request.clear(); // evita duplicados si re-seteás
  api.interceptors.request.use((cfg) => {
    if (token) cfg.headers.Authorization = `Bearer ${token}`;
    return cfg;
  });
};
