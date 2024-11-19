import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';

function DetailOrderAdmin() {
    const { id } = useParams();
    const [order, setOrder] = useState(null);
    const [selectedState, setSelectedState] = useState("");

    // List of available states with labels for the dropdown and corresponding values for the backend
    const states = [
        { label: "Registered", value: 0 },
        { label: "Confirmed", value: 1 },
        { label: "Packed", value: 2 },
        { label: "Shipped", value: 3}
    ];

    // Fetch order details when the component loads or when the order ID changes
    useEffect(() => {
        fetch(`/api/orders/${id}`)
            .then(response => response.json())
            .then(data => {
                setOrder(data);
                setSelectedState(data.state);
            })
            .catch(error => console.error('Error fetching order details:', error));
    }, [id]);

    // Update the selected state in local component state when the user selects a new option
    const handleStateChange = (event) => {
        setSelectedState(Number(event.target.value)); // Convert selected value to number
    };

    // Send updated state to the backend when the user clicks the update button
    const updateOrderState = () => {
        fetch(`/api/orders/state/${id}`, {
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

    if (!order) {
        return <p>Loading...</p>;
    }

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
            <p><strong>State:</strong> {states.find(s => s.value === order.state)?.label}</p>

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
                    <h4><strong>Total: </strong>{order.orderLines.reduce((total, line) => total + (line.amount * line.wine.price), 0).toFixed(2)} DKK</h4>
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

export default DetailOrderAdmin;