import {Fragment,useState} from 'react';
import Collapse from "react-bootstrap/Collapse";
import Button from 'react-bootstrap/Button';
import VesselEditForm from './VesselEditForm';
import VesselsSearch from './VesselsSearch';
import { fetchVesselDetails } from './Vessels';


const VesselTable = ({ 
  vessels,
  fetchData, 
  updateVessel, 
  deleteVsl, 
  feedBackStatus, 
  setFeedBackStatus, 
  feedBackMsg,
  handleSort 
}) => {

const [open, setOpen] = useState(new Array(vessels.length).fill(false));
const [openEdit, setOpenEdit] = useState(new Array(vessels.length).fill(false));
// const [feedBackMsg, setFeedBackMsg] = useState("");
//   const [feedBackStatus, setFeedBackStatus] = useState(false);
const initialFilterValues = {
  vesselId: "",
  name: "",
  type: "",
  flag: "",
  imonumber: ""
};
const [filterValues, setFilterValues] = useState(initialFilterValues);

const trigger = (vesselId) => {
    if(!open[vesselId]){
      fetchData(vesselId);
    }
    console.log("verificare open: ", open[vesselId])
    setOpen((prevOpen) => ({
      ...prevOpen,
      [vesselId]: !prevOpen[vesselId]
    }));
  };

function updateVsl(vesselId){
    // alert("UPDATE will be avaible soon.")
    setOpenEdit((prevOpen) =>
      prevOpen.map((p, i) => (i !== vesselId ? p : !p))
    );
}

const handleFilterChange = (e) => {
  const { name, value } = e.target;
  setFilterValues((prevValues) => ({ ...prevValues, [name]: value }));
};

const clearFilter = () => {
  setFilterValues(initialFilterValues)
}

const filterVessels = vessels.filter(
  (vessel) => {
    const {
      vesselId,
      name,
      type,
      flag,
      imonumber
    } = filterValues;
    return (
      (vessel.vesselId === parseInt(vesselId) || vesselId === "") &&
      (vessel.name.includes(name) || name === "") &&
      (vessel.type.includes(type) || type === "") &&
      (vessel.flag.includes(flag) || flag === "") &&
      (vessel.imonumber === imonumber || imonumber === "")
    );
  }
);

  return (
    <>
      <VesselsSearch 
        filterValues={filterValues} 
        filterBy={handleFilterChange} 
        clearFilter = {clearFilter}
      />
      <div style={{ width: "90%" }} className="d-flex justify-content-center mx-auto my-3">
        <table>
          <thead>
            <tr style={{ cursor: 'ns-resize' }}>
              <th onClick={() => handleSort("vesselId")}>Vessel Id</th>
              <th onClick={() => handleSort("name")}>Name</th>
              <th onClick={() => handleSort("type")}>Type</th>
              <th onClick={() => handleSort("flag")}>Flag</th>
              <th onClick={() => handleSort("imonumber")}>IMO Number</th>
            </tr>
          </thead>
          <tbody>
            {filterVessels && 
              filterVessels.map((vessel,idx) => (
              <Fragment key={idx}>
                <tr key={vessel.vesselId}>
                  <td className="py-3">{vessel.vesselId}</td>
                  <td className="py-3">{vessel.name}</td>
                  <td className="py-3">{vessel.type}</td>
                  <td className="py-3">{vessel.flag}</td>
                  <td className="py-3">{vessel.imonumber}</td>
                  <Button 
                        variant="primary" 
                        // onClick={()=>fetchVesselDetails(vessel.vesselId)}
                        onClick={()=>trigger(vessel.vesselId)}
                        // aria-controls={`example-collapse-text-${employee.employeeId}`}
                        // aria-expanded={open[employee.employeeId]}
                    >
                        📑
                    </Button>{' '}
                    <Button 
                      variant="info"
                      className="mx-1"
                      onClick={()=>updateVsl(idx)}
                    >
                      ✍️
                    </Button>{' '}
                    <Button 
                      variant="danger"
                      className="mx-1"
                      onClick={()=>deleteVsl(vessel.vesselId)}
                      >
                        ❌
                      </Button>{' '}
                </tr>
                <tr>
                  <td colSpan="10">
                    <Collapse in={open[vessel.vesselId]}>
                      <div
                        style={{ textAlign: "center" }}
                        // id={`${idx}`}
                        id={`details-${vessel.vesselId}`}
                      >
                        <table style={{ margin: "auto", width: "70%" }}>
                          <thead>
                            <tr>
                              <th>Dwt</th>
                              <th>Grt</th>
                              <th>MMSI</th>
                              <th>Call Sign</th>
                              <th>Email</th>
                            </tr>
                          </thead>
                          <tbody>
                              <tr key={vessel.vesselId}>
                                <td className='px-2'>{vessel.dwt}</td>
                                <td className='px-2'>{vessel.grt}</td>
                                <td className='px-2'>{vessel.mmsi}</td>
                                <td className='px-2'>{vessel.callSign}</td>
                                <td className='px-2'>{vessel.email}</td>
                              </tr>
                          </tbody>
                        </table>
                      </div>
                    </Collapse>
                  </td>
                </tr>
                <tr>
                      <td colSpan="10">
                          <VesselEditForm
                            vesselObj = {vessel}
                            updateVessel={updateVessel}
                            openEdit = {openEdit[idx]}
                            feedBackStatus = {feedBackStatus}
                            feedBackMsg = {feedBackMsg}
                            setFeedBackStatus={setFeedBackStatus}
                            // setEmployeesFetch={setEmployeesFetch}
                          />
                      </td>
                </tr>
              </Fragment>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default VesselTable;