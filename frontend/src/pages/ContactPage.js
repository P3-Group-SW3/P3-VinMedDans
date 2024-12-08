import Footer from "../components/Footer";
import Header from "../components/Header";
import {CustomerLinks} from "./CustomerLinkContext";
import React from "react";

function ContactPage() {
    return (
        <div className="ContactPage">
            <Header
                links={CustomerLinks}
                showCart={true}
            />
            <div className="row align-items-center mb-4 my-5" >
                <div className={`col-lg-6 py-0`}>
                    <img
                        src='http://localhost:8080/api/images/image6.png'
                        alt='Vinflasker'
                        className="img-fluid mx-auto d-block"
                    />
                </div>
                <div className={`col-lg-6 text-center text-lg-start`}>
                    <h3 className="header-large pt-3"> Har du spørgsmål til os? </h3>
                    <div className="body-text gap-3">
                        Har du spørgsmål til os, vores process eller ønsker du som virksomhed at indgå et samarbejde
                        med os? Så kontakt os her:
                        <strong> +45 3117 5720 </strong>
                        <a href="mailto:vinmeddans@gmail.com"> vinmeddans@gmail.com </a>
                    </div>
                </div>
            </div>

            <Footer/>
        </div>
    );
}

export default ContactPage;
