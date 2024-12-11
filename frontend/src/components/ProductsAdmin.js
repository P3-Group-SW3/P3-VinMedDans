import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/fonts.css'
import Button from "./Button";

function ProductsAdmin() {
    const [products, setProducts] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {

        fetch('/api/wine/getList')
            .then(response => response.json())
            .then(data => {
                if (data) {
                    setProducts(data);
                } else {
                    console.warn('Received empty data for products');
                }
            })
            .catch(error => console.error('Error fetching products:', error));
    }, []);



    const handleRowClick = (product) => {
        navigate(`/administrator/products/${product.id}`, {state: {product}});
    };

    return (
        <div className="rounded">
            <h1 className='header-large' style={{color: 'white', backgroundColor: '#C0924D'}}>sortiment</h1>
            <table className="table table-hover" style={{fontFamily: 'Rubik, sans-serif'}}>
                <thead>
                <tr>
                    <th>Navn</th>
                    <th>Antal</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product) => (
                    <tr
                        key={product.id}
                        onClick={() => handleRowClick(product)}
                        style={{cursor: 'pointer'}}
                    >
                        <td>{product.name}</td>
                        <td>{product.amountLeft}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div className="d-flex justify-content-center">
                <Button text='Tilføj vin' onClick={() => navigate('/create-wine')} isWide={true}/>
            </div>
        </div>
    );
}

export default ProductsAdmin;
