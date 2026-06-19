import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Form, Button, Modal } from 'react-bootstrap';
import { toast } from 'react-toastify';
import CamionService from '../../services/camionService';
import FormInput from '../common/FormInput'; 
import { getEstadoTexto } from '../../utils/badges';
const CamionForm = ({ show, handleClose, onSave, camionAEditar }) => {
  const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm();

  useEffect(() => {
    if (camionAEditar) {
      Object.keys(camionAEditar).forEach((key) => setValue(key, camionAEditar[key]));
    } else {
      reset();
    }
  }, [camionAEditar, setValue, reset]);
  const isEnViaje = camionAEditar?.estado === 'EN_VIAJE';
  const onSubmit = async (data) => {
  try {
    const payload = {
      ...data,
      capacidadToneladas: parseFloat(data.capacidadToneladas),
    };

    if (camionAEditar) {
      await CamionService.update(camionAEditar.id, payload);
      toast.success("Camión actualizado con éxito!");
    } else {
      await CamionService.create(payload);
      toast.success("Camión creado con éxito!");
    }
    onSave();
    handleClose();
  } catch (error) {
    toast.error("Error al guardar el camión");
  }
};
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>{camionAEditar ? "Editar Camión" : "Nuevo Camión"}</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Modal.Body>
          <FormInput label="Patente" name="patente" register={register} error={errors.patente} />
          <FormInput label="Marca" name="marca" register={register} error={errors.marca} />
          <FormInput label="Modelo" name="modelo" register={register} error={errors.modelo} />
          <FormInput 
            label="Capacidad (Tn)" 
            name="capacidadToneladas" 
            type="number" 
            step="0.1" 
            register={register} 
            error={errors.capacidadToneladas} 
          />
          {camionAEditar && isEnViaje ? (
            <Form.Group className="mb-3">
              <Form.Label>Estado</Form.Label>
              <Form.Control type="text" value={`${getEstadoTexto(camionAEditar?.estado)} (bloqueado hasta finalizar)`} disabled readOnly />
            </Form.Group>
          ) : camionAEditar ? (
            <Form.Group className="mb-3">
              <Form.Label>Estado</Form.Label>
              <Form.Select {...register("estado", { required: true })}>
                <option value="DISPONIBLE">Disponible</option>
                <option value="EN_TALLER">En Taller</option>
              </Form.Select>
            </Form.Group>
          ) : null}          
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Cerrar</Button>
          <Button variant="primary" type="submit">{camionAEditar ? "Actualizar" : "Guardar"}</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default CamionForm;