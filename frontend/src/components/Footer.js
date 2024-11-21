import React from 'react';

function Footer() {
  return (
      <footer className="container-fluid py-4" style={{backgroundColor: '#C0924D'}}>
          <div className="row justify-content-around">
              <FooterColumn items={["Instagram", "Levering"]}/>
              <FooterColumn items={["Email", "Betingelser og vilkår"]}/>
              <FooterColumn items={["Telefonnummer", "Kontrolrapport"]}/>
          </div>
      </footer>
  );
}

function FooterColumn({items}) {
    return (
        <div className="flex-column">
            <ul className="list-unstyled text-center">
                {items.map((item, index) => (
                <li key={index} style={{ fontFamily: 'Rubik', color: 'white', marginBottom: '1em' }}>{item}</li>
                ))}
            </ul>
        </div>
  );
}

export default Footer;
