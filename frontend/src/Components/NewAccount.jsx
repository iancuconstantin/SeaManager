import { Container } from "react-bootstrap"
import Register from "./Register";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export const NewAccount = ({ isLoggedIn }) => {
    const navigate = useNavigate();

    useEffect(() => {
        if (!isLoggedIn) {
        navigate('/login')
        }
    }, [isLoggedIn]);

    return(
        <Container className="my-5 w-100 w-md-75 w-lg-50">
            <div className="leftDiv border border-warning rounded">
                <Register/>
            </div>
        </Container>
    )
}