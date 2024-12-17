import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/fonts.css'
import '../styles/admin.css'

const AdminOrders = () => {
    const [orders, setOrders] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`/api/orders/getList`)
            .then(response => response.json())
            .then(data => {
                if (data) {
                    setOrders(data);
                    console.log(data);
                } else {
                    console.warn(`Received empty data for order`);
                }
            })
            .catch(error => console.error('Error fetching products:', error));
    }, []);


    const clickRow = (item) => {
        navigate(`/administrator/orders/${item.id}`, {
            state: {
                item
            }
        });
    };

    return (
        <div className="admin-module">
            <h1 className='header-large' style={{color: '#C0924D'}}>
                Ordrer
            </h1>
            <div className="scrollable">
                <table className="table table-hover" style={{fontFamily: 'Rubik, sans-serif'}}>
                    <thead>
                        <tr>
                            <th> Ordrenummer </th>
                            <th> Navn </th>
                            <th> Dato </th>
                            <th> Status </th>
                        </tr>
                    </thead>
                    <tbody>
                    {orders.map((item) => (
                        <tr
                            key={item.id}
                            onClick={() => clickRow(item)}
                            style={{cursor: 'pointer'}}
                        >
                            <td> {item.id} </td>
                            <td> {item.fullName} </td>
                            <td> {new Date(item.date).toLocaleDateString()} </td>
                            <td> {item.state} </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default AdminOrders;
