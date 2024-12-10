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
        { header: 'Navn', field: 'fullName' },
        { header: 'Dato', field: 'date' },
        { header: 'Status', field: 'state' }
    ];

    return (
        <div className="admin-dashboard">
            <AdminModule category="wine" title="sortiment" columns={productColumns}/>
            <AdminModule category="orders" title="ordrer" columns={orderColumns}/>
        </div>
    );
}

export default AdminDashboard;