import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { Form, Button, Modal } from 'react-bootstrap';
import { toast } from 'react-toastify';
import ViajeService from '../../services/viajeService';
import FormInput from '../common/FormInput';

const ViajeForm = ({ show, handleClose, onSave, viajeAEditar }) => {
  const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm();
  const [camiones, setCamiones] = useState([]);
  const [camioneros, setCamioneros] = useState([]);

  // Identificar si el viaje está en curso
  const isEnCurso = viajeAEditar?.estado === 'EN_CURSO';

  useEffect(() => {
    const loadData = async () => {
      const [cams, cers] = await Promise.all([ViajeService.getCamionesDisponibles(), ViajeService.getCamioneros()]);
      setCamiones(cams.data);
      setCamioneros(cers.data);
    };
    loadData();
  }, []);

  useEffect(() => {
    if (viajeAEditar) {
      // Seteamos valores individuales para asegurar que los Selects seleccionen la opción correcta
      setValue("origen", viajeAEditar.origen);
      setValue("destino", viajeAEditar.destino);
      setValue("distanciaKm", viajeAEditar.distanciaKm);
      setValue("fechaSalida", viajeAEditar.fechaSalida);
      setValue("montoFlete", viajeAEditar.montoFlete);
      setValue("camionId", viajeAEditar.camionId);
      setValue("camioneroId", viajeAEditar.camioneroId);
      setValue("estado", viajeAEditar.estado);
    } else {
      reset({ estado: 'PENDIENTE' });
    }
  }, [viajeAEditar, setValue, reset]);
  const onSubmit = async (data) => {
    try {
      const payload = { 
        ...data, 
        distanciaKm: parseFloat(data.distanciaKm), 
        montoFlete: parseFloat(data.montoFlete) 
      };
      
      if (viajeAEditar) {
        await ViajeService.update(viajeAEditar.id, payload);
      } else {
        await ViajeService.create(payload);
      }
      
      toast.success("Viaje guardado con éxito");
      onSave();
      handleClose();
    } catch (e) {
      // El error ya lo captura el interceptor de api.js
    }
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>{viajeAEditar ? "Editar Viaje" : "Nuevo Viaje"}</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Modal.Body>
          {/* Si está en curso, bloqueamos todo lo que no sea estado */}
          <FormInput 
            label="Origen" 
           name="origen" 
           register={register} 
            rules={{ required: "El origen es obligatorio" }}
            error={errors.origen}
            disabled={isEnCurso} 
          />
          <FormInput 
           label="Destino" 
           name="destino" 
           register={register} 
           rules={{ required: "El destino es obligatorio" }}
           error={errors.destino}
           disabled={isEnCurso} 
          />
          <FormInput 
           label="Distancia (Km)" 
           name="distanciaKm" 
           type="number" 
           register={register} 
           rules={{ required: "La distancia es obligatoria", min: { value: 0.1, message: "Debe ser mayor a 0" } }}
           error={errors.distanciaKm}
           disabled={isEnCurso} 
          />
          <FormInput 
           label="Fecha Salida" 
            name="fechaSalida" 
           type="date" 
           register={register} 
           rules={{ required: "La fecha es obligatoria" }}
           error={errors.fechaSalida}
           disabled={isEnCurso} 
          />
          <FormInput 
           label="Monto Flete" 
           name="montoFlete" 
           type="number" 
           register={register} 
           rules={{ required: "El monto es obligatorio", min: { value: 0.01, message: "Debe ser mayor a 0" } }}
           error={errors.montoFlete}
          />          

          <Form.Group className="mb-3">
            <Form.Label>Camión</Form.Label>
            <Form.Select {...register("camionId", { required: true })} disabled={isEnCurso}>
              <option value="">Seleccione un camión...</option>
              {camiones.map(c => (
                <option key={c.id} value={c.id} disabled={c.estado === 'EN_VIAJE'}>
                  {c.patente} - {c.marca} {c.estado === 'EN_VIAJE' ? '(Ocupado)' : ''}
                </option>
              ))}
            </Form.Select>
            {errors.camionId && <Form.Text className="text-danger">Debe seleccionar un camión.</Form.Text>}
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Camionero</Form.Label>
            <Form.Select {...register("camioneroId", { required: true })} disabled={isEnCurso}>
              <option value="">Seleccione un camiónero...</option>
              {camioneros.map(c => (
                <option key={c.id} value={c.id}>{c.nombre} {c.apellido}</option>
              ))}
            </Form.Select>
            {errors.camioneroId && <Form.Text className="text-danger">Debe seleccionar un camiónero.</Form.Text>}
          </Form.Group>

          {/* El Estado solo se permite cambiar si no es un viaje nuevo */}
          {viajeAEditar && (
            <Form.Group className="mb-3">
              <Form.Label>Estado</Form.Label>
              <Form.Select {...register("estado", { required: true })}>
                <option value="PENDIENTE">Pendiente</option>
                <option value="EN_CURSO">En Curso</option>
                <option value="FINALIZADO">Finalizado</option>
                <option value="CANCELADO">Cancelado</option>
              </Form.Select>
              {errors.estado && <Form.Text className="text-danger">Debe seleccionar un Estado.</Form.Text>}
            </Form.Group>
          )}
          {/* Setear la fecha de llegada cuando viaje finaliza */}
          {viajeAEditar?.fechaLlegada && (
             <Form.Group className="mb-3">
               <Form.Label>Fecha de Llegada</Form.Label>
               <Form.Control 
                 type="text" 
                 value={viajeAEditar.fechaLlegada} 
                 disabled 
                 readOnly 
               />
             </Form.Group>
           )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" type="submit">Guardar</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};
export default ViajeForm;