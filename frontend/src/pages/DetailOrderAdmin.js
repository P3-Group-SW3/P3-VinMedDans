import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';

function DetailOrderAdmin() {
    const { id } = useParams();
    const [order, setOrder] = useState(null);


    useEffect(() => {
        fetch(`/api/orders/${id}`)
            .then(response => response.json())
            .then(data => {
                setOrder(data);
            })
            .catch(error => console.error('Error fetching order details:', error));
    }, [id]);

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
                    <h4><strong>Total: </strong>{order.orderLines.reduce((total, line) => total + (line.amount * line.wine.price), 0).toFixed(2)} DKK</h4>
                </div>
            ) : (
                <p>No order lines found.</p>
            )}
        </div>
    );
}

export default DetailOrderAdmin;