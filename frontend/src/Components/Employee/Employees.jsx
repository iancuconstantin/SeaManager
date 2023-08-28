import {useLoaderData, useNavigation, redirect} from "react-router-dom";
import {useState, useEffect} from "react";
import AddEmployeeForm from "./EmployeeAdd";
import EmployeeTable from "./EmployeeTable";
import EmployeeSearch from "./EmployeeSearch";
import EmployeePagination from "./EmployeePagination";
import {getBasicAuthHeaders, getBearerAuthHeaders} from '../../authUtils';
import Button from "react-bootstrap/Button";
import Collapse from "react-bootstrap/Collapse";
import {fetchBearerAuth, fetchBasicAuth, fetchBearerAuthWithBody} from "../../fetchFunction";
import {parseTextResponse, parseJsonResponse} from "../../responseParsers";
import { handleRequestAndCheckResponse } from "../../authUtils";

export const Employees = () => {
    const [employeesFetch, setEmployeesFetch] = useState(useLoaderData());
    

    const [currentPage, setCurrentPage] = useState(0);
    


    const navigation = useNavigation();
    const [open, setOpen] = useState(false);
    const [feedBackMsg, setFeedBackMsg] = useState("");
    const [feedBackStatus, setFeedBackStatus] = useState(false);
    const [sortColumn, setSortColumn] = useState("");
    const [sortOrder, setSortOrder] = useState("asc");


    useEffect(() => {
        console.log("verificare currentPage: ", currentPage);
        setTimeout(async() => {
            console.log("a intrat in useEffect!");
            const data = await employeeLoader(currentPage);
            setEmployeesFetch(data);
        }, 1000);
    }, [currentPage]);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    
    async function addNewEmployee(formData) {
        try {

            //const response = await fetchBearerAuthWithBody("http://localhost:8080/api/employee","POST", formData);
            console.log(JSON.stringify(formData))

            const headers = getBearerAuthHeaders();
            headers.append("Content-Type", "application/json");
            const response = await fetch("http://localhost:8080/api/employee", {
                method: "POST",
                headers: headers,
                body: JSON.stringify(formData),
            });
            if (response.ok) {
                setFeedBackStatus(true);
                setFeedBackMsg("New employee was added!");
                let sailor = await response.json();
                formData.employeeId = sailor.employeeId;
                setEmployeesFetch([formData, ...employeesFetch]);
            } else {
                if (response.status === 422) {
                    setFeedBackStatus(true);
                    setFeedBackMsg(`Email ${formData.email} already exist!`);
                } else {
                    setFeedBackStatus(true);
                    setFeedBackMsg("Something went wrong! Try again later.");
                }
            }
        } catch (error) {
            setFeedBackStatus(true);
            setFeedBackMsg("Connection error!");
        }
    }

    async function updateEmployee(formData, employeeID) {
        const headers = getBearerAuthHeaders();
        headers.append("Content-Type", "application/json");
        try {
            const response = await fetch(
                `http://localhost:8080/api/employee/${employeeID}`,
                {
                    method: "PUT",
                    headers: headers,
                    body: JSON.stringify(formData),
                }
            );

            if (response.ok) {
                setFeedBackStatus(true);
                setFeedBackMsg("Employee was successfuly updated!");
                setEmployeesFetch((prevEmployees) =>
                    prevEmployees.map((employee) =>
                        employee.employeeId === formData.employeeId ? formData : employee
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

    async function deleteEmp(employeeId) {
        const confirmed = window.confirm(
            "Are you sure you want to delete this employee?"
        );
        if (confirmed) {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/employee/${employeeId}`,
                    {
                        method: "DELETE",
                        headers: getBearerAuthHeaders()
                    }
                );

                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                // Do something with the successful response
                const updatedEmployees = employeesFetch.filter(
                    (employee) => employee.employeeId !== employeeId
                );
                setEmployeesFetch(updatedEmployees);
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
        
        const sortedEmployees = [...employeesFetch.content].sort((a, b) => {
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
        setEmployeesFetch(prevState => ({
            ...prevState,
            content: sortedEmployees
        }));
    };

    if (navigation.state === "loading") {
        return <h3>Loading...</h3>;
    }

    return (
        <>
            {/* ADD EMPLOYEE */}
            <Button
                className="btn-sm"
                onClick={() => setOpen(!open)}
                aria-controls="example-collapse-text"
                aria-expanded={open}
                variant="success"
            >
                Add New Employee
            </Button>{" "}

            {feedBackStatus && (
                <h3>{feedBackMsg}</h3>
            )}

            <Collapse in={open}>
                <AddEmployeeForm
                    open={open}
                    addNewEmployee={addNewEmployee}
                    feedBackStatus={feedBackStatus}
                    setFeedBackStatus={setFeedBackStatus}
                    feedBackMsg={feedBackMsg}
                    setOpen={setOpen}
                />
            </Collapse>
            {/* ADD EMPLOYEE */}
            <EmployeeTable
                employees={employeesFetch}
                fetchData={fetchCertificates}
                updateEmployee={updateEmployee}
                deleteEmp={deleteEmp}
                handleSort={handleSort}
                setEmployeesFetch={setEmployeesFetch}
                feedBackStatus={feedBackStatus}
                setFeedBackStatus={setFeedBackStatus}
                feedBackMsg={feedBackMsg}
            />
            <EmployeePagination 
                handlePageChange={handlePageChange} 
                currentPage={currentPage}
                totalPages={employeesFetch.totalPages}
                
            />
        </>
    );
};

export const employeeLoader = async (page=0) => {
    const token = localStorage.getItem("token");
    const pageSize = 10;

    if (!token) {
        return redirect("/login");
    }

    const response = await fetch(`http://localhost:8080/api/employee/${page}/${pageSize}`, {
        headers: getBearerAuthHeaders()
    });
    const data = await parseJsonResponse(response);
    console.log("verificare data: ", data);
    return data;
}

export const fetchCertificates = async (employeeId) => {
    console.log("certificates")
    const response = await fetch(`http://localhost:8080/api/employee/${employeeId}`, {
        headers: getBearerAuthHeaders()
    });
    const data = await response.json();
    return data;
}