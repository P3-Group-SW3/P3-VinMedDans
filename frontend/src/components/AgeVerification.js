import React, {useEffect, useState} from "react";
import '../styles/modal.css'
import Button from "./Button";


const AgeVerification = () => {

    const [cookieAge, setCookieAge] = useState('');
    const [show, setShow] = useState(false);
    const showClass = show ? "modal display-block" : "modal display-none";

    useEffect(() => {
        fetch('api/createCookie')
            .then(response => console.log(response))
            .catch(error => console.error('Error fetching data: ', error))

        fetch('api/cookieAge')
            .then(response => console.log(response))
            .then(data => {
                setCookieAge(data);
                console.log("Fetched cookie age:", cookieAge);
                console.log("Fetched data:", data);
                if (cookieAge === 'new') {
                    setShow(true);
                } else if (cookieAge === 'old') {
                    setShow(false);
                } else {
                    console.error("Invalid cookie age value:", data);
                }
            })
            .catch(error => console.error('Error fetching data: ', error))
    }, []);
    
    const redirectToBR = () => {
        document.location = "https://www.br.dk/";
    }

    const oldEnough = () => {
        console.log("Yessss");
        setShow(false);
        console.log("After press yes: ", show)
    }

    return (
        <div className="d-flex">
            <div className={showClass}>
                <section className="modal-main" >
                    < Button text={"Ja"} onClick={oldEnough} />
                    < Button text={"Nej"} onClick={redirectToBR} />
                </section>
            </div>
        </div>
    );
};

export default AgeVerification;