import React, { useState } from "react";
import CartOverlay from './CartOverlay'
import '../styles/modal.css'

const Cart = () => {
    const [show, setShow] = useState(true);

    const showModal = () => {
        setShow((show) => !show); // Toggle state instead of just setting to true
        console.log("Modal state after click:", show); // Log to confirm state after update
    };

    const hideModal = () => {
        setShow(false);
    };

    return (
        <main>
            <CartOverlay show={show} handleClose={hideModal}>
                <p>Modal</p>
            </CartOverlay>
            <button type="button" onClick={showModal}>
                Open
            </button>
        </main>
    );
};

export default Cart