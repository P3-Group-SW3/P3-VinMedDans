import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

function DetailProductAdmin() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        fetch(`/api/getWineById/${id}`)
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => setProduct(data))
            .catch(error => console.error('Error fetching product:', error));
    }, [id]);

    if (!product) return <div>Loading...</div>;

    return (
        <div>
            <h1>{product.name}</h1>
            <p>ID: {product.id}</p>
            <p>Price: {product.price}</p>
            <p>Description: {product.description}</p>
            <p>Amount Left: {product.amountLeft}</p>
            <img src={product.imageURL} alt={product.name} style={{ width: '150px' }} />
        </div>
    );
}

export default DetailProductAdmin;