import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function OrdersAdmin() {
    const [items, setItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('/api/orders')
            .then(response => response.json())
            .then(data => setItems(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    const handleRowClick = (id) => {
        navigate(`/orders/${id}`);
    };

    return (
        <div className="container">
            <h1>Orders</h1>
            <table className="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Address</th>
                    <th>City</th>
                    <th>Zip Code</th>
                    <th>State</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {items.map((item, index) => (
                    <tr
                        key={index}
                        onClick={() => handleRowClick(item?.id)}
                        style={{ cursor: 'pointer' }}
                    >
                        <td>{item?.id}</td>
                        <td>{item?.fullName}</td>
                        <td>{item?.mail}</td>
                        <td>{item?.phoneNumber}</td>
                        <td>{item?.adress}</td>
                        <td>{item?.city}</td>
                        <td>{item?.zipCode}</td>
                        <td>{item?.state}</td>
                        <td>{item?.date}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default OrdersAdmin;