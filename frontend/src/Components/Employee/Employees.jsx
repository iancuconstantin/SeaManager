import { useLoaderData,useNavigation } from "react-router-dom";
import { useState } from "react";
import EmployeeTable from "./EmployeeTable";
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import { Form } from 'react-bootstrap';
import RankList from './RankList';

export const Employees = () => {
    const employeesFetch = useLoaderData();
    const navigation = useNavigation();
    const [rank, setRank] = useState('');
    

    if(navigation.state === "loading"){
        return <h3>Loading...</h3>
    }
    

    const handleRankChange = (event) => {
        setRank(event.target.value);
    };

    return(
        <>
            <h2>Employees page</h2>
            <EmployeeTable employees={employeesFetch} fetchData={fetchCertificates}/>
            <Form.Group className="d-flex justify-content-center mx-auto" style={{ width: "90%" }}>
                <InputGroup>
                    <Form.Control
                        placeholder="FirstName"
                        aria-label="FirstName"
                        aria-describedby="basic-addon1"
                    />
                    <Form.Control
                    placeholder="LastName"
                    aria-label="LastName"
                    aria-describedby="basic-addon1"
                    />
                    <Form.Control type="email" placeholder="Email" />
                    <Form.Control type="date" placeholder="Date Of Birth" />
                    <Form.Control placeholder="Address"/>
                    <Form.Control type='number' placeholder="Contact No"/>
                    <Form.Control as="select" value={rank} onChange={handleRankChange}>
                        <option value="">Select a rank...</option>
                        {RankList.map((rankOption, index) => (
                            <option key={index} value={rankOption}>{rankOption}</option>
                        ))}
                    </Form.Control>
                    <Form.Select aria-label="Default select example">
                        <option>Gender</option>
                        <option value="MALE">MALE</option>
                        <option value="FEMALE">FEMALE</option>
                    </Form.Select>
                    <Button variant="success">Add Employee</Button>{' '}
                </InputGroup>
            </Form.Group>
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