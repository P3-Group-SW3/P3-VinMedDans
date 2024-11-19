import React, { useState, useEffect } from "react";
import {useNavigate} from "react-router-dom";
import '../styles/modal.css'
import '../styles/button.css'
import cartImage from '../images/basket.png';
import OrderLineEdit from "./OrderLineEdit";


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

const CartOverlay = ({ handleClose, show }) => {
    const showHideClassName = show ? "modal display-block" : "modal display-none";
    console.log("Modal class applied:", showHideClassName);

    const navigate = useNavigate();

    const [orderLines, setOrderLines] = useState([]);

    const refreshOrderLines = () => {
        fetch('api/getAllOrderLines/jph')
            .then(response => response.json())
            .then(data => setOrderLines(data))
            .catch(error => console.error('Error fetching data: ', error));
    }

    useEffect(() => { refreshOrderLines() }, []);

    return (
        <div className={showHideClassName} onClick={handleClose}>
            <section className="modal-main" onClick={(e) => e.stopPropagation()}>
                <div className="list-group list-group-flush mb-4">
                    {orderLines.map((orderLine) => (
                        <div key={orderLine.id}
                             className="list-group-item d-flex flex-column">
                            <div className="d-flex flex-row justify-content-between align-items-center">
                                <div className="d-flex align-items-center">
                                    <img
                                        src={orderLine.wine.imageURL}
                                        alt={orderLine.wine.name}
                                        className="img-fluid"
                                        style={{width: '50px', height: '50px', objectFit: 'cover'}}
                                    />
                                </div>
                                <div className="flex-column">
                                    <span>{orderLine.wine.name}</span>
                                    < OrderLineEdit orderLine={orderLine} onUpdate={refreshOrderLines}/>
                                </div>
                                <span>{orderLine.wine.price * orderLine.amount},-</span>
                            </div>
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
                    <a className="button wide" onClick={() => navigate(`/checkout`)}>
                        Gå til betaling
                    </a>
                </div>
            </section>
        </div>
    );
};

export default Cart