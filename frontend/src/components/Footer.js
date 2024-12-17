import React from 'react';

function Footer() {
    return (
        <footer className="container-fluid py-4" style={{backgroundColor: '#C0924D'}}>
            <div className="row justify-content-around">
                <FooterLinks items={["Instagram", "Levering"]}/>
                <FooterLinks items={["Email", "Betingelser og vilkår"]}/>
                <FooterLinks items={["Telefonnummer", "Kontrolrapport"]}/>
            </div>
        </footer>
    );
}

function FooterLinks({ items }) {
    const links = {
        "Instagram": "https://www.instagram.com/vinmeddans/",
        "Email": "mailto:vinmeddans@gmail.com",
        "Levering": "#",
        "Betingelser og vilkår": "#",
        "Telefonnummer": "tel:+4531175720",
        "Kontrolrapport": "https://www.findsmiley.dk/1259568"
    };

    return (
        <div className="col-4">
            <ul className="list-unstyled text-center">
                {items.map((item, index) => (
                    <li key={index} style={{ fontFamily: 'Rubik', color: 'white', marginBottom: '1em' }}>
                        {links[item] ? (
                            <a href={links[item]} style={{ color: 'white', textDecoration: 'none' }}>
                                {item}
                            </a>
                        ) : (
                            item
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Footer;