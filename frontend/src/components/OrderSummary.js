import React from "react";
import Orders from "./Orders";

function OrderSummary() {
    const total = Orders.reduce((acc, item) => acc + item.price * item.quantity, 0);

    // Calculates amount of bottles
    const totalQuantity = Orders.reduce((acc, item) => acc + item.quantity, 0);

    // Calculate discount
    const discount = Math.floor(totalQuantity / 6) * 50;



    return (
        <section>
            <div className="list-group mb-4">
                {Orders.map((item) => (
                    <div key={item.id} className="list-group-item d-flex justify-content-between align-items-center">
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
                <p>{total},-</p>
            </div>
            <div className="d-flex justify-content-between">
                <p>Rabat</p>
                <p>{discount},-</p>
            </div>
            <div className="d-flex justify-content-between">
                <p>Samlet beløb</p>
                <p>{total - discount},-</p>
            </div>
        </section>
    );
}

export default OrderSummary;