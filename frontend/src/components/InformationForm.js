import React, { useEffect, useState }from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Section from './Section';
import Footer from './Footer';
import '../styles/InformationForm.css';

class InformationForm extends React.Component {
    render() {
        return (
            <div className="square">
                <form>
                    Kontakt oplysninger
                    <input name="frontName" placeholder="Fornavn"/>
                    <input name="lastName" placeholder="Efternavn"/>
                    <input name="mail" placeholder="Mail"/>
                    <input name="phoneNumber" placeholder="Tillefon nummer"/>
                    {" "}
                    Levering
                    <CountrySelect />
                    <input name="areaCode" placeholder="Postnummer"/>
                    <input name="adres" placeholder="Adresse"/>
                    <input name="town" placeholder="By"/>
                </form>
            </div>)


    }
}

const CountrySelect = () => {
    const [countries, setCountries] = useState([]);
    const [selectedCountry, setSelectedCountry] = useState({});

    useEffect(() => {
        fetch(
            "https://valid.layercode.workers.dev/list/countries?format=select&flags=true&value=code"
        )
            .then((response) => response.json())
            .then((data) => {
                setCountries(data.countries);
                setSelectedCountry(data.userSelectValue);
            });
    }, []);
    return (
        <Select
            options={countries}
            value={selectedCountry}
            onChange={(selectedOption) => setSelectedCountry(selectedOption)}
        />
    );
};