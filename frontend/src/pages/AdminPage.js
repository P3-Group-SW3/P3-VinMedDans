import React, { useEffect, useState } from 'react';

function WineList() {
    const [wines, setWines] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8000/api/getAllWines')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => setWines(data))
            .catch(error => console.error('Error fetching wines:', error));
    }, []);

    return (
        <div>
            <h1>Our Wines</h1>
            <ul>
                {wines.map(wine => (
                    <li key={wine.id}>
                        <h2>{wine.name}</h2>
                        <img src={wine.imageURL} />
                        <p>{wine.description}</p>
                        <p>Price: ${wine.price}</p>
                        <p>In stock: {wine.amountLeft}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default WineList;