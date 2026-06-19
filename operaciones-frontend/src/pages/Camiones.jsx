import { useState, useEffect } from 'react';
import { Container, Table, Button, Spinner } from 'react-bootstrap';
import { toast } from 'react-toastify';
import CamionService from '../services/camionService';
import CamionForm from '../components/camiones/CamionForm';
import { getEstadoCamionBadge } from '../utils/badges';
const Camiones = () => {
  const [camiones, setCamiones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [camionAEditar, setCamionAEditar] = useState(null);

  useEffect(() => {
    fetchCamiones();
  }, []);

  const fetchCamiones = async () => {
    try {
      const response = await CamionService.getAll();
      setCamiones(response.data);
    } catch (error) {
      console.error("Error al cargar camiones:", error);
    } finally {
      setLoading(false);
    }
  };
  const handleEdit = (camion) => {
  setCamionAEditar(camion);
  setShowModal(true);
  };

  const handleDelete = async (id) => {
  if (window.confirm('¿Estás seguro de eliminar este camión?')) {
    try {
      await CamionService.delete(id);
      toast.success("Camión eliminado correctamente");
      fetchCamiones(); // Refresca la tabla
    } catch (error) {
      console.error(error);
      toast.error("No se pudo eliminar: es posible que tenga viajes asignados.");
    }
  }
  };

  if (loading) return <Spinner animation="border" className="mt-5" />;

  return (
    
    <Container className="mt-4">
      <h2>Gestión de Camiones</h2>
      
      <div className="mb-3">
        <Button variant="success" onClick={() => { setCamionAEditar(null); setShowModal(true)}}>
          Nuevo Camión
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Patente</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>Capacidad (Tn)</th>
            <th>Disponible</th>
            <th>Acciones</th>
            <th>Estado</th>
          </tr>
        </thead>
        
        <tbody>
         {camiones.length > 0 ? (
            
           camiones.map((c) => (
              <tr key={c.id}>
                <td>{c.patente}</td>
                <td>{c.marca}</td>
                <td>{c.modelo}</td>
                <td>{c.capacidadToneladas}</td>
                <td>{c.disponible ? 'Sí' : 'No'}</td>
                <td>
                  {c.estado === 'EN_VIAJE' ? (
                    <Button variant="secondary" size="sm" disabled>
                      Bloqueado
                    </Button>
                  ) : (
                    <>
                    <Button variant="warning" size="sm" className="me-2" onClick={() => handleEdit(c)}>Editar</Button>
                    <Button variant="danger" size="sm" onClick={() => handleDelete(c.id)}>Eliminar</Button>
                    </>
                  )}
                </td>
                <td>{getEstadoCamionBadge(c.estado)}</td>
              </tr>
            ))
         ) : (
           <tr>
             <td colSpan="7" className="text-center">No hay camiones registrados.</td>
           </tr>
         )}
       </tbody>
      </Table>
      <CamionForm 
      show={showModal} 
      handleClose={() => setShowModal(false)} 
      onSave={fetchCamiones}
      camionAEditar={camionAEditar}/>
      </Container>
  );
};

export default Camiones;