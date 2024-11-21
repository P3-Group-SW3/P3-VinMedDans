import React from 'react';
import Button from './Button';

function IncDecButton({ decrementQuantity, incrementQuantity, quantity, scale = 1}) {
    const quantityStyle = {
        width: `${30 * scale}px`,
        fontSize: `${20 * scale}px`,
        lineHeight: `${30 * scale}px`,
    };

    return (
        <div className="d-flex" style={{ transform: `scale(${scale})` }}>
            <Button
                text="-"
                onClick={decrementQuantity}
                flatRight={true}
                scale={scale}
            />

            <div className="quantity-display bg-white" style={quantityStyle}>
                <span>{quantity}</span>
            </div>

            <Button
                text="+"
                onClick={incrementQuantity}
                flatLeft={true}
                scale={scale}
            />
        </div>
    );
}

export default IncDecButton;
