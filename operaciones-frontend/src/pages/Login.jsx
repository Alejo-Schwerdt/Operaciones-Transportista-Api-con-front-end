import { useState } from 'react';
import { Container, Form, Button, Alert } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Login = () => {
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(credentials);
      navigate('/dashboard'); // Redirigir al dashboard o home protegido
    } catch (err) {
      alert("Error al iniciar sesión: Verifica tus credenciales");
    }
  };

  return (
    <Container className="mt-5 col-md-4">
      <h2>Iniciar Sesión</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control 
            type="email" 
            onChange={(e) => setCredentials({...credentials, email: e.target.value})} 
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Contraseña</Form.Label>
          <Form.Control 
            type="password" 
            onChange={(e) => setCredentials({...credentials, password: e.target.value})} 
          />
        </Form.Group>
        <Button variant="primary" type="submit">Ingresar</Button>
      </Form>
      {/* ... links ... */}
    </Container>
  );
};

export default Login;