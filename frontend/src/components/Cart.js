import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import { useCart } from "./CartContext";
import '../styles/button.css';
import '../styles/header.css';
import cartImage from '../images/basket.png';
import removeImage from '../images/remove.svg';
import Button from "./Button";
import IncDecButton from "./IncDecButton";

/*
 * The Cart component handles the dropdown-menu functionality.
 */
const Cart = () => {

    //Outer component with dropdown-menu button and container with the CartContent.
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

/*
 * The CartContent component handles the list of order lines.
 */
const CartContent = () => {

    //Using the CartContext to get order lines, total price and refreshCart function
    const { orderLines, totalPrice, refreshCart } = useCart();
    const navigate = useNavigate();

    //On mount: Refresh cart content
    useEffect(() => {
        refreshCart();
    }, [])

    //Empty cart by using clearCart API
    const emptyCart = () => {
        fetch('api/clearCart/')
            .then(response => console.log(response))
            .then(refreshCart)
            .catch(error => console.error('Error fetching data: ', error));
    }

    //Remove an order line from cart by using deleteOrderLine API
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

    //Show a list of order lines if there are any.
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
    //If there are no order lines for this customer, show text indicating the cart is empty.
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

/*
 * The OrderLineEdit component is in each order line
 * It allows a customer to change amount of a product in their cart.
 */
const OrderLineEdit = ({orderLine}) => {

    //Uses refreshCart from CartContext.
    const { refreshCart } = useCart();
    //The state 'quantity' tracks the amount of products in the specified order line
    const [quantity, setQuantity] = useState(orderLine.amount);

    //Based on the value of 'change' parameter, quantity state is updated as well as amount stored in orderline
    const updateQuantity = (change) => {

        const newQuantity = quantity + change;

        //Only change quantity if it remains a positive integer
        if (newQuantity > 0) {
            setQuantity(newQuantity) //local state
            orderLine.amount = newQuantity; //store in order line object
        }
        
        //Save updated order line to the database
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