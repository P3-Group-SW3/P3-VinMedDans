import React, { useState } from "react";
import '../styles/modal.css'
import Button from "./Button";


const AgeVerification = ({show}) => {

    const showHideClassName = show ? "modal display-block" : "modal display-none";
    
    const redirectToBR = () => {
        document.location = "https://www.br.dk/";
    }

    const oldEnough = () => {
        console.log("Yessss");
    }

    return (
        <div className="d-flex">
            <div className={showHideClassName}>
                <section className="modal-main" >
                    < Button text={"Ja"} onClick={oldEnough} />
                    < Button text={"Nej"} onClick={redirectToBR} />
                </section>
            </div>
        </div>
    );
};

export default AgeVerification;