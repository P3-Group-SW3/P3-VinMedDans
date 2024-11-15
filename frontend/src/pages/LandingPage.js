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
            <Section
                title="Hvad er frugtvin"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                imagePosition="left"
                imagePath='localhost:3000/api/images/image4.jpg'
            />
            <Section
                title="Jord til bord"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                imagePosition="right"
                imagePath='localhost:3000/api/images/image5.jpg'
            />
            <Section
                title="Køb vores frugtvin"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                buttonText="Gå til webshop"
                imagePosition="left"
                imagePath='localhost:3000/api/images/image6.jpg'
            />
            <Footer />
        </div>
    );
}

export default Landingpage;
