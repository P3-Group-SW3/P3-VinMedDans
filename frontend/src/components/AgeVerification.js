import React, {useEffect, useState} from "react";
import '../styles/modal.css'
import Button from "./Button";


const AgeVerification = () => {

    const [show, setShow] = useState(false);

    useEffect(() => {
        fetch('api/createCookie')
            .then(response => {
                if (response.ok) {
                    console.log('Cookie successfully created!');
                } else {
                    console.warn('Failed to create cookie. Status:', response.status);
                }
            })
            .catch(error => {
                console.error('Error creating cookie:', error);
            });

        fetch('api/cookieAge')
            .then(response => response.json())
            .then(data => {
                if (data.cookieAge === 'new') {
                    setShow(true);
                    console.log("New cookie! Show: ", show);
                } else if (data.cookieAge === 'old') {
                    setShow(false);
                    console.log("Old cookie... Show: ", show);
                } else {
                    console.warn("Invalid cookie age value:", data.cookieAge);
                }
            })
            .catch(error => console.error('Error fetching data: ', error));
    }, []);

    useEffect(() => {
        console.log("Show state updated:", show);
    }, [show]);

    const redirectToBR = () => {
        document.location = "https://www.br.dk/";
    }

    const ageVerified = () => {
        fetch('api/updateCookie')
            .then(response => console.log(response))
            .catch(error => console.error('Error fetching data: ', error))
        setShow(false);
        console.log("After press yes: ", show)
    }

        if (show) {
            console.log("AgeVerification is shown");
            return (
                <div className='modal show' style={{backdropFilter: 'blur(15px)'}}>
                    <div className="modal-dialog modal-dialog-centered">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title text-center">Er du 18 eller over?</h5>
                            </div>
                            <div className="modal-body">
                                <div className="d-flex justify-content-around">
                                    <Button text={"Ja"} onClick={ageVerified} isWide={true}/>
                                    <Button text={"Nej"} onClick={redirectToBR} isWide={true}/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )
        } else {
            return null;
        }
};

export default AgeVerification;