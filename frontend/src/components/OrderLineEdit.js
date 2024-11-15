import React, { useState } from "react";
import Button from "./Button";

const OrderLineEdit = (orderLine) => {
    const [quantity, setQuantity] = useState(orderLine.amount);

    const incrementQuantity = () => {
        setQuantity(quantity => quantity + 1);
    }

    const decrementQuantity = () => {
        setQuantity(quantity => quantity - 1);
    }

    return (
        <div className="d-inline-flex align-items-center gap 2">
            < Button
                text="-"
                onClick={decrementQuantity}
                makeSquare={true}
                makeCircle={false}
            />

            <div className="quantity-display" style={{ width: '50 px', textAlign: 'center'}}>
                {quantity}
            </div>

            < Button
                text="+"
                onClick={incrementQuantity}
                makeSquare={true}
                makeCircle={false}
            />
        </div>
    )
}

export default OrderLineEdit;