import { useLoaderData,useNavigation } from "react-router-dom";
import VoyagesTable from "./VoyagesTable";
import InputGroup from 'react-bootstrap/InputGroup';
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';

export const Voyages = () => {
    const voyagesFetch = useLoaderData();
    const navigation = useNavigation();
    if(navigation.state === "loading"){
        return <h3>Loading...</h3>
    }

    return(
        <>
            <h2>Voyages page</h2>
            <VoyagesTable voyages={voyagesFetch} />
            <Form.Group className="d-flex justify-content-center mx-auto" style={{ width: "90%" }}>
                <InputGroup>
                    <Form.Control placeholder="Vessel" aria-label="Vessel"/>
                    <Form.Control placeholder="Departure Port" aria-label="Departure Port"/>
                    <Form.Control placeholder="Arrival Port" aria-label="Arrival Port"/>
                    <Button variant="success">Add Voayage</Button>{' '}
                </InputGroup>
            </Form.Group>
        </>
        
    )
}

export const voyageLoader = async () => {
    const response = await fetch('http://localhost:8080/api/voyage');
    const data = await response.json();
    return data;
}