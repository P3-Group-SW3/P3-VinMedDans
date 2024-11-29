import React, { useState, useEffect } from 'react';
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
