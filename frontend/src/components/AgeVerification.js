import React, {useEffect, useState} from "react";
import '../styles/modal.css'
import Button from "./Button";

/*
 * Modal to verify the age of the customer.
 * Is only shown to visitors with no stored customer cookie.
 */
const AgeVerification = () => {

    //The state 'show' handles the visibility of the module
    const [show, setShow] = useState(false);

    //On mount: Create customer cookie if necessary and check if modal should be shown
    useEffect(() => {
        //Fetch createCookie API to create a customer cookie if there is none beforehand
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

        //Fetch cookieAge API to see if customer cookie is 'new' or 'old', set 'show' accordingly
        fetch('api/cookieAge')
            .then(response => response.json())
            .then(data => {
                if (data.cookieAge === 'new') {
                    setShow(true);
                } else if (data.cookieAge === 'old') {
                    setShow(false);
                } else {
                    console.warn("Invalid cookie age value:", data.cookieAge);
                }
            })
            .catch(error => console.error('Error fetching data: ', error));
    }, []);

    //Redirects the customer to BR's website if they declare that they are younger than 18
    const redirectToBR = () => {
        document.location = "https://www.br.dk/";
    }

    //Hides modal and sets cookieAge to 'old' if customer declares that they are 18 or older
    const ageVerified = () => {
        fetch('api/updateCookie')
            .then(response => console.log(response))
            .catch(error => console.error('Error fetching data: ', error))
        setShow(false);
    }
        //Show modal based on the value of 'show'
        if (show) {
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