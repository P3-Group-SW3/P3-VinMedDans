import React from 'react';
import '../styles/admin.css'
import AdminModule from "../components/AdminModule";

/*
 * The AdminDashBoard page displays the different AdminModules for
 * wine, order, event and distributor.
 * The columns define the information shown in the columns of each AdminModule table.
 * Headers are shown in the table header, fields are mapped to each row
 */
const AdminDashboard = () => {

    const wineColumns = [
        { header: 'Navn', field: 'name' },
        { header: 'Antal', field: 'stock' }
    ];
    const orderColumns = [
        { header: 'Ordrenr.', field: 'id' },
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
            <AdminModule category="wine" title="sortiment" columns={wineColumns}/>
            <AdminModule category="orders" title="ordrer" columns={orderColumns}/>
            <AdminModule category="event" title="events" columns={eventColumns}/>
            <AdminModule category="distributor" title="forhandlere" columns={distributorColumns}/>
        </div>
    );
}

export default AdminDashboard;