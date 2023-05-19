import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import {Link} from "react-router-dom";
import {Button} from "react-bootstrap";
import myImage from "../images/apple-touch-icon.png";
import jwt_decode from 'jwt-decode';
import {useState, useEffect} from "react";


const Menu = ({handleLogout}) => {
    const [isAdmin, setIsAdmin] = useState(false);
    const token = localStorage.getItem('token');


    useEffect(() => {
        if (token) {
            const decodedToken = jwt_decode(token);
            if (decodedToken && decodedToken.scope === 'ADMIN USER') {
                setIsAdmin(true);
            } else {
                setIsAdmin(false);
            }
        } else {
            setIsAdmin(false);
        }
    }, [token]);


    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand href="/" style={{fontFamily: 'Cormorant Garamond, serif'}}>
                    <img src={myImage} alt="Logo" style={{width: '15%', height: 'auto'}}/>
                    SeaManager
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Link to="/employees">
                            <Button variant="info" className="mx-2 mt-3 btn-sm">
                                Employees
                            </Button>
                        </Link>
                        {isAdmin && <Link to="/vessels">
                            <Button variant="info" className="mx-2 mt-3 btn-sm">
                                Vessels
                            </Button>
                        </Link>}
                        <Link to="/voyages">
                            <Button variant="info" className="mx-2 mt-3 btn-sm">
                                Voyages
                            </Button>
                        </Link>

                        {isAdmin &&
                            <Link to="/newAccount">
                                <Button variant="outline-warning" className="mx-2 mt-3 btn-sm">
                                    New Account
                                </Button>
                            </Link>
                        }

                        <Link to="/login">
                            <Button variant="outline-warning" className="mx-2 mt-3 btn-sm" onClick={handleLogout}>
                                Logout
                            </Button>
                        </Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Menu;
