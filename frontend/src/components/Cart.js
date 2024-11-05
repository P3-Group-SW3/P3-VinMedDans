import React, { useState } from "react";
import {useNavigate} from "react-router-dom";
import OrderSummary from './OrderSummary'
import '../styles/modal.css'


const Cart = () => {

    const navigate = useNavigate();

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
                <OrderSummary/>
                <a className="button" onClick={() => navigate(`/checkout`)}>
                    Gå til betaling
                </a>
            </CartOverlay>
            <button className="cart-button" type="button" onClick={showModal}>
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