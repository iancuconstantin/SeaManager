import { useLoaderData,useNavigation } from "react-router-dom";
import { useState } from "react";
import EmployeeTable from "./EmployeeTable";
import Button from 'react-bootstrap/Button';
import Collapse from 'react-bootstrap/Collapse';
import AddEmployeeForm from "./EmployeeAdd";

export const Employees = () => {
    const employeesFetch = useLoaderData();
    const navigation = useNavigation();
    const [open, setOpen] = useState(false);
    

    if(navigation.state === "loading"){
        return <h3>Loading...</h3>
    }
    

    return(
        <>
            <h2>Employees page</h2>
            {/* ADD EMPLOYEE */}
            <Button
                onClick={() => setOpen(!open)}
                aria-controls="example-collapse-text"
                aria-expanded={open}
                variant="success"
            >
                Add New Employee
            </Button>{' '}
            <Collapse in={open}>
                <AddEmployeeForm open={open}/>
            </Collapse>
            {/* ADD EMPLOYEE */}
            <EmployeeTable employees={employeesFetch} fetchData={fetchCertificates}/>
        </>
        
    )
}



export const employeeLoader = async () => {
    const response = await fetch('http://localhost:8080/api/employee');
    const data = await response.json();
    return data;
}
export const fetchCertificates = async (employeeId) => {
    const response = await fetch(`http://localhost:8080/api/employee/${employeeId}`);
    const data = await response.json();
    console.log("verificare certif. :", data.certificates);
}