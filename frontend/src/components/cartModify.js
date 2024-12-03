import React, {useState} from "react";
import Button from './Button';
import IncDecButton from "./IncDecButton";

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

    console.log("Item: ", item.item);

    fetch('/api/createAndEditOrderLine', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ wineID: item.item.id, amount: quantity }),
    })
    .then(response => response.json())
    .then(data => console.log('Success:', data))
    .catch((error) => {
      console.error('Error:', error);
    });

    setQuantity(1);
  }

  return (
    <div className="d-flex" style={{ gap: "1rem" }}>
        <IncDecButton
            decrementQuantity={decrementQuantity}
            incrementQuantity={incrementQuantity}
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