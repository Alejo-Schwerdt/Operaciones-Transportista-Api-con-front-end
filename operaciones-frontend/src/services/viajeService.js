import api from './api';

const ViajeService = {
  getAll: () => api.get('/viajes'),
  create: (data) => api.post('/viajes', data),
  update: (id, data) => api.put(`/viajes/${id}`, data),
  delete: (id) => api.delete(`/viajes/${id}`),
  getCamionesDisponibles: () => api.get('/camiones?disponible=true'), 
  getCamioneros: () => api.get('/camioneros'), // Corrección: asegúrate que la ruta sea /camioneros
  getViajesPorCamionero: (camioneroId) => api.get(`/viajes/camionero/${camioneroId}`),
};

export default ViajeService;