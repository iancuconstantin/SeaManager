import "./App.css";
import {useState, useEffect} from "react";
import {NewAccount} from "./Components/NewAccount";
import Login from "./Components/Login";
import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
    Outlet,
    RouterProvider,
    useNavigate
} from "react-router-dom";
import {Employees, employeeLoader} from "./Components/Employee/Employees";
import {Vessels, vesselsLoader} from "./Components/Vessels/Vessels";
import {Voyages, voyageLoader} from "./Components/Voyages/Voyages";

import Menu from "./Components/Menu";
import Button from "react-bootstrap/Button";

window.Buffer = window.Buffer || require("buffer").Buffer;


function App() {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const handleLogin = () => {
        // Perform login logic here
        // Set isLoggedIn to true if login is successful
        setIsLoggedIn(true);
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        localStorage.removeItem("token");
    }

    const router = createBrowserRouter(
        createRoutesFromElements(
            <Route
                path="/"
                element={<Root isLoggedIn={isLoggedIn} handleLogout={handleLogout}/>}
            >
                <Route
                    index
                    path="/employees"
                    element={<Employees isLoggedIn={isLoggedIn}/>}
                    loader={employeeLoader}
                />
                <Route path="/login" element={<Login handleLogin={handleLogin}/>}/>
                <Route path="/newAccount" element={<NewAccount isLoggedIn={isLoggedIn}/>}/>
                {/* <Route path="/data" element={<Data isLoggedIn={isLoggedIn}/>} loader={dataLoader} /> */}
                {/* <Route path="/contact" element={<Contact isLoggedIn={isLoggedIn}/>} /> */}
                <Route path="/vessels" element={<Vessels isLoggedIn={isLoggedIn}/>} loader={vesselsLoader}/>
                <Route path="/voyages" element={<Voyages isLoggedIn={isLoggedIn}/>} loader={voyageLoader}/>
                <Route path="/menu" element={<Menu/>}/>
                <Route path="/login"
                       element={<Button variant="outline-warning" className="mx-2 mt-3 btn-sm" onClick={handleLogout}>
                           Logout
                       </Button>}/>
            </Route>
        )
    );


    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsLoggedIn(token);
    }, []);


    return (
        <div className="App">
            <RouterProvider router={router}/>
        </div>
    );
}

export default App;

const Root = ({isLoggedIn, handleLogout}) => {

    const navigate = useNavigate();
    useEffect(() => {
        if (!isLoggedIn) {
            navigate('/login')
        } else {
            navigate('/')
        }

    }, [isLoggedIn]);

    return (
        <>
            {isLoggedIn && (
                // <div>
                //   {/* <Link to="/data">
                //     <Button variant="info" className="mx-2 mt-3 btn-sm">
                //       Data
                //     </Button>
                //   </Link> */}
                //   {/* <Link to="/contact">
                //     <Button variant="info" className="mx-2 mt-3 btn-sm">
                //       Contact
                //     </Button>
                //   </Link> */}
                //   <Link to="/employees">
                //     <Button variant="info" className="mx-2 mt-3 btn-sm">
                //       Employees
                //     </Button>
                //   </Link>
                //   <Link to="/vessels">
                //     <Button variant="info" className="mx-2 mt-3 btn-sm">
                //       Vessels
                //     </Button>
                //   </Link>
                //   <Link to="/voyages">
                //     <Button variant="info" className="mx-2 mt-3 btn-sm">
                //       Voyages
                //     </Button>
                //   </Link>
                //   <Link to="/newAccount">
                //     <Button variant="outline-warning" className="mx-2 mt-3 btn-sm">
                //       New Account
                //     </Button>
                //   </Link>
                //   <Link to="/menu">
                //     <Button variant="outline-warning" className="mx-2 mt-3 btn-sm">
                //       Menu
                //     </Button>
                //   </Link>

                // </div>
                <div>
                    <Menu handleLogout={handleLogout}/>
                </div>
            )}
            <div>
                <Outlet/>
            </div>
        </>
    );
};
