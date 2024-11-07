import React, { useState } from "react";
import {useNavigate} from "react-router-dom";
import OrderSummary from './OrderSummary'
import '../styles/modal.css'
import cartImage from '../images/basket.png';


const Cart = () => {

    const navigate = useNavigate();

    const [show, setShow] = useState(false);

    const toggleShow = () => {
        setShow((show) => !show);
        console.log("Modal state after click:", show);
    };

    return (
        <div className="d-flex">
            <CartOverlay show={show} handleClose={toggleShow}>
                <OrderSummary/>
                <a className="button d-flex justify-self-center" onClick={() => navigate(`/checkout`)}>
                    Gå til betaling
                </a>
            </CartOverlay>
            <a className="cart-button" role="button" onClick={toggleShow}>
                <img src={cartImage} className="img-fluid" alt="Kurv"/>
            </a>
        </div>
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