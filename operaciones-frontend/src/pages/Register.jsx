import { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import api from '../services/api';

const Register = () => {
  const [formData, setFormData] = useState({ 
    nombre: '', 
    email: '', 
    password: '' 
  });
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Ajustado a 'nombre' según tu entidad Usuario que vimos antes
      await api.post('/auth/register', formData);
      navigate('/login');
    } catch (err) {
      console.error(err);
      alert("Error al registrar: " + (err.response?.data || "Verifica los datos"));
    }
  };

  return (
    <Container className="mt-5 col-md-4">
      <h2>Crear Cuenta</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Nombre</Form.Label>
          <Form.Control type="text" onChange={(e) => setFormData({...formData, nombre: e.target.value})} />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control type="email" onChange={(e) => setFormData({...formData, email: e.target.value})} />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Contraseña</Form.Label>
          <Form.Control type="password" onChange={(e) => setFormData({...formData, password: e.target.value})} />
        </Form.Group>
        <Button variant="success" type="submit">Registrarse</Button>
      </Form>
    </Container>
  );
};

export default Register;