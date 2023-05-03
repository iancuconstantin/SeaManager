import './App.css';
// import Navbar from './Components/Navbar';
import {Data, dataLoader} from './Components/Data';
import {Contact} from './Components/Contact';
import {Home} from './Components/Home';
import { createBrowserRouter, createRoutesFromElements, Route, Link, Outlet, RouterProvider} from 'react-router-dom';
import { Employees, employeeLoader } from './Components/Employee/Employees';
import {Vessels, vesselsLoader} from './Components/Vessels/Vessels';
import { Voyages, voyageLoader } from './Components/Voyages/Voyages';

function App() {

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path='/' element={<Root/>}>
          <Route index element={<Home/>}/>
          <Route path='/data' element={<Data/>} loader={dataLoader}/>
          <Route path='/contact' element={<Contact/>}/>
          <Route path='/employees' element={<Employees/>} loader={employeeLoader}/>
          <Route path='/vessels' element={<Vessels/>} loader={vesselsLoader}/>
          <Route path='/voyages' element={<Voyages/>} loader={voyageLoader}/>
      </Route>
    )
  )

  return (
    <div className="App">
      {/* <Navbar/>
      <p>Hello World!</p> */}
      <RouterProvider router={router}/>
    </div>
  );
}

export default App;


const Root = () => {
  return(
    <>
      <div>
        <Link to={"/"}>Home</Link>
        <Link to={"/data"}>Data</Link>
        <Link to={"/contact"}>Contact</Link>
        <Link to={"/employees"}>Employees</Link>
        <Link to={"/vessels"}>Vessels</Link>
        <Link to={"/voyages"}>Voyages</Link>
      </div>

      <div>
        <Outlet/>
      </div>
    </>
  )
}