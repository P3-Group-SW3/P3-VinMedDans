import React from 'react';
import '../styles/admin.css'
import AdminModule from "../components/AdminModule";

const AdminDashboard = () => {

    const productColumns = [
        { header: 'Navn', field: 'name' },
        { header: 'Antal', field: 'amountLeft' }
    ];
    const orderColumns = [
        { header: 'Ordrenummer', field: 'id' },
        { header: 'Navn', field: 'full_name' },
        { header: 'Dato', field: 'date' },
        { header: 'Status', field: 'state' }
    ];

    return (
        <div className="admin-dashboard">
            <AdminModule category="products" title="sortiment" apiHandle="/api/wine/getList" columns={productColumns}/>
            <AdminModule category="orders" title="ordrer" apiHandle="/api/orders/getList" columns={orderColumns}/>
        </div>
    );
}

export default AdminDashboard;