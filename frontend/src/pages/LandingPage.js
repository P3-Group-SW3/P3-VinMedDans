import React from 'react';
import Header from '../components/Header';
import Section from '../components/Section';
import Footer from '../components/Footer';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css';
import {CustomerLinks} from "./CustomerLinkContext";


function Landingpage() {
    return (
        <div className="Landingpage">
            <Header
                links={ CustomerLinks }
                showCart={true}
            />
            <Section
                title="Hvad er frugtvin"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                imagePosition="left"
                imagePath='http://localhost:8080/api/images/image4.png'
            />
            <Section
                title="Jord til bord"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                imagePosition="right"
                imagePath='http://localhost:8080/api/images/image5.png'
            />
            <Section
                title="Køb vores frugtvin"
                description="Lorem ipsum consectetur adipiscing elit. Maecenas tincidunt ac dolor eget gravida..."
                buttonText="Gå til webshop"
                imagePosition="left"
                imagePath='http://localhost:8080/api/images/image6.png'
            />
            <Footer />
        </div>
    );
}

export default Landingpage;
