import {React, useState} from 'react';
import { Example } from './EmployeeAccordion';
import Collapse from 'react-bootstrap/Collapse';
import { Button} from 'react-bootstrap';


const EmployeeTable = ({ employees, fetchData }) => {

    const [open, setOpen] = useState({});

    const trigger = (employeeId) => {
        fetchData(employeeId);
        console.log(employeeId);
        setOpen(prevOpen => ({ ...prevOpen, [employeeId]: !prevOpen[employeeId] }));
    }

    return (
        <div style={{ width: "90%" }} className="d-flex justify-content-center mx-auto my-3">
            <table>
                <thead>
                <tr>
                    <th>Employee Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Birth Date</th>
                    <th>Age</th>
                    <th>Address</th>
                    <th>Contact No</th>
                    <th>Rank</th>
                    <th>Gender</th>
                    <th>Current Voyage</th>
                </tr>
                </thead>
                <tbody>
                {employees.map((employee) => (
                    <>
                        <tr className='m-3' key={employee.employeeId}>
                            <td className="py-3">{employee.employeeId}</td>
                            <td className="py-3">{employee.firstName}</td>
                            <td className="py-3">{employee.lastName}</td>
                            <td className="py-3">{employee.email}</td>
                            <td className="py-3">{employee.birthDate}</td>
                            <td className="py-3">{employee.age}</td>
                            <td className="py-3">{employee.address}</td>
                            <td className="py-3">{employee.contactNo}</td>
                            <td className="py-3">{employee.rank}</td>
                            <td className="py-3">{employee.gender}</td>
                            <td className="py-3">{employee.currentVoyage || "N/A"}</td>
                            <Button variant="danger">Delete</Button>{' '}
                            {/* <Button variant="primary" onClick={()=>fetchData(employee.employeeId)}>✅</Button>{' '} */}
                            <Button 
                                variant="primary" 
                                onClick={()=>trigger(employee.employeeId)}
                                aria-controls={`example-collapse-text-${employee.employeeId}`}
                                aria-expanded={open[employee.employeeId]}
                                >
                                    ✅
                            </Button>{' '}
                        </tr>
                        <tr>
                            <td colSpan="10">
                                <Collapse in={open[employee.employeeId]}>
                                    <div style={{textAlign: "center"}} id={`${employee.employeeId}`}>
                                        <table style={{margin: "auto", width:"70%"}}>
                                            <thead>
                                                <tr>
                                                <th>Type</th>
                                                <th>Serial Number</th>
                                                <th>Issue Date</th>
                                                <th>Expiry Date</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {employee.certificates.map(certificate => (
                                                <tr key={certificate.serialNumber}>
                                                    <td>{certificate.type}</td>
                                                    <td>{certificate.serialNumber}</td>
                                                    <td>{certificate.issueDate}</td>
                                                    <td>{certificate.expiryDate}</td>
                                                </tr>
                                                ))}
                                            </tbody>
                                        </table>
                                    </div>
                                </Collapse>
                            </td>
                        </tr>
                    </>
                ))}
                </tbody>
            </table>
        </div>
        
    );
};

export default EmployeeTable;
