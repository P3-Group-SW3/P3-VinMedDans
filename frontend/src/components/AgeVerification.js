import React, {useEffect, useState} from "react";
import '../styles/modal.css'
import Button from "./Button";


const AgeVerification = () => {

    const [cookieAge, setCookieAge] = useState('');
    const [show, setShow] = useState(false);
    const showClass = show ? "age-modal display-block" : "age-modal display-none";

    useEffect(() => {
        fetch('api/createCookie')
            .then(response => console.log(response))
            .catch(error => console.error('Error fetching data: ', error))

        fetch('api/cookieAge')
            .then(response => response.text())
            .then(data => {
                setCookieAge(data);
                console.log("Fetched cookie age:", cookieAge);
                console.log("Type of data: ", typeof data);
                console.log("Fetched data:", data);
                if (data === 'new') {
                    setShow(true);
                } else if (data === 'old') {
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
        fetch('api/updateCookie')
            .then(response => console.log(response))
            .catch(error => console.error('Error fetching data: ', error))
        setShow(false);
        console.log("After press yes: ", show)
    }

    return (
        <div className={showClass} >
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title text-center">Er du 18 eller over?</h5>
                    </div>
                    <div className="modal-body">
                        <div className="d-flex justify-content-around">
                            < Button text={"Ja"} onClick={oldEnough} isWide={true}/>
                            < Button text={"Nej"} onClick={redirectToBR} isWide={true}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AgeVerification;