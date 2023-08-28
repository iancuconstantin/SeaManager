import React from 'react';
import Pagination from 'react-bootstrap/Pagination';

const EmployeePagination = ({currentPage, handlePageChange, totalPages}) => {
    const active = 1;
    const items = [];

    for (let number = 1; number <= totalPages; number++) {
        items.push(
        <Pagination.Item 
            key={number} 
            active={number === currentPage + 1}
            onClick={() => handlePageChange(number-1)}
            >
            {number}
        </Pagination.Item>
        );
    }

    return (
        <div className="d-flex justify-content-center mx-auto my-3">
            <Pagination>{items}</Pagination>
        </div>
    );
};

export default EmployeePagination;
