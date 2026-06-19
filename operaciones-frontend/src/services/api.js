import axios from 'axios';
import { toast } from 'react-toastify';

const api = axios.create({
  baseURL: 'http://localhost:8081/api'
});

api.interceptors.request.use((config) => {
  const user = JSON.parse(localStorage.getItem('user'));
  if (user && user.token) {
    config.headers.Authorization = `Bearer ${user.token}`;
  }
  return config;
});

// NUEVO: Interceptor para capturar errores automáticamente
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Si el backend envía un mensaje de error (IllegalStateException, etc.)
    const message = error.response?.data?.message || error.message || "Ocurrió un error inesperado";
    
    // Mostramos el mensaje al usuario
    toast.error(message);
    
    return Promise.reject(error);
  }
);

export default api;