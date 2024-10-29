import React from 'react';

function ContactInfo() {
    return (
        <section>
            <h2>Kontaktoplysninger</h2>
            <form>
                <div className="form-group">
                    <input type="text" className="form-control mt-2" placeholder="Fornavn"/>
                    <input type="text" className="form-control mt-2" placeholder="Efternavn"/>
                    <input type="email" className="form-control" placeholder="Mail"/>
                    <input type="text" className="form-control mt-2" placeholder="Telefonnummer"/>
                </div>
                <h2>Levering</h2>
                <div className="form-group">
                    <input type="text" className="form-control" placeholder="Land/Område" />
                    <input type="text" className="form-control mt-2" placeholder="Adresse" />
                    <input type="text" className="form-control mt-2" placeholder="Postnummer" />
                    <input type="text" className="form-control mt-2" placeholder="By" />
                </div>
                <h2>Betaling</h2>
                <div className="form-group">
                    <input type="text" className="form-control" placeholder="Kortnummer" />
                    <input type="text" className="form-control mt-2" placeholder="Kortnummer" />
                    <input type="text" className="form-control mt-2" placeholder="Kortnummer" />
                </div>
                <button type="submit" className="btn btn-dark mt-3">Bekræft Køb</button>
            </form>
        </section>
    );
}

export default ContactInfo;