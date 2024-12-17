import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/fonts.css'
import '../styles/admin.css'
import Button from "./Button";

/*
 * Dashboard module for administrator dashboard.
 * Can be used for either wine, events, distributor and orders
 */

const AdminModule = ({title, category, columns} ) => {

    //The state 'items' contains array of items in the given category
    const [items, setItems] = useState([]);
    const navigate = useNavigate();

    //On mount: Get all items and assign to 'items' state
    useEffect(() => {
        fetch(`/api/${category}/getList`)
            .then(response => response.json())
            .then(data => {
                if (data) {
                    setItems(data);
                } else {
                    console.warn(`Received empty data for ${category}`);
                }
            })
            .catch(error => console.error('Error fetching products:', error));
    }, []);

    //Redirects to specific item page when clicking a table row. Passes the item object
    const clickRow = (item) => {
        navigate(`/administrator/${category}/${item.id}`, {
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
                            {/* Table head values mapped from the 'columns' array */}
                            {columns.map((col, index) => (
                                <th key={index}>{col.header}</th>
                            ))}
                        </tr>
                    </thead>
                        <tbody>
                            {/* Table rows mapped from the 'columns' array, 10 rows max */}
                            {items?.slice(0, 10).map((item) => (
                                <tr
                                    key={item.id}
                                    onClick={() => clickRow(item)}
                                    style={{ cursor: 'pointer' }}
                                >
                                    {columns.map((col, index) => (
                                        <td key={index}>
                                            {item[col.field]}
                                        </td>
                                    ))}
                                </tr>
                            ))}
                        </tbody>
                </table>
                {category === 'orders' &&
                    <div className="d-flex justify-content-center my-1">
                        <Button text="Vis flere" onClick={() => navigate(`/administrator/${category}`)}/>
                    </div>
                }
            </div>
        </div>
    );
}

export default AdminModule;
