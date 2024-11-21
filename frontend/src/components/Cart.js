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
        fetch('api/getAllOrderLines')
            .then(response => response.json())
            .then(data => setOrderLines(data))
            .catch(error => console.error('Error fetching data: ', error));
        console.log("Orderline:", orderLines);
    }

    useEffect(() => { refreshOrderLines() }, []);

    return (
        <div className={showHideClassName} onClick={handleClose}>
            <section className="modal-main" onClick={(e) => e.stopPropagation()}>
                <div className="list-group list-group-flush mb-4">
                    {orderLines.map((orderLine) => (
                        <div key={orderLine.id}
                             className="list-group-item d-flex px-0 py-3">
                            <img
                                src={orderLine.wine.imageURL}
                                alt={orderLine.wine.name}
                                className="img-fluid"
                                style={{width: '50px', height: '50px', objectFit: 'cover'}}
                            />
                            <div className="flex-column w-100">
                                <div className="d-flex justify-content-between ml-2">
                                    <span>{orderLine.wine.name}</span>
                                    <span>{orderLine.wine.price * orderLine.amount},-</span>
                                </div>
                                <div className="d-flex justify-content-start">
                                    < OrderLineEdit orderLine={orderLine} onUpdate={refreshOrderLines}/>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="d-flex justify-content-between">
                    <p className="mb-0">Total inkl. moms</p>
                    <p className="mb-0">100,-</p>
                </div>
                <div className="d-flex justify-content-between">
                    <p className="mb-0">Rabat</p>
                    <p className="mb-0">200,-</p>
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