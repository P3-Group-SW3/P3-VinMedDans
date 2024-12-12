import React, { useState, useEffect } from "react";

const ProductDisplay = () => {
    const handleCheckout = async (event) => {
        event.preventDefault();
        const response = await fetch("/api/payment/create-checkout-session", {
            method: "POST",
        });
        const data = await response.json();
        if (data.url) {
            window.location.href = data.url;
        }
    };

    return (
        <section>
            <div className="product">
                <img
                    src="https://i.imgur.com/EHyR2nP.png"
                    alt="The cover of Stubborn Attachments"
                />
                <div className="description">
                    <h3>Stubborn Attachments</h3>
                    <h5>$20.00</h5>
                </div>
            </div>
            <form onSubmit={handleCheckout}>
                <button type="submit">
                    Checkout
                </button>
            </form>
        </section>
    );
};

const Message = ({ message }) => (
    <section>
        <p>{message}</p>
    </section>
);

export default function Payment() {
    const [message, setMessage] = useState("");

    useEffect(() => {
        // Check to see if this is a redirect back from Checkout
        const query = new URLSearchParams(window.location.search);

        if (query.get("success")) {
            const sessionId = query.get("session_id");
            // Poll payment status from your backend
            const interval = setInterval(() => {
                fetch(`/api/payment/status?sessionId=${sessionId}`)
                    .then(response => response.json())
                    .then(data => {
                        if (data.status === "paid") {
                            setMessage("Order placed! You will receive an email confirmation.");
                            clearInterval(interval);
                        } else {
                            setMessage("Payment not completed. Please try again.");
                        }
                    });
            }, 3000); // Poll every 3 seconds
        }

        if (query.get("canceled")) {
            setMessage(
                "Order canceled -- continue to shop around and checkout when you're ready."
            );
        }
    }, []);

    return message ? (
        <Message message={message} />
    ) : (
        <ProductDisplay />
    );
}