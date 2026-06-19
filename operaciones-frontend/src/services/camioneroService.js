import api from './api';

const CamioneroService = {
  getAll: () => api.get('/camioneros'),
  getById: (id) => api.get(`/camioneros/${id}`),
  create: (data) => api.post('/camioneros', data),
  update: (id, data) => api.put(`/camioneros/${id}`, data),
  delete: (id) => api.delete(`/camioneros/${id}`),
};

export default CamioneroService;