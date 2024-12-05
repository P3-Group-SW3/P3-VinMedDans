import React, { createContext, useContext, useState } from "react";

const CartContext = createContext(null);

export const CartProvider = ({ children }) => {
    const [orderLines, setOrderLines] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);

    //Refresh the orderLines and total price for current customerID
    const refreshCart = () => {
        fetch('api/getAllOrderLines')
            .then(response => response.json())
            .then(data => {
                if (data) {
                    setOrderLines(data);
                } else {
                    console.warn('Received empty data for order lines');
                }
            })
            .catch(error => {
                console.error('Error fetching order lines:', error);
            });

        fetch('api/getPrice')
            .then(response => response.json())
            .then(data => {
                if (data)
                    setTotalPrice(data);
                else {
                    console.warn('Received empty data for total price')
                }
            })
            .catch(error => console.error('Error fetching price:', error));
    }

    return (
        <CartContext.Provider value={{ orderLines, totalPrice, refreshCart }}>
            {children}
        </CartContext.Provider>
    );
};

export const useCart = () => useContext(CartContext);