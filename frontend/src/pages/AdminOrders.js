import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/fonts.css'
import '../styles/admin.css'
import Button from "./Button";

const AdminOrders = () = {
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
        navigate(`/administrator/orders/${order.id}`, {
            state: {
                item
            }
        });
    };

    return (
        <div className="admin-module">
            <h1 className='header-large' style={{color: '#C0924D'}}>
                {title}
                {category!=="orders" &&
                    < Button text="Opret" onClick={() => navigate(`/administrator/${category}/create`)}></Button>}
            </h1>
            <div className="scrollable">
                <table className="table table-hover" style={{fontFamily: 'Rubik, sans-serif'}}>
                    <thead>
                    <tr>
                        {columns.map((col, index) => (
                            <th key={index}>{col.header}</th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {items?.slice(0,10).map((item) => (
                        <tr
                            key={item.id}
                            onClick={() => clickRow(item)}
                            style={{cursor: 'pointer'}}
                        >
                            {columns.map((col, index) => (
                                <td key={index}>{item[col.field]}</td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table>
                {items.length > 10 && (
                    <div className="d-flex justify-content-center my-1">
                        <Button text="Vis flere" onClick={() => navigate(`/administrator/${category}`)} />
                    </div>
                )}
            </div>
        </div>
    );
}

export default AdminOrders;
