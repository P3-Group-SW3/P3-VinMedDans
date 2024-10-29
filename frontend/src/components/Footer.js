import React from 'react';

function Footer() {
  return (
    <footer className="container-fluid bg-light py-4">
      <div className="row mx-5 justify-content-between">
        <FooterColumn items={["Instagram", "Telefonnummer", "Email"]} />
        <FooterColumn items={["Levering", "Handelsbetingelser", "Kontrolrapport"]} />
      </div>
    </footer>
  );
}

function FooterColumn({ items }) {
  return (
    <div className="col-md-auto">
      <ul className="list-unstyled">
        {items.map((item, index) => (
          <li key={index} className="py-2" >{item}</li>
        ))}
      </ul>
    </div>
  );
}

export default Footer;
