import { useState, useEffect } from 'react';
import { Container, Table, Button, Spinner } from 'react-bootstrap';
import { toast } from 'react-toastify';
import CamioneroService from '../services/camioneroService';
import CamioneroForm from '../components/camioneros/CamioneroForm';
import ViajesPorCamioneroModal from '../components/camioneros/ViajesPorCamioneroModal';
const Camioneros = () => {
  const [camioneros, setCamioneros] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [camioneroAEditar, setCamioneroAEditar] = useState(null);
  const [showViajesModal, setShowViajesModal] = useState(false);
  const [selectedCamionero, setSelectedCamionero] = useState(null);

  useEffect(() => { fetchCamioneros(); }, []);

  const fetchCamioneros = async () => {
    try {
      const response = await CamioneroService.getAll();
      setCamioneros(response.data);
    } catch (error) { toast.error("Error al cargar camioneros"); }
    finally { setLoading(false); }
  };
  const verViajes = (camionero) => {
  setSelectedCamionero(camionero);
  setShowViajesModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Eliminar este camionero?')) {
      try {
        await CamioneroService.delete(id);
        toast.success("Camionero eliminado");
        fetchCamioneros();
      } catch (error) { toast.error("No se puede eliminar"); }
    }
  };

if (loading) {
  return (
    <Container className="text-center mt-5">
      <Spinner animation="border" />
    </Container>
  );
}
return (
  <Container className="mt-4">
    <h2>Gestión de Camioneros</h2>
    <Button className="mb-3" onClick={() => { setCamioneroAEditar(null); setShowModal(true); }}>
      Nuevo Camionero
    </Button>
    <Table striped bordered hover>
      <thead>...</thead>
      <tbody>
        {camioneros.map(c => (
          <tr key={c.id}>
            <td>{c.nombre}</td><td>{c.apellido}</td><td>{c.dni}</td>
            <td>{c.numeroLicencia}</td>
            <td>{c.habilitado ? 'Habilitado' : 'Inhabilitado'}</td>
            <td>
              <Button variant="warning" size="sm" className="me-2" onClick={() => { setCamioneroAEditar(c); setShowModal(true); }}>Editar</Button>
              <Button variant="danger" size="sm" onClick={() => handleDelete(c.id)}>Eliminar</Button>
              <Button variant="info" size="sm" onClick={() => verViajes(c)}>Viajes</Button>
            </td>
          </tr>
        ))}
      </tbody>
    </Table>
    <CamioneroForm show={showModal} handleClose={() => setShowModal(false)} onSave={fetchCamioneros} camioneroAEditar={camioneroAEditar} />
    <ViajesPorCamioneroModal 
      show={showViajesModal} 
      handleClose={() => setShowViajesModal(false)} 
      camionero={selectedCamionero} 
    />
  </Container>
);
};
export default Camioneros;