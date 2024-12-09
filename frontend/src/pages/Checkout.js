import React, { useState, useEffect } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import { CustomerLinks } from "./CustomerLinkContext";
import Button from "../components/Button";

function CheckoutPage() {
    const [message, setMessage] = useState("");
    const [order, setOrder] = useState({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        address: "",
        zipCode: "",
        city: ""
    });
    const [orderLines, setOrderLines] = useState([]);
    const [termsAccepted, setTermsAccepted] = useState(false);

    useEffect(() => {
        // Fetch order lines
        fetch("/api/getAllOrderLines", {
            method: "GET",
            credentials: "include"
        })
        .then(response => response.json())
        .then(data => setOrderLines(data));
    }, []);

    const handleCheckout = async (event) => {
        event.preventDefault();
        if (!termsAccepted) {
            setMessage("Du skal acceptere vilkår og betingelser for at fortsætte.");
            return;
        }
        const response = await fetch("/api/payment/create-checkout-session", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(order)
        });
        const data = await response.json();
        if (data.url) {
            window.location.href = data.url;
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setOrder(prevOrder => ({
            ...prevOrder,
            [name]: value
        }));
    };

    const handleCheckboxChange = (e) => {
        setTermsAccepted(e.target.checked);
    };

    const total = orderLines.reduce((acc, item) => acc + item.wine.price * item.amount, 0);
    const totalQuantity = orderLines.reduce((acc, item) => acc + item.amount, 0);
    const discount = Math.floor(totalQuantity / 6) * 50;

    return (
        <div className="CheckoutPage">
            <Header
                links={CustomerLinks}
                showCart={true}
            />
            <div className="container my-5">
                <div className="row">
                    <div className="col-lg-6 mb-4">
                        <h3 className="mb-4">Faktureringsoplysninger</h3>
                        <form onSubmit={handleCheckout}>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="firstName"
                                    value={order.firstName}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="Fornavn"
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="lastName"
                                    value={order.lastName}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="Efternavn"
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <input
                                    type="email"
                                    name="email"
                                    value={order.email}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="E-mail"
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="phone"
                                    value={order.phone}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="Telefon"
                                />
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="address"
                                    value={order.address}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="Adresse"
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="zipCode"
                                    value={order.zipCode}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="Postnummer"
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <input
                                    type="text"
                                    name="city"
                                    value={order.city}
                                    onChange={handleChange}
                                    className="form-control"
                                    placeholder="By"
                                    required
                                />
                            </div>
                            <div className="form-check mb-3">
                                <input
                                    type="checkbox"
                                    className="form-check-input"
                                    id="terms"
                                    checked={termsAccepted}
                                    onChange={handleCheckboxChange}
                                />
                                <label className="form-check-label" htmlFor="terms">
                                    Jeg accepterer vilkår og betingelser
                                </label>
                            </div>
                            <div className="d-grid">
                                <Button type="submit" className="btn btn-dark mt-3" text={"Gå til betaling"}/>
                            </div>
                        </form>
                    </div>
                    <div className="col-lg-6">
                        <h3 className="mb-4">Ordreoversigt</h3>
                        <div className="list-group mb-4">
                            {orderLines.map((item) => (
                                <div key={item.ID} className="list-group-item d-flex justify-content-between align-items-center">
                                    <div className="d-flex align-items-center">
                                        <img
                                            src={item.wine.imageURL}
                                            alt={item.wine.name}
                                            className="img-fluid"
                                            style={{ width: '50px', height: '50px', objectFit: 'cover' }}
                                        />
                                    </div>
                                    <span>{item.wine.name}</span>
                                    <span>{item.amount}</span>
                                    <span>{item.wine.price * item.amount},-</span>
                                </div>
                            ))}
                        </div>
                        <div className="d-flex justify-content-between">
                            <p>Total inkl. moms</p>
                            <p>{total},-</p>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    );
}

export default CheckoutPage;