import { Form } from 'react-bootstrap';

const FormInput = ({ label, name, register, rules, error, type = "text", ...rest }) => {
  return (
    <Form.Group className="mb-3">
      {label && <Form.Label>{label}</Form.Label>}
      <Form.Control 
        type={type} 
        {...register(name, rules)} 
        isInvalid={!!error} 
        {...rest} 
      />
      {error && <Form.Control.Feedback type="invalid">{error.message}</Form.Control.Feedback>}
    </Form.Group>
  );
};

export default FormInput;