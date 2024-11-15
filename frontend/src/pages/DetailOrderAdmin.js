import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';

function DetailOrderAdmin() {
    const { id } = useParams();
    const [order, setOrder] = useState(null);
    const [selectedState, setSelectedState] = useState("");

    const states = [
        { label: "Registered", value: 0 },
        { label: "Packed", value: 1 },
        { label: "Sent", value: 2 }
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
        setSelectedState(Number(event.target.value));
    };

    // Send updated state to the backend when the user clicks the update button
    const updateOrderState = () => {
        fetch(`/api/orders/state/${id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ state: selectedState })
        })
            .then(response => {
                if (response.ok) {
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
            <p><strong>State:</strong> {states.find(s => s.value === order.state)?.label}</p>

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