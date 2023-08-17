import { useLoaderData, useNavigation,useNavigate } from "react-router-dom";
import { useState,useEffect } from "react";
import VoyagesTable from "./VoyagesTable";
import InputGroup from 'react-bootstrap/InputGroup';
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Collapse from 'react-bootstrap/Collapse';
import AddVoyageForm from './VoyagesAdd';
import {getBasicAuthHeaders, getBearerAuthHeaders} from '../../authUtils';

export const Voyages = ({isLoggedIn}) => {
    const voyagesFetch = useLoaderData();
    const navigation = useNavigation();
    const [vesselType, setVesselType] = useState('');
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    // async function addNewCrewMember (voyageId,employeeId,formData) {
    //     // console.log("VERIFICARE FORMDATA LA PRIMIRE: ", formData);
    //     // const voyageId = formData.voyageId;
    //     // const employeeId = formData.employeeId;
    //     console.log("formDATA addNewCrewMember: ", formData);
    //     try{
    //         const headers = getBearerAuthHeaders();
    //         headers.append("Content-Type", "application/json");
    //         const response = await fetch(`http://localhost:8080/api/voyage/${voyageId}/add/${employeeId}`,{
    //             method: "PUT",
    //             headers: headers,
    //             body: JSON.stringify(formData),
    //         });
    //         if(response.ok){
    //             console.log("add new crew member SUCCEESS!")
    //             console.log("RASPUNS OK: ",response);
    //         } else {
    //             console.log("add new crew member FAILED!");
    //             console.log("RASPUNS FAILED: ",response);
    //         }

    //     } catch (e) {
    //         console.log("EROARE: ", e);
    //     }
    // }

    if(navigation.state === "loading"){
        return <h3>Loading...</h3>
    }

    return(
        <>
            <h2>Voyages page</h2>

            {/* ADD VOYAGE */}
            <Button
                onClick={() => setOpen(!open)}
                aria-controls="example-collapse-text"
                aria-expanded={open}
                variant="success"
            >
                Add New Voyage
            </Button>{' '}
            <Collapse in={open}>
                <AddVoyageForm open={open}/>
            </Collapse>
            {/* ADD EMPLOYEE */}


            <VoyagesTable 
                voyages={voyagesFetch} 
                fetchData={fetchCrewList}
                // addNewCrewMember = {addNewCrewMember}
            />
            {/* <Form.Group className="d-flex justify-content-center mx-auto" style={{ width: "90%" }}>
                <InputGroup>
                    <Form.Control placeholder="Vessel" aria-label="Vessel"/>
                    <Form.Control placeholder="Departure Port" aria-label="Departure Port"/>
                    <Form.Control placeholder="Arrival Port" aria-label="Arrival Port"/>
                    <Button variant="success">Add Voayage</Button>{' '}
                </InputGroup>
            </Form.Group> */}
        </>
        
    )
}

export const voyageLoader = async () => {
    const response = await fetch('http://localhost:8080/api/voyage', {
        headers: getBearerAuthHeaders()
    });
    const data = await response.json();
    console.log("verificare data: ", data)
    return data;
}

export const fetchCrewList = async (voyageId) => {
    const response = await fetch(`http://localhost:8080/api/voyage/${voyageId}`, {
        headers: getBearerAuthHeaders()
    });
    const data = await response.json();
    return data;
}