import React, { useEffect, useState }from 'react';
import Select from "react-select";
import '../styles/InformationForm.css';
import '../styles/styles.css';
import '../bootstrap/dist/css/bootstrap.min.css'

const InformationForm = () => {
    return (
        <section>
            <div name="square" className="square align-items-center" method="post">
                <form method="POST">
                    <br/>
                    <h2 >Kontakt oplysninger</h2><br/>
                    <div className="form-control-lg" name="contacts">
                        <div className="row justify-content-around">
                            <input className="name" name="firstName" placeholder="Fornavn"/>
                            <input className="name" name="lastName" placeholder="Efternavn"/>
                            <input className="name" name="mail" placeholder="Mail"/>
                            <input className="name" name="phonenumber" placeholder="Tillefon nummer"/>
                        </div>
                    </div>
                    <br/>
                    <h2>Levering</h2><br/>
                    <div className="CountrySelect align-self-center" name="CountrySelect">
                        <CountrySelect/>
                    </div>
                    <div className="form-control-lg">
                        <div className="row justify-content-around">
                            <input className="levering" name="areaCode" placeholder="Postnummer"/>
                            <input className="levering" name="town" placeholder="By"/>
                            <input className="levering" name="adres" placeholder="Adresse"/>

                        </div>
                    </div>
                    <div className="row justify-content-around ">
                        <button type="submit" className="btn btn-dark mt-3 align-self-center">Bekræft Køb (placeholder)</button>
                    </div>


                </form>
            </div>
        </section>)
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