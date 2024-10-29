import React from "react";

function PaymentTerminal(){
    return(
        <section>
        <h2>Betaling</h2>
            <div className="form-group">
                <input type="text" className="form-control mt-2" placeholder="Kortnummer" />
                <input type="text" className="form-control mt-2" placeholder="Udløbsdato" />
                <input type="text" className="form-control mt-2" placeholder="Sikkerhedskode" />
            </div>
            <button type="submit" className="btn btn-dark mt-3">Bekræft Køb</button>
        </section>
)
}

export default PaymentTerminal;
