import { Navbar, Container, Nav, Button } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Navigation = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="mb-4">
      <Container>
        <Navbar.Brand onClick={() => navigate('/dashboard')} style={{ cursor: 'pointer' }}>
            Gestión Logística
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {user && (
              <>
                <Nav.Link onClick={() => navigate('/camiones')}>Camiones</Nav.Link>
                <Nav.Link onClick={() => navigate('/camioneros')}>Camioneros</Nav.Link>
                <Nav.Link onClick={() => navigate('/viajes')}>Viajes</Nav.Link>
                <Nav.Link onClick={() => navigate('/empresas')}>Empresas</Nav.Link>
              </>
            )}
          </Nav>
          <Nav>
            {user ? (
              <Button variant="outline-light" size="sm" onClick={() => { logout(); navigate('/login'); }}>
                Cerrar Sesión
              </Button>
            ) : (
              <Button variant="primary" size="sm" onClick={() => navigate('/login')}>Ingresar</Button>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Navigation;