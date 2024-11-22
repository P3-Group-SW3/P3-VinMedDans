import React, { useState } from "react";
import IncDecButton from "./IncDecButton";

const OrderLineEdit = ({orderLine, onUpdate}) => {
    const [quantity, setQuantity] = useState(orderLine.amount);

    const incrementQuantity = () => {
        console.log("Previous quantity: ", orderLine.amount);

        orderLine.amount = quantity + 1;
        setQuantity(quantity => orderLine.amount);

        fetch('/api/createAndEditOrderLine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify( orderLine ),
        })
            .then(response => response.json())
            .then(data => console.log('Success:', data))
            .catch((error) => {
                console.error('Error:', error);
            });
        console.log("Current quantity: ", quantity);
    }

    const decrementQuantity = () => {
        console.log("Previous quantity: ", orderLine.amount);

        if (quantity - 1 > 0) {
            orderLine.amount = quantity - 1;
            setQuantity(quantity => orderLine.amount);

            fetch('/api/createAndEditOrderLine', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify( orderLine ),
            })
                .then(response => response.json())
                .then(data => console.log('Success:', data))
                .catch((error) => {
                    console.error('Error:', error);
                });
            console.log("Current quantity: ", quantity);
        } else if (quantity - 1 === 0) {
            fetch('/api/deleteOrderLine', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify( orderLine ),
            })
                .then(response => response.json())
                .then(data => console.log('Success:', data))
                .then(() => onUpdate())
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
    }

    return (
        <IncDecButton
            decrementQuantity={decrementQuantity}
            incrementQuantity={incrementQuantity}
            quantity={quantity}
            scale={0.8}
        />
    )
}

export default OrderLineEdit;