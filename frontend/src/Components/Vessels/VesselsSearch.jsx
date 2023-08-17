import * as React from 'react';
import { Form, InputGroup, Button, FormGroup, FormControl } from 'react-bootstrap';
import vesselType from './VesselType';

const VesselsSearch = ({filterValues, filterBy, clearFilter}) => {
    return (
        <>
            <h3>Filter field</h3>
            <Form>
                <Form.Group>
                    <InputGroup className="justify-content-center">
                        {/* VESSEL ID */}
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="vesselId"
                                placeholder="Id"
                                aria-label="Id"
                                aria-describedby="basic-addon1"
                                value={filterValues.vesselId}
                                onChange={filterBy}
                            />
                        </Form.Group>
                        
                        {/* VESSEL NAME */}
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="name"
                                placeholder="Name"
                                aria-label="Name"
                                aria-describedby="basic-addon1"
                                value={filterValues.name}
                                onChange={filterBy}
                            />
                        </Form.Group>

                        {/* TYPE */}
                        <Form.Group className="p-1">
                            <Form.Select 
                                className='form-select-sm'
                                name="type"
                                value={filterValues.type}
                                onChange={filterBy}
                            >
                                <option value="">Select a type...</option>
                                {vesselType.map((type, index) => (
                                    <option key={index} value={type}>{type}</option>
                                ))}
                            </Form.Select>
                        </Form.Group>

                        {/* FLAG */}
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="flag"
                                placeholder="Flag"
                                aria-label="Flag"
                                aria-describedby="basic-addon1"
                                value={filterValues.flag}
                                onChange={filterBy}
                            />
                        </Form.Group>

                        {/* IMO NUMBER */}
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="imonumber"
                                type='number'
                                min="1000000"
                                max="9999999"
                                placeholder="IMO Number"
                                value={filterValues.imonumber}
                                onChange={filterBy}
                            />
                        </Form.Group>

                    </InputGroup>
                </Form.Group>
                <Button
                    variant="danger"
                    className="mx-1 btn-sm"
                    onClick={() => clearFilter()}
                >
                    ❌
                </Button>
            </Form>
        </>
    );
};

export default VesselsSearch;