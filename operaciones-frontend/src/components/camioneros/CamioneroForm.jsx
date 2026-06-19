import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Form, Button, Modal } from 'react-bootstrap';
import { toast } from 'react-toastify';
import CamioneroService from '../../services/camioneroService';
import FormInput from '../common/FormInput';

const CamioneroForm = ({ show, handleClose, onSave, camioneroAEditar }) => {
  const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm();

  useEffect(() => {
  if (camioneroAEditar) {
    Object.keys(camioneroAEditar).forEach(key => setValue(key, camioneroAEditar[key]));
  } else { 
    reset({ habilitado: true }); 
  }
}, [camioneroAEditar, setValue, reset]);

  const onSubmit = async (data) => {
    try {
      if (camioneroAEditar) await CamioneroService.update(camioneroAEditar.id, data);
      else await CamioneroService.create(data);
      
      toast.success("Guardado con éxito");
      onSave();
      handleClose();
    } catch (error) { toast.error("Error al guardar"); }
  };
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton><Modal.Title>{camioneroAEditar ? "Editar" : "Nuevo"} Camionero</Modal.Title></Modal.Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Modal.Body>
          <FormInput 
            label="Nombre" 
            name="nombre" 
            register={register} 
             rules={{ required: "El Nombre es obligatorio" }} error={errors.nombre}
          />
          <FormInput 
            label="Apellido" 
            name="apellido" 
            register={register} 
            rules={{ required: "El Apellido es obligatorio" }} error={errors.apellido}
          />
          <FormInput 
            label="DNI" 
            name="dni" 
            register={register} 
            rules={{ required: "El DNI es obligatorio" }} error={errors.dni}
          />
          <FormInput 
            label="Email" 
            name="email" 
            type="email" 
            register={register} 
            error={errors.email} 
          />
          <FormInput 
          label="Teléfono" 
          name="telefono" 
          register={register} 
          error={errors.telefono} 
          />
          <FormInput 
          label="Número de Licencia" 
          name="numeroLicencia" 
          register={register} 
          error={errors.numeroLicencia} 
          />
          <Form.Group className="mb-3">
            <Form.Check type="switch" label="Habilitado" {...register("habilitado")} />
          </Form.Group>
        </Modal.Body>
            
            <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Cerrar</Button>
          <Button type="submit">Guardar</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};
export default CamioneroForm;