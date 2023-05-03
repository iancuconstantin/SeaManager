import { useState, useEffect } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';
// import RankList from './RankList';

function AddVesselForm({open}) {
    const [validated, setValidated] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        type: '',
        flag: '',
        callSign: '',
        yearBuilt: '',
        email: '',
        imonumber: '',
        dwt: '',
        grt: '',
        mmsi: ''
    });

    // useEffect(() => {
    //     // validate form data here
    //     const firstNameRegex = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
    //     const lastNameRegex = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
    //     const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    //     const contactNoRegex = /^\d+$/;

    //     const { firstName, lastName, email, dob, contactNo } = formData;
        
    //     const isValidFirstName = firstNameRegex.test(firstName);
    //     const isValidLastName = lastNameRegex.test(lastName);
    //     const isValidEmail = emailRegex.test(email);
    //     const isValidContactNo = contactNoRegex.test(contactNo);
    //     const isValidRank = formData.rank !== '';
    //     const isValidGender = formData.gender !== '';

    //     setValidated(isValidFirstName && 
    //         isValidLastName && 
    //         isValidEmail && 
    //         isValidContactNo &&
    //         isValidRank &&
    //         isValidGender );
    // }, [formData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        console.log(name,value);
        if(name==="contactNo"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: parseInt(value)
            }));
        } else if(name==="dob"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: new Date(value).toISOString().slice(0, 10)
            }));
        } else {
            setFormData((prevFormData) => ({
            ...prevFormData,
            [name]: value
            }));
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
        e.stopPropagation();
        } else {
        // submit form data here
        console.log(formData)
        }
        setValidated(true);
    };

    return (
        <>
            {/* {open && (
            <Form noValidate validated={validated} onSubmit={handleSubmit} className="justify-content-center">
            <Form.Group className="d-flex" style={{ width: "90%" }}>
                <InputGroup className="justify-content-center">
                    <Form.Group>
                        <Form.Control
                        name="firstName"
                        placeholder="FirstName"
                        aria-label="FirstName"
                        aria-describedby="basic-addon1"
                        required
                        value={formData.firstName}
                        onChange={handleChange}
                    />
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Please enter a valid first name</Form.Control.Feedback>
                    </Form.Group>
                    */}
                    {/* repeat Form.Group for other form controls */}
                    {/*
                    <Form.Group>
                        <Form.Control
                        name="lastName"
                        placeholder="LastName"
                        aria-label="LastName"
                        aria-describedby="basic-addon1"
                        required
                        value={formData.lastName}
                        onChange={handleChange}
                    />
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control 
                        name="email"
                        type="email" 
                        placeholder="Email" 
                        required
                        value={formData.email} 
                        onChange={handleChange}
                    />
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control 
                        name="dob"
                        type="date" 
                        placeholder="Date Of Birth" 
                        required
                        value={formData.dob} 
                        onChange={handleChange}
                        max={currentDate}
                    />
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control 
                        name="address"
                        placeholder="Address" 
                        required
                        value={formData.address} 
                        onChange={handleChange}
                    />
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control 
                        name="contactNo"
                        type='number' 
                        min="0"
                        onkeypress="return (event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))"
                        placeholder="Contact No" 
                        required
                        value={formData.contactNo} 
                        onChange={handleChange}
                    />
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control 
                            name="rank"
                            as="select"
                            onChange={handleChange}
                            required
                            value={formData.rank} 
                        >
                        <option value="">Select a rank...</option>
                        {RankList.map((rankOption, index) => (
                            <option key={index} value={rankOption}>{rankOption}</option>
                        ))}
                        </Form.Control>
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                    <Form.Select 
                        name="gender"
                        aria-label="Default select example" 
                        required
                        value={formData.gender}
                        onChange={handleChange}
                    >
                        <option value="">Gender</option>
                        <option value="MALE">MALE</option>
                        <option value="FEMALE">FEMALE</option>
                    </Form.Select>
                        <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                        <Form.Control.Feedback type="invalid">Invalid input</Form.Control.Feedback>
                    </Form.Group>
                    </InputGroup>
                    <Button variant="success" type="submit">Add</Button>{' '}
                </Form.Group>
            </Form>
            )} 
        */}
            <p>Form to add new vessel....WILL BE HERE.</p>
        </>
    );
}

export default AddVesselForm;
