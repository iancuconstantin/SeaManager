import { useState, useEffect } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';
import Collapse from "react-bootstrap/Collapse";
import RankList from './RankList';

function EditEmployeeForm({openEdit,employeeObj, updateEmployee, feedBackMsg, feedBackStatus, setFeedBackStatus, setEmployeesFetch}) {
    const today = new Date();
    const minDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());
    const maxDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 3);
    const [validated, setValidated] = useState(false);
    const [formData, setFormData] = useState();


    useEffect(()=>{
        setFormData({...employeeObj})
    },[employeeObj])

    useEffect(() => {
        if (feedBackStatus) {
            const timer = setTimeout(() => {
            setFeedBackStatus(false);
        }, 3000);
        return () => clearTimeout(timer);
        }
    }, [feedBackStatus]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if(name==="contactNo"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: parseInt(value)
            }));
        } else if(name==="birthDate"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: new Date(value).toISOString().slice(0, 10)
            }));
            
        } else if(name==="readinessDate"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: new Date(value).toISOString().slice(0, 10)
            }));
        }else {
            setFormData((prevFormData) => ({
            ...prevFormData,
            [name]: value
            }));
        }
    };

    const handleSubmit = (e) => {
        const form = e.currentTarget;
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
        } else {
            e.preventDefault();
            e.stopPropagation();
            updateEmployee(formData, employeeObj.employeeId);
            setEmployeesFetch((prevEmployees) =>
                    prevEmployees.map((employee) =>
                        employee.employeeId === formData.employeeId ? formData : employee
                    )
                );
        }
        setValidated(true);
    };

    return (
        <>
            {( openEdit && !feedBackStatus &&
            <Collapse in={openEdit}>
            <Form noValidate validated={validated} onSubmit={handleSubmit} className="justify-content-center">
                <Form.Group className="d-flex" style={{ width: "90%" }}>
                    <InputGroup className="justify-content-center">
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="firstName"
                                placeholder="FirstName"
                                aria-label="FirstName"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.firstName}
                                onChange={handleChange}
                                pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid first name</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="lastName"
                                placeholder="LastName"
                                aria-label="LastName"
                                aria-describedby="basic-addon1"
                                required
                                value={formData.lastName}
                                onChange={handleChange}
                                pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid last name</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                className='form-control-sm'
                                name="email"
                                type="email" 
                                placeholder="Email" 
                                required
                                value={formData.email} 
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid email</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                className='form-control-sm'
                                name="birthDate"
                                type="date" 
                                placeholder="Date Of Birth" 
                                required
                                value={formData.birthDate} 
                                onChange={handleChange}
                                max={minDate.toISOString().split('T')[0]}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Date Of Birth</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                className='form-control-sm'
                                name="address"
                                placeholder="Address" 
                                required
                                value={formData.address} 
                                onChange={handleChange}
                                pattern="^.{5,150}$"
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Address</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                className='form-control-sm'
                                name="contactNo"
                                type='number' 
                                min="100000000"
                                max="999999999999999"
                                placeholder="Contact No" 
                                required
                                value={formData.contactNo} 
                                onChange={handleChange}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Contact No</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Select 
                                className='form-select-sm'
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
                            </Form.Select>
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Rank</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Select 
                                className='form-select-sm'
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
                            <Form.Control.Feedback type="invalid">Please enter a valid Gender</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                className='form-control-sm'
                                name="readinessDate"
                                type="date" 
                                placeholder="Readiness Date" 
                                required
                                value={formData.readinessDate} 
                                onChange={handleChange}
                                min={maxDate.toISOString().split('T')[0]}
                            />
                            <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                            <Form.Control.Feedback type="invalid">Please enter a valid Date Of Birth</Form.Control.Feedback>
                        </Form.Group>
                    </InputGroup>
                        <Button className='btn-sm' variant="success" type="submit">UPDATE</Button>{' '}
                </Form.Group>
            </Form>
            </Collapse>
            )}
            {feedBackStatus && (
                <h3>{feedBackMsg}</h3>
            )}
        </>
    );
}

export default EditEmployeeForm;
