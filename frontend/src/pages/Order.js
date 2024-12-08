import { CustomerLinks } from "./CustomerLinkContext";
import Header from "../components/Header";
import Footer from "../components/Footer";
import { useState, useEffect } from "react";

const usePaymentStatus = () => {
    const [message, setMessage] = useState("");

    useEffect(() => {
        const query = new URLSearchParams(window.location.search);

        if (query.get("success")) {
            const sessionId = query.get("session_id");
            const interval = setInterval(() => {
                fetch(`/api/payment/status?sessionId=${sessionId}`)
                    .then(response => response.json())
                    .then(data => {
                        if (data.status === "paid") {
                            setMessage("Bestilling afgivet! Du vil modtage en e-mail bekræftelse.");
                            clearInterval(interval);
                        } else {
                            setMessage("Betaling ikke gennemført. Prøv venligst igen.");
                        }
                    });
            }, 3000);
        }

        if (query.get("canceled")) {
            setMessage(
                "Bestilling annulleret -- fortsæt med at shoppe og checkout, når du er klar."
            );
        }
    }, []);

    return message;
};

function OrderPage() {
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);
    const message = usePaymentStatus();

    useEffect(() => {
        const orderId = new URLSearchParams(window.location.search).get("orderID");
        fetch(`/api/orders/${orderId}`)
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
            <div className="container my-5">
                <h1>Order Status</h1>
                <p>{message}</p>
                <h2>Order Details</h2>
                <div className="list-group mb-4">
                    {order.items.map((item) => (
                        <div key={item.id} className="list-group-item d-flex justify-content-between align-items-center">
                            <div className="d-flex align-items-center">
                                <img
                                    src={item.image}
                                    alt={item.name}
                                    className="img-fluid"
                                    style={{ width: '50px', height: '50px', objectFit: 'cover' }}
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
                    <p>{order.total},-</p>
                </div>
                <div className="d-flex justify-content-between">
                    <p>Rabat</p>
                    <p>{order.discount},-</p>
                </div>
                <div className="d-flex justify-content-between">
                    <p>Samlet beløb</p>
                    <p>{order.total - order.discount},-</p>
                </div>
            </div>
            <Footer />
        </div>
    );
}

export default OrderPage;