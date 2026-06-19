import { useState, useEffect } from 'react';
import { Container, Table, Button, Spinner, Badge } from 'react-bootstrap';
import { toast } from 'react-toastify';
import ViajeService from '../services/viajeService';
import ViajeForm from '../components/viajes/ViajeForm';
import { getEstadoBadge } from '../utils/badges';

const Viajes = () => {
  const [viajes, setViajes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [viajeAEditar, setViajeAEditar] = useState(null);

  useEffect(() => {
    fetchViajes();
  }, []);

  const fetchViajes = async () => {
    try {
      const response = await ViajeService.getAll();
      setViajes(response.data);
    } catch (error) {
      toast.error("Error al cargar los viajes");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar este viaje?')) {
      try {
        await ViajeService.delete(id);
        toast.success("Viaje eliminado");
        fetchViajes();
      } catch (error) {
        toast.error("No se pudo eliminar el viaje");
      }
    }
  };
  const handleTerminarViaje = async (viaje) => {
  if (window.confirm("¿Confirmar que el viaje ha finalizado?")) {
    try {
      await ViajeService.update(viaje.id, { ...viaje, estado: 'FINALIZADO' });
      toast.success("Viaje finalizado correctamente");
      fetchViajes();
    } catch (error) {
    }
  }
};
  if (loading) return <Container className="text-center mt-5"><Spinner animation="border" /></Container>;

  return (
    <Container className="mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Gestión de Viajes</h2>
        <Button variant="success" onClick={() => { setViajeAEditar(null); setShowModal(true); }}>
          Nuevo Viaje
        </Button>
      </div>

      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>Origen</th>
            <th>Destino</th>
            <th>Camión</th>
            <th>Camionero</th>
            <th>Estado</th>
            <th>Flete</th>
            <th>Acciones</th>
            <th>Fecha Salida</th>
            <th>Fecha llegada</th>
          </tr>
        </thead>
        <tbody>
           {viajes.map((v) => (
            <tr key={v.id}>
              <td>{v.origen}</td>
               <td>{v.destino}</td>
               <td>{v.patenteCamion}</td>
               <td>{v.nombreCamionero}</td>
               <td>{getEstadoBadge(v.estado)}</td>
              <td>${v.montoFlete?.toLocaleString() ?? 'N/D'}</td>
              <td>
                {v.estado === 'EN_CURSO' ? (
                  <Button variant="success" size="sm" className="me-2" onClick={() => handleTerminarViaje(v)}>
                    Terminar
                  </Button>
                ) : v.estado === 'FINALIZADO' ? (
                  <Button variant="secondary" size="sm" className="me-2" disabled>
                    Finalizado
                  </Button>
                ) : (
                  <Button variant="warning" size="sm" className="me-2" onClick={() => { setViajeAEditar(v); setShowModal(true); }}>
                    Editar
                  </Button>
                )}

                {/* Solo permitir eliminar si no está en curso */}
                {v.estado !== 'EN_CURSO' && (
                  <Button variant="danger" size="sm" onClick={() => handleDelete(v.id)}>Eliminar</Button>
                )}
              </td>
              <td>{v.fechaSalida}</td>
              <td>{v.fechaLlegada || '—'}</td>
             </tr>
           ))}
        </tbody>
      </Table>

      <ViajeForm 
        show={showModal} 
        handleClose={() => setShowModal(false)} 
        onSave={fetchViajes} 
        viajeAEditar={viajeAEditar} 
      />
    </Container>
  );
};

export default Viajes;