import "./App.css";
import { useState,useEffect } from "react";
import { Data, dataLoader } from "./Components/Data";
import { Contact } from "./Components/Contact";
import { Home } from "./Components/Home";
import Login from "./Components/Login";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  Link,
  Outlet,
  RouterProvider,
  useNavigate
} from "react-router-dom";
import { Employees, employeeLoader } from "./Components/Employee/Employees";
import { Vessels, vesselsLoader } from "./Components/Vessels/Vessels";
import { Voyages, voyageLoader } from "./Components/Voyages/Voyages";
import { Button } from "react-bootstrap";
window.Buffer = window.Buffer || require("buffer").Buffer;


function App() {

  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = () => {
    // Perform login logic here
    // Set isLoggedIn to true if login is successful
    setIsLoggedIn(true);
  };

  // if (!isLoggedIn) {
  //   navigate("/login", { replace: true });
  // }

  // const router = createBrowserRouter(
  //   createRoutesFromElements(
  //     <Route path="/" element={<Root />}>
  //       <Route index element={<Home />} />
  //       <Route path="/data" element={<Data />} loader={dataLoader} />
  //       <Route path="/contact" element={<Contact />} />
  //       <Route
  //         path="/employees"
  //         element={<Employees />}
  //         loader={employeeLoader}
  //       />
  //       <Route path="/vessels" element={<Vessels />} loader={vesselsLoader} />
  //       <Route path="/voyages" element={<Voyages />} loader={voyageLoader} />
  //     </Route>
  //   )
  // );

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route
        path="/"
        // element={isLoggedIn ? <Root /> : <Login onLogin={handleLogin} />}
        element={<Root isLoggedIn={isLoggedIn}/>}
      >
        <Route path="/login" element={<Login onLogin={handleLogin} loader={dataLoader} />} />
        <Route index element={<Home />}/>
        <Route path="/data" element={<Data isLoggedIn={isLoggedIn}/>} loader={dataLoader} />
        <Route path="/contact" element={<Contact isLoggedIn={isLoggedIn}/>} />
        <Route
          path="/employees"
          element={<Employees isLoggedIn={isLoggedIn}/>}
          loader={employeeLoader}
        />
        <Route path="/vessels" element={<Vessels isLoggedIn={isLoggedIn} />} loader={vesselsLoader} />
        <Route path="/voyages" element={<Voyages isLoggedIn={isLoggedIn} />} loader={voyageLoader} />
      </Route>
    )
  );

  return (
    <div className="App">
      <RouterProvider router={router} />
    </div>
  );
}

export default App;

const Root = ({isLoggedIn}) => {
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
    {isLoggedIn && 
      <div>
        <Link to="/">
          <Button variant="info" className="mx-2 mt-3 btn-sm">
            Home
          </Button>
        </Link>
        <Link to="/data">
          <Button variant="info" className="mx-2 mt-3 btn-sm">
            Data
          </Button>
        </Link>
        <Link to="/contact">
          <Button variant="info" className="mx-2 mt-3 btn-sm">
            Contact
          </Button>
        </Link>
        <Link to="/employees">
          <Button variant="info" className="mx-2 mt-3 btn-sm">
            Employees
          </Button>
        </Link>
        <Link to="/vessels">
          <Button variant="info" className="mx-2 mt-3 btn-sm">
            Vessels
          </Button>
        </Link>
        <Link to="/voyages">
          <Button variant="info" className="mx-2 mt-3 btn-sm">
            Voyages
          </Button>
        </Link>
      </div>
    }
      <div>
        <Outlet />
      </div>
    </>
  );
};
