import React from 'react';
import Button from './Button'

function IncDecButton({ decrementQuantity, incrementQuantity, quantity }) {

    return (
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
    );
}

export default IncDecButton;