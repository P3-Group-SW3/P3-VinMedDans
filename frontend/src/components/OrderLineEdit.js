import React, { useState } from "react";
import IncDecButton from "./IncDecButton";
import {useCart} from "./CartContext";

const OrderLineEdit = ({orderLine}) => {
    const { refreshCart } = useCart();
    const [quantity, setQuantity] = useState(orderLine.amount);

    const updateQuantity = (change) => {
        let newQuantity = orderLine.amount + change;
        if (newQuantity > 0) {
            setQuantity(newQuantity)
        }

        fetch('/api/createAndEditOrderLine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify( orderLine ),
        })
            .then(response => response.json())
            .then(data => console.log('Success:', data))
            .then(refreshCart)
            .catch((error) => {
                console.error('Error:', error);
            });
        console.log("Current quantity: ", quantity);
    }

    return (
        <IncDecButton
            decrementQuantity={() => updateQuantity(-1)}
            incrementQuantity={() => updateQuantity(+1)}
            quantity={quantity}
            scale={0.8}
        />
    )
}

export default OrderLineEdit;