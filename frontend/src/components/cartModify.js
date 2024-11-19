import React, { useState } from "react";
import Button from './Button';

export const CartModify = (item) => {
  const [quantity, setQuantity] = useState(1);

  const incrementQuantity = () => {
    setQuantity(prevQuantity => Math.min(prevQuantity + 1, 10));
  };

  const decrementQuantity = () => {
    setQuantity(prevQuantity => Math.max(prevQuantity - 1, 1));
  };

  // API call to add item to cart
  const addToCart = () => {
    console.log("Button clicked with item:", item, "quantity:", quantity);

    fetch('/api/addToCart', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ item, quantity }),
    })
    .then(response => response.json())
    .then(data => console.log('Success:', data))
    .catch((error) => {
      console.error('Error:', error);
    });
  }

  return (
    <div className="d-flex" style={{ gap: "1rem" }}>
        <div className="d-flex">
            <Button
              text="-"
              onClick={decrementQuantity}
              flatRight={true}
            />

            <div className="quantity-display bg-white">
              <span>
                  {quantity}
              </span>
            </div>

            <Button
              text="+"
              onClick={incrementQuantity}
              flatLeft={true}
            />
        </div>
        <Button
          text="Føj til kurv"
          isWide={true}
          onClick={ addToCart }
        />
    </div>
  );
};