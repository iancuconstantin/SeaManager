import {React, useState} from 'react';
import Collapse from 'react-bootstrap/Collapse';
import { Button} from 'react-bootstrap';
import EmployeeEdit from './EmployeeEdit';
import EmployeeSearch from "./EmployeeSearch";


const EmployeeTable = ({ employees, fetchData, updateEmployee, deleteEmp, handleSort }) => {

    const [open, setOpen] = useState({});
    const [openEdit, setOpenEdit] = useState({});
    const [feedBackMsg, setFeedBackMsg] = useState("");
    const [feedBackStatus, setFeedBackStatus] = useState(false);

    const [filterValues, setFilterValues] = useState({ employeeId: '',firstName: '',lastName: '', email: '',age:'',address: '',contactNo: '', rank:'', gender:'', readinessDate: ''});
    

    const trigger = (employeeId) => {
        fetchData(employeeId);
        setOpen(prevOpen => ({ ...prevOpen, [employeeId]: !prevOpen[employeeId] }));
    }

    const updateEmp = (employeeId) => {
        setOpenEdit(prevOpen => ({ ...prevOpen, [employeeId]: !prevOpen[employeeId] }));
    }

    const handleFilterChange = (e) => {
        const {name, value} = e.target
        setFilterValues((prevValues) => ({ ...prevValues, [name]: value }));
    }
    
    const filterEmployees = employees.filter(
            (employee) =>
                (employee.employeeId === parseInt(filterValues.employeeId) || filterValues.employeeId === '') && 
                (employee.firstName === filterValues.firstName || filterValues.firstName ==='') &&
                (employee.lastName === filterValues.lastName || filterValues.lastName ==='') &&
                (employee.email === filterValues.email || filterValues.email ==='') &&
                (employee.age === parseInt(filterValues.age) || filterValues.age ==='') &&
                (employee.address === filterValues.address || filterValues.address ==='') && 
                (employee.contactNo === parseInt(filterValues.contactNo) || filterValues.contactNo ==='') &&
                (employee.rank === filterValues.rank || filterValues.rank ==='') &&
                (employee.gender === filterValues.gender || filterValues.gender ==='') &&
                (employee.readinessDate === filterValues.readinessDate || filterValues.readinessDate ==='')
        );
    
    return (
        <>
            <EmployeeSearch filterBy={handleFilterChange}/>
            <div style={{ width: "90%", fontSize: 12}} className="d-flex justify-content-center mx-auto my-3">
                <table>
                    <thead>
                    <tr>
                        <th onClick={() => handleSort('employeeId')}>Employee Id</th>
                        <th onClick={() => handleSort('firstName')}>First Name</th>
                        <th onClick={() => handleSort('lastName')}>Last Name</th>
                        <th onClick={() => handleSort('email')}>Email</th>
                        <th onClick={() => handleSort('birthDate')}>Birth Date</th>
                        <th onClick={() => handleSort('age')}>Age</th>
                        <th onClick={() => handleSort('address')}>Address</th>
                        <th onClick={() => handleSort('contactNo')}>Contact No</th>
                        <th onClick={() => handleSort('rank')}>Rank</th>
                        <th onClick={() => handleSort('gender')}>Gender</th>
                        <th onClick={() => handleSort('readinessDate')}>Readiness Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filterEmployees && filterEmployees.map((employee) => (
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
                                <td className="py-3">{employee.readinessDate || "N/A"}</td>
                                <Button 
                                    variant="primary"
                                    className="mx-1 btn-sm"
                                    onClick={()=>trigger(employee.employeeId)}
                                    aria-controls={`example-collapse-text-${employee.employeeId}`}
                                    aria-expanded={open[employee.employeeId]}
                                >
                                    📑
                                </Button>{' '}
                                <Button 
                                    variant="info" 
                                    className="mx-1 btn-sm"
                                    onClick={()=>updateEmp(employee.employeeId)}
                                >
                                    ✍️
                                </Button>{' '}
                                <Button 
                                    variant="danger"
                                    className="mx-1 btn-sm"
                                    onClick={()=>deleteEmp(employee.employeeId)}
                                >
                                        ❌
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
                            <tr>
                                <td colSpan="10">
                                    <Collapse in={openEdit[employee.employeeId]}>
                                        <EmployeeEdit openEdit={openEdit[employee.employeeId]} employeeObj={employee} updateEmployee={updateEmployee} feedBackMsg={feedBackMsg} feedBackStatus={feedBackStatus} setFeedBackStatus={setFeedBackStatus}/>
                                    </Collapse>
                                </td>
                            </tr>
                        </>
                    ))}
                    </tbody>
                </table>
            </div>
        </>
        
    );
};

export default EmployeeTable;