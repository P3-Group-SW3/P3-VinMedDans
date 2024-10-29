import React from 'react';
import Header from '../components/Header';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import InformationForm from '../components/InformationForm';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';
import OrderSummary from "../components/OrderSummary";
import ContactInfo from "../components/ContactInfo";


function Checkout() {
    return (
        <div className="Landingpage">
            <Header />
            <Navbar />
            <main className="container my-4">
                <div className="row mt-2">
                    <div className="col-md-6">
                        <InformationForm/>
                    </div>
                    <div className="col-md-6">
                        <OrderSummary/>
                    </div>
                </div>
            </main>
            <Footer/>
        </div>
    );
}

export default Checkout;
