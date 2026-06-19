import api from './api';

const EmpresaService = {
  getAll: () => api.get('/empresa'),
  getById: (id) => api.get(`/empresa/${id}`),
  create: (data) => api.post('/empresa', data),
  update: (id, data) => api.put(`/empresa/${id}`, data),
  delete: (id) => api.delete(`/empresa/${id}`),
  asignarCamionero: (empresaId, camioneroId) => 
    api.post(`/empresa/${empresaId}/camioneros/${camioneroId}`)
};

export default EmpresaService;