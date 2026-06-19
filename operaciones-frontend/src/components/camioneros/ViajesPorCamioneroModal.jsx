import { useState, useEffect } from 'react';
import { Modal, Table, Spinner } from 'react-bootstrap';
import { toast } from 'react-toastify';
import ViajeService from '../../services/viajeService';
import { getEstadoBadge } from '../../utils/badges';

const ViajesPorCamioneroModal = ({ show, handleClose, camionero }) => {
  const [viajes, setViajes] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (camionero && show) {
      fetchViajes();
    }
  }, [camionero, show]);

  const fetchViajes = async () => {
    setLoading(true);
    try {
      const response = await ViajeService.getViajesPorCamionero(camionero.id);
      setViajes(response.data);
    } catch (error) {
      toast.error("Error al cargar los viajes del camionero");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal show={show} onHide={handleClose} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>
          Viajes de {camionero?.nombre} {camionero?.apellido}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {loading ? (
          <div className="text-center"><Spinner animation="border" /></div>
        ) : (
          <Table striped bordered hover size="sm">
            <thead>
              <tr>
                <th>Origen</th>
                <th>Destino</th>
                <th>Estado</th>
                <th>Fecha Salida</th>
              </tr>
            </thead>
            <tbody>
              {viajes.length > 0 ? (
                viajes.map(v => (
                  <tr key={v.id}>
                    <td>{v.origen}</td>
                    <td>{v.destino}</td>
                    <td>{getEstadoBadge(v.estado)}</td>
                    <td>{v.fechaSalida}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="4" className="text-center">Este camionero no tiene viajes registrados.</td>
                </tr>
              )}
            </tbody>
          </Table>
        )}
      </Modal.Body>
    </Modal>
  );
};

export default ViajesPorCamioneroModal;