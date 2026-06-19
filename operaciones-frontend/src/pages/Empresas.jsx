import { useState, useEffect } from 'react';
import { Container, Table, Button, Spinner } from 'react-bootstrap';
import { toast } from 'react-toastify';
import EmpresaService from '../services/empresaService';
import EmpresaForm from '../components/empresas/EmpresaForm';

const Empresas = () => {
  const [empresas, setEmpresas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [empresaAEditar, setEmpresaAEditar] = useState(null);

  useEffect(() => { fetchEmpresas(); }, []);

  const fetchEmpresas = async () => {
    try {
      const response = await EmpresaService.getAll();
      setEmpresas(response.data);
    } catch (error) { toast.error("Error al cargar empresas"); }
    finally { setLoading(false); }
  };

  return (
    <Container className="mt-4">
      <div className="d-flex justify-content-between mb-3">
        <h2>Gestión de Empresas</h2>
        <Button variant="success" onClick={() => { setEmpresaAEditar(null); setShowModal(true); }}>Nueva Empresa</Button>
      </div>
      <Table striped bordered hover>
        <thead>
          <tr><th>Nombre</th><th>CUIT</th><th>Email</th><th>Retención</th><th>Activa</th><th>Acciones</th></tr>
        </thead>
        <tbody>
          {empresas.map(e => (
            <tr key={e.id}>
              <td>{e.nombre}</td><td>{e.cuit}</td><td>{e.email}</td>
              <td>{e.porcentajeRetencion}%</td>
              <td>{e.activa ? 'Sí' : 'No'}</td>
              <td>
                <Button variant="warning" size="sm" onClick={() => { setEmpresaAEditar(e); setShowModal(true); }}>Editar</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <EmpresaForm show={showModal} handleClose={() => setShowModal(false)} onSave={fetchEmpresas} empresaAEditar={empresaAEditar} />
    </Container>
  );
};
export default Empresas;