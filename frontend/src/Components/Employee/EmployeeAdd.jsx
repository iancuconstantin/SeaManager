import { useState, useEffect } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';
import RankList from './RankList';

function AddEmployeeForm({open, setOpen, addNewEmployee, feedBackStatus, setFeedBackStatus, feedBackMsg}) {
    const today = new Date();
    const minDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());
    const maxDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3);
    const [validated, setValidated] = useState(false);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        birthDate: '',
        address: '',
        contactNo: null,
        rank: '',
        gender: '',
        age:'',
        certificates:[],
        contracts:[],
        employeeId:'',
        readinessDate:''
    });


    useEffect(() => {
        if (feedBackStatus) {
            const timer = setTimeout(() => {
            setFeedBackStatus(false);
        }, 3000);
        return () => clearTimeout(timer);
        }
    }, [feedBackStatus]);

    useEffect(()=>{
        setValidated(true);
    },[formData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if(name==="contactNo"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: parseInt(value)
            }));
        } else if(name==="birthDate" || name==="readinessDate"){
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

    const calculateAge = (dob) => {
        const diffMs = Date.now() - dob.getTime();
        const ageDate = new Date(diffMs);
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    };

    const handleSubmit = (e) => {
        const form = e.currentTarget;
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
        } else {
            e.preventDefault();
            e.stopPropagation();
            const dob = new Date(formData.birthDate);
            const age = calculateAge(dob);
            formData.age = age;
            addNewEmployee(formData);
            setFormData({
                firstName: '',
                lastName: '',
                email: '',
                birthDate: '',
                address: '',
                contactNo: null,
                rank: '',
                gender: '',
                age:'',
                certificates:[],
                contracts:[],
                employeeId:'',
                readinessDate:''
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
                            <Form.Control
                                name="firstName"
                                placeholder="FirstName"
                                aria-label="FirstName"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.firstName}
                                className='form-control-sm'
                                onChange={handleChange}
                                pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid first name</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                name="lastName"
                                placeholder="LastName"
                                aria-label="LastName"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.lastName}
                                className='form-control-sm'
                                onChange={handleChange}
                                pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid last name</Form.Control.Feedback>
                        </Form.Group>
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
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="birthDate"
                                type="date" 
                                placeholder="Date Of Birth" 
                                required
                                value={formData.birthDate} 
                                className='form-control-sm'
                                onChange={handleChange}
                                max={minDate.toISOString().split('T')[0]}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Date Of Birth</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="address"
                                placeholder="Address" 
                                required
                                value={formData.address} 
                                className='form-control-sm'
                                onChange={handleChange}
                                pattern="^.{5,150}$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Address</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="contactNo"
                                type='number' 
                                min="100000000"
                                max="999999999999999"
                                placeholder="Contact No" 
                                required
                                value={formData.contactNo} 
                                className='form-control-sm'
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Contact No</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Select 
                                name="rank"
                                as="select"
                                className='form-select-sm'
                                onChange={handleChange}
                                required
                                value={formData.rank} 
                            >
                                <option value="">Select a rank...</option>
                                {RankList.map((rankOption, index) => (
                                    <option key={index} value={rankOption}>{rankOption}</option>
                                ))}
                            </Form.Select>
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Rank</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Select 
                                name="gender"
                                aria-label="Default select example"
                                required
                                value={formData.gender}
                                className='form-select-sm'
                                onChange={handleChange}
                            >
                                <option value="">Gender</option>
                                <option value="MALE">MALE</option>
                                <option value="FEMALE">FEMALE</option>
                            </Form.Select>
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Gender</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                name="readinessDate"
                                type="date" 
                                placeholder="Readiness Date" 
                                required
                                value={formData.readinessDate} 
                                className='form-control-sm'
                                onChange={handleChange}
                                min={maxDate.toISOString().split('T')[0]}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Readiness Date</Form.Control.Feedback>
                        </Form.Group>
                    </InputGroup>
                    <Button className="btn-sm" variant="success" type="submit">Add</Button>{' '}
                </Form.Group>
            </Form>
            )}
        </>
    );
}

export default AddEmployeeForm;