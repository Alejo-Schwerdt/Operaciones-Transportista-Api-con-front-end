import { Badge } from 'react-bootstrap';

export const getEstadoBadge = (estado) => {
  const variants = {
    PENDIENTE: "warning",
    EN_CURSO: "primary",
    FINALIZADO: "success",
    CANCELADO: "danger"
  };
  return <Badge bg={variants[estado] || "secondary"}>{estado}</Badge>;
};
export const getEstadoCamionBadge = (estado) => {
  const config = {
    DISPONIBLE: { texto: "Disponible", variant: "success" },
    EN_VIAJE: { texto: "En Viaje", variant: "primary" },
    EN_TALLER: { texto: "En Taller", variant: "warning" }
  };
  const { texto, variant } = config[estado] || { texto: estado, variant: "secondary" };
  return <Badge bg={variant}>{texto}</Badge>;
};
export const getEstadoTexto = (estado) => {
  const textos = {
    DISPONIBLE: "Disponible",
    EN_VIAJE: "En Viaje",
    EN_TALLER: "En Taller"
  };
  return textos[estado] || estado;
};