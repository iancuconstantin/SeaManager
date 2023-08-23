import {useState, useEffect} from 'react';
import { Collapse, Form, InputGroup, Button } from 'react-bootstrap';
import vesselType from './VesselType';

function VesselEditForm({vesselObj, openEdit, updateVessel, feedBackStatus, setFeedBackStatus, feedBackMsg}) {

    const [validated, setValidated] = useState(false);
    const [formData, setFormData] = useState();

    useEffect(()=>{
        setFormData({...vesselObj})
    },[vesselObj])

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
        console.log("VERIFICAREEEEE: ", formData);
    },[formData])

    const handleChange = (e) => {
        const { name, value } = e.target;
        console.log("verificare name: ", name);
        console.log("verificare value: ", value);
        if(name==="imonumber"){
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: parseInt(value)
                }));
        } else {
            setFormData((prevFormData) => ({
                ...prevFormData,
                [name]: value
                }));
        }
    }

    const handleSubmit = (e) => {
        //handle submit logic goes here
        const form = e.currentTarget;
        if(form.checkValidity() === false){
            e.preventDefault();
            e.stopPropagation();
        } else {
            e.preventDefault();
            e.stopPropagation();
            updateVessel(formData, vesselObj.vesselId);
        }
    }

    return (
        <>
            {(openEdit && !feedBackStatus && 
                <Collapse in={openEdit}>
                    <Form noValidate validated={validated} onSubmit={handleSubmit} className="justify-content-center">
                        <Form.Group className="d-flex" style={{ width: "90%" }}>
                            <InputGroup className="justify-content-center">
                    {/* VESSEL NAME */}
                            <Form.Group className="p-1">
                                <Form.Control
                                    className='form-control-sm'
                                    name="name"
                                    placeholder="VesselName"
                                    aria-label="vesselName"
                                    aria-describedby="basic-addon1"
                                    required
                                    value={formData.name}
                                    onChange={handleChange}
                                    pattern="^[a-zA-Z]{2,25}(([',. -][a-zA-Z ])?[a-zA-Z]*)$"
                                />
                                <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                                <Form.Control.Feedback type="invalid">Please enter a name</Form.Control.Feedback>
                            </Form.Group>

                            {/* VESSEL TYPE */}
                            <Form.Group className="p-1">
                                <Form.Select 
                                    className='form-select-sm'
                                    name="type"
                                    as="select"
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
                                <Form.Control.Feedback type="invalid">Please select a Type</Form.Control.Feedback>
                            </Form.Group>

                            {/* VESSEL FLAG */}
                            <Form.Group className="p-1">
                                <Form.Control
                                    className='form-control-sm'
                                    name="flag"
                                    placeholder="VesselFlag"
                                    aria-label="vesselFlag"
                                    aria-describedby="basic-addon1"
                                    required
                                    value={formData.flag}
                                    onChange={handleChange}
                                    pattern="[a-zA-Z]*"
                                />
                                <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                                <Form.Control.Feedback type="invalid">Please enter a flag</Form.Control.Feedback>
                            </Form.Group>
                            {/* IMO NUMBER */}
                            <Form.Group className="p-1">
                                <Form.Control 
                                    className='form-control-sm'
                                    name="imonumber"
                                    type='number' 
                                    min="1000000"
                                    max="9999999"
                                    placeholder="IMO No" 
                                    required
                                    value={formData.imonumber} 
                                    onChange={handleChange}
                                />
                                <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                                <Form.Control.Feedback type="invalid">IMO No must have 7 digits!</Form.Control.Feedback>
                            </Form.Group>

                            </InputGroup>
                            <Button className='btn-sm' variant="success" type="submit">UPDATE</Button>{' '}
                        </Form.Group>
                    </Form>
                </Collapse>
            )}
            {/* {feedBackStatus && (
                <h3>{feedBackMsg}</h3>
            )} */}
        </>
    );
}

export default VesselEditForm;