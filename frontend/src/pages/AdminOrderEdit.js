import React, {useState} from 'react';
import {useLocation} from "react-router-dom";

const AdminOrderEdit = () => {
    const [order, setOrder] = useState(useLocation().state.item);
    const [selectedState, setSelectedState] = useState(null);

    // List of available states with labels for the dropdown and corresponding values for the backend
    const states = [
        { label: "Registered", value: 0 },
        { label: "Confirmed", value: 1 },
        { label: "Packed", value: 2 },
        { label: "Shipped", value: 3}
    ];

    // Update the selected state in local component state when the user selects a new option
    const handleStateChange = (event) => {
        setSelectedState(Number(event.target.value));
    };

    const updateOrderState = () => {
        fetch(`/api/orders/state/${order.id}`, {
            method: 'POST', // Using POST to match backend endpoint
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ state: selectedState }) // Send selected state as JSON
        })
            .then(response => {
                if (response.ok) {
                    // If successful, update the order's state in local component state
                    setOrder(prevOrder => ({ ...prevOrder, state: selectedState }));
                } else {
                    console.error('Error updating order state');
                }
            })
            .catch(error => console.error('Error updating order state:', error));
    };

    return (
        <div className="container">
            <h1>Order Details</h1>
            <p><strong>ID:</strong> {order.id}</p>
            <p><strong>Full Name:</strong> {order.fullName}</p>
            <p><strong>Email:</strong> {order.mail}</p>
            <p><strong>Phone Number:</strong> {order.phoneNumber}</p>
            <p><strong>Address:</strong> {order.adress}</p>
            <p><strong>City:</strong> {order.city}</p>
            <p><strong>Zip Code:</strong> {order.zipCode}</p>
            <p><strong>Date:</strong> {new Date(order.date).toLocaleDateString()}</p>
            <p><strong>State:</strong> {order.state}</p>

            <h3>Orderlines</h3>
            {order.orderLines && order.orderLines.length > 0 ? (
                <div>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                        </tr>
                        </thead>
                        <tbody>
                        {order.orderLines.map((line, index) => (
                            <tr key={index}>
                                <td>{line.wine.name}</td>
                                <td>{line.amount}</td>
                                <td>{line.wine.price}</td>
                                <td>{(line.amount * line.wine.price).toFixed(2)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <h4>
                        <strong>Total: </strong>{order.orderLines.reduce((total, line) => total + (line.amount * line.wine.price), 0).toFixed(2)} DKK
                    </h4>
                </div>
            ) : (
                <p>No order lines found.</p>
            )}


            <div className="form-group">
                <label htmlFor="orderState">Update Order State:</label>
                <select
                    id="orderState"
                    value={selectedState}
                    onChange={handleStateChange}
                    className="form-control"
                >
                    {states.map(state => (
                        <option key={state.value} value={state.value}>{state.label}</option>
                    ))}
                </select>
                <button onClick={updateOrderState} className="btn btn-primary mt-2">Update State</button>
            </div>
        </div>
    );
}

export default AdminOrderEdit;