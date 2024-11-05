import React, { useState } from "react";
import OrderSummary from './OrderSummary'
import '../styles/modal.css'


const Cart = () => {
    const [show, setShow] = useState(false);

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
                <OrderSummary />
            </CartOverlay>
            <button type="button" onClick={showModal}>
                Kurv
            </button>
        </main>
    );
};

const CartOverlay = ({ handleClose, show, children }) => {
    const showHideClassName = show ? "modal display-block" : "modal display-none";
    console.log("Modal class applied:", showHideClassName);

    return (
        <div className={showHideClassName} onClick={handleClose}>
            <section className="modal-main" onClick={(e) => e.stopPropagation()}>
                {children}
            </section>
        </div>
    );
};

export default Cart