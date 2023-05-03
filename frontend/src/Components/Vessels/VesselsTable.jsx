import React from 'react';
import Button from 'react-bootstrap/Button';

const VesselTable = ({ vessels }) => {

async function updateVessel(vesselId){
    alert("UPDATE will be avaible soon.")
}
async function deleteVessel(vesselId){
    alert("DELETE will be avaible soon.")
}

  return (
    <div style={{ width: "90%" }} className="d-flex justify-content-center mx-auto my-3">
      <table>
        <thead>
          <tr>
            <th>Vessel Id</th>
            <th>Name</th>
            <th>Type</th>
            <th>Flag</th>
            <th>IMO Number</th>
          </tr>
        </thead>
        <tbody>
          {vessels.map((vessel) => (
            <tr key={vessel.vesselId}>
              <td className="py-3">{vessel.vesselId}</td>
              <td className="py-3">{vessel.name}</td>
              <td className="py-3">{vessel.type}</td>
              <td className="py-3">{vessel.flag}</td>
              <td className="py-3">{vessel.imonumber}</td>
              <Button 
                    variant="primary" 
                    // onClick={()=>trigger(employee.employeeId)}
                    // aria-controls={`example-collapse-text-${employee.employeeId}`}
                    // aria-expanded={open[employee.employeeId]}
                >
                    📑
                </Button>{' '}
                <Button 
                  variant="info"
                  className="mx-1"
                  onClick={()=>updateVessel(vessel.vesselId)}
                >
                  ✍️
                </Button>{' '}
                <Button 
                  variant="danger"
                  className="mx-1"
                  onClick={()=>deleteVessel(vessel.vesselId)}
                  >
                    ❌
                  </Button>{' '}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default VesselTable;