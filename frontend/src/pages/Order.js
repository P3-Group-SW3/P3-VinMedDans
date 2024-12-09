import { CustomerLinks } from "./CustomerLinkContext";
import Header from "../components/Header";
import Footer from "../components/Footer";
import StateCircles from "../components/StateCircles";
import { useState, useEffect } from "react";

import "../styles/order.css";

const usePaymentStatus = () => {
    const [state, setState] = useState("REGISTERED");
    const states = ["REGISTERED", "CONFIRMED", "PACKED", "SHIPPED"];

    useEffect(() => {
        const sessionId = new URLSearchParams(window.location.search).get("session_id");

        const fetchPaymentStatus = () => {
            if (sessionId) {
                fetch(`/api/orders/state/session/${sessionId}`)
                    .then(response => response.json())
                    .then(data => {
                        setState(states[data]);
                    })
                    .catch(error => {
                        console.error("Error fetching payment status:", error);
                    });
            }
        };

        fetchPaymentStatus(); // Check once on load

        const interval = setInterval(fetchPaymentStatus, 60000); // Check every 1 minute

        return () => clearInterval(interval);
    }, []);

    return state;
};

function OrderPage() {
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);
    const message = usePaymentStatus();

    useEffect(() => {
        const session_id = new URLSearchParams(window.location.search).get("session_id");
        fetch(`/api/orders/session/${session_id}`)
            .then(response => response.json())
            .then(data => {
                setOrder(data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Error fetching order:", error);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!order) {
        return <div>Order not found</div>;
    }

    return (
        <div className="OrderPage">
            <Header
                links={CustomerLinks}
                showCart={false}
            />
            <div className="container py-5">
                <div className="card shadow-sm">
                    <div className="card-body">
                    <div className="d-flex flex-column align-items-center">
                        <h2 className="card-title">Order Details</h2>

                        <div className="mb-4">
                            <StateCircles
                                state={message}
                                customStateNames={{
                                    "REGISTERED": "Modtaget",
                                    "CONFIRMED": "Bekræftet",
                                    "PACKED": "Pakket",
                                    "SHIPPED": "Afsendt"
                                }}
                            />
                        </div>
                    </div>

                        <div className="list-group mb-4">
                            {order.orderLines.map((orderLine) => (
                                <div key={orderLine.id} className="list-group-item d-flex justify-content-between align-items-center">
                                    <div className="d-flex align-items-center">
                                        <img
                                            src={orderLine.wine.imageURL}
                                            alt={orderLine.wine.name}
                                            className="img-fluid"
                                            style={{ width: '50px', height: '50px', objectFit: 'cover' }}
                                        />
                                    </div>
                                    <span>{orderLine.wine.name}</span>
                                    <span>{orderLine.quantity}</span>
                                    <span>{orderLine.wine.price * orderLine.quantity},-</span>
                                </div>
                            ))}
                        </div>
                        <div className="d-flex justify-content-between">
                            <p>Total inkl. moms</p>
                            <p>{order.orderLines.reduce((total, orderLine) => total + (orderLine.wine.price * orderLine.quantity), 0)},-</p>
                        </div>
                        <div className="d-flex justify-content-between">
                            <p>Samlet beløb</p>
                            <p>{order.orderLines.reduce((total, orderLine) => total + (orderLine.wine.price * orderLine.quantity), 0) - (order.discount || 0)},-</p>
                        </div>
                    </div>
                </div>
            </div>
            <Footer className="sticky-footer mt-auto w-100"/>
        </div>
    );
}

export default OrderPage;