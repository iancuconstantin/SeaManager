import { Fragment, useEffect, useState } from "react";
import Collapse from "react-bootstrap/Collapse";
import { Button } from "react-bootstrap";
import EmployeeEdit from "./EmployeeEdit";
import EmployeeSearch from "./EmployeeSearch";

const EmployeeTable = ({
  employees,
  fetchData,
  updateEmployee,
  deleteEmp,
  handleSort,
  setEmployeesFetch
}) => {
  const [open, setOpen] = useState(new Array(employees.length).fill(false));
  const [openEdit, setOpenEdit] = useState(new Array(employees.length).fill(false));
  const [feedBackMsg, setFeedBackMsg] = useState("");
  const [feedBackStatus, setFeedBackStatus] = useState(false);
  const initialFilterValues = {
    employeeId: "",
    firstName: "",
    lastName: "",
    email: "",
    age: "",
    address: "",
    contactNo: "",
    rank: "",
    gender: "",
    readinessDate: "",
  };

  useEffect(()=>{
    console.log(employees)
  },[])

  const [filterValues, setFilterValues] = useState(initialFilterValues);

  const trigger = (employeeId) => {
    if(!open[employeeId]){
      fetchData(employeeId);
    }
    // fetchData(employeeId);
    setOpen((prevOpen) => ({
      ...prevOpen,
      [employeeId]: !prevOpen[employeeId]
    }));
  };

  const updateEmp = (employeeId) => {
    setOpenEdit((prevOpen) =>
      prevOpen.map((p, i) => (i !== employeeId ? p : !p))
    );
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilterValues((prevValues) => ({ ...prevValues, [name]: value }));
  };

  const clearFilter = () => {
    setFilterValues(initialFilterValues)
  }

  const filterEmployees = employees.filter(
    (employee) => {
      const {
        employeeId,
        firstName,
        lastName,
        email,
        age,
        address,
        contactNo,
        rank,
        gender,
        readinessDate,
      } = filterValues;
    
      return (
        (employee.employeeId === parseInt(employeeId) || employeeId === "") &&
        (employee.firstName.includes(firstName) || firstName === "") &&
        (employee.lastName.includes(lastName) || lastName === "") &&
        (employee.email.includes(email) || email === "") &&
        (employee.age === parseInt(age) || age === "") &&
        (employee.address.includes(address) || address === "") &&
        (employee.contactNo === parseInt(contactNo) || contactNo === "") &&
        (employee.rank === rank || rank === "") &&
        (employee.gender === gender || gender === "") &&
        // (employee.readinessDate.includes(readinessDate) || readinessDate === "")
        (((employee.readinessDate && employee.readinessDate.includes(readinessDate)) || readinessDate === ""))
      );
    }
  );

  return (
    <>
      <EmployeeSearch 
        filterValues={filterValues} 
        filterBy={handleFilterChange} 
        clearFilter = {clearFilter}
      />
      <div
        style={{ width: "90%", fontSize: 12 }}
        className="d-flex justify-content-center mx-auto my-3"
      >
        <table>
          <thead>
            <tr style={{ cursor: 'ns-resize' }}>
              <th onClick={() => handleSort("employeeId")}>Employee Id</th>
              <th onClick={() => handleSort("firstName")}>First Name</th>
              <th onClick={() => handleSort("lastName")}>Last Name</th>
              <th onClick={() => handleSort("email")}>Email</th>
              <th onClick={() => handleSort("birthDate")}>Birth Date</th>
              <th onClick={() => handleSort("age")}>Age</th>
              <th onClick={() => handleSort("address")}>Address</th>
              <th onClick={() => handleSort("contactNo")}>Contact No</th>
              <th onClick={() => handleSort("rank")}>Rank</th>
              <th onClick={() => handleSort("gender")}>Gender</th>
              <th onClick={() => handleSort("readinessDate")}>
                Readiness Date
              </th>
            </tr>
          </thead>
          <tbody>
            {filterEmployees &&
              filterEmployees.map((employee, idx) => (
                <Fragment key={idx}>
                  <tr className="m-3" key={employee.employeeId}>
                    <td className="py-3">{employee.employeeId}</td>
                    <td className="py-3">{employee.firstName}</td>
                    <td className="py-3">{employee.lastName}</td>
                    <td className="py-3">{employee.email}</td>
                    <td className="py-3">{employee.birthDate}</td>
                    <td className="py-3">{employee.age}</td>
                    <td className="py-3">{employee.address}</td>
                    <td className="py-3">{employee.contactNo}</td>
                    <td className="py-3">{employee.rank}</td>
                    <td className="py-3">{employee.gender}</td>
                    <td className="py-3">{employee.readinessDate || "N/A"}</td>
                    <td>
                      <Button
                        variant="primary"
                        className="mx-1 btn-sm"
                        onClick={() => trigger(employee.employeeId)}
                        aria-controls={`certificates-${employee.employeeId}`}
                        aria-expanded={open[employee.employeeId]}
                      >
                        📑
                      </Button>{" "}
                      <Button
                        variant="info"
                        className="mx-1 btn-sm"
                        onClick={() => updateEmp(idx)}
                      >
                        ✍️
                      </Button>{" "}
                      <Button
                        variant="danger"
                        className="mx-1 btn-sm"
                        onClick={() => deleteEmp(employee.employeeId)}
                      >
                        ❌
                      </Button>
                    </td>
                  </tr>
                  <tr>
                    <td colSpan="10">
                      <Collapse in={open[employee.employeeId]}>
                        <div
                          style={{ textAlign: "center" }}
                          // id={`${idx}`}
                          id={`certificates-${employee.employeeId}`}
                        >
                          <table style={{ margin: "auto", width: "70%" }}>
                            <thead>
                              <tr>
                                <th>Type</th>
                                <th>Serial Number</th>
                                <th>Issue Date</th>
                                <th>Expiry Date</th>
                              </tr>
                            </thead>
                            <tbody>
                              {employee.certificates.map((certificate) => (
                                <tr key={certificate.serialNumber}>
                                  <td>{certificate.type}</td>
                                  <td>{certificate.serialNumber}</td>
                                  <td>{certificate.issueDate}</td>
                                  <td>{certificate.expiryDate}</td>
                                </tr>
                              ))}
                            </tbody>
                          </table>
                        </div>
                      </Collapse>
                    </td>
                  </tr>
                  <tr>
                    <td colSpan="10">
                      <EmployeeEdit
                        openEdit={openEdit[idx]}
                        employeeObj={employee}
                        updateEmployee={updateEmployee}
                        feedBackMsg={feedBackMsg}
                        feedBackStatus={feedBackStatus}
                        setFeedBackStatus={setFeedBackStatus}
                        setEmployeesFetch={setEmployeesFetch}
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

export default EmployeeTable;
