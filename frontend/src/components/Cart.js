import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import { useCart } from "./CartContext";
import '../styles/button.css';
import '../styles/header.css';
import cartImage from '../images/basket.png';
import removeImage from '../images/remove.svg';
import Button from "./Button";
import IncDecButton from "./IncDecButton";

const Cart = () => {

    return (
        <div className="dropdown justify-self-end me-2">
            <button className="unstyled" type="button" data-bs-toggle="dropdown" data-bs-auto-close="outside"
                    aria-expanded="false">
                <img src={cartImage} className="img-fluid" alt="Kurv" />
            </button>
            <ul className="dropdown-menu dropdown-menu-end border-0 bg-transparent">
                <CartContent />
            </ul>
        </div>
    );
};

const CartContent = () => {

    const { orderLines, totalPrice, refreshCart } = useCart();

    useEffect(() => {
        refreshCart();
    }, [])

    const navigate = useNavigate();


    const emptyCart = () => {
        fetch('api/clearCart/')
            .then(response => console.log(response))
            .then(refreshCart)
            .catch(error => console.error('Error fetching data: ', error));
    }

    const removeFromCart = (orderLine) => {
        fetch('api/deleteOrderLine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify( orderLine )
        })
            .then(response => console.log(response))
            .then(refreshCart)
            .catch(error => console.error('Error fetching data: ', error));
    }

    if (orderLines.length > 0) {
        return (
            <section className="cart-dropdown">
                <div className="list-group list-group-flush mb-4">
                    {orderLines.map((orderLine) => (
                        <div key={orderLine.id}
                             className="list-group-item d-flex px-0 py-3">
                            <img
                                src={orderLine.wine.imageURL}
                                alt={orderLine.wine.name}
                                style={{width: '50px', height: '50px', padding: '0'}}
                            />
                            <div className="flex-column w-100">
                                <div className="d-flex justify-content-between ml-2">
                                    <span>{orderLine.wine.name}</span>
                                    <span>{orderLine.wine.price * orderLine.amount},-</span>
                                </div>
                                <div className="d-flex justify-content-between ml-2">
                                    < OrderLineEdit orderLine={orderLine} />
                                    <button className="unstyled" onClick={() => removeFromCart(orderLine)}>
                                        <img src={removeImage} className="w-75" alt="Remove"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="d-flex justify-content-between">
                    <p>Samlet beløb</p>
                    <p>{totalPrice},-</p>
                </div>
                <div className="d-flex gap-2">
                    < Button text='Gå til betaling' onClick={() => navigate(`/checkout`)} isWide={true} scale={0.8} />
                    < Button text='Tøm kurv' onClick={emptyCart} isWide={true} scale={0.8} />
                </div>
            </section>
        );
    } else {
        return (
            <section className="cart-dropdown">
                <div className="d-flex">
                    <p className="my-3"> <em>Kurven er tom.</em> </p>
                </div>
            </section>
        );
    }
};

const OrderLineEdit = ({orderLine}) => {
    const { refreshCart } = useCart();
    const [quantity, setQuantity] = useState(orderLine.amount);

    const updateQuantity = (change) => {
        const newQuantity = quantity + change;
        if (newQuantity > 0) {
            setQuantity(newQuantity)
        }

        fetch('/api/createAndEditOrderLine', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify( orderLine ),
        })
            .then(response => response.json())
            .then(data => console.log('Success:', data))
            .then(refreshCart)
            .catch((error) => {
                console.error('Error:', error);
            });
        console.log("Current quantity: ", quantity);
    }

    return (
        <IncDecButton
            decrementQuantity={() => updateQuantity(-1)}
            incrementQuantity={() => updateQuantity(+1)}
            quantity={quantity}
            scale={0.8}
        />
    )
}

export default Cart;