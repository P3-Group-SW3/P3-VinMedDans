import React, {useState} from "react";
import Button from './Button';
import IncDecButton from "./IncDecButton";
import {useCart} from "./CartContext";

/*
 * The CartModify component handles adding a specified amount of product to a customer's cart
 */
export const CartModify = (item) => {
    //Get refreshCart from CartContext and set the local state 'quantity' to a default value 1
    const { refreshCart } = useCart();
    const [quantity, setQuantity] = useState(1);


    const updateQuantity = (change) => {
        const newQuantity = quantity + change;

        //Only change quantity if it is between 1 and 10
        if (newQuantity > 0 && newQuantity <= 10) {
          setQuantity(newQuantity)
        }
    };

    // API call to add product to cart
    const addToCart = () => {
        fetch('/api/createAndEditOrderLine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ wineID: item.item.id, amount: quantity }),
        })
            .then(response => response.json())
                .then(() => refreshCart())
            .catch((error) => {
              console.error('Error:', error);
            });

        //Reset quantity
        setQuantity(1);
        console.log("Added to cart.")
  }

    return (
        <div className="d-flex" style={{ gap: "1rem" }}>
            <IncDecButton
                decrementQuantity={() => updateQuantity(-1)}
                incrementQuantity={() => updateQuantity(+1)}
                quantity={quantity}
                scale={1}
            />
            <Button
              text="Føj til kurv"
              isWide={true}
              onClick={ addToCart }
            />
        </div>
    );
};

export default CartModify;