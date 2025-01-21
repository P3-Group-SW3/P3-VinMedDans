import React from 'react';
import Button from './Button';

/*
 * IncDecButton (increase/decrease) displays a quantity with - and + buttons on each side
 * The increment and decrement functions are customisable, as well as the scale
 */
const IncDecButton = ({ decrementQuantity, incrementQuantity, quantity, scale = 1}) => {

    //Scale of the quantity display is scaled by the parameter 'scale' (default is 1)
    const quantityStyle = {
        width: `${30 * scale}px`,
        fontSize: `${20 * scale}px`,
        lineHeight: `${30 * scale}px`,
    };

    return (
        <div className="d-flex" style={{ transform: `scale(${scale})` }}>
            {/* Button is flat on side facing the quantity display and scaled to the same size */}
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
