import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function DetailProductAdmin() {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [newAmountLeft, setNewAmountLeft] = useState('');
    const navigate = useNavigate();

    // Hent produktdata når komponenten indlæses
    useEffect(() => {
        fetch(`/api/wine/getById/${id}`)
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                setProduct(data);

                setNewAmountLeft(data.amountLeft);  // Initialiser med den nuværende værdi af amountLeft

            })
            .catch(error => console.error('Error fetching product:', error));
    }, [id]);


    // Håndter ændring af amountLeft
    const handleAmountChange = (event) => {
        setNewAmountLeft(event.target.value);
    };

    // Send opdatering af amountLeft til serveren
    const handleUpdateAmount = () => {
        if (newAmountLeft !== '') {
            fetch(`/api/wine/admin/createAndEdit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    ID: product.ID,
                    name: product.name,
                    price: product.price,
                    description: product.description,
                    amountLeft: newAmountLeft,
                    imageURL: product.imageURL
                }),
            })
                .then(response => {
                    if (response.ok) {
                        alert('Amount updated successfully');
                        setProduct(prevProduct => ({ ...prevProduct, amountLeft: newAmountLeft }));
                    } else {
                        alert('Error updating amount');
                    }
                })
                .catch(error => console.error('Error updating product:', error));
        }
    };

    // Håndter sletning af produkt
    const handleDeleteProduct = () => {
        fetch(`/api/wine/admin/delete/${product.ID}`, { method: 'POST' })
            .then(response => {
                if (response.ok) {
                    alert('Product deleted successfully');
                    navigate('/products');  // Omdiriger til produktlisten efter sletning
                } else {
                    alert('Error deleting product');
                }
            })
            .catch(error => console.error('Error deleting product:', error));
    };

    if (!product) return <div>Loading...</div>;

    return (
        <div>
            <h1>{product.name}</h1>
            <p>ID: {product.ID}</p>
            <p>Price: {product.price}</p>
            <p>Description: {product.description}</p>
            <p>
                Amount Left:
                <input
                    type="number"
                    value={newAmountLeft}
                    onChange={handleAmountChange}
                    style={{ width: '80px' }}
                />
                <button onClick={handleUpdateAmount}>Update Amount</button>
            </p>
            <img src={product.imageURL} alt={product.name} style={{ width: '150px' }} />

            <div>
                <button onClick={handleDeleteProduct} style={{ backgroundColor: 'red', color: 'white' }}>
                    Delete Product
                </button>
            </div>
        </div>
    );
}

export default DetailProductAdmin;