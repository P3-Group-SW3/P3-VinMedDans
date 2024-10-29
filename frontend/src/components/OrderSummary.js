import React from "react";

function OrderSummary() {
    return (
        <section>
            <h2>Navn på produktet</h2>
            <div className="list-group mb-4">
                <div className="list-group-item d-flex justify-content-between align-items-center">
                    <div className="d-flex align-items-center">
                        <div className="product-image-placeholder bg-secondary mr-3" style={{ width: '50px', height: '50px' }}></div>
                        <span>Vin1</span>
                    </div>
                    <span>2</span>
                    <span>xxx,-</span>
                </div>
                <div className="list-group-item d-flex justify-content-between align-items-center">
                    <div className="d-flex align-items-center">
                        <div className="product-image-placeholder bg-secondary mr-3" style={{ width: '50px', height: '50px' }}></div>
                        <span>Vin2</span>
                    </div>
                    <span>2</span>
                    <span>xxx,-</span>
                </div>
            </div>
            <div className="d-flex justify-content-between">
                <p>Total inkl. xxx kr. i moms</p>
                <p>xxx,-</p>
            </div>
            <div className="d-flex justify-content-between">
                <p>Rabat</p>
                <p>xxx,-</p>
            </div>
        </section>
    );
}

export default OrderSummary;