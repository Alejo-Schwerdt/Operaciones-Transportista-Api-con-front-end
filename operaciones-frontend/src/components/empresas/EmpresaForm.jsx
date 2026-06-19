import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Form, Button, Modal } from 'react-bootstrap';
import { toast } from 'react-toastify';
import EmpresaService from '../../services/empresaService';
import FormInput from '../common/FormInput';

const EmpresaForm = ({ show, handleClose, onSave, empresaAEditar }) => {
  const { register, handleSubmit, reset, setValue, formState: { errors } } = useForm();

  useEffect(() => {
    if (empresaAEditar) {
      Object.keys(empresaAEditar).forEach(key => setValue(key, empresaAEditar[key]));
    } else { reset(); }
  }, [empresaAEditar, setValue, reset]);

  const onSubmit = async (data) => {
    try {
      const payload = { ...data, porcentajeRetencion: parseFloat(data.porcentajeRetencion) };
      if (empresaAEditar) await EmpresaService.update(empresaAEditar.id, payload);
      else await EmpresaService.create(payload);
      
      toast.success("Empresa guardada con éxito");
      onSave();
      handleClose();
    } catch (e) { toast.error("Error al guardar empresa"); }
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton><Modal.Title>{empresaAEditar ? "Editar" : "Nueva"} Empresa</Modal.Title></Modal.Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Modal.Body>
          <FormInput label="Nombre" name="nombre" register={register} error={errors.nombre} />
          <FormInput label="CUIT" name="cuit" register={register} error={errors.cuit} />
          <FormInput label="Email" name="email" type="email" register={register} error={errors.email} />
          <FormInput label="% Retención" name="porcentajeRetencion" type="number" step="0.1" register={register} error={errors.porcentajeRetencion} />
          <Form.Check type="switch" label="Empresa Activa" {...register("activa")} className="mt-3" />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" type="submit">Guardar</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};
export default EmpresaForm;