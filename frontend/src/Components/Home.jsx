import { Container } from "react-bootstrap"
import Register from "./Register";

export const Home = () => {
    return(
        <Container className="mt-5">
            <div className="leftDiv border border-warning rounded">
                <Register/>
            </div>
        </Container>
    )
}