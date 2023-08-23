import {React,useState, Fragment} from 'react';
import Button from 'react-bootstrap/Button';
import Tooltip from 'react-bootstrap/Tooltip'
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Collapse from "react-bootstrap/Collapse";
import { Form } from 'react-bootstrap';
// import {getBasicAuthHeaders, getBearerAuthHeaders} from '../../authUtils';


const VoyagesTable = ({ voyages, deleteVoyage, fetchCrewList, addNewCrewMember, removeCrewListMember}) => {

  const [open, setOpen] = useState(new Array(voyages.length).fill(false));
  const initialFormDataValues = {
    startDate: "",
    endDate: ""
  }
  const [formData, setFormData] = useState(initialFormDataValues);
  const [employeeId, setEmployeeId] = useState("");
  const [crewList, setCrewList] = useState([]);

  const trigger = async (voyageId) => {
    if(!open[voyageId]){
      
      const data = await fetchCrewList(voyageId);
      setCrewList([...data]);
      setEmployeeId("");
    }
    
    setOpen((prevOpen) => ({
      ...prevOpen,
      [voyageId]: !prevOpen[voyageId]
    }));
  };

  const removeMember = (voyageId, employeeId) => {
    removeCrewListMember(voyageId, employeeId)
    setOpen((prevOpen) => ({
      ...prevOpen,
      [voyageId]: !prevOpen[voyageId]
    }));
  }

  async function updateVoyage(voyageId){
    alert("UPDATE will be avaible soon.")
  }
  

  const renderTooltipVessel = (vessel) => (
  <Tooltip id="button-tooltip" {...vessel}>
    <p>TYPE: {vessel.type}</p>
    <p>FLAG: {vessel.flag}</p>
    <p>YEAR BUILT: {vessel.yearBuilt}</p>
    <p>EMAIL: {vessel.email}</p>
    <p>IMO NUMBER: {vessel.imonumber}</p>
    <p>CALL SIGN: {vessel.callSign}</p>
    <p>DWT: {vessel.dwt}</p>
    <p>GRT: {vessel.grt}</p>
    <p>MMSI: {vessel.mmsi}</p>
  </Tooltip>
  );

  const renderTooltipDeparture = (departure) => (
  <Tooltip id="button-tooltip" {...departure}>
    <p>DATE: {departure.date}</p>
    <p>INTERACTION TYPE: {departure.portInteractionType}</p>
    <p>ALLOWS CREW CHANGE: {departure.port.allowsCrewChange? "YES" : "NO"}</p>
  </Tooltip>
  );

  const renderTooltipArrival = (arrival) => (
  <Tooltip id="button-tooltip" {...arrival}>
    <p>DATE: {arrival.date}</p>
    <p>INTERACTION TYPE: {arrival.portInteractionType}</p>
    <p>ALLOWS CREW CHANGE: {arrival.port.allowsCrewChange? "YES" : "NO"}</p>
  </Tooltip>
  );

  const handleChange = (e) => {
    setEmployeeId(e.target.value);
  }

  const handleSubmit = (e,voyage) => {
    const form = e.currentTarget;

    if(form.checkValidity() === false){
        e.preventDefault();
        e.stopPropagation();
    } else {
        e.preventDefault();
        e.stopPropagation();

        const updatedFormData = {
          ...formData,
          startDate: voyage.departure.date,
          endDate: voyage.arrival.date
        };
        setFormData(updatedFormData);
        
        addNewCrewMember(voyage.voyageId, employeeId, updatedFormData);
        setFormData(initialFormDataValues);
        console.log("verificare formData dupa add new employee: ", formData);
    }
    setOpen(!open)
    // setValidated(true);
};



  return (
    <div 
      style={{ width: "90%" }} 
      className="d-flex justify-content-center mx-auto my-3"
    >
      <table>
        <thead>
          <tr>
            <th>Voyage ID</th>
            <th>Vessel</th>
            <th>Name</th>
            <th>Departure Port</th>
            <th>Arrival Port</th>
          </tr>
        </thead>
        <tbody>
          {voyages.map((voyage,idx) => (
            <Fragment key={idx}>
            <tr key={voyage.voyageId}>
              <td className="py-3">{voyage.voyageId}</td>
              <OverlayTrigger
                placement="right"
                delay={{ show: 250, hide: 400 }}
                overlay={renderTooltipVessel(voyage.vessel)}
              >
                  <td className="py-3">{voyage.vessel.name}</td>
              </OverlayTrigger>
              <td className="py-3">{voyage.name}</td>
              <OverlayTrigger
                placement="right"
                delay={{ show: 250, hide: 400 }}
                overlay={renderTooltipDeparture(voyage.departure)}
              >
                <td className="py-3">{voyage.departure.port.name}</td>
              </OverlayTrigger>
              <OverlayTrigger
                placement="right"
                delay={{ show: 250, hide: 400 }}
                overlay={renderTooltipArrival(voyage.arrival)}
              >
                <td className="py-3">{voyage.arrival.port.name}</td>
              </OverlayTrigger>
              <Button 
                  variant="primary"
                  onClick={() => trigger(voyage.voyageId)}
              >
                  📑
              </Button>{' '}
              <Button 
                variant="info"
                className="mx-1"
                onClick={()=>updateVoyage(voyage.voyageId)}
                >
                  ✍️
                </Button>{' '}
              <Button 
                variant="danger"
                className="mx-1"
                onClick={()=>deleteVoyage(voyage.voyageId)}
                >
                  ❌
                </Button>{' '}
            </tr>
            <tr>
              <td colSpan="10">
              <Collapse in={open[voyage.voyageId]}>
                        <div
                          style={{ textAlign: "center" }}
                          // id={`${idx}`}
                          id={`crewList-${voyage.voyageId}`}
                        >
                          <table style={{ margin: "auto", width: "70%" }}>
                            <thead>
                              <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Age</th>
                                <th>Rank</th>
                                <th>Gender</th>
                              </tr>
                            </thead>
                            <tbody>
                              {crewList && crewList.map((employee) => (
                                <tr key={employee.employeeId}>
                                  <td>{employee.firstName}</td>
                                  <td>{employee.lastName}</td>
                                  <td>{employee.age}</td>
                                  <td>{employee.rank}</td>
                                  <td>{employee.gender}</td>
                                  <td>
                                    <Button 
                                      variant="light"
                                      onClick={()=>removeMember(voyage.voyageId, employee.employeeId)}
                                    >
                                      ❌
                                    </Button>{' '}
                                  </td>
                                </tr>
                              ))}
                            {/* ADD NEW MEMBER IN CREWLIST */}
                            <tr>
                              <td colSpan="10" className="text-center">
                                <div className="d-flex justify-content-center align-items-center">
                                  <p className="m-0 me-2">Employee ID:</p>
                                  <Form onSubmit={(e)=>handleSubmit(e,voyage)} className="d-flex align-items-center gap-2">
                                      <Form.Group className="mb-0" controlId="formBasicId">
                                        <Form.Control 
                                          name="employeeId"
                                          type="number" 
                                          value={employeeId}
                                          voyage={voyage}
                                          min="1"
                                          required
                                          onChange={handleChange}
                                          placeholder="ID" />
                                      </Form.Group>
                                      <Button variant="success" type="submit">
                                        ADD
                                      </Button>
                                  </Form>
                                </div>
                              </td>
                            </tr>
                            {/* ADD NEW MEMBER IN CREWLIST */}
                            </tbody>
                          </table>
                        </div>
                      </Collapse>
              </td>
            </tr>
            </Fragment>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default VoyagesTable;