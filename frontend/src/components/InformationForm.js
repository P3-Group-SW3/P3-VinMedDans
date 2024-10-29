import React, { useEffect, useState }from 'react';
import Select from "react-select";
import '../styles/InformationForm.css';

const InformationForm = () => {
    return (
        <div className="square">
            <form>
                <br/>
                <h2>Kontakt oplysninger</h2><br/>
                <div className="diname">
                    <br/><input className="name" name="firstName" placeholder="Fornavn"/>
                    <input className="name" name="lastName" placeholder="Efternavn"/><br/>
                </div>
                <br/>
                <div className="contacts">
                    <br/><input className="mail" name="mail" placeholder="Mail"/>
                    <input name="phoneNumber" placeholder="Tillefon nummer"/><br/>
                </div>

                <br/><br/>
                Levering<br/>
                <CountrySelect/><br/>
                <input name="areaCode" placeholder="Postnummer"/>
                <input name="town" placeholder="By"/>
                <input name="adres" placeholder="Adresse"/>
            </form>
        </div>)
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

export default InformationForm;