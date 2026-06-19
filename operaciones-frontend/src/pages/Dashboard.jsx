import { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Spinner } from 'react-bootstrap';
import CamionService from '../services/camionService';
import ViajeService from '../services/viajeService';
import EmpresaService from '../services/empresaService';

const Dashboard = () => {
  const [stats, setStats] = useState({
  totalViajes: 0,
  totalCamiones: 0,
  empresasActivas: 0,
  disponibles: 0,
  enViaje: 0,
  enTaller: 0
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
  const fetchData = async () => {
    try {
      const [viajesRes, camionStatsRes, empresasRes] = await Promise.all([
        ViajeService.getAll(),
        CamionService.getStats(),
        EmpresaService.getAll()
      ]);

      const camionStats = camionStatsRes.data; // { disponibles, enViaje, enTaller }

      setStats({
        totalViajes: viajesRes.data.length,
        empresasActivas: empresasRes.data.filter(e => e.activa).length,
        disponibles: camionStats.disponibles,
        enViaje: camionStats.enViaje,
        enTaller: camionStats.enTaller,
        totalCamiones: camionStats.disponibles + camionStats.enViaje + camionStats.enTaller
      });
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };
  fetchData();
  }, []);

  if (loading) return <Container className="text-center mt-5"><Spinner animation="border" /></Container>;

  return (
  <Container className="mt-4">
  <h2>Panel Principal</h2>
  
  <Row className="mt-4">
    <Col md={4}>
      <StatCard title="Total Viajes" value={stats.totalViajes} color="primary" />
    </Col>
    <Col md={4}>
      <Card className="text-white bg-success mb-3 shadow">
        <Card.Body>
          <Card.Title>Camiones</Card.Title>
          <Card.Text className="display-4 font-weight-bold">{stats.totalCamiones}</Card.Text>
          <Card.Text className="mb-0">{stats.disponibles} disponibles</Card.Text>
        </Card.Body>
      </Card>
    </Col>
    <Col md={4}>
      <StatCard title="Empresas Activas" value={stats.empresasActivas} color="info" />
    </Col>
  </Row>

  <Row className="mt-4">
    <Col md={12}>
      <Card className="shadow-sm">
        <Card.Header>Estado de la Flota</Card.Header>
        <Card.Body>
          <Row className="text-center">
            <Col>
              <div className="text-success display-6">{stats.disponibles}</div>
              <div>Disponibles</div>
            </Col>
            <Col>
              <div className="text-primary display-6">{stats.enViaje}</div>
              <div>En Viaje</div>
            </Col>
            <Col>
              <div className="text-warning display-6">{stats.enTaller}</div>
              <div>En Taller</div>
            </Col>
          </Row>
        </Card.Body>
      </Card>
    </Col>
  </Row>
</Container>
    
  );
};

const StatCard = ({ title, value, color }) => (
  <Card className={`text-white bg-${color} mb-3 shadow`}>
    <Card.Body>
      <Card.Title>{title}</Card.Title>
      <Card.Text className="display-4 font-weight-bold">{value}</Card.Text>
    </Card.Body>
  </Card>
);

export default Dashboard;