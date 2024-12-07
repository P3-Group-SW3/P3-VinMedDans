import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/fonts.css'
import '../styles/admin.css'

function AdminModule({title, category, apiHandle, columns} ) {
    const [items, setItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        console.log('Admin stuff: ', title, category, apiHandle)
        fetch(`${apiHandle}`)
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



    const clickRow = (item) => {
        navigate(`/administrator/${category}/${item.id}`, {state: {item}});
    };

    return (
        <div className="admin-module">
            <h1 className='header-large' onClick={() => navigate(`/administrator/${category}`)} style={{color: 'white', backgroundColor: '#C0924D', cursor: 'pointer'}}>{title}</h1>
            <table className="table table-hover" style={{fontFamily: 'Rubik, sans-serif'}}>
                <thead>
                <tr>
                    {columns.map((col, index) => (
                        <th key={index}>{col.header}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {items?.map((item) => (
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
        </div>
    );
}

export default AdminModule;
