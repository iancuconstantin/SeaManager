import { useLoaderData, useNavigation, redirect } from "react-router-dom";
import { useState } from "react";
import VoyagesTable from "./VoyagesTable";
import InputGroup from 'react-bootstrap/InputGroup';
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Collapse from 'react-bootstrap/Collapse';
import AddVoyageForm from './VoyagesAdd';
import {getBasicAuthHeaders, getBearerAuthHeaders} from '../../authUtils';

export const Voyages = ({isLoggedIn}) => {
    const [voyagesFetch, setVoyagesFetch] = useState(useLoaderData());
    const navigation = useNavigation();
    const [vesselType, setVesselType] = useState('');
    const [open, setOpen] = useState(false);
    

    async function addNewCrewMember (voyageId, employeeId, updatedFormData) {
        try{
            const headers = getBearerAuthHeaders();
            headers.append("Content-Type", "application/json");
            const response = await fetch(`http://localhost:8080/api/voyage/${voyageId}/add/${employeeId}`,{
                method: "PUT",
                headers: headers,
                body: JSON.stringify(updatedFormData),
            });
            if(response.ok){
                console.log("add new crew member SUCCEESS!")
                console.log("RASPUNS OK: ",response);
            } else {
                console.log("add new crew member FAILED!");
                console.log("RASPUNS FAILED: ",response);
            }
    
        } catch (e) {
            console.log("EROARE: ", e);
        }
    }
    
    async function removeCrewListMember (voyageId, employeeId) {
        try{
            const headers = getBearerAuthHeaders();
            headers.append("Content-Type", "application/json");
            const response = await fetch(`http://localhost:8080/api/voyage/${voyageId}/remove/${employeeId}`,{
                method: "DELETE",
                headers: headers
            });
            if(response.ok){
                console.log("remove crew member SUCCEESS!")
                console.log("RASPUNS OK: ",response);
            } else {
                console.log("remove crew member FAILED!");
                console.log("RASPUNS FAILED: ",response);
            }
    
        } catch (e) {
            console.log("EROARE: ", e);
        }
    }

    async function deleteVoyage(voyageId){
        // alert("DELETE will be avaible soon.")
        const confirmed = window.confirm(
          "Are you sure you want to delete this voyaage?"
      );
      if (confirmed) {
          try{
            const headers = getBearerAuthHeaders();
            headers.append("Content-Type", "application/json");
            const response = await fetch(`http://localhost:8080/api/voyage/${voyageId}`,{
                method: "DELETE",
                headers: headers
            });
            if(response.ok){
                console.log("remove voyage SUCCEESS!")
                console.log("RASPUNS OK: ",response);
            } else {
                console.log("remove voyage FAILED!");
                console.log("RASPUNS FAILED: ",response);
            }
  
            const updatedVoyages = voyagesFetch.filter(
                (voyage) => voyage.voyageId !== voyageId
            );
            setVoyagesFetch(updatedVoyages);
  
        } catch (e) {
            console.log("EROARE: ", e);
        }
      }
    }

    if(navigation.state === "loading"){
        return <h3>Loading...</h3>
    }

    return(
        <>
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
                deleteVoyage={deleteVoyage}
                fetchCrewList={fetchCrewList}
                addNewCrewMember={addNewCrewMember}
                removeCrewListMember={removeCrewListMember}
            />
        </>
        
    )
}

export const voyageLoader = async () => {

    const token = localStorage.getItem("token");
    
    if (!token) {
        return redirect("/login");
    }

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
    return data.crewList;
}