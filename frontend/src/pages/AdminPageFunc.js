import React, { useState, useEffect } from 'react';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';
import OrdersAdmin from "../components/OrdersAdmin";
import ProductsAdmin from "../components/ProductsAdmin";

function AdminPageFunc() {

    return(
        <div>
            <OrdersAdmin />
            <ProductsAdmin />
        </div>
    )
}



export default AdminPageFunc;
