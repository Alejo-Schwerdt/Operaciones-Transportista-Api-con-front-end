import api from './api';

const CamionService = {
  getAll: () => api.get('/camiones'),
  getById: (id) => api.get(`/camiones/${id}`),
  create: (data) => api.post('/camiones', data),
  update: (id, data) => api.put(`/camiones/${id}`, data),
  delete: (id) => api.delete(`/camiones/${id}`),
  getStats: () => api.get('/camiones/stats'),
};

export default CamionService;