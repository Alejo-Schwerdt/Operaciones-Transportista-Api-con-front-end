import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

// Si App.jsx está en src/ y los componentes están en src/components/...
import Navigation from './components/Navbar';
import ProtectedRoute from './routes/ProtectedRoute';
// Si App.jsx está en src/ y las páginas están en src/pages/...
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Camiones from './pages/Camiones';
import Camioneros from './pages/Camioneros';
import Viajes from './pages/Viajes';
import Empresas from './pages/Empresas';
import Dashboard from './pages/Dashboard';
import { useAuth } from './context/AuthContext';

function App() {
  const { user } = useAuth();
  
  return (
    <BrowserRouter>
      <Navigation />
      <Routes>
        {/* Rutas Públicas */}
        <Route path="/" element={<Home />} />
        <Route path="/login" element={user ? <Navigate to="/dashboard" /> : <Login />} />
        <Route path="/register" element={<Register />} />
        
        {/* Rutas Protegidas */}
        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/camiones" element={<Camiones />} />
          <Route path="/camioneros" element={<Camioneros />} />
          <Route path="/viajes" element={<Viajes />} />
          <Route path="/empresas" element={<Empresas />} />
        </Route>

        {/* Catch-all para redireccionar rutas inexistentes */}
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;