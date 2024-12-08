import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function DetailProductAdmin() {
    const { id } = useParams();
    const [product, setProduct] = useState();
    const [selectedAmountLeft, setSelectedAmountLeft] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`/api/wine/getById/${id}`)
            .then((response) => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then((data) => {
                setProduct(data);
                setSelectedAmountLeft(data.amountLeft);
            })
            .catch((error) => console.error('Error fetching product:', error));
    }, [id]);

    const handleDeleteProduct = () => {
        if (window.confirm('Are you sure you want to delete this product?')) {
            fetch(`/api/wine/admin/delete/${id}`, {
                method: 'POST',
            })
                .then((response) => {
                    if (!response.ok) throw new Error('Failed to delete product');
                    return response.json();
                })
                .then(() => {
                    alert('Product deleted successfully!');
                    navigate('/admin');
                })
                .catch((error) => console.error('Error deleting product:', error));
        }
    };

    const handleEditProduct = () => {
        fetch('/api/wine/admin/createAndEdit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                id: product.id,
                amountLeft: selectedAmountLeft,
            }),
        })
            .then((response) => {
                if (!response.ok) throw new Error('Failed to edit product');
                return response.json();
            })
            .then(() => {
                alert('Product edited successfully!');
                navigate('/admin');
            })
            .catch((error) => console.error('Error editing product:', error));
        console.log('Sending data:', { id: product.id, amountLeft: selectedAmountLeft });
    };

    if (!product) {
        return (
            <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Vi brygger</span>
                </div>
            </div>
        );
    }

    return (
        <div className="container py-5">
            <div className="card shadow-sm">
                <img
                    src={product.imageURL}
                    alt={product.name}
                    className="card-img-top"
                    style={{ objectFit: 'contain', maxHeight: '200px', maxWidth: '200px' }}
                />
                <div className="card-body">
                    <h3 className="card-title">{product.name}</h3>
                    <p className="card-text"><strong>ID:</strong> {product.id}</p>
                    <p className="card-text"><strong>Price:</strong> {product.price} DKK</p>
                    <p className="card-text"><strong>Description:</strong> {product.description}</p>
                    <p className="card-text"><strong>Amount Left:</strong> {product.amountLeft}</p>
                    <div className="form-group my-3">
                        <label>Amount Left</label>
                            <input
                            type="number"
                            name="selectedAmountLeft"
                            value={selectedAmountLeft}
                            onChange={(e) => setSelectedAmountLeft(e.target.value)}
                            className="form-control"
                            />
                    </div>
                    <div className="d-flex justify-content-end">
                        <button onClick={handleEditProduct} className="btn btn-primary me-2"> Save Changes </button>

                        <button onClick={handleDeleteProduct} className="btn btn-danger"> Delete Product </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DetailProductAdmin;