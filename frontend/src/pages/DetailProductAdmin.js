import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function DetailProductAdmin() {
    const { id } = useParams();
    const [product, setProduct] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        fetch(`/api/wine/getById/${id}`)
            .then((response) => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then((data) => {
                setProduct(data);
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

                    <div className="d-flex justify-content-end">
                        <button onClick={handleDeleteProduct} className="btn btn-danger"> Delete Product </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DetailProductAdmin;