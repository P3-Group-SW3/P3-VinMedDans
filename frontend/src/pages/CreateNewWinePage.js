import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function CreateNewWinePage() {
    const [wineData, setWineData] = useState({
        name: '',
        amountLeft: 0,
        price: 0,
        description: '',
        imageURL: '',
    });
    const navigate = useNavigate();

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setWineData({ ...wineData, [name]: value });
    };

    const handleCreateNewWine = () => {
        fetch('/api/wine/admin/createAndEdit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(wineData),
        })
            .then(response => {
                if (!response.ok) throw new Error('Failed to create wine');
                return response.json();
            })
            .then(() => {
                alert('New wine created successfully!');
                navigate('/admin');
            })
            .catch(error => console.error('Error creating wine:', error));
    };

    return (
        <div className="container">
            <h2>Create New Wine</h2>
            <form>
                <div className="form-group">
                    <label>Name</label>
                    <input type="text" name="name" value={wineData.name} onChange={handleInputChange} className="form-control" />
                </div>
                <div className="form-group">
                    <label>Amount Left</label>
                    <input type="number" name="amountLeft" value={wineData.amountLeft} onChange={handleInputChange} className="form-control" />
                </div>
                <div className="form-group">
                    <label>Price</label>
                    <input type="number" name="price" step="0.01" value={wineData.price} onChange={handleInputChange} className="form-control" />
                </div>
                <div className="form-group">
                    <label>Description</label>
                    <textarea name="description" value={wineData.description} onChange={handleInputChange} className="form-control" />
                </div>
                <div className="form-group">
                    <label>Image URL</label>
                    <input type="file" name="imageURL" value={wineData.imageURL} onChange={handleInputChange} className="form-control" />
                </div>
                <button type="button" onClick={handleCreateNewWine} className="btn btn-primary mt-3">
                    Submit
                </button>
            </form>
        </div>
    );
}

export default CreateNewWinePage;