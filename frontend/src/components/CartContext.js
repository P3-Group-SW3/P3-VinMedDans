import React, { createContext, useContext, useState } from "react";

//Default context value is null
const CartContext = createContext(null);

/*
 * The CartProvider component can wrap any number of components.
 * These child components will have access to the context's functions and variables.
 */
export const CartProvider = ({ children }) => {
    //The states orderLines and totalPrice are declared with default values
    const [orderLines, setOrderLines] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);

    //use getAllOrderLines and getPrice APIs to populate the state variables
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

//This custom hook can be used in any children of the CartProvider to import variables and functions
export const useCart = () => useContext(CartContext);