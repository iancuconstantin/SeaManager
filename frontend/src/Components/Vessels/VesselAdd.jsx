import { useState, useEffect } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';
import vesselType from './VesselType';
// import RankList from './RankList';

function AddVesselForm({open, addNewVessel, setOpen, feedBackStatus, setFeedBackStatus}) {
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
        mmsi: '',
        vesselId: ''
    });

    useEffect(() => {
        if (feedBackStatus) {
            const timer = setTimeout(() => {
            setFeedBackStatus(false);
        }, 3000);
        return () => clearTimeout(timer);
        }
    }, [feedBackStatus]);

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

    useEffect(()=>{
        setValidated(true);
    },[formData]);

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
            e.preventDefault();
            e.stopPropagation();
        console.log(formData)
        } else {
        // submit form data here
        e.preventDefault();
        e.stopPropagation();
        console.log(formData)
        addNewVessel(formData)
        setFormData({
            name: '',
            type: '',
            flag: '',
            callSign: '',
            yearBuilt: '',
            email: '',
            imonumber: '',
            dwt: '',
            grt: '',
            mmsi: '',
            vesselId: ''
        })
        }
        setOpen(!open)
        // setValidated(true);
    };

    return (
        <>
            {open && (
            <Form noValidate validated={validated} onSubmit={handleSubmit} className="justify-content-center">
                <Form.Group className="d-flex" style={{ width: "90%" }}>
                    <InputGroup className="justify-content-center m-3">
                        <Form.Group className="p-1">
                            {/* VESSEL NAME */}
                            <Form.Control
                                name="name"
                                placeholder="Name"
                                aria-label="Name"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.name}
                                className='form-control-sm'
                                onChange={handleChange}
                                pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid name</Form.Control.Feedback>
                        </Form.Group>
                        {/* VESSEL TYPE */}
                        <Form.Group className="p-1">
                            <Form.Select 
                                name="type"
                                as="select"
                                className='form-select-sm'
                                onChange={handleChange}
                                required
                                value={formData.type} 
                            >
                                <option value="">Select a type...</option>
                                {vesselType.map((type, index) => (
                                    <option key={index} value={type}>{type}</option>
                                ))}
                            </Form.Select>
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Type</Form.Control.Feedback>
                        </Form.Group>
                        {/* VESSEL FLAG */}
                        <Form.Group className="p-1">
                            {/* VESSEL NAME */}
                            <Form.Control
                                name="flag"
                                placeholder="Flag"
                                aria-label="Flag"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.flag}
                                className='form-control-sm'
                                onChange={handleChange}
                                pattern="[a-zA-Z]*"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid flag</Form.Control.Feedback>
                        </Form.Group>
                        {/* VESSEL MMSI */}
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="mmsi"
                                type='number' 
                                min="100000000"
                                max="999999999"
                                placeholder="MMSI" 
                                required
                                value={formData.mmsi} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid MMSI No</Form.Control.Feedback>
                        </Form.Group>

                        {/* VESSEL CALL SIGN */}
                        <Form.Group className="p-1">
                            <Form.Control
                                name="callSign"
                                placeholder="Call Sign"
                                aria-label="Call Sign"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.callSign}
                                className='form-control-sm'
                                onChange={handleChange}
                                pattern="[a-zA-Z\d]{5}"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid call sign</Form.Control.Feedback>
                        </Form.Group>

                        {/* VESSEL GRT */}
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="grt"
                                type='number' 
                                min="10000"
                                max="999999"
                                placeholder="GRT" 
                                required
                                value={formData.grt} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid GRT No</Form.Control.Feedback>
                        </Form.Group>

                        {/* VESSEL DWT */}
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="dwt"
                                type='number' 
                                min="10000"
                                max="999999"
                                placeholder="DWT" 
                                required
                                value={formData.dwt} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid DWT No</Form.Control.Feedback>
                        </Form.Group>

                        {/* VESSEL YEAR BUILT */}
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="yearBuilt"
                                type='number' 
                                min="1000"
                                max="9999"
                                placeholder="Year Built" 
                                required
                                value={formData.yearBuilt} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Year Built</Form.Control.Feedback>
                        </Form.Group>

                        {/* VESSEL EMAIL */}
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="email"
                                type="email" 
                                placeholder="Email" 
                                required
                                value={formData.email} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid email</Form.Control.Feedback>
                        </Form.Group>

                        {/* VESSEL IMO NUMBER */}
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="imonumber"
                                type='number' 
                                min="1000000"
                                max="9999999"
                                placeholder="Year Built" 
                                required
                                value={formData.imonumber} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid IMO Number</Form.Control.Feedback>
                        </Form.Group>

                    </InputGroup>
                    <Button className="btn-sm" variant="success" type="submit">Add</Button>{' '}
                </Form.Group>
            </Form>
            )}
        </>
    );
}

export default AddVesselForm;