import React, { useState } from "react";
import Button from './Button';

export const CartModify = () => {
  const [quantity, setQuantity] = useState(1);

  const incrementQuantity = () => {
    setQuantity(prevQuantity => Math.min(prevQuantity + 1, 10));
  };

  const decrementQuantity = () => {
    setQuantity(prevQuantity => Math.max(prevQuantity - 1, 1));
  };

  return (
    <div className="d-inline-flex align-items-center gap-3 position-relative">
      <div className="d-inline-flex align-items-center gap-2">
        <Button 
          text="-" 
          onClick={decrementQuantity} 
          makeCircle={true}
        />
        
        <div className="quantity-display" style={{ width: "50px", textAlign: "center" }}>
          {quantity}
        </div>

        <Button 
          text="+" 
          onClick={incrementQuantity} 
          makeCircle={true}
        />
      </div>
      <div style={{ margin: '0 10px' }}>
        <Button
          text="FØJ TIL KURV"
          onClick={() => console.log("Button clicked")}
        />
      </div>
    </div>
  );
};