import "./App.css";
import { useState,useEffect } from "react";
import { NewAccount } from "./Components/NewAccount";
import Login from "./Components/Login";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
  Outlet,
  RouterProvider,
  useNavigate
} from "react-router-dom";
import { Employees, employeeLoader } from "./Components/Employee/Employees";
import { Vessels, vesselsLoader } from "./Components/Vessels/Vessels";
import { Voyages, voyageLoader } from "./Components/Voyages/Voyages";
import Menu from "./Components/Menu";
window.Buffer = window.Buffer || require("buffer").Buffer;


function App() {

  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = () => {
    // Perform login logic here
    // Set isLoggedIn to true if login is successful
    setIsLoggedIn(true);
  };

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route
        path="/"
        element={<Root isLoggedIn={isLoggedIn}/>}
      >
        <Route
          index
          path="/employees"
          element={<Employees/>}
          loader={employeeLoader}
        />
        <Route path="/login" element={<Login handleLogin={handleLogin}/>} />
        <Route path="/newAccount" element={<NewAccount isLoggedIn={isLoggedIn}/>}/>
        <Route path="/vessels" element={<Vessels isLoggedIn={isLoggedIn} />} loader={vesselsLoader} />
        <Route path="/voyages" element={<Voyages isLoggedIn={isLoggedIn} />} loader={voyageLoader} />
        <Route path="/menu" element={<Menu/>}/>
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
      {isLoggedIn && <Menu/>}
      <div>
        <Outlet />
      </div>
    </>
  );
};
