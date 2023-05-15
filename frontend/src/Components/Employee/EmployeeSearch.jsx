import { Form, InputGroup } from 'react-bootstrap';
import RankList from './RankList';

const EmployeeSearch = ({filterBy}) => {
    return(
        <>
            <h3>Filter field</h3>
            <Form>
                <Form.Group>
                    <InputGroup className="justify-content-center">
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="employeeId"
                                placeholder="Id"
                                aria-label="Id"
                                aria-describedby="basic-addon1"
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="firstName"
                                placeholder="FirstName"
                                aria-label="FirstName"
                                aria-describedby="basic-addon1"
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="lastName"
                                placeholder="LastName"
                                aria-label="LastName"
                                aria-describedby="basic-addon1"
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="email"
                                type="email" 
                                placeholder="Email" 
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="age"
                                type="number" 
                                placeholder="Age" 
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="address"
                                placeholder="Address"
                                aria-label="Address"
                                aria-describedby="basic-addon1"
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control
                                className='form-control-sm'
                                name="contactNo"
                                type='number'
                                min="100000000"
                                max="999999999999999"
                                placeholder="Contact No" 
                                onChange={filterBy}
                            />
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Select 
                                className='form-select-sm'
                                name="rank"
                                // as="select"
                                onChange={filterBy}
                            >
                                <option value="">Select a rank...</option>
                                {RankList.map((rankOption, index) => (
                                    <option key={index} value={rankOption}>{rankOption}</option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Select
                                className='form-select-sm'
                                name="gender"
                                aria-label="Default select example"
                                onChange={filterBy}
                            >
                                <option value="">Gender</option>
                                <option value="MALE">MALE</option>
                                <option value="FEMALE">FEMALE</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="p-1">
                            <Form.Control 
                                className='form-control-sm'
                                name="readinessDate"
                                type="date" 
                                placeholder="Readiness Date" 
                                onChange={filterBy}
                            />
                        </Form.Group>
                    </InputGroup>
                </Form.Group>
            </Form>
        </>
    )
}

export default EmployeeSearch;