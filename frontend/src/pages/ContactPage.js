import Footer from "../components/Footer";
import Navbar from "../components/Navbar";
import Header from "../components/Header";
import images from "../images/images";

function ContactPage() {
    return (
        <div className="ContactPage">
            <Header />
            <Navbar />

            <div className="contact-content container my-5">
                <div className="row">
                    <div className="col-md-6 d-flex justify-content-center align-items-center">
                        <img
                            src= {images.image6}
                            alt="Vinflasker"
                            className="img-fluid"
                            style={{ maxWidth: "80%" }}
                        />
                    </div>
                    <div className="col-md-6">
                        <h2 >Har du spørgsmål til os?</h2>
                        <p>
                            Har du spørgsmål til os, vores process eller ønsker du som virksomhed at indgå et samarbejde med os? Så kontakt os her:
                        </p>
                        <p><strong>+45 3117 5720</strong></p>
                        <p><a href="mailto:vinmeddans@gmail.com">vinmeddans@gmail.com</a></p>
                    </div>
                </div>
            </div>

            <Footer />
        </div>
    );
}

export default ContactPage;