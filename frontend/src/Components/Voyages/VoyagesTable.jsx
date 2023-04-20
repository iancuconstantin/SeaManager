import React from 'react';
import Button from 'react-bootstrap/Button';

const VoyagesTable = ({ voyages }) => {
  return (
    <div style={{ width: "90%" }} className="d-flex justify-content-center mx-auto my-3">
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
          {voyages.map((voyage) => (
            <tr key={voyage.voyageId}>
              <td className="py-3">{voyage.voyageId}</td>
              <td className="py-3">{voyage.vessel.name}</td>
              <td className="py-3">{voyage.name}</td>
              <td className="py-3">{voyage.departure.port.name}</td>
              <td className="py-3">{voyage.arrival.port.name}</td>
              <Button variant="danger">Delete</Button>{' '}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default VoyagesTable;