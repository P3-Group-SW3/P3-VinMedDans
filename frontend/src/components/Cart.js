import React, { useState } from "react";
import {useNavigate} from "react-router-dom";
import OrderSummary from './OrderSummary'
import '../styles/modal.css'
import cartImage from '../images/basket.png';
import Orders from "./Orders";


const Cart = () => {

    const [show, setShow] = useState(false);

    const toggleShow = () => {
        setShow((show) => !show);
        console.log("Modal state after click:", show);
    };

    return (
        <div className="d-flex">
            <CartOverlay show={show} handleClose={toggleShow}>
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

    const navigate = useNavigate();

    return (
        <div className={showHideClassName} onClick={handleClose}>
            <section className="modal-main" onClick={(e) => e.stopPropagation()}>
                <div className="list-group list-group-flush mb-4">
                    {Orders.map((item) => (
                        <div key={item.id}
                             className="list-group-item d-flex justify-content-between align-items-center">
                            <div className="d-flex align-items-center">
                                <img
                                    src={item.image}
                                    alt={item.name}
                                    className="img-fluid"
                                    style={{width: '50px', height: '50px', objectFit: 'cover'}}
                                />
                            </div>
                            <span>{item.name}</span>
                            <span>{item.quantity}</span>
                            <span>{item.price * item.quantity},-</span>
                        </div>
                    ))}
                </div>
                <div className="d-flex justify-content-between">
                    <p>Total inkl. moms</p>
                    <p>100,-</p>
                </div>
                <div className="d-flex justify-content-between">
                    <p>Rabat</p>
                    <p>200,-</p>
                </div>
                <div className="d-flex justify-content-between">
                    <p>Samlet beløb</p>
                    <p>300,-</p>
                </div>
                <div className="d-flex justify-content-center">
                    <a className="button" onClick={() => navigate(`/checkout`)}>
                        Gå til betaling
                    </a>
                </div>
            </section>
        </div>
    );
};

export default Cart