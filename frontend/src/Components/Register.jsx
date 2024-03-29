import { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {getBearerAuthHeaders} from "../authUtils";

const Register = () => {
  const [validated, setValidated] = useState(false);
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordsMatch, setPasswordsMatch] = useState(false);

  const [formData, setFormData] = useState({
    username: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    authorities: 'USER'
});

  const handleChange = (e) => {
    const { name, value,checked } = e.target;
    console.log("name: ",name, "checked:",checked)
    if(name==="isAdmin" && value==="on"){
      setFormData((prevFormData) => ({
        ...prevFormData,
        authorities: "ADMIN,USER"
    }));
    } else if(name==="authorities" && !checked) {
      setFormData((prevFormData) => ({
        ...prevFormData,
        [name]: "USER"
    }));
    } else {
      setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: value
            }));
    }
  }

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    // setConfirmPassword(e.target.value);
    if(formData.password === e.target.value){
      console.log("a intrat in if")
      setPasswordsMatch(true)
    } else {
      console.log("a intrat in else")
      setPasswordsMatch(false)
    }
  };

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      event.preventDefault();
      event.stopPropagation();
      if (passwordsMatch) {
        console.log(formData)
        try {

          //const response = await fetchBearerAuthWithBody("http://localhost:8080/api/employee","POST", formData);
          console.log(JSON.stringify(formData))

          const headers = getBearerAuthHeaders();
          headers.append("Content-Type", "application/json");
          const response = await fetch("http://localhost:8080/api/user", {
            method: "POST",
            headers: headers,
            body: JSON.stringify(formData),
          });
          if (response.ok) {

          } else {

          }
        } catch (error) {
          console.log(error)
        }
        console.log("pass match!")
      } else {
        console.log(formData)
        // Passwords don't match, set passwordsMatch state to false
        console.log("pass do not match!")
      }

    }

    setValidated(true);
  };

  return (
    <>
      <h5 className="p-3">Register New Employee</h5>
      <Form
        className="p-3"
        noValidate
        validated={validated}
        onSubmit={handleSubmit}
      >
        <Form.Group className="p-1" controlId="formGroupUsername">
          <Form.Label>Username</Form.Label>
          <Form.Control
            name="username"
            required
            className="form-control-sm"
            type="text"
            placeholder="Username"
            pattern="^[a-zA-Z0-9]{4,20}$"
            onChange={handleChange}
          />
          <Form.Control.Feedback type="invalid">
            Username must be alphanumeric and have 4 to 20 characters
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="p-1" controlId="formGroupFirstName">
          <Form.Label>First Name</Form.Label>
          <Form.Control
            name="firstName"
            required
            className="form-control-sm"
            type="text"
            placeholder="First Name"
            pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
            onChange={handleChange}
          />
          <Form.Control.Feedback type="invalid">
            First name should be between 2 and 25 characters long
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="p-1" controlId="formGroupLastName">
          <Form.Label>Last Name</Form.Label>
          <Form.Control
            name="lastName"
            required
            className="form-control-sm"
            type="text"
            placeholder="Last Name"
            pattern="^[a-zA-Z]{2,15}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
            onChange={handleChange}
          />
          <Form.Control.Feedback type="invalid">Last name should be between 2 and 15 characters long</Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formGroupEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            name="email"
            required
            className="form-control-sm"
            type="email"
            placeholder="Enter email"
            onChange={handleChange}
          />
          <Form.Control.Feedback type="invalid">Invalid email</Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formGroupPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            name="password"
            required
            className="form-control-sm"
            type="password"
            placeholder="Password"
            pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
            onChange={handleChange}
          />
          <Form.Control.Feedback type="invalid">
            Password must have at least 8 characters, one uppercase letter, one lowercase letter, and one digit
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formGroupConfirmPassword">
          <Form.Label>Confirm Password</Form.Label>
          <Form.Control
            required
            className="form-control-sm"
            type="password"
            placeholder="Confirm Password"
            pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
            isInvalid={!passwordsMatch}
            onChange={handleConfirmPasswordChange}
          />
          <Form.Control.Feedback type="invalid">
            Passwords do not match
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formGroupIsAdmin">
          <div key={`default-checkbox`} className="mb-3">
            <Form.Check
                name="isAdmin"
                type={"checkbox"}
                id={`default-checkbox`}
                label={`Is Admin`}
                onChange={handleChange}
              />
          </div>
        </Form.Group>

        <Button type="submit" className="btn-sm" variant="outline-info">

          Register
        </Button>{" "}
      </Form>
    </>
  );
};

export default Register;
