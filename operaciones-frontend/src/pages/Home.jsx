import { Container, Button, Stack } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const navigate = useNavigate();

  return (
    <Container className="text-center mt-5">
      <h1>Bienvenido al Sistema de Gestión Logística</h1>
      <p>Controla tus camiones, camioneros y viajes de forma eficiente.</p>
      <Stack gap={2} className="col-md-4 mx-auto mt-4">
        <Button variant="primary" onClick={() => navigate('/login')}>Iniciar Sesión</Button>
        <Button variant="outline-secondary" onClick={() => navigate('/register')}>Registrarse</Button>
      </Stack>
    </Container>
  );
};

export default Home;