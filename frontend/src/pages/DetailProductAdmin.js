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
                setNewAmountLeft(data.amountLeft); // Sæt initial værdi for `newAmountLeft`
            })
            .catch(error => console.error('Error fetching product:', error));
    }, [id]);

    // Funktion til at håndtere opdatering af `amountLeft`
    const handleUpdateAmountLeft = async () => {
        try {
            const response = await fetch(`/api/wine/admin/createAndEdit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: product.id,
                    name: product.name,
                    price: product.price,
                    description: product.description,
                    imageURL: product.imageURL,
                    amountLeft: newAmountLeft // Opdateret værdi for amountLeft
                }),
            });

            if (response.ok) {
                setProduct({ ...product, amountLeft: newAmountLeft });
                alert('AmountLeft updated successfully!');
            } else {
                alert('Failed to update AmountLeft.');
            }
        } catch (error) {
            console.error('Error updating AmountLeft:', error);
            alert('An error occurred while updating AmountLeft.');
        }
    };

    if (!product) return <div>Loading...</div>;

    return (
        <div>
            <h1>{product.name}</h1>
            <p>ID: {product.id}</p>
            <p>Price: {product.price}</p>
            <p>Description: {product.description}</p>
            <p>Amount Left: {product.amountLeft}</p>
            <img src={product.imageURL} alt={product.name} style={{ width: '200px' }} />

            <div style={{ marginTop: '20px' }}>
                <label>
                    New Amount Left:
                    <input
                        type="number"
                        value={newAmountLeft}
                        onChange={(e) => setNewAmountLeft(e.target.value)}
                        min="0"
                        style={{ marginLeft: '10px' }}
                    />
                </label>
                <button onClick={handleUpdateAmountLeft} style={{ marginLeft: '10px' }}>Update</button>
            </div>
        </div>
    );
}

export default DetailProductAdmin;