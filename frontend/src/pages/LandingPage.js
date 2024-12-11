import AgeVerification from "../components/AgeVerification";
import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import {CustomerLinks} from "./CustomerLinkContext";
import AlternatingItems from "../components/AlternatingItems";


function LandingPage() {

    const sections = [
        {
            title: 'Hvad er frugtvin?',
            description: `Frugtvin er en sjov størrelse, som mange endnu ikke har stiftet bekendtskab med, og det vil vi gerne ændre på. Betegnelsen for frugtvin er den gærede saft fra alle andre frugter end vindruer, såsom æbler, stikkelsbær, hindbær osv. Vores frugtvin er lavet på en base af æblemost, hvorefter vi tilføjer den ønskede frugt, som giver den sin karakteristiske smag. Ligesom traditionel vin har det en super kompleks og nuanceret smag med masser af forskellige noter, men det vi i sin tid faldt over, var at det kunne være så eksperimenterende. Vi elsker at udforske og lege med de forskellige smagssammensætninger, og se hvilken vej det tager os.`,
            imageURL: 'http://localhost:8080/api/images/image4.png'
        },
        {
            title: 'Jord til bord',
            description: `I Danmark har vi nogle af de lækreste frugter og bær i verden, som vokser frit i naturen. Hele grunden til at vi startede med at lave frugtvin var, at vi følte der var så meget frugt i den danske natur, som bare gik til spilde. Den ånd prøver vi at holde fast i, og bestræber os på at håndplukke så meget af frugten vi bruger, som muligt. Det bliver gjort forskellige steder på Sjælland i løbet af sensommeren og efteråret. Æblerne, som er grundstenen i alle vores vine, køber vi fra en gammel frugtplantage i Dyssegård, som hverken bliver beskåret eller sprøjtet.Det er vigtigt for os, at det produkt vi producerer, er rent lækkert. Vi gør det, for at have så lille en effekt på klimaet som muligt, ved at bruge den frugt som ellers ville rådne på træer og buske, hvis ikke vi havde plukket den. Det er en vigtig kerneværdi for os, og vil fortsat være en vigtig ting for os.`,
            imageURL: 'http://localhost:8080/api/images/image5.png'
        },
        {
            title: 'Køb vores vin her',
            description: `Du tror sikkert at frugtvin kun er et produkt til de smarte trendy mennesker fra storbyen, og der tager du fejl. Frugtvin er for alle. Det er for dig der mangler en sjov værtindegave, dig der er eventyrlysten, dig der vil prøve noget nyt, og dig der bare mangler noget lækkert at drikke en kedelig tirsdag aften.`,
            imageURL: 'http://localhost:8080/api/images/image6.png',
            buttonText: 'Gå til webshop',
            buttonPath: '/shop'
        }
    ]

    return (
        <div className="Landingpage">
            <AgeVerification/>
            <Header
                links={ CustomerLinks }
                showCart={true}
            />
            <AlternatingItems items={sections}/>
            <Footer />
        </div>
    );
}

export default LandingPage;
