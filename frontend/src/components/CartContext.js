import React, { createContext, useContext, useState } from "react";

const CartContext = createContext(null);

export const CartProvider = ({ children }) => {
    const [orderLines, setOrderLines] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);

    const refreshCart = () => {
        fetch('api/getAllOrderLines')
            .then(response => response.json())
            .then(data => setOrderLines(data))
            .catch(error => console.error('Error fetching data:', error));

        fetch('api/getPrice')
            .then(response => response.json())
            .then(data => setTotalPrice(data))
            .catch(error => console.error('Error fetching price:', error));

        console.log("Refreshed cart.");
    }

    return (
        <CartContext.Provider value={{ orderLines, totalPrice, refreshCart }}>
            {children}
        </CartContext.Provider>
    );
};

export const useCart = () => useContext(CartContext);