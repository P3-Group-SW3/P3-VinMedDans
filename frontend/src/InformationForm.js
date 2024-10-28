import React from 'react';
import Header from './components/Header';
import Navbar from './components/Navbar';
import Section from './components/Section';
import Footer from './components/Footer';
import './styles/InformationForm.css';

class InformationForm extends React.Component {
    render() {
        return (
            <div className="square">
                <form>
                    Kontakt oplysninger
                    <input name="name" placeholder="Name"/>
                    <input name="mail" placeholder="Mail"/>
                    <input name="phoneNumber" placeholder="Phone Number"/>
                    {" "}
                    Adresse

                </form>
            </div>)


    }
}