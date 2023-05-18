import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import myImage from "../images/apple-touch-icon.png";

const Menu = () => {
  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand href="/" style={{ fontFamily: 'Cormorant Garamond, serif' }}>
            <img src={myImage} alt="Logo" style={{ width: '15%', height: 'auto' }}/>
            SeaManager
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {/* <Nav.Link href="#home">Home</Nav.Link>
            <Nav.Link href="#link">Link</Nav.Link> */}
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
            <Link to="/newAccount">
              <Button variant="outline-warning" className="mx-2 mt-3 btn-sm">
                New Account
              </Button>
            </Link>
            {/* <Link to="/menu">
              <Button variant="outline-warning" className="mx-2 mt-3 btn-sm">
                Menu
              </Button>
            </Link> */}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Menu;
