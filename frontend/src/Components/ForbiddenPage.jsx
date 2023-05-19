import React from "react";
import { useNavigate } from "react-router-dom";

const ForbiddenPage = () => {
	const navigate = useNavigate();

	const redirectToEmployee = () => {
		navigate("/employee");
	};

	return (
		<div>
			<h1>403 Forbidden</h1>
			<p>Access to this page is forbidden.</p>
			<button onClick={redirectToEmployee}>Go to Employee Page</button>
		</div>
	);
};

export default ForbiddenPage;