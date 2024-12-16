import React from 'react';
import '../styles/admin.css'
import AdminModule from "../components/AdminModule";

const AdminDashboard = () => {

    const productColumns = [
        { header: 'Navn', field: 'name' },
        { header: 'Antal', field: 'stock' }
    ];
    const orderColumns = [
        { header: 'Ordrenummer', field: 'id' },
        { header: 'Navn', field: 'fullName' },
        { header: 'Dato', field: 'date' },
        { header: 'Status', field: 'state' }
    ];
    const eventColumns = [
        {header: 'Navn', field: 'title'},
        {header: 'Dato', field: 'date'},
        {header: 'Tid', field: 'time'},
        {header: 'Sted', field: 'location'}
    ]
    const distributorColumns = [
        {header: 'Navn', field: 'name'},
        {header: 'Adresse', field: 'location'},
        {header: 'Hjemmeside', field: 'websiteURL'}
    ]

    return (
        <div className="admin-dashboard">
            <AdminModule category="wine" title="sortiment" columns={productColumns}/>
            <AdminModule category="orders" title="ordrer" columns={orderColumns}/>
            <AdminModule category="event" title="events" columns={eventColumns}/>
            <AdminModule category="distributor" title="forhandlere" columns={distributorColumns}/>
        </div>
    );
}

export default AdminDashboard;