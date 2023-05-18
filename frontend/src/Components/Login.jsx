import { useState } from "react";
import {fetchBasicAuth,fetchBearerAuth} from "../fetchFunction";
import {parseTextResponse} from "../responseParsers";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

const Login = ({handleLogin}) => {
  const [validated, setValidated] = useState(false);
  const [loginDetails, setLoginDetails] = useState({username:"", password:""})
  const [showError, setShowError] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    event.stopPropagation();
    setValidated(true);

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      return;
    }

    try {
      console.log(loginDetails)
      const response = await fetchBasicAuth("http://localhost:8080/token", "POST", loginDetails.username, loginDetails.password);
      const token = await parseTextResponse(response);
      console.log("TOKEN:" , token)
      if (token) {
        console.log(token);
        localStorage.setItem("token", token);
        handleLogin();
      } else {
        setShowError(true);
      }
    } catch (e) {
      console.log(e);
    }


  };

  const setPassword = (pass) => setLoginDetails({username: loginDetails.username, password: pass});
  const setUsername = (username) => setLoginDetails({username: username, password: loginDetails.password});

  return (
      <>
        <h5 className="p-3">Login</h5>
        <Form
            className="p-3"
            noValidate
            //validated={validated}
            //onSubmit={(event) => handleSubmit(event)}
        >
          <Form.Group className="p-1" controlId="formGroupUsername">
            <Form.Label>Username</Form.Label>
            <Form.Control
                onChange={(e) => setUsername(e.target.value)}
                className="form-control-sm"
                type="text"
                placeholder="Username"
            />
            <Form.Control.Feedback type="invalid">Please enter a username</Form.Control.Feedback>
          </Form.Group>
          <Form.Group className="p-1" controlId="formGroupPassword1">
            <Form.Label>Password</Form.Label>
            <Form.Control
                onChange={(e) => setPassword(e.target.value)}
                className="form-control-sm"
                type="password"
                placeholder="Password"
            />
            <Form.Control.Feedback type="invalid">Please enter a password</Form.Control.Feedback>
          </Form.Group>
          {showError && <Form.Text
              className="d-flex justify-content-center text-center"
              style={{
                fontSize: "16px",
                color: "red",
                marginTop: "1rem",
                marginBottom: "1rem",
              }}>
            Wrong credentials! Please try again.
          </Form.Text>}
          <Button
              onClick={(e) => handleSubmit(e)}
              type="submit"
              className="btn-sm"
              variant="outline-warning">
            Login
          </Button>{" "}
        </Form>
      </>
  );
};

export default Login;