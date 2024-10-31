import React from 'react';
import Header from '../components/Header';
import Navbar from '../components/Navbar';
import Section from '../components/Section';
import Footer from '../components/Footer';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';


function Landingpage() {
    return (
        <div className="Landingpage">
            <Header />
            <Navbar />
            <Section
                title="Hvad er frugtvin"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                imagePosition="left"
                imagePath='image4'
            />
            <Section
                title="Jord til bord"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                imagePosition="right"
                imagePath='image5'
            />
            <Section
                title="Køb vores frugtvin"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                buttonText="GÅ TIL WEBSHOP"
                imagePosition="left"
                imagePath='image6'
            />
            <Footer />
        </div>
    );
}

export default Landingpage;
