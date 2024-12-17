import React, {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";

const AdminOrderEdit = () => {
    const { state: { item: currentOrder } } = useLocation();
    const [order, setOrder] = useState(currentOrder);

    // Define the enum mapping
    const stateEnum = {
        REGISTERED: 0,
        CONFIRMED: 1,
        PACKED: 2,
        SHIPPED: 3
    };

    // Convert stateEnum to an array for dropdown options
    const states = Object.entries(stateEnum).map(([label, value]) => ({ label, value }));

    // Initialize selectedState using the numeric value from stateEnum
    const [selectedState, setSelectedState] = useState(stateEnum[order.state]);

    useEffect(() => {
        console.log("Selected ", selectedState);
        console.log("State ", order.state);
        console.log("State number ", stateEnum[order.state])
    }, [selectedState]);

    const updateOrderState = (event) => {
        const updatedState = Number(event.target.value);
        setSelectedState(updatedState);
        fetch(`/api/orders/state/${order.id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ state: updatedState })
        })
            .then(response => {
                if (response.ok) {
                    console.log('Success!', response)
                } else {
                    console.error('Error updating order state:', response);
                }
            })
            .catch(error => console.error('Error updating order state:', error));
    };

    return (
        <div className="container">
            <h1 className="header-large"> ordre {order.id} </h1>

            <div className="body-text">
                <label htmlFor="orderState">Ordrestatus:</label>
                <select id="orderState" value={selectedState} onChange={updateOrderState}>
                    {states.map(state => (
                        <option key={state.value} value={state.value}>
                            {state.label}
                        </option>
                    ))}
                </select>
            </div>

            <p className="body-text">
                <br/>
                <strong>Bestilling afgivet d. {new Date(order.date).toLocaleDateString()}</strong> <br/>
                <strong>Navn:</strong> {order.fullName} <br/>
                <strong>Email:</strong> {order.mail} <br/>
                <strong>Telefonnummer:</strong> {order.phoneNumber} <br/>
                <strong>Adresse:</strong> {order.address}, {order.zipCode} {order.city} <br/>
            </p>

            <h3 className="header-large"> ordreoversigt</h3>
            {order.orderLines && order.orderLines.length > 0 ? (
                <div className="body-text">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Produktnavn</th>
                            <th>Mængde</th>
                            <th>Pr. styk</th>
                            <th>Pris</th>
                        </tr>
                        </thead>
                        <tbody>
                        {order.orderLines.map((orderLine, index) => (
                            <tr key={index}>
                                <td>{orderLine.wine.name}</td>
                                <td>{orderLine.amount}</td>
                                <td>{orderLine.wine.price}</td>
                                <td>{(orderLine.amount * orderLine.wine.price).toFixed(2)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <h4>
                        <strong>Totalpris: </strong>{order.orderLines.reduce((total, orderLine) => total + (orderLine.amount * orderLine.wine.price), 0).toFixed(2)} DKK
                    </h4>
                </div>
            ) : (
                <p className="body-text">Ingen varer i denne order.</p>
            )}
        </div>
    );
}

export default AdminOrderEdit;