import { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

const Login = ({handleLogin}) => {
  const [validated, setValidated] = useState(false);

  const handleSubmit = (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
      setValidated(true);
    }

    handleLogin();
  };

  return (
    <>
      <h5 className="p-3">Login</h5>
      <Form
        className="p-3"
        noValidate
        validated={validated}
        onSubmit={handleSubmit}
      >
        <Form.Group className="p-1" controlId="formGroupUsername">
          <Form.Label>Username</Form.Label>
          <Form.Control
            // required
            className="form-control-sm"
            type="text"
            placeholder="Username"
          />
          <Form.Control.Feedback type="invalid">Please enter a username</Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="p-1" controlId="formGroupPassword1">
          <Form.Label>Password</Form.Label>
          <Form.Control
            // required
            className="form-control-sm"
            type="password"
            placeholder="Password"
          />
          <Form.Control.Feedback type="invalid">Please enter a password</Form.Control.Feedback>
        </Form.Group>
        <Button type="submit" className="btn-sm" variant="outline-warning">
          Login
        </Button>{" "}
      </Form>
    </>
  );
};

export default Login;
