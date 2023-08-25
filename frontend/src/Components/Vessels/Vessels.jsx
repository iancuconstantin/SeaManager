import { useLoaderData,useNavigation,useNavigate } from "react-router-dom";
import { useState,useEffect } from "react";
import VesselsTable from "./VesselsTable";
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import { Form } from 'react-bootstrap';
import VesselType from "./VesselType";
import Collapse from 'react-bootstrap/Collapse';
import AddVesselForm from './VesselAdd';
import {getBasicAuthHeaders, getBearerAuthHeaders} from '../../authUtils';

export const Vessels = ({ isLoggedIn }) => {
    const [vesselsFetch, setVesselsFetch] = useState(useLoaderData());
    const navigation = useNavigation();
    const [vesselType, setVesselType] = useState('');
    const [open, setOpen] = useState(false);
    const [feedBackMsg, setFeedBackMsg] = useState("");
    const [feedBackStatus, setFeedBackStatus] = useState(false);
    const [sortColumn, setSortColumn] = useState("");
    const [sortOrder, setSortOrder] = useState("asc");
    const navigate = useNavigate();

    // useEffect(() => {
    //     if (!isLoggedIn) {
    //     navigate('/login')
    //     }
    // }, [isLoggedIn]);

    const handleTypeChange = (event) => {
        setVesselType(event.target.value);
    };

    async function addNewVessel(formData) {
        try {

            //const response = await fetchBearerAuthWithBody("http://localhost:8080/api/employee","POST", formData);
            console.log(JSON.stringify(formData))

            const headers = getBearerAuthHeaders();
            headers.append("Content-Type", "application/json");
            const response = await fetch("http://localhost:8080/api/vessel", {
                method: "POST",
                headers: headers,
                body: JSON.stringify(formData),
            });
            if (response.ok) {
                setFeedBackStatus(true);
                setFeedBackMsg("New vessel was added!");
                let vessel = await response.json();
                formData.vesselId = vessel.vesselId;
                setVesselsFetch([formData, ...vesselsFetch]);
            } else {
                if (response.status === 422) {
                    setFeedBackStatus(true);
                    setFeedBackMsg(`IMO ${formData.imonumber} already exist!`);
                } else {
                    setFeedBackStatus(true);
                    setFeedBackMsg("Something went wrong! Try again later.");
                }
            }
        } catch (error) {
            setFeedBackStatus(true);
            console.log("verificare eroare la add new vessel: ", error)
            setFeedBackMsg("Connection error!");
        }
    }

    async function updateVessel(formData, vesselID) {
        const headers = getBearerAuthHeaders();
        headers.append("Content-Type", "application/json");
        try {
            const response = await fetch(
                `http://localhost:8080/api/vessel/${vesselID}`,
                {
                    method: "PUT",
                    headers: headers,
                    body: JSON.stringify(formData),
                }
            );

            if (response.ok) {
                setFeedBackStatus(true);
                setFeedBackMsg("Vessel was successfuly updated!");
                setVesselsFetch((prevVessel) =>
                    prevVessel.map((vessel) =>
                        vessel.vesselId === formData.vesselId ? formData : vessel
                    )
                );
                // setEmployeesFetch([...employeesFetch, formData]);
            } else {
                setFeedBackStatus(true);
                setFeedBackMsg("Something went wrong! Try again later.");
            }
        } catch (e) {
            console.error("Connection error:", e);
            setFeedBackStatus(true);
            setFeedBackMsg("Connection error!");
        }
    }

    async function deleteVsl(vesselId) {
        const confirmed = window.confirm(
            "Are you sure you want to delete this vessel?"
        );
        if (confirmed) {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/vessel/${vesselId}`,
                    {
                        method: "DELETE",
                        headers: getBearerAuthHeaders()
                    }
                );

                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                // Do something with the successful response
                const updatedVessel = vesselsFetch.filter(
                    (vessel) => vessel.vesselId !== vesselId
                );
                setVesselsFetch(updatedVessel);
                // window.location.reload();
                // alert("DELETE SUCCESS!")
            } catch (error) {
                console.error("There was a problem with the delete request:", error);
            }
        }
    }

    const handleSort = (column) => {
        let newOrder = "desc";
        if (sortColumn === column && sortOrder === "desc") {
            newOrder = "asc";
        }
        setSortColumn(column);
        setSortOrder(newOrder);

        const sortedVessels = [...vesselsFetch].sort((a, b) => {
            let result = 0;
            if (a[column] > b[column]) {
                result = 1;
            } else if (a[column] < b[column]) {
                result = -1;
            }
            if (newOrder === "asc") {
                result *= -1;
            }
            return result;
        });
        setVesselsFetch(sortedVessels);
    };

    if(navigation.state === "loading"){
        return <h3>Loading...</h3>
    }

    return(
        <>
            {/* ADD EMPLOYEE */}
            <Button
                onClick={() => setOpen(!open)}
                aria-controls="example-collapse-text"
                aria-expanded={open}
                variant="success"
            >
                Add New Vessel
            </Button>{' '}

            {feedBackStatus && (
                <h3>{feedBackMsg}</h3>
            )}

            <Collapse in={open}>
                <AddVesselForm 
                    open={open}
                    addNewVessel={addNewVessel}
                    feedBackStatus={feedBackStatus}
                    setFeedBackStatus={setFeedBackStatus}
                    setOpen={setOpen}
                />
            </Collapse>
            {/* ADD EMPLOYEE */}
            <VesselsTable 
                vessels={vesselsFetch} 
                fetchData={fetchVesselDetails}
                updateVessel={updateVessel}
                deleteVsl={deleteVsl}
                feedBackStatus={feedBackStatus}
                setFeedBackStatus={setFeedBackStatus}
                feedBackMsg={feedBackMsg}
                handleSort={handleSort}
                />
            {/* <Form.Group className="d-flex justify-content-center mx-auto" style={{ width: "90%" }}>
                <InputGroup>
                    <Form.Control placeholder="Name" aria-label="Name"/>
                    <Form.Control as="select" value={vesselType} onChange={handleTypeChange}>
                        <option value="">Select a vessel type...</option>
                        {VesselType.map((type, index) => (
                            <option key={index} value={type}>{type}</option>
                        ))}
                    </Form.Control>
                    <Form.Control placeholder="Flag" aria-label="Flag"/>
                    <Form.Control type='number' placeholder="IMO Number"/>
                    <Button variant="success">Add Vessel</Button>{' '}
                </InputGroup>
            </Form.Group> */}
        </>
        
    )
}

export const vesselsLoader = async () => {
    const response = await fetch('http://localhost:8080/api/vessel', {
        headers: getBearerAuthHeaders()
    });
    const data = await response.json();
    console.log(data)
    return data;
}

export const fetchVesselDetails = async (vesselId) => {
    const response = await fetch(`http://localhost:8080/api/vessel/${vesselId}`, {
        headers: getBearerAuthHeaders()
    });
    const data = await response.json();
    return data;
}