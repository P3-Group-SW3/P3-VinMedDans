import React from 'react';
import '../styles/styles.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import OrdersAdmin from "../components/OrdersAdmin";
import ProductsAdmin from "../components/ProductsAdmin";

function AdminPage() {
    return (
        <div className="container-fluid">
            <div className="row mt-4">
                <div className="col-lg-6 col-md-12 mb-4">
                    <div className="card shadow-sm">
                        <div className="card-header bg-secondary text-white">
                            <h5>Orders</h5>
                        </div>
                        <div className="card-body overflow-auto" style={{ maxHeight: '400px' }}>
                            <OrdersAdmin />
                        </div>
                    </div>
                </div>
                <div className="col-lg-6 col-md-12 mb-4">
                    <div className="card shadow-sm">
                        <div className="card-header bg-secondary text-white">
                            <h5>Products</h5>
                        </div>
                        <div className="card-body overflow-auto" style={{ maxHeight: '400px' }}>
                            <ProductsAdmin />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AdminPage;
