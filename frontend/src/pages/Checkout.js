import React from 'react';
import Header from '../components/Header';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import InformationForm from '../components/InformationForm';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';


function Checkout() {
    return (
        <div className="Landingpage">
            <Header />
            <Navbar />
            <InformationForm />
            <Footer />
        </div>
    );
}

export default Checkout;
