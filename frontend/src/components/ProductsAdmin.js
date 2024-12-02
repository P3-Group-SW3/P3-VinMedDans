import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function ProductsAdmin() {
    const [products, setProducts] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {

        fetch('/api/wine/getList')  // Opdateret endpoint

            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => setProducts(data))
            .catch(error => console.error('Error fetching products:', error));
    }, []);

    const handleRowClick = (id) => {
        navigate(`/products/${id}`);  // Navigerer til produktdetaljer baseret på produkt-ID
    };

    return (
        <div className="container">
            <h1>Products</h1>
            <table className="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Amount Left</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th>Image</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product, index) => (
                    <tr
                        key={index}
                        onClick={() => handleRowClick(product.id)}
                        style={{cursor: 'pointer'}}
                    >
                        <td>{product.id}</td>
                        <td>{product.name}</td>
                        <td>{product.amountLeft}</td>
                        <td>{product.price}</td>
                        <td>{product.description}</td>
                        <td>
                            {product.imageURL ? (
                                <img src={product.imageURL} alt={product.name} style={{width: '50px'}}/>
                            ) : (
                                'No image'
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <button onClick={() => navigate('/create-wine')} className="btn btn-primary mt-3">
                Create New Wine
            </button>
        </div>
    );
}

export default ProductsAdmin;
